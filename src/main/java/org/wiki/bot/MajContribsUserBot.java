package org.wiki.bot;

import java.util.List;
import java.util.Map;

import net.sourceforge.jwbf.core.contentRep.Article;

import org.jdom2.Namespace;
import org.wiki.bot.beans.WikiArticle;
import org.wiki.bot.exceptions.WikiBotException;

public class MajContribsUserBot extends MediaWikiBotProxy
{
   
   public static final Namespace ns               = Namespace.NO_NAMESPACE;
   protected static final String BaseEnWikiAPIURL = "https://fr.wikipedia.org/w/";
   
   public MajContribsUserBot()
   {
      super();
   }
   
   private int getNbCategoryMembers(String titreCateg) throws WikiBotException
   {
      MediaWikiBotProxy.CPT_DEPTH=0;
      List<WikiArticle> listCateg = this.getArticleNamesFromCateg(titreCateg, null, 500, 0);
      System.out.println("Taille ListCateg:" + listCateg.size());
      return listCateg.size();
   }
   
   private int getNbArticlesFromPortals(String portal) throws WikiBotException
   {
      List<WikiArticle> listArticles = this.getArticleNamesFromPortal(portal, 100);
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
      MajContribsUserBot bot = new MajContribsUserBot();
      bot.login("Nanebiard", "manuxx");
      Map<String, WikiArticle> mapContribs = bot.getUserContribs("User:Nanebiard", 100);
      Article userPage = bot.getArticle("Utilisateur:Nanebiard");
      String contenuPageUtilisateur = userPage.getText(); 
      System.out.println(contenuPageUtilisateur);
      int startContrib = contenuPageUtilisateur.indexOf("<!-- BEGINCONTRIB -->");
      int endContrib = contenuPageUtilisateur.indexOf("<!-- ENDCONTRIB -->");

      String startContent = contenuPageUtilisateur.substring(0, startContrib+21);
      String endContent = contenuPageUtilisateur.substring(endContrib);
      String contrib="";
      for (String pageId : mapContribs.keySet())
      {
         contrib += "[[" + mapContribs.get(pageId) + "]], \n";
      }
      
      String articleModif = startContent + "\n" + contrib + endContent;
      
      userPage.setText(articleModif);
      userPage.setMinorEdit(true);
      userPage.save("Màj contrib avec bot MajContribUser");
      
      System.out.println(articleModif);
   }
   
}
