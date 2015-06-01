package org.wiki.bot;

import net.sourceforge.jwbf.core.contentRep.Article;

public class ListeDeSuiviBot extends MediaWikiBotProxy
{
   
   public ListeDeSuiviBot()
   {
      super();
   }
   
   /**
    * @param args
    */
   public static void main(String[] args) throws Exception
   {

      ListeDeSuiviBot bot = new ListeDeSuiviBot();
      bot.login("Nanebiard", "manuxx");
      Article article = bot.getArticle("Utilisateur:Nanebiard");
      String texte = article.getText();
      System.out.println(texte);
   }
}
