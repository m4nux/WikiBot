package org.wiki.bot.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpWikiRequest
{
   private HttpClient httpClient;
   protected String     proxyHost;
   protected int        proxyPort;
   
   public HttpWikiRequest()
   {
      this.httpClient = new DefaultHttpClient();
      
   }
   
   public HttpWikiRequest(String proxyHost, int proxyPort)
   {
      this.proxyHost = proxyHost;
      this.proxyPort = proxyPort;
      this.httpClient = new DefaultHttpClient();
      // HttpHost proxy = new HttpHost("localhost", 10000, "http");
      HttpHost proxy = new HttpHost(this.proxyHost, this.proxyPort, "http");
      this.httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
      
   }
   
   public HttpClient getHttpClient()
   {
      return httpClient;
   }
   
   public void setHttpClient(HttpClient httpClient)
   {
      this.httpClient = httpClient;
   }
   
   public String getProxyHost()
   {
      return proxyHost;
   }
   
   public void setProxyHost(String proxyHost)
   {
      this.proxyHost = proxyHost;
   }
   
   public int getProxyPort()
   {
      return proxyPort;
   }
   
   public void setProxyPort(int proxyPort)
   {
      this.proxyPort = proxyPort;
   }
   
   /**
    * A generic method to execute any type of Http Request and constructs a
    * response object
    * 
    * @param requestBase
    *           the request that needs to be exeuted
    * @return server response as <code>String</code>
    */
   public String executeRequest(String urlString)
   {
      String responseString = "";
      
      HttpPost postRequest = new HttpPost(urlString);
      
      if (this.proxyHost != null)
      {
         HttpHost proxy = new HttpHost(this.proxyHost, this.proxyPort);
         RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
         postRequest.setConfig(config);
      }
      
      InputStream responseStream = null;
      
      try
      {
         HttpResponse response = this.httpClient.execute(postRequest);
         if (response != null)
         {
            HttpEntity responseEntity = response.getEntity();
            
            if (responseEntity != null)
            {
               responseStream = responseEntity.getContent();
               if (responseStream != null)
               {
                  BufferedReader br = new BufferedReader(new InputStreamReader(responseStream));
                  String responseLine = br.readLine();
                  String tempResponseString = "";
                  while (responseLine != null)
                  {
                     tempResponseString = tempResponseString + responseLine
                              + System.getProperty("line.separator");
                     responseLine = br.readLine();
                  }
                  br.close();
                  if (tempResponseString.length() > 0)
                  {
                     responseString = tempResponseString;
                  }
               }
            }
         }
      }
      catch (ClientProtocolException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      finally
      {
         if (responseStream != null)
         {
            try
            {
               responseStream.close();
            }
            catch (IOException e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
      
      return responseString;
   }
   
}
