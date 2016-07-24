import exceptions.DepositNotExistException;
import exceptions.InitialBalancePassedUpperBoundException;
import exceptions.InitialBalanceBecameZeroException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by Niloofar on 7/20/2016.
 */
public class Server implements Serializable ,Runnable {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ServerData serverData;


    public Server(ServerData serverData, Socket socket) {
        this.serverData = serverData;
        this.socket = socket;
    }

    public Server() {
    }

    public static void main(String[] args) throws IOException {

        ServerData serverData = JsonParser.parse();

        ServerSocket serverSocket = new ServerSocket(serverData.getPort());
        while(true) {
            System.out.println("Waiting for socket");
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
            System.out.println("Connection received from " + socket.getInetAddress().getHostName());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            while (true) {
                try {
                    System.out.println("wait for data");
                    Transaction transaction = (Transaction) in.readObject();

                    System.out.println("client>" + transaction.getTransactionType());

                    System.out.println("client>" + transaction.getDeposit());

                    System.out.println("client>" + transaction.getTransactionAmount());

                    System.out.println("client>" + transaction.getTransactionId());

                    boolean findDeposit = false;
                    try{

                        for (Deposit deposit : serverData.getDeposits()) {
                            if (transaction.getDeposit().equals(deposit.getId())) {
                                if (transaction.getTransactionType().equals("deposit")) {
                                    deposit.depositVerb(transaction.getTransactionAmount());
                                }//deposit = withdraw
                                else {
                                    deposit.withdraw(transaction.getTransactionAmount());
                                }
                                findDeposit = true;
                                out.writeObject("Transaction done");
                                break;
                            }
                        }
                        if (!findDeposit) {
                            System.out.println("deposit ID's is not matched");

                        }
                    }catch (DepositNotExistException | InitialBalancePassedUpperBoundException | InitialBalanceBecameZeroException e){
                        out.writeObject(e.getMessage());
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

            /*try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
                System.out.println("wait for data");
                Transaction transaction = (Transaction) in.readObject();

                System.out.println("client>" + transaction.getTransactionType());
                out.writeObject("get the type");

            }catch (IOException e){
                e.printStackTrace();
            }*/

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
    }


}
