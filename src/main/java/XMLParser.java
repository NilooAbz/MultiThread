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
        nList.item(0);

        Terminal terminal = new Terminal();

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Integer id = Integer.parseInt(eElement.getAttribute("id"));
                System.out.println(id);
                String type = eElement.getAttribute("type");
                System.out.println(type);

                terminal.setTerminalId(id);
                terminal.setTerminalType(type);
            }
        }

        NodeList serverList = doc.getElementsByTagName("server");
        nList.item(0);

        for (int temp = 0; temp < serverList.getLength(); temp++) {
            Node serverNode = serverList.item(temp);
            System.out.println("\nCurrent Element :" + serverNode.getNodeName());
            if (serverNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) serverNode;
                String ip = eElement.getAttribute("ip");
                System.out.println(ip);
                Integer port = Integer.parseInt(eElement.getAttribute("port"));
                System.out.println(port);

                terminal.setIp(ip);
                terminal.setPort(port);
            }
        }

        NodeList outLogList = doc.getElementsByTagName("outLog");
        nList.item(0);

        for (int temp = 0; temp < outLogList.getLength(); temp++) {
            Node outLogNode = outLogList.item(temp);
            System.out.println("\nCurrent Element :" + outLogNode.getNodeName());
            if (outLogNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) outLogNode;
                String path =eElement.getAttribute("path");
                System.out.println(path);

                terminal.setPath(path);
            }
        }

        NodeList transactionNodeList = doc.getElementsByTagName("transaction");
        nList.item(0);

        List<Transaction> transactionList = new ArrayList<Transaction>();
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

                transactionList.add(transaction);
            }
        }
        return terminal;
    }
}
