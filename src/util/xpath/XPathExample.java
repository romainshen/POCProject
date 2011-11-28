package util.xpath;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.boc.itm.utils.SoapApi;

import ch.qos.logback.classic.Logger;


public class XPathExample {
	private static String strXML = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<inventory>" +
			"<book year=\"2000\">" +
			"<title>Snow Crash</title>" +
			"<author>Neal Stephenson</author>" +
			"<publisher>Spectra</publisher>" +
			"<isbn>0553380958</isbn>" +
			"<price>14.95</price>" +
			"</book>" +
			
			"<book year=\"2005\">" +
			"<title>Burning Tower</title>" +
			"<author>Larry Niven</author>" +
			"<author>Jerry Pournelle</author>" +
			"<publisher>Pocket</publisher>" +
			"<isbn>0743416910</isbn>" +
			"<price>5.99</price>" +
			"</book>" +
			
			"<book year=\"1995\">" +
			"<title>Zodiac</title>" +
			"<author>Neal Stephenson</author>" +
			"<publisher>Spectra</publisher>" +
			"<isbn>0553573862</isbn>" +
			"<price>7.50</price>" +
			"</book>" +
			"</inventory>";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//factory.setNamespaceAware(true);
			DocumentBuilder builder;
			
			builder = factory.newDocumentBuilder();
			//System.out.println(strXML);
//			Document doc = builder.parse("src/xpath/inputfile.xml");
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("util/xpath/inputfile.xml");
			Document doc = builder.parse(in);
			
			//Document doc = builder.parse(new InputSource(new ByteArrayInputStream(strXML.getBytes())));
			
			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xpath = xFactory.newXPath();
			//"//book[author='Neal Stephenson']/price/text()"
			//XPathExpression expr = xpath.compile("//title/text()");
			XPathExpression expr = xpath.compile("//NAME/text()");
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			for (int i = 0; i < nodes.getLength(); i++) {
				System.out.println(nodes.item(i).getNodeName() + ":" + nodes.item(i).getNodeValue());
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
