package org.wiki.bot;

import java.util.List;

import net.sourceforge.jwbf.core.contentRep.Article;

import org.jdom2.Namespace;
import org.wiki.bot.exceptions.WikiBotException;

public class CategRugbyBot extends MediaWikiBotProxy
{
   
   public static final Namespace ns               = Namespace.NO_NAMESPACE;
   protected static final String BaseEnWikiAPIURL = "https://fr.wikipedia.org/w/";
   
   public CategRugbyBot()
   {
      super();
   }
   
   private int getNbCategoryMembers(String titreCateg) throws WikiBotException
   {
      MediaWikiBotProxy.CPT_DEPTH=0;
      List<String> listCateg = this.getArticleNamesFromCateg(titreCateg, null, 500, 0);
      System.out.println("Taille ListCateg:" + listCateg.size());
      return listCateg.size();
   }
   
   private int getNbArticlesFromPortals(String portal) throws WikiBotException
   {
      List<String> listArticles = this.getArticleNamesFromPortal(portal, 100);
      System.out.println("Taille ListArticles:" + listArticles.size());
      System.out.println("Valeur ListArticles:" + listArticles.toString());
      return listArticles.size();
   }
   
   private boolean verifPresenceModeleOrphelin(String titreArticle)
   {
      boolean presenceModele=false;
      Article article = this.getArticle(titreArticle);
      String texte = article.getText();
      if ((texte.contains("{{orphelin")) || (texte.contains("{{article_orphelin")))
      {
         presenceModele=true;
      }
      return presenceModele;
   }
   
   /**
    * @param args
    */
   public static void main(String[] args) throws Exception
   {
      CategRugbyBot bot = new CategRugbyBot();
      bot.login("Nanebiard", "manuxx");
      int nbArticleCateg = bot.getNbCategoryMembers("Category:Plateforme_Java");
      //int nbArticleFromPortal = bot.getNbArticlesFromPortals("Portail:Rugby_à_XV");
      System.out.println(nbArticleCateg);
   }
   
}
