package api.soap;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SOAPInterface {
	private static Logger logger = LoggerFactory.getLogger(SOAPInterface.class);
	private static HttpClient httpclient;
	private static MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager;
	static {
		multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();
		
		httpclient = new HttpClient(multiThreadedHttpConnectionManager);
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false); 
	}

	public static HttpClient getinstance() {
		if (multiThreadedHttpConnectionManager == null) {
			multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();
		}
		if (httpclient == null) {
			httpclient = new HttpClient(multiThreadedHttpConnectionManager);
			httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false); 
		}
		return httpclient;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String strURL = "http://3.3.3.63:1920///cms/soap";
		String strReq1 = "<CT_Get>      <userid>sysadmin</userid>      <password>tiv0li</password>      <table>KPH.KPH10MANAL</table>      <sql>SELECT CEC_NAME, LPAR_NAME, PI, E FROM KPH.KPH10MANAL AT ('itm_test') WHERE SYSTEM.PARMA('NODELIST','*HMC_BASE',9) AND SYSTEM.PARMA('TIMEOUT','120',3) AND STATE=2 ORDER BY CEC_NAME,LPAR_NAME</sql>    </CT_Get>";
		String strReq2 = "<CT_Get>      <userid>sysadmin</userid>      <password>tiv0li</password>      <table>KPK.KPK02GLOBA</table>      <sql>SELECT ORIGINNODE, NAME, NOP, CPU_TOTAL, CA, CU, MTM, MAM, MUM, MACHINE_ID FROM KPK.KPK02GLOBA AT ('itm_test') WHERE SYSTEM.PARMA('NODELIST','*CEC_BASE',9) AND SYSTEM.PARMA('TIMEOUT','30',2)</sql>    </CT_Get>";
		String strReq3 = "<CT_Get>      <userid>sysadmin</userid>      <password>tiv0li</password>      <table>KPX.KPX14LOGIC</table>      <sql>SELECT MACHINE_ID, HOSTNAME, LN, ORIGINNODE FROM KPX.KPX14LOGIC AT ('itm_test') WHERE SYSTEM.PARMA('NODELIST','*AIX_PREMIUM',13) AND SYSTEM.PARMA('TIMEOUT','30',2)</sql>    </CT_Get>";

		postRequest(strReq1, strURL);
		
		postSOAP(strURL, strReq2);

		
	}
	
	
	public static String postRequest(String content, String url) {
		// String strURL = "http://3.3.3.63:1920///cms/soap";
		String strURL = url;
		PostMethod post = null;
		String resBody = null;
		try {
			post = new PostMethod(strURL);
			post.setRequestBody(content);
			post.setRequestHeader("Content-Type", "text/xml");
			//post.addRequestHeader("Connection", "close");  
			//post.setRequestHeader("MessageType", "Call");
			// ·µ»Ø×´Ì¬
			int statusCode = getinstance().executeMethod(post);
			resBody = post.getResponseBodyAsString();
			post.getResponseBodyAsStream();
			logger.debug(resBody);
			return resBody;
		} catch (Throwable e) {
			logger.error("·¢ËÍÇëÇó´íÎó...", e);
		} finally {
			post.releaseConnection();
		}
		return resBody;
	}
	
	public static String postSOAP(String strURL, String strRequest){
		String resBody = null;
		// Prepare HTTP post
        PostMethod post = new PostMethod(strURL);
		try {
			
	        // Request content will be retrieved directly
	        // from the input stream
	        RequestEntity entity = new StringRequestEntity(strRequest, "text/xml","UTF-8");
	        post.setRequestEntity(entity);
	        // consult documentation for your web service
	        //post.setRequestHeader("SOAPAction", strSoapAction);
	        // Get HTTP client
	        HttpClient httpclient = getinstance();
	        // Execute request
        
            int result = httpclient.executeMethod(post);
            // Display status code
            logger.debug("Response status code: " + result);
            // Display response
            logger.debug("Response body: ");
            resBody = post.getResponseBodyAsString();
            logger.debug(resBody);
            return resBody;
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            // Release current connection to the connection pool once you are done
            post.releaseConnection();
        }
        return resBody;
	}

}
