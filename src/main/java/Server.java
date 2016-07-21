import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Niloofar on 7/20/2016.
 */
public class Server extends Thread{

    private Integer port;
    private String outLog;
    private Deposits deposit;
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

    public String getOutLog() {return outLog;}

    public void setOutLog(String outLog) {this.outLog = outLog;}

    public Deposits getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposits deposit) {
        this.deposit = deposit;
    }


    public void run(){
        try {
            //single thread
            /*ServerSocket serverSocket = new ServerSocket(getPort());
            System.out.println("Waiting for connection");
            connection = serverSocket.accept();
*/
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());

            try {
                System.out.println("client>" + in.readObject());
                out.writeObject("recived data");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
                out.close();
                //connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
}
