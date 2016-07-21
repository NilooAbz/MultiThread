import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Niloofar on 7/20/2016.
 */
public class Server extends Thread {

    private Integer port;
    private String outLog;
    private Deposit deposit;
    private Socket connection;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public static void main(String[] args) throws IOException {

        Server server = JsonParser.parse();
        //multi thread

        ServerSocket serverSocket = new ServerSocket(server.getPort());
        System.out.println("Waiting for connection");
        Socket socket = serverSocket.accept();
        server.setConnection(socket);
        server.run();

        //single thread

        /*while(true){
            server.run();
        }*/

    }

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

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }

    public void run() {
        try {
            //single thread
            /*ServerSocket serverSocket = new ServerSocket(getServerPort());
            System.out.println("Waiting for connection");
            connection = serverSocket.accept();
*/
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            while (true) {
                try {
                    System.out.println("wait for data");
                    Transaction transaction = (Transaction) in.readObject();

                    System.out.println("client>" + transaction.getTransactionType());
                    out.writeObject("Ok");

                    if (transaction.getTransactionType().equals("")) {
                        break;
                    }

                } catch (EOFException e) {

                    in.close();
                    out.close();
                    connection.close();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
}
