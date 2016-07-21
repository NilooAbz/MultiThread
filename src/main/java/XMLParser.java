import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niloofar on 7/20/2016.
 */
public class XMLParser {

    public static Terminal Parse() throws ParserConfigurationException, IOException, SAXException {
        File file = new File("terminal.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("terminal");
        Node terminalNode = nList.item(0);

        Terminal terminal = new Terminal();


        System.out.println("\nCurrent Element :" + terminalNode.getNodeName());
        if (terminalNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) terminalNode;
            Integer id = Integer.parseInt(eElement.getAttribute("id"));
            System.out.println(id);
            String type = eElement.getAttribute("type");
            System.out.println(type);

            terminal.setTerminalId(id);
            terminal.setTerminalType(type);
        }


        Node serverElement = doc.getElementsByTagName("server").item(0);

        System.out.println("\nCurrent Element :" + serverElement.getNodeName());
        if (serverElement.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) serverElement;
            String ip = eElement.getAttribute("ip");
            System.out.println(ip);
            Integer port = Integer.parseInt(eElement.getAttribute("port"));
            System.out.println(port);

            terminal.setServerIP(ip);
            terminal.setServerPort(port);
        }

        Node outLogElement = doc.getElementsByTagName("outLog").item(0);


        System.out.println("\nCurrent Element :" + outLogElement.getNodeName());
        if (outLogElement.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) outLogElement;
            String path = eElement.getAttribute("path");
            System.out.println(path);

            terminal.setPath(path);
        }


        NodeList transactionNodeList = doc.getElementsByTagName("transaction");

        List<Transaction> transactions = new ArrayList<Transaction>();
        for (int temp = 0; temp < transactionNodeList.getLength(); temp++) {
            Node transactionNode = transactionNodeList.item(temp);
            System.out.println("\nCurrent Element :" + transactionNode.getNodeName());
            if (transactionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) transactionNode;
                Integer id = Integer.parseInt(eElement.getAttribute("id"));
                System.out.println(id);
                String type = eElement.getAttribute("type");
                System.out.println(type);
                BigDecimal amount = new BigDecimal(eElement.getAttribute("amount"));
                System.out.println(amount);
                String deposit = eElement.getAttribute("deposit");
                System.out.println(deposit);

                Transaction transaction = new Transaction();

                transaction.setTransactionId(id);
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
