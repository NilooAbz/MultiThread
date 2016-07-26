import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;


/**
 * Created by DotinSchool2 on 7/25/2016.
 */

public class ResponseToTerminal {


    //public void saveXML(String terminalId, String transactionId, String messageResponse) throws ParserConfigurationException, TransformerException {
    public ResponseToTerminal(String terminalId, String transactionId, String messageResponse) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document document = docBuilder.newDocument();
        Element rootElement = document.createElement("Response");
        document.appendChild(rootElement);

        // TerminalId elements
        Element terminal = document.createElement("Terminal");
        rootElement.appendChild(terminal);

        Attr attr = document.createAttribute("id");
        attr.setValue(terminalId);
        terminal.setAttributeNode(attr);

        // transactionId elements
        Element transaction = document.createElement("transactionId");
        transaction.appendChild(document.createTextNode(transactionId));
        terminal.appendChild(transaction);

        // message elements
        Element message = document.createElement("message");
        message.appendChild(document.createTextNode(messageResponse));
        terminal.appendChild(message);

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("response.xml"));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);

        System.out.println("File saved!");

    }
}
