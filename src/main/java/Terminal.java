import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

/**
 * Created by Niloofar on 7/20/2016.
 */
public class Terminal implements Serializable{

    private Integer terminalId;
    private String terminalType;
    private String serverIP;
    private Integer serverPort;
    private String path;
    private List<Transaction> transactions;

    Terminal(){}


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {this.path = path;}

    //client socket
    void run(){

        try {
            Socket client = new Socket(getServerIP(), getServerPort());
            ObjectInputStream inFromServer = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
            outToServer.flush();
            try {
                for (Transaction transaction : transactions) {
                    outToServer.writeObject(transaction);
                    System.out.println("server>" + inFromServer.readObject());
                }
                inFromServer.close();
                outToServer.close();
                client.close();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        String filePath =  "terminal2.xml";
        Terminal terminal = XMLParser.Parse(filePath);
        terminal.run();
    }
}
