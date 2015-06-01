package org.wiki.bot.util;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.jdom2.Document;
import org.wiki.bot.exceptions.WikiBotException;

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
      double resultDec = coord.getDegrees() + (coord.getMinutes()+(coord.getSeconds()/60))/60;
      return resultDec;
   }
   
   public static Coordonnees decimal2Degrees(double value)
   {
      int degrees = (int) value;
      double partieDecimal = value-degrees;
      int min = (int) (partieDecimal*60);
      double sec = ((partieDecimal*60)-min) * 60;
      return new Coordonnees(degrees, min, sec);
   }
}
