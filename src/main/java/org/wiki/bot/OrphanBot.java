package org.wiki.bot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;

import net.sourceforge.jwbf.core.contentRep.Article;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.wiki.bot.http.WPQuery;

public class OrphanBot extends MediaWikiBotProxy
{
   
   public static final Namespace ns               = Namespace.NO_NAMESPACE;
   protected static final String BaseEnWikiAPIURL = "https://fr.wikipedia.org/w/";
   
   public OrphanBot()
   {
      super();
   }
   
   protected int getBackLinksSize(String article) throws Exception
   {
      
      WPQuery wpquery =  new WPQuery();
      wpquery.addParam("list", "backlinks");
      wpquery.addParam("blnamespace", "0");
      wpquery.addParam("bltitle", article);
      String xmlResponse = wpquery.executeRequest();

      Document doc = new SAXBuilder().build(new StringReader(xmlResponse));
      String xmlBackLinks = new XMLOutputter().outputString(doc);
      System.out.print(xmlBackLinks);
      /*
      Element itemInfo = doc.getRootElement().getChild("query", ns).getChild("logevents", ns)
               .getChild("item", ns);
      return Long.parseLong(itemInfo.getAttributeValue("logid"));
      */
      Element itemInfo = doc.getRootElement().getChild("query", ns).getChild("backlinks", ns);
      itemInfo.getChildren().size();
      return itemInfo.getChildren().size();
   }

   
   private int getNbArticlesLies(String titreArticle) throws Exception
   {
      int size = getBackLinksSize(titreArticle);
      System.out.println("BacklinksSize:" + size);
      return size;
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
      boolean presenceModeleOrphelin=false;
      String titreArticle = null;
      OrphanBot bot = new OrphanBot();
      OrphanBot writerBot = new OrphanBot();
      writerBot.login("Nanebiard", "manuxx");
      Article orphanListArticle = writerBot.getArticle("Utilisateur:Nanebiard/Sandbox/OrphanList");
      //orphanListArticle.clear();
      String stringList="{| class=\"wikitable alternance centre\"" + "\n"
               + "|+ Liste des articles" + "\n"
               + "|-" + "\n"
               + "|" + "\n"
               + "! scope=\"col\" | Nombre d'article liés" + "\n"
               + "! scope=\"col\" | présence modèle orphelin" + "\n"
               + "|-" + "\n";
      try
      {
         // open input stream test.txt for reading purpose.
         BufferedReader br = new BufferedReader(new FileReader(
                  "target/test-classes/listearticletest.txt"));
         while ((titreArticle = br.readLine()) != null)
         {
            int nbArticleLies = bot.getNbArticlesLies(titreArticle);
            System.out.println("------------------");
            System.out.println(titreArticle);
            System.out.println();
            System.out.println("------------------");
            stringList += "! scope=\"row\" | [[" + titreArticle + "]]\n";
            if (nbArticleLies<3)
            {
               stringList += "| {{Coloré|#FF0000|" + nbArticleLies + "}}\n";
            } else if (nbArticleLies==10)
            {
               stringList += "| {{Coloré|#0000FF|+" + nbArticleLies + "}}\n";
            } else
            {
               stringList += "| {{Coloré|#0000FF|" + nbArticleLies + "}}\n";
               
            }
            presenceModeleOrphelin = bot.verifPresenceModeleOrphelin(titreArticle);
            stringList += "| " + presenceModeleOrphelin + "\n";
            stringList += "|-" + "\n";
         }
         stringList += "|}" + "\n";
         orphanListArticle.setText(stringList);
         orphanListArticle.save("Test OrphanBot sur liste complete de LD, c'est beau :)");
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
   
}
