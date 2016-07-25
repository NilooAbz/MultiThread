import exceptions.DepositNotExistException;
import exceptions.InitialBalancePassedUpperBoundException;
import exceptions.InitialBalanceBecameZeroException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Niloofar on 7/20/2016.
 */
public class Server implements Serializable ,Runnable {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ServerData serverData;
    Terminal terminal;

    public Server(ServerData serverData, Socket socket) {
        this.serverData = serverData;
        this.socket = socket;
    }

    public Server() {
    }


    Logger serverOutLog = Logger.getLogger("serverOutLog");

    public static void main(String[] args) throws IOException {

        ServerData serverData = JsonParser.parse();

        Logger serverOutLog = Logger.getLogger("serverOutLog");
        System.out.println(serverData);
        System.out.println(serverData.getOutLog());
        FileHandler fileHandler = new FileHandler(serverData.getOutLog());
        serverOutLog.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        serverOutLog.info("Start logging");

        ServerSocket serverSocket = new ServerSocket(serverData.getPort());
        while(true) {
            serverOutLog.info("Waiting for socket");
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            Socket socket = serverSocket.accept();
//            server.setSocket(socket);
            new Thread( new Server(serverData , socket)).start();
//            server.run();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            serverOutLog.info("Connection received from " + socket.getInetAddress().getHostAddress());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            while (true) {
                try {
                    serverOutLog.info("wait for data");
                    Transaction transaction = (Transaction) in.readObject();

                    serverOutLog.info("client transaction type is >" + transaction.getTransactionType());
                    //terminal.terminalOutLog.info("declare the type");
                    serverOutLog.info("client transaction deposit is >" + transaction.getDeposit());

                    serverOutLog.info("client transaction amount is >" + transaction.getTransactionAmount());

                    serverOutLog.info("client transaction id is >" + transaction.getTransactionId());

                    boolean findDeposit = false;
                    try{
                        for (Deposit deposit : serverData.getDeposits()) {
                            if (transaction.getDeposit().equals(deposit.getId())) {
                                if (transaction.getTransactionType().equals("deposit")) {
                                    deposit.depositVerb(transaction.getTransactionAmount());
                                    serverOutLog.info("Terminal with deposit number " + transaction.getDeposit() + "is requesting for deposit");
                                }//deposit = withdraw
                                else {
                                    deposit.withdraw(transaction.getTransactionAmount());
                                    serverOutLog.info("Terminal with deposit number " + transaction.getDeposit() + "is requesting for withdraw");
                                    out.writeObject("Terminal with deposit number " + transaction.getDeposit());
//                                    +"and terminal id "+
//                                            terminal.getId() + "is requesting for withdraw");
                                }
                                findDeposit = true;
                                out.writeObject("transaction done.\n " + "Now initial value for deposit number " + transaction.getDeposit()+
                                        "is: " + deposit.getInitialBalance());
                                serverOutLog.info("Transaction done");
                                break;
                            }
                        }
                        if (!findDeposit) {
                            //System.out.println("deposit ID's is not matched");
                            serverOutLog.info("deposit ID's is not matched");

                        }
                    } catch (DepositNotExistException e){
                        out.writeObject(e.getMessage());
                        //terminal.terminalOutLog.info(e.getMessage());
                        serverOutLog.info(e.getMessage());
                    } catch (InitialBalancePassedUpperBoundException e){
                        out.writeObject(e.getMessage());
                        //terminal.terminalOutLog.info(e.getMessage());
                        serverOutLog.info(e.getMessage());
                    } catch (InitialBalanceBecameZeroException e){
                        out.writeObject(e.getMessage());
                        //terminal.terminalOutLog.info(e.getMessage());
                        serverOutLog.info(e.getMessage());
                    }

                    if (transaction.getTransactionType().equals("")) {
                        break;
                    }
                    //always read the input until reached to the end of the file or stream , so close it with this exception:
                } catch (EOFException e) {

                    in.close();
                    out.close();
                    socket.close();
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
    }
}
