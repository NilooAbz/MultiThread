import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by Niloofar on 7/20/2016.
 */
public class Server extends Thread {

    private Integer port;
    private String outLog;
    private List<Deposit> deposits;
    private Socket connection;
    private ObjectInputStream in;
    private ObjectOutputStream out;


    public Socket getConnection() {
        return connection;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getOutLog() {
        return outLog;
    }

    public void setOutLog(String outLog) {
        this.outLog = outLog;
    }


    public void setDeposits(List<Deposit> deposits) {this.deposits = deposits;}

    public void run() {
        try {
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            while (true) {
                try {
                    System.out.println("wait for data");
                    Transaction transaction = (Transaction) in.readObject();

                    System.out.println("client>" + transaction.getTransactionType());
                    out.writeObject("the type is: " + transaction.getTransactionType());

                    System.out.println("client>" + transaction.getDeposit());
                    out.writeObject("deposit of transaction is: " + transaction.getDeposit());

                    System.out.println("client>" + transaction.getTransactionAmount());
                    out.writeObject("transaction amount is: " + transaction.getTransactionAmount());

                    System.out.println("client>" + transaction.getTransactionId());
                    out.writeObject("transaction id is: " + transaction.getTransactionId());

                    for (Deposit deposit: deposits) {
                        if(transaction.getDeposit().equals(deposit.getId())){
                            if (transaction.getTransactionType().equals("deposit")){
                                deposit.depositVerb(transaction.getTransactionAmount());
                            }//deposit = withdraw
                            else {
                                deposit.withdraw(transaction.getTransactionAmount());
                            }
                        }else {
                            System.out.println("deposit ID's is not matched");
                        }
                        //System.out.println("new intialBalance for depositId: " + deposit.getId() + " = " + deposit.depositVerb(transaction.getTransactionAmount()));
                    }

                    if (transaction.getTransactionType().equals("")) {
                        break;
                    }
                //always read the input until reached to the end of the file or stream , so close it with this exception:
                } catch (EOFException e) {

                    in.close();
                    out.close();
                    connection.close();
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            /*try (ObjectInputStream in = new ObjectInputStream(connection.getInputStream())){
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
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {

        Server server = JsonParser.parse();

        ServerSocket serverSocket = new ServerSocket(server.getPort());
        serverSocket.setSoTimeout(10000);
        System.out.println("Waiting for connection");
        Socket socket = serverSocket.accept();
        server.setConnection(socket);
        server.run();

    }


}
