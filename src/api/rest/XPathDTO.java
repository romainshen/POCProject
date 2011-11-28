package api.rest;

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

public class XPathDTO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RESTInterface restInf = new RESTInterface();
//		String strReq1 = "https://3.3.3.65:9443/maxrest/rest/os/SRM_SR?_format=json&_compact=1&_rootonly=1&_keys=1&_maxItems=1&_fd=MEP_SRAPPRLIST";
		String strReq2 = "https://3.3.3.65:9443/maxrest/rest/os/OSTPSERVER?_exactmatch=1&name=LPAR003003003144";
//		String strReq3 = "myhttps://3.3.3.65/maxrest/rest/os/SRM_SR?_format=json&_compact=1&_rootonly=1&_keys=1&_maxItems=1&_fd=MEP_SRAPPRLIST";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
		
			//Document doc = builder.parse("XmlFile/books.xml");
			InputStream is = restInf.postSSLREST(strReq2,"GET");
			Document doc = builder.parse(is);
			
			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xpath = xFactory.newXPath();
			String strExpr = "/QueryOSTPSERVERResponse/OSTPSERVERSet/TPSERVER/NAME/text()";
//			XPathExpression expr = xpath.compile("//book[author='Neal Stephenson']/price/text()");
			XPathExpression expr = xpath.compile(strExpr);
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			for (int i = 0; i < nodes.getLength(); i++) {
				System.out.println(nodes.item(i).getNodeValue());
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
