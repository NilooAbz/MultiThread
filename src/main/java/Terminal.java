import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Niloofar on 7/20/2016.
 */
public class Terminal extends Thread implements Serializable{

    private String terminalId;
    private String terminalType;
    private String serverIP;
    private Integer serverPort;
    private String path;
    private List<Transaction> transactions;
    private ResponseToTerminal response;

    private static Logger terminalOutLog  = Logger.getLogger("terminalOutLog");

    static Terminal terminal;
    public static void main(String[] args) {
        String filePath = args[0];
        System.out.println("##################################"+filePath);


        try {
            terminal = XMLParser.Parse(filePath);
            //terminal.setResponse(new ResponseToTerminal("response.xml"));
            FileHandler fileHandler = new FileHandler(terminal.getPath());
            terminalOutLog.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            terminalOutLog.info("Start logging");

            terminal.run();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }



    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
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

    public ResponseToTerminal getResponse() {
        return response;
    }

    public void setResponse(ResponseToTerminal response) {
        this.response = response;
    }

    public void setPath(String path) {this.path = path;}

    //client socket
    public void run(){

        try {
            terminalOutLog.info("Terminal is trying to make a connection to specific server");
            Socket client = new Socket(getServerIP(), getServerPort());
            terminalOutLog.info("terminal and server is connected now.");
            ObjectInputStream inFromServer = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
            outToServer.flush();
            try {

                //Object message = new ArrayList<>();
                for (Transaction transaction : transactions) {
                    outToServer.writeObject(transaction);
                    String message = inFromServer.readObject().toString();
                    //new ResponseToTerminal(getTerminalId(), transaction.getTransactionId(), message );
                    terminalOutLog.info("server>" + message);
                    response.saveXML(getTerminalId(), transaction.getTransactionId(), message );
                }
                //response.saveXML(getTerminalId(), transaction.getTransactionId(), message );

                inFromServer.close();
                outToServer.close();
                client.close();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
