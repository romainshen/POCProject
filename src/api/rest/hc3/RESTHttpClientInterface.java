package api.rest.hc3;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class RESTHttpClientInterface {
	private static Logger logger = LoggerFactory.getLogger(RESTHttpClientInterface.class);
	private static String USER_NAME = "admin";
	private static String USER_PWD = "888888";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RESTHttpClientInterface restInf = new RESTHttpClientInterface();
		String strReq1 = "https://3.3.3.65:9443/maxrest/rest/os/SRM_SR?_format=json&_compact=1&_rootonly=1&_keys=1&_maxItems=1&_fd=MEP_SRAPPRLIST";
		String strReq2 = "https://3.3.3.65:9443/maxrest/rest/os/PMZHBR1_PMRDPSIVIEW?CLASSSTRUCTUREID=PMRDPCLCPR&_format=json&_fd=PMZHBT_SRVPRJLUSER&_compact=1&_orderbydesc=STATUSDATE&_rsStart=0&_maxItems=5";
		String strReq3 = "myhttps://3.3.3.65/maxrest/rest/os/SRM_SR?_format=json&_compact=1&_rootonly=1&_keys=1&_maxItems=1&_fd=MEP_SRAPPRLIST";
		String strReq4 = "https://3.3.3.65:9443/maxrest/rest/os/OSTPSERVER?_exactmatch=1&name=LPAR003003003144";
		
		restInf.hcSSLREST(strReq4);

	}
	
	public void hcSSLREST(String strReq) {
		  /*HttpClient httpclient = new HttpClient();
		  GetMethod httpget = new GetMethod(strReq); 
		  try { 
		    httpclient.executeMethod(httpget);
		    logger.debug(httpget.getStatusLine().toString());
		    logger.debug(httpget.getResponseBodyAsString());
		  } catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    httpget.releaseConnection();
		  }*/
		try {
			Protocol easyhttps = new Protocol("https", new EasySSLProtocolSocketFactory(), 9443);

			URI uri = new URI(strReq, true);
			// use relative url only
			GetMethod httpget = new GetMethod(uri.getPathQuery());
			HostConfiguration hc = new HostConfiguration();
			hc.setHost(uri.getHost(), uri.getPort(), easyhttps);
			HttpClient client = new HttpClient();
			client.getState().setCredentials(
	                new AuthScope(AuthScope.ANY_HOST,9443,AuthScope.ANY_REALM),
	                new UsernamePasswordCredentials(USER_NAME, USER_PWD)
	            );
			client.executeMethod(hc, httpget);
			System.out.println(httpget.getStatusLine());
			logger.debug(httpget.getResponseBodyAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
/*		HttpClient httpclient = new HttpClient();
		Protocol.registerProtocol("https", 
				new Protocol("https", new MySecureProtocolSocketFactory(), 9443));

		//Protocol myhttps = new Protocol("https", new MySecureProtocolSocketFactory (), 9443);
		
		httpclient.getState().setCredentials(
                new AuthScope(AuthScope.ANY_HOST,9443,AuthScope.ANY_REALM),
                new UsernamePasswordCredentials(USER_NAME, USER_PWD)
            );
        GetMethod httpget = new GetMethod(strReq);

        httpget.setDoAuthentication( true );

        try {
			httpclient.executeMethod(httpget);
			System.out.println(httpget.getStatusLine());
			logger.debug(httpget.getResponseBodyAsString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpget.releaseConnection();
		}*/
	}
	
	
	/**
	 * for httpclient method
	 * @author ShenQY
	 *
	 */
	class MySecureProtocolSocketFactory implements SecureProtocolSocketFactory{

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Socket createSocket(String host, int port, InetAddress localAddress, int localPort) throws IOException, UnknownHostException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Socket createSocket(String host, int port, InetAddress localAddress, int localPort, HttpConnectionParams params) throws IOException,
				UnknownHostException, ConnectTimeoutException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
