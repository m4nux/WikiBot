package org.wiki.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.Namespace;
import org.wiki.bot.exceptions.WikiBotException;
import org.wiki.bot.exoplanetsbot.Planet;
import org.wiki.bot.http.WPQuery;
import org.wiki.bot.util.Coordonnees;
import org.wiki.bot.util.Utils;

public class ExoplanetsBot extends MediaWikiBotProxy
{
   Map<String, Planet> starsMap = new HashMap<String, Planet>();
   public static final Namespace ns               = Namespace.NO_NAMESPACE;
   protected static final String BaseEnWikiAPIURL = "https://fr.wikipedia.org/w/";
   
   public ExoplanetsBot()
   {
      super();
   }
   
   public ExoplanetsBot(String proxyHost, int proxyPort)
   {
      super(proxyHost, proxyPort);
   }
   
   public void recupListFromEPE() throws WikiBotException
   {
      int cpt=0;
      String csvLine="";
      WPQuery epeQuery = new WPQuery(this.proxyHost, this.proxyPort);
      // String xmlResponse =
      // epeQuery.executeRequest("http://exoplanet.eu/catalog/votable/");
      String cvsResponse = epeQuery.executeRequest("http://localhost/html/exoplanet.eu_catalog.csv");


      try
      {
         BufferedReader br = new BufferedReader(new StringReader(cvsResponse));
         /* Première ligne, c'est la liste des colonnes */
         String headers = br.readLine();
         
         while ((csvLine = br.readLine()) != null)
         {
            System.out.println(cpt);
            String[] tabLine = csvLine.split(",");
            Planet p = new Planet(tabLine);
            this.starsMap.put(p.getName(), p);
            cpt++;
         }
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
         
   }
   
   
   @Override
   public String toString()
   {
      String result="ExoplanetsBot [starsMap=";
      for (String name : this.starsMap.keySet())
      {
         result += this.starsMap.get(name).toString() + "\n";
      }
      result += "]";
      return result;
      
   }

   /**
    * @param args
    */
   public static void main(String[] args) throws Exception
   {
      //ExoplanetsBot bot = new ExoplanetsBot("proxy.local.asterion.fr", 8080);
      //bot.recupListFromEPE();
      double dec = Utils.degrees2Decimal(new Coordonnees(39, 32, 54));
      System.out.println("dec:" + dec);
      Coordonnees coord = Utils.decimal2Degrees(dec);
      System.out.println("coord:" + coord.getDegrees() + "° " + coord.getMinutes() + "' " + coord.getSeconds() + "''");
      //System.out.println(bot.isAnArticle("Pigon"));
      //System.out.println(bot.toString());
      // System.out.println(listContribs);
   }
   
}
