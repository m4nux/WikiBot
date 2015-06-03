package org.wiki.bot.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WPQuery extends HttpWikiRequest
{
   
   private static final String BaseEnWikiAPIURL = "https://fr.wikipedia.org/w/";
   
   private String              targetUrl        = "";
   private final String        BASEAPIQUERY     = BaseEnWikiAPIURL
                                                         + "/api.php?action=query&continue=&format=xml";
   private Map<String, String> httpParams       = new HashMap<String, String>();
   
   public WPQuery()
   {
      
   }
   
   public WPQuery(String proxyhost, int proxyPort)
   {
      super(proxyhost, proxyPort);
   }
   
   public void addParam(String param, String value)
   {
      this.httpParams.put(param, value);
   }
   
   public String executeRequest()
   {
      this.buildRequest();
      return super.executeRequest(targetUrl);
   }
   
   private void buildRequest()
   {
      this.targetUrl = BASEAPIQUERY;
      Iterator<String> itKey = httpParams.keySet().iterator();
      while (itKey.hasNext())
      {
         String key = itKey.next();
         this.targetUrl += "&" + key + "=" + httpParams.get(key);
      }
   }
   
}