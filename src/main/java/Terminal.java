import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Niloofar on 7/20/2016.
 */
public class Terminal extends Thread implements Serializable{

    private Integer terminalId;
    private String terminalType;
    private String serverIP;
    private Integer serverPort;
    private String path;
    private List<Transaction> transactions;
    ResponseToTerminal response;

    Logger terminalOutLog  = Logger.getLogger("terminalLog");
    public static void main(String[] args) {
        String filePath =args[0];
        System.out.println("##################################"+filePath);

        Terminal terminal;
        try {
            terminal = XMLParser.Parse(filePath);
            terminal.setResponse(new ResponseToTerminal(filePath));
            Logger terminalOutLog = Logger.getLogger("terminalOutLog");
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
                for (Transaction transaction : transactions) {
                    outToServer.writeObject(transaction);
                    String message = inFromServer.readObject().toString();
                    response.saveToXML(transaction , message,transaction.getTransactionId());
                    terminalOutLog.info("server>" + message);


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


}
