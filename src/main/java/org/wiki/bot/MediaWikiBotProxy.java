package org.wiki.bot;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.jwbf.core.actions.HttpActionClient;
import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.wiki.bot.beans.WikiArticle;
import org.wiki.bot.exceptions.WikiBotException;
import org.wiki.bot.http.HttpWikiRequest;
import org.wiki.bot.http.WPQuery;
import org.wiki.bot.interfaces.IWikiBot;
import org.wiki.bot.logs.Log;

public class MediaWikiBotProxy implements IWikiBot
{

   public static Log LOG;
   
   public static int             CPT_DEPTH = 0;
   public static final Namespace ns        = Namespace.NO_NAMESPACE;
   
   protected String              proxyHost;
   protected int                 proxyPort;
   
   private MediaWikiBot          bot;
   
   public MediaWikiBotProxy()
   {
      HttpWikiRequest wikiRequest = new HttpWikiRequest();
      
      HttpActionClient client = HttpActionClient.builder() //
               .withUrl("http://fr.wikipedia.org/w/") //
               .withClient(wikiRequest.getHttpClient()).withUserAgent("jwbf", "3.0").build();
      this.bot = new MediaWikiBot(client);
      
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
   
   public MediaWikiBotProxy(String proxyHost, int proxyPort)
   {
      this.proxyHost = proxyHost;
      this.proxyPort = proxyPort;
      
      HttpWikiRequest wikiRequest = new HttpWikiRequest(proxyHost, proxyPort);
      
      HttpActionClient client = HttpActionClient.builder() //
               .withUrl("http://fr.wikipedia.org/w/") //
               .withClient(wikiRequest.getHttpClient()).withUserAgent("jwbf", "3.0").build();
      this.bot = new MediaWikiBot(client);
      
   }
   
   public void login(String login, String pass)
   {
      this.bot.login(login, pass);
   }

   public Article getArticle(String name)
   {
      return this.bot.getArticle(name, 0);
   }
   
   public Article getArticle(String name, int properties)
   {
      return this.bot.getArticle(name, properties);
   }
   
   @Override
   public List<WikiArticle> getBackLinksArticleNames(String articleName) throws WikiBotException
   {
      WPQuery wpquery = new WPQuery(proxyHost, proxyPort);
      wpquery.addParam("list", "backlinks");
      wpquery.addParam("blnamespace", "0");
      wpquery.addParam("bltitle", articleName);
      
      String xmlResponse = wpquery.executeRequest();
      Document doc;
      List<WikiArticle> returnArticleNamesList = new ArrayList<WikiArticle>();
      try
      {
         doc = new SAXBuilder().build(new StringReader(xmlResponse));
         String xmlCategMembers = new XMLOutputter().outputString(doc);
         System.out.print(xmlCategMembers);
         Element itemInfo = doc.getRootElement().getChild("query", ns)
                  .getChild("categorymembers", ns);
         itemInfo.getChildren().size();
         Iterator<Element> itArticleNames = itemInfo.getChildren().iterator();
         while (itArticleNames.hasNext())
         {
            Element e = itArticleNames.next();
            returnArticleNamesList.add(new WikiArticle(e.getName(), e.getName()));
         }
         
         return returnArticleNamesList;
      }
      catch (JDOMException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         throw new WikiBotException(e.getCause());
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         throw new WikiBotException(e.getCause());
      }
      
   }
   
   @Override
   public List<WikiArticle> getArticleNamesFromPortal(String portal, int limit)
            throws WikiBotException
   {
      
      WPQuery wpquery = new WPQuery(proxyHost, proxyPort);
      wpquery.addParam("list", "backlinks");
      wpquery.addParam("bllimit", String.valueOf(limit));
      wpquery.addParam("blnamespace", "0");
      wpquery.addParam("bltitle", portal);
      
      String xmlResponse = wpquery.executeRequest();
      
      Document doc;
      List<WikiArticle> returnArticleNamesList = new ArrayList<WikiArticle>();
      try
      {
         doc = new SAXBuilder().build(new StringReader(xmlResponse));
         
         String xmlCategMembers = new XMLOutputter().outputString(doc);
         XMLOutputter outputter = new XMLOutputter();
         Format format = Format.getPrettyFormat();
         format.setIndent("    ").setLineSeparator(System.getProperty("line.separator"));
         outputter.setFormat(format);
         String xmlCategMembersFormat = outputter.outputString(doc);
         System.out.print(xmlCategMembersFormat);
         
         Element itemInfo = doc.getRootElement().getChild("query", ns)
                  .getChild("categorymembers", ns);
         itemInfo.getChildren().size();
         Iterator<Element> itArticleNames = itemInfo.getChildren().iterator();
         while (itArticleNames.hasNext())
         {
            Element e = itArticleNames.next();
            returnArticleNamesList.add(new WikiArticle(e.getName(), e.getName()));
         }
         
         return returnArticleNamesList;
      }
      catch (JDOMException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         throw new WikiBotException(e.getCause());
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         throw new WikiBotException(e.getCause());
      }
      
   }
   
   @Override
   public List<WikiArticle> getArticleNamesFromCateg(String categ, String pageid, int limit,
            int depth) throws WikiBotException
   {
      CPT_DEPTH++;
      WPQuery wpquery = new WPQuery(proxyHost, proxyPort);
      wpquery.addParam("list", "categorymembers");
      wpquery.addParam("cmlimit", String.valueOf(limit));
      if (pageid != null)
      {
         wpquery.addParam("cmpageid", pageid);
      }
      else
      {
         wpquery.addParam("cmtitle", categ);
      }
      String xmlResponse = wpquery.executeRequest();
      
      Document doc;
      List<WikiArticle> returnArticleNamesList = new ArrayList<WikiArticle>();
      try
      {
         doc = new SAXBuilder().build(new StringReader(xmlResponse));
         
         String xmlCategMembers = new XMLOutputter().outputString(doc);
         XMLOutputter outputter = new XMLOutputter();
         Format format = Format.getPrettyFormat();
         format.setIndent("    ").setLineSeparator(System.getProperty("line.separator"));
         outputter.setFormat(format);
         String xmlCategMembersFormat = outputter.outputString(doc);
         //System.out.print(xmlCategMembersFormat);
         
         Element itemInfo = doc.getRootElement().getChild("query", ns)
                  .getChild("categorymembers", ns);
         Iterator<Element> itArticleNames = itemInfo.getChildren().iterator();
         while (itArticleNames.hasNext())
         {
            Element e = itArticleNames.next();
            if (e.getAttribute("ns").getValue().equalsIgnoreCase("0"))
            {
               returnArticleNamesList.add(new WikiArticle(e.getAttribute("pageid").getValue(), e
                        .getAttribute("title").getValue()));
            }
            else if (e.getAttribute("ns").getValue().equalsIgnoreCase("14"))
            {
               LOG.info("Traitement de la sous-categorie " + e.getAttribute("title")
                        .getValue());
               /* Il s'agit d'une sous catégorie */
               if (CPT_DEPTH <= depth)
               {
                  returnArticleNamesList.addAll(getArticleNamesFromCateg(e.getAttribute("title")
                           .getValue(), e.getAttribute("pageid").getValue(), limit, depth));
               }
            }
         }
         
         return returnArticleNamesList;
      }
      catch (JDOMException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         throw new WikiBotException(e.getCause());
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         throw new WikiBotException(e.getCause());
      }
      
   }
   
   @Override
   public Map<String, WikiArticle> getUserContribs(String user, int limit) throws WikiBotException
   {
      
      WPQuery wpquery = new WPQuery(proxyHost, proxyPort);
      wpquery.addParam("list", "usercontribs");
      wpquery.addParam("ucuser", user);
      wpquery.addParam("uclimit", String.valueOf(limit));
      wpquery.addParam("ucnamespace", "0");
      
      String xmlResponse = wpquery.executeRequest();
      
      Document doc;
      Map<String, WikiArticle> returnArticleNamesList = new HashMap<String, WikiArticle>();
      try
      {
         doc = new SAXBuilder().build(new StringReader(xmlResponse));
         
         String xmlCategMembers = new XMLOutputter().outputString(doc);
         XMLOutputter outputter = new XMLOutputter();
         Format format = Format.getPrettyFormat();
         format.setIndent("    ").setLineSeparator(System.getProperty("line.separator"));
         outputter.setFormat(format);
         String xmlUserContribs = outputter.outputString(doc);
         System.out.print(xmlUserContribs);
         
         Element itemInfo = doc.getRootElement().getChild("query", ns).getChild("usercontribs", ns);
         itemInfo.getChildren().size();
         Iterator<Element> itArticleNames = itemInfo.getChildren().iterator();
         while (itArticleNames.hasNext())
         {
            Element e = itArticleNames.next();
            returnArticleNamesList.put(e.getAttributeValue("pageid"), new WikiArticle(e
                     .getAttribute("pageid").getValue(), e.getAttribute("title").getValue()));
         }
         
         return returnArticleNamesList;
      }
      catch (JDOMException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         throw new WikiBotException(e.getCause());
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         throw new WikiBotException(e.getCause());
      }
   }
   
   @Override
   public boolean isAnArticle(WikiArticle article) throws WikiBotException
   {
      WPQuery wpquery = new WPQuery(proxyHost, proxyPort);
      wpquery.addParam("indexpageids", "");
      wpquery.addParam("titles", article.getName());
      
      String xmlResponse = wpquery.executeRequest();
      Document doc;
      try
      {
         doc = new SAXBuilder().build(new StringReader(xmlResponse));
         String xmlCategMembers = new XMLOutputter().outputString(doc);
         XMLOutputter outputter = new XMLOutputter();
         Format format = Format.getPrettyFormat();
         format.setIndent("    ").setLineSeparator(System.getProperty("line.separator"));
         outputter.setFormat(format);
         //String xmlUserContribs = outputter.outputString(doc);
         //System.out.print(xmlUserContribs);
         
         Element itemInfo = doc.getRootElement().getChild("query", ns).getChild("pageids", ns)
                  .getChild("id", ns);
         return !itemInfo.getValue().equalsIgnoreCase("-1");
      }
      catch (JDOMException e)
      {
         throw new WikiBotException(e.getCause());
      }
      catch (IOException e)
      {
         throw new WikiBotException(e.getCause());
      }
   }
   
}
