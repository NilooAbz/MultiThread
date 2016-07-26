import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niloofar on 7/20/2016.
 */
class XMLParser {

    static Terminal Parse(String filePath) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList terminalNodeList = doc.getElementsByTagName("terminal");
        Node terminalNode = terminalNodeList.item(0);

        Terminal terminal = new Terminal();

        System.out.println("\nCurrent Element :" + terminalNode.getNodeName());
        if (terminalNode.getNodeType() == Node.ELEMENT_NODE) {
            Element terminalElement = (Element) terminalNode;
            String id = terminalElement.getAttribute("id");
            System.out.println(id);
            String type = terminalElement.getAttribute("type");
            System.out.println(type);

            terminal.setTerminalId(id);
            terminal.setTerminalType(type);
        }


        Node serverNode = doc.getElementsByTagName("server").item(0);

        System.out.println("\nCurrent Element :" + serverNode.getNodeName());
        if (serverNode.getNodeType() == Node.ELEMENT_NODE) {
            Element serverElement = (Element) serverNode;
            String ip = serverElement.getAttribute("ip");
            System.out.println(ip);
            Integer port = Integer.parseInt(serverElement.getAttribute("port"));
            System.out.println(port);

            terminal.setServerIP(ip);
            terminal.setServerPort(port);
        }

        Node outLogNode = doc.getElementsByTagName("outLog").item(0);

        System.out.println("\nCurrent Element :" + outLogNode.getNodeName());
        if (outLogNode.getNodeType() == Node.ELEMENT_NODE) {
            Element outLogElement = (Element) outLogNode;
            String path = outLogElement.getAttribute("path");
            System.out.println(path);

            terminal.setPath(path);
        }


        NodeList transactionNodeList = doc.getElementsByTagName("transaction");

        List<Transaction> transactions = new ArrayList<Transaction>();
        for (int temp = 0; temp < transactionNodeList.getLength(); temp++) {
            Node transactionNode = transactionNodeList.item(temp);
            System.out.println("\nCurrent Element :" + transactionNode.getNodeName());
            if (transactionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element transactionElement = (Element) transactionNode;
                String id = transactionElement.getAttribute("id");
                System.out.println(id);
                String type = transactionElement.getAttribute("type");
                System.out.println(type);
                BigDecimal amount = new BigDecimal(transactionElement.getAttribute("amount"));
                System.out.println(amount);
                String deposit = transactionElement.getAttribute("deposit");
                System.out.println(deposit);

                Transaction transaction = new Transaction();
                //ResponseTransaction responseTransaction = new ResponseTransaction();

                transaction.setTransactionId(id);
                //responseTransaction.setTransactionId();
                transaction.setTransactionType(type);
                transaction.setTransactionAmount(amount);
                transaction.setDeposit(deposit);




                transactions.add(transaction);
            }
        }
        terminal.setTransactions(transactions);
        return terminal;


    }

}
