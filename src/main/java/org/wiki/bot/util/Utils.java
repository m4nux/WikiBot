package org.wiki.bot.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import net.sourceforge.jwbf.core.contentRep.Article;

import org.jdom2.Document;
import org.wiki.bot.OrphanBot;
import org.wiki.bot.exceptions.WikiBotException;
import org.wiki.bot.exoplanetsbot.Planet;

public class Utils
{
   /**
    * Methode utilisee pour transformer la structure XML DOM passee en argument
    * en chaine de caracteres
    * 
    * @param document
    * @throws EStorageException
    */
   public static StringBuffer domToString(Document document) throws WikiBotException
   {
      StringWriter xmlout = new StringWriter();
      StreamResult result = new StreamResult(xmlout);
      
      try
      {
         TransformerFactory fabrique = TransformerFactory.newInstance();
         Transformer transformer = fabrique.newTransformer();
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
         
      }
      catch (Exception ex)
      {
         throw new WikiBotException("Echec lors de la transformation du Document DOM "
                  + "en chaine de caracteres", ex.getCause());
      }
      
      return xmlout.getBuffer();
   }
   
   /* 39d, 32m, 54s = 39.5483333 */
   public static double degrees2Decimal(Coordonnees coord)
   {
      double resultDec = coord.getDegrees() + (coord.getMinutes() + (coord.getSeconds() / 60)) / 60;
      return resultDec;
   }
   
   /* 39d, 32m, 54s = 39.5483333 */
   public static double ad2Decimal(Coordonnees coord)
   {
      double resultDec = coord.getDegrees() * 15 + (coord.getMinutes() + (coord.getSeconds() / 60))
               / 60;
      return resultDec;
   }
   
   public static Coordonnees decimal2Degrees(double value)
   {
      int degrees = (int) value;
      double partieDecimal = value - degrees;
      int min = (int) (partieDecimal * 60);
      double sec = ((partieDecimal * 60) - min) * 60;
      
      DecimalFormat df = new DecimalFormat("##.##");
      // df.setRoundingMode(RoundingMode.HALF_UP);
      return new Coordonnees(degrees, min, Long.valueOf(df.format(sec)));
   }
   
   public static Planet createPlanetFromWikiArticle(Article jwbfArticle) throws WikiBotException
   {
      boolean presenceInfoBoxExo = false;
      Map<String, String> infoBoxValues = new HashMap<String, String>();
      Planet exoPlanet = new Planet();
      String text = jwbfArticle.getText();
      
      try
      {
         BufferedReader br = new BufferedReader(new StringReader(text));
         String articleLine;
         
         while ((articleLine = br.readLine()) != null)
         {
            if (articleLine.startsWith("{{Infobox Exoplanète"))
            {
               presenceInfoBoxExo = true;
               break;
            }
         }
         
         if (!presenceInfoBoxExo)
         {
            exoPlanet.setErreurWiki(WikiBotException.TYPE_ERROR_MODELE_MANQUANT[1]);
         }
         
         while ((articleLine = br.readLine()) != null)
         {
            
            if (articleLine.startsWith("}}"))
            {
               break;
            }
            else
            {
               Pattern p = Pattern.compile("[^\\|]*\\|([^=]+)=(.*)");
               Matcher m = p.matcher(articleLine);
               
               if (m.matches())
               {
                  infoBoxValues.put(m.group(1).replace(" ", ""), m.group(2).replace(" ", ""));
               }
            }
            
         }
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      exoPlanet.setName(infoBoxValues.get("nom"));
      String[] tabAd =
      { "0", "0", "0" };
      String[] tabDec =
      { "0", "0", "0" };
      if ((!infoBoxValues.containsKey("ascensiondroite"))
               || (!infoBoxValues.containsKey("déclinaison")))
      {
         exoPlanet.setErreurWiki(WikiBotException.TYPE_ERROR_AD_DEC_MANQUANT[1]);
      }
      else
      {
         if (infoBoxValues.get("ascensiondroite").contains("/"))
         {
            tabAd = infoBoxValues.get("ascensiondroite").split("/");
         }
         
         if (infoBoxValues.get("déclinaison").contains("/"))
         {
            tabDec = infoBoxValues.get("déclinaison").split("/");
         }
      }
      
      Coordonnees coordAd = new Coordonnees(Integer.valueOf(tabAd[0]).intValue(), Integer.valueOf(
               tabAd[1]).intValue(), Double.parseDouble(tabAd[2]));
      Coordonnees coordDec = new Coordonnees(Integer.valueOf(tabDec[0]).intValue(), Integer
               .valueOf(tabDec[1]).intValue(), Double.parseDouble(tabDec[2]));
      exoPlanet.setRa(String.valueOf(Utils.ad2Decimal(coordAd)));
      exoPlanet.setDec(String.valueOf(Utils.degrees2Decimal(coordDec)));
      exoPlanet.setStar_distance(infoBoxValues.get("distance"));
      exoPlanet.setDiscovered(infoBoxValues.get("date"));
      
      return exoPlanet;
   }
   
   public static String createWikiTable(String titre, Map<String, Planet> planets)
   {
      
      String stringList = "{| class=\"wikitable alternance centre\"" + "\n" + "|+ " + titre + "\n"
               + "|-" + "\n" + "! scope=\"col\" | Nom de la planète" + "\n"
               + "! scope=\"col\" | Ascension droite" + "\n" + "! scope=\"col\" | Déclinaison"
               + "\n" + "! scope=\"col\" | Status" + "\n" + "|-" + "\n";
      for (String planetName : planets.keySet())
      {
         stringList += "! scope=\"row\" | [[" + planetName + "]]\n";
         stringList += "| " + planets.get(planetName).getRa() + "\n";
         stringList += "| " + planets.get(planetName).getDec() + "\n";
         stringList += "| "
                  + (planets.get(planetName).getErreurWiki() == null ? "OK" : planets.get(
                           planetName).getErreurWiki()) + "\n";
         stringList += "|-" + "\n";
      }
      stringList += "|}" + "\n";
      return stringList;
   }
   
   public static String createWikiTableConsolidation(String titre,
            Map<String, Planet[]> starsMapCommon)
   {
      
      String stringList = "{| class=\"wikitable alternance centre\"" + "\n" + "|+ " + titre + "\n"
               + "|-" + "\n" + "! scope=\"col\" | Nom planète\n"
               + "! scope=\"col\" | Planète wiki\n" + "! scope=\"col\" | AD\n"
               + "! scope=\"col\" | Dec\n" + "! scope=\"col\" | Status\n"
               + "! scope=\"col\" | Planète EPE\n" + "! scope=\"col\" | AD\n"
               + "! scope=\"col\" | Dec\n" + "! scope=\"col\" | Status\n" + "|-" + "\n";
      for (String planetName : starsMapCommon.keySet())
      {
         Planet pwiki = starsMapCommon.get(planetName)[0];
         Planet pEPE = starsMapCommon.get(planetName)[1];
         stringList += "! scope=\"row\" | " + planetName + "]]\n";
         if (pwiki!=null)
         {
            stringList += "| [[" + pwiki.getName() + "]]\n";
            stringList += "| " + pwiki.getRa() + "\n";
            stringList += "| " + pwiki.getDec() + "\n";
            stringList += "| " + (pwiki.getErreurWiki() == null ? "OK" : pwiki.getErreurWiki()) + "\n";
         }
         if (pEPE!=null)
         {
            stringList += "| " + pEPE.getName() + "\n";
            stringList += "| " + pEPE.getRa() + "\n";
            stringList += "| " + pEPE.getDec() + "\n";
            stringList += "| " + (pEPE.getErreurWiki() == null ? "OK" : pEPE.getErreurWiki()) + "\n";
         }
         stringList += "|-" + "\n";
      }
      stringList += "|}" + "\n";
      return stringList;
   }
   
   public static String possibleMatch(Planet refPlanet, Collection<Planet> starsMap)
   {
      for (Planet currentPlanet : starsMap)
      {
         if (currentPlanet.getName() != null)
         {
            
            if (currentPlanet.getName().equalsIgnoreCase(refPlanet.getName()))
            {
               return currentPlanet.getName();
            }
            
            if ((currentPlanet.getRa() != null) && (currentPlanet.getDec() != null)
                     && (refPlanet.getRa() != null) && (refPlanet.getDec() != null))
            {
               int adcurr = Double.valueOf(currentPlanet.getRa()).intValue();
               int adref = Double.valueOf(refPlanet.getRa()).intValue();
               
               int deccurr = Double.valueOf(currentPlanet.getDec()).intValue();
               int decref = Double.valueOf(refPlanet.getDec()).intValue();
               
               if ((adcurr == adref) && (deccurr == decref))
               {
                  return currentPlanet.getName();
               }
            }

         }
      }
      return null;
   }
 
   /**
    * Méthode équivalente au "join" du langage Perl
    * 
    * @param token
    * @param strings
    * @return
    */
   public static String join(String token, Object[] strings)
   {
      if (strings.length == 0)
      {
         return "";
      }
      else
      {
         StringBuffer sb = new StringBuffer();
         
         for (int x = 0; x < (strings.length - 1); x++)
         {
            sb.append(strings[x]);
            sb.append(token);
         }
         sb.append(strings[strings.length - 1]);
         
         return (sb.toString());
      }
   }
   
}
