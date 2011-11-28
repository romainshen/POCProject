package util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.map.MapToObject;

import com.boc.tsam.xml.EntityUtility;


public class XMLParserTest {
	private static Logger logger = LoggerFactory.getLogger(XMLParserTest.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("XMLParserTest start!");
		
		try {
			ClassLoader loader = XMLParserTest.class.getClassLoader();  
			InputStream inputStream = loader.getResourceAsStream("response.xml");
			
			
//			StringWriter writer = new StringWriter();
//			IOUtils.copy(inputStream, writer, "UTF-8");
//			String theString = writer.toString();
//			logger.debug("Method1 result: " + theString);
//			
//			
//			String result2 = IOUtils.toString(inputStream,"UTF-8");
//			logger.debug("Method2 result: " + result2);
			
			
			HashMap map = EntityUtility.xmlParse(inputStream);
			logger.debug(map.toString());
			//printMapAttr("root",map);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		

	}
	public static void printMapAttr(Object inKey, Map inMap){
		logger.debug(inKey.toString() + inMap);
		for(Object key : inMap.keySet()){
			
			Object value = inMap.get(key);
			if (value instanceof String){
				logger.debug(inKey + "-" + key + " : " + value);
			}else if(value instanceof List){
				printListAttr(inKey + "-" + key,(List)value);
			}else if(value instanceof Map){
				printMapAttr(inKey + "-" + key, (Map)value);
				
			}
	    }
	}
	
	private static void printListAttr(Object inKey, List inList) {
		logger.debug(inKey.toString());
		for (Iterator iterator = inList.iterator(); iterator.hasNext();) {
			Object value = (Object) iterator.next();
			if (value instanceof String){
				logger.debug(inKey + " : " + value);
			}else if(value instanceof List){
				printListAttr(inKey + "-",(List)value);
			}else if(value instanceof Map){
				printMapAttr(inKey + "-", (Map)value);
			}
		}
	}
}
