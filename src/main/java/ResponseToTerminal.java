import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;


/**
 * Created by DotinSchool2 on 7/25/2016.
 */

public class ResponseToTerminal {


    //public void saveXML(String terminalId, String transactionId, String messageResponse) throws ParserConfigurationException, TransformerException {
    static void saveXML(String terminalId, String transactionId, String messageResponse) throws ParserConfigurationException, TransformerException, IOException, SAXException {


        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document;
        Element root;
        File varTmpDir = new File("response.xml");

        boolean fileExist = varTmpDir.exists();
        if (!fileExist) {
            document = documentBuilder.newDocument();
            root = document.createElement("Responses");
            document.appendChild(root);
        } else {

            document = documentBuilder.parse("response.xml");
            root = document.getDocumentElement();
        }



        // root elements
        Element newResponseElement = document.createElement("Response");
        root.appendChild(newResponseElement);

        // TerminalId elements
        Element newTerminalElement = document.createElement("Terminal");
        newResponseElement.appendChild(newTerminalElement);

        Attr attr = document.createAttribute("id");
        attr.setValue(terminalId);
        newTerminalElement.setAttributeNode(attr);

        // transactionId elements
        Element transaction = document.createElement("transactionId");
        transaction.appendChild(document.createTextNode(transactionId));
        newTerminalElement.appendChild(transaction);

        // message elements
        Element message = document.createElement("message");
        message.appendChild(document.createTextNode(messageResponse));
        newTerminalElement.appendChild(message);



//        // write the content into xml file
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        DOMSource source = new DOMSource(document);
//        StreamResult result = new StreamResult("response.xml");
//
//
        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);
//
//        transformer.transform(source, result);



        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult("response.xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        transformer.transform(source, result);


        System.out.println("File saved!");


    }
}
