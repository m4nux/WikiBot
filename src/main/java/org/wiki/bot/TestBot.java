package org.wiki.bot;

import java.net.URL;


import net.sourceforge.jwbf.core.actions.HttpActionClient;
import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

public class TestBot extends MediaWikiBotProxy
{
   
   public TestBot()
   {
      super();
   }
   
   /**
    * @param args
    */
   public static void main(String[] args) throws Exception
   {
      String wrongWord = "parmis";
      String correctedWord = "parmi";
      String articleACorriger = "Utilisateur:Nanebiard/Brouillon";
      //String articleACorriger = "Tour_Perret_(Grenoble)";
      
      TestBot bot = new TestBot();
      bot.login("Nanebiard", "manuxx");
      Article article = bot.getArticle(articleACorriger);
      String texte = article.getText();
      if (texte.contains(wrongWord))
      {
         
         System.out.println("Le mot \"" + wrongWord + "\" a été trouvé dans l'article " + articleACorriger);
         texte = texte.replaceAll(wrongWord, correctedWord);
         article.setMinorEdit(true);
         article.setText(texte);
         article.save("Correction de la page par OrthoBot (Version Beta)");
      }
      else
      {
         System.out.println("Le mot mal orthographié \"" + wrongWord
                  + "\" n'a pas été trouvé dans l'article \"" + articleACorriger + "\"");
      }
      // article.clear();
      // article.save("Test de nettoyage de la page par OrthoBot ");
      
   }
}
