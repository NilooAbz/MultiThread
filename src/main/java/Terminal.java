import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Niloofar on 7/20/2016.
 */
public class Terminal {

    private Integer terminalId;
    private String terminalType;
    private String ip;
    private Integer port;
    private String path;
    Transaction transaction;

    Socket client;
    ObjectInputStream inFromServer;
    ObjectOutputStream outToServer;
    Terminal(){}


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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {this.path = path;}

    //client socket
    void run(){

        try {
            client = new Socket(getIp(),getPort());
            inFromServer = new ObjectInputStream(client.getInputStream());
            outToServer = new ObjectOutputStream(client.getOutputStream());
            outToServer.flush();
            try {
                outToServer.writeObject("hura vasl shodam");
                System.out.println("server>" + inFromServer.readObject());
                client.close();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        Terminal terminal = XMLParser.Parse();
        terminal.run();
    }
}
