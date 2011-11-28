package api.rest.hc4;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.rest.hc3.EasySSLProtocolSocketFactory;



public class RESTHttpClient4Interface {
	private static Logger logger = LoggerFactory.getLogger(RESTHttpClient4Interface.class);
	private static String USER_NAME = "admin";
	private static String USER_PWD = "888888";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RESTHttpClient4Interface restInf = new RESTHttpClient4Interface();
		String strReq1 = "https://3.3.3.65:9443/maxrest/rest/os/SRM_SR?_format=json&_compact=1&_rootonly=1&_keys=1&_maxItems=1&_fd=MEP_SRAPPRLIST";
		String strReq2 = "https://3.3.3.65:9443/maxrest/rest/os/PMZHBR1_PMRDPSIVIEW?CLASSSTRUCTUREID=PMRDPCLCPR&_format=json&_fd=PMZHBT_SRVPRJLUSER&_compact=1&_orderbydesc=STATUSDATE&_rsStart=0&_maxItems=5";
		String strReq3 = "myhttps://3.3.3.65/maxrest/rest/os/SRM_SR?_format=json&_compact=1&_rootonly=1&_keys=1&_maxItems=1&_fd=MEP_SRAPPRLIST";
		String strReq4 = "https://3.3.3.65:9443/maxrest/rest/os/OSTPSERVER?_exactmatch=1&name=LPAR003003003144";
		
		fetchHttpsRest(strReq4);

	}
	
	public static void fetchHttpsRest(String strReq) {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		try {
			SSLContext context = SSLContext.getInstance("SSL");
			context.init(null, new TrustManager[] { new EasyX509TrustManager() }, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(context);
			socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme sch = new Scheme("https", 9443, socketFactory);
            httpclient.getConnectionManager().getSchemeRegistry().register(sch);
            
            httpclient.getCredentialsProvider().setCredentials(
	                new AuthScope(AuthScope.ANY_HOST,9443,AuthScope.ANY_REALM),
	                new UsernamePasswordCredentials(USER_NAME, USER_PWD)
	            );
            HttpGet httpget = new HttpGet(strReq);

            System.out.println("executing request" + httpget.getRequestLine());

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
            }
            logger.debug("result:" + EntityUtils.toString(entity));
            EntityUtils.consume(entity);
            
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}
	
	private static class MySSLSocketFactory extends SSLSocketFactory {
	    SSLContext sslContext = SSLContext.getInstance("TLS");

	    public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
	        super(truststore);

	        TrustManager tm = new X509TrustManager() {
				
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					// TODO Auto-generated method stub
					
				}
			};

	        sslContext.init(null, new TrustManager[] { tm }, null);
	    }

	    @Override
	    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
	        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
	    }

	    @Override
	    public Socket createSocket() throws IOException {
	        return sslContext.getSocketFactory().createSocket();
	    }
	}

	private static class EasyX509TrustManager implements X509TrustManager {

    	public void checkClientTrusted(
    			java.security.cert.X509Certificate[] chain, String authType)
    			throws CertificateException {
    		
    		
    	}

    	public void checkServerTrusted(
    			java.security.cert.X509Certificate[] chain, String authType)
    			throws CertificateException {
    		
    		
    	}

    	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    		
    		
    		return null;
    	}

    }

}
