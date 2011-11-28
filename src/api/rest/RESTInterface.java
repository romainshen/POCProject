package api.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RESTInterface {
	private static Logger logger = LoggerFactory.getLogger(RESTInterface.class);
	private static String USER_NAME = "admin";
	private static String USER_PWD = "888888";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//RESTInterface restInf = new RESTInterface();
		String strReq1 = "https://3.3.3.65:9443/maxrest/rest/os/SRM_SR?_format=json&_compact=1&_rootonly=1&_keys=1&_maxItems=1&_fd=MEP_SRAPPRLIST";
		String strReq2 = "https://3.3.3.65:9443/maxrest/rest/os/PMZHBR1_PMRDPSIVIEW?CLASSSTRUCTUREID=PMRDPCLCPR&_format=json&_fd=PMZHBT_SRVPRJLUSER&_compact=1&_orderbydesc=STATUSDATE&_rsStart=0&_maxItems=5";
		String strReq3 = "myhttps://3.3.3.65/maxrest/rest/os/SRM_SR?_format=json&_compact=1&_rootonly=1&_keys=1&_maxItems=1&_fd=MEP_SRAPPRLIST";
		String strReq4 = "https://3.3.3.65:9443/maxrest/rest/os/OSTPSERVER?_exactmatch=1&name=LPAR003003003144";
		fetchSSLRESTString(strReq1,"GET");
		fetchSSLRESTString(strReq2,"GET");
		

	}
	
	
	public static String fetchSSLRESTString(String authServiceURL, String reqMethod){
		InputStream is = postSSLREST(authServiceURL,reqMethod);
		try {
			
			String rsString = IOUtils.toString(is, "UTF-8");
			is.close();
			logger.debug("result :" + rsString);
			return rsString;
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			
		}
		return null;
	}

	public static InputStream postSSLREST(String authServiceURL, String reqMethod){
		
		// 认证	
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USER_NAME, USER_PWD.toCharArray());
			}
		});
		HttpsURLConnection conn = null;
		try {
			logger.debug("reqeust url:" + authServiceURL);
			URL url = new URL(authServiceURL);
			conn = (HttpsURLConnection) url.openConnection();

			
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());

			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());

			conn.setRequestMethod(reqMethod);

			conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			conn.setRequestProperty("Accept", "text/*");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Pragma", "no-cache");

			String userpath = USER_NAME + ":" + USER_PWD;
			String enUserPath = new String(Base64.encodeBase64(userpath.getBytes()));
			
			//String enconding = new sun.misc.BASE64Encoder().encode(userpath.getBytes());
			
			
			conn.setRequestProperty("Authorization", "Basic " + enUserPath);
			conn.setDoInput(true);
			conn.setDoOutput(false);
			
			
			InputStream responseStream = conn.getInputStream();
			byte[] resultByte = IOUtils.toByteArray(responseStream);
			logger.debug("result :" + new String(resultByte));
			InputStream resultStream = new ByteArrayInputStream(resultByte);
			responseStream.close();
		
			

			return resultStream;

		} catch (Exception ex) {
			logger.error("发送Rest请求，返回结果集错误!", ex);
		} finally {
			conn.disconnect();
		}
		return null;
	}
	/**
	 * java.net.connection method
	 *
	 */
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {

		public boolean verify(String hostname, SSLSession session) {
			return true;
		}

		public boolean verify(String arg0, String arg1) {
			return true;
		}

	}
	/**
	 * For java.net.connection method
	 *
	 */
	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

		}

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {

			return null;
		}

	}
	
}
