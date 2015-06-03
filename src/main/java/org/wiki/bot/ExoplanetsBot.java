package org.wiki.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.jwbf.core.contentRep.Article;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jdom2.Namespace;
import org.wiki.bot.beans.WikiArticle;
import org.wiki.bot.exceptions.WikiBotException;
import org.wiki.bot.exoplanetsbot.Planet;
import org.wiki.bot.http.WPQuery;
import org.wiki.bot.logs.Log;
import org.wiki.bot.util.Utils;

public class ExoplanetsBot extends MediaWikiBotProxy
{
   
   private Map<String, Planet>   starsMapFromEPE      = new HashMap<String, Planet>();
   private Map<String, Planet>   starsMapFromWiki     = new HashMap<String, Planet>();
   private Map<String, Planet[]> starsMapCommon       = new HashMap<String, Planet[]>();
   private Map<String, Planet>   starsMapWikiOrphelin = new HashMap<String, Planet>();
   public static final Namespace ns                   = Namespace.NO_NAMESPACE;
   protected static final String BaseEnWikiAPIURL     = "https://fr.wikipedia.org/w/";
   
   public ExoplanetsBot()
   {
      super();
   }
   
   public ExoplanetsBot(String proxyHost, int proxyPort)
   {
      super(proxyHost, proxyPort);
   }
   
   public Map<String, Planet> getStarsMapFromEPE()
   {
      return starsMapFromEPE;
   }
   
   public void setStarsMapFromEPE(Map<String, Planet> starsMapFromEPE)
   {
      this.starsMapFromEPE = starsMapFromEPE;
   }
   
   public Map<String, Planet> getStarsMapFromWiki()
   {
      return starsMapFromWiki;
   }
   
   public void setStarsMapFromWiki(Map<String, Planet> starsMapFromWiki)
   {
      this.starsMapFromWiki = starsMapFromWiki;
   }
   
   public Map<String, Planet[]> getStarsMapCommon()
   {
      return starsMapCommon;
   }
   
   public void setStarsMapCommon(Map<String, Planet[]> starsMapCommon)
   {
      this.starsMapCommon = starsMapCommon;
   }
   
   
   public Map<String, Planet> getStarsMapWikiOrphelin()
   {
      return starsMapWikiOrphelin;
   }

   public void setStarsMapWikiOrphelin(Map<String, Planet> starsMapWikiOrphelin)
   {
      this.starsMapWikiOrphelin = starsMapWikiOrphelin;
   }

   public void recupListFromEPE() throws WikiBotException
   {
      int cpt = 0;
      String csvLine = "";
      WPQuery epeQuery = new WPQuery(this.proxyHost, this.proxyPort);
      // String xmlResponse =
      // epeQuery.executeRequest("http://exoplanet.eu/catalog/votable/");
      String cvsResponse = epeQuery
               .executeRequest("http://localhost/html/exoplanet.eu_catalog.csv");
      
      try
      {
         BufferedReader br = new BufferedReader(new StringReader(cvsResponse));
         /* Première ligne, c'est la liste des colonnes */
         String headers = br.readLine();
         LOG.info("Lecture sequentiel du fichier csv EPE exoplanet.eu_catalog.csv");
         while ((csvLine = br.readLine()) != null)
         {
            String[] tabLine = csvLine.split(",");
            Planet p = new Planet(tabLine);
            this.starsMapFromEPE.put(p.getName(), p);
            cpt++;
            if (cpt % 100 == 0)
            {
               LOG.info("Traitement de la ligne " + cpt);
            }
         }
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
   }
   
   public void recupListFromWiki() throws WikiBotException
   {
      int cpt = 0;
      LOG.info("Récupération des articles wiki sur les exoplanètes à partir de la catégorie Category:Exoplanète_par_année_de_découverte");
      List<WikiArticle> listeArticleExoplanet = this.getArticleNamesFromCateg(
               "Category:Exoplanète_par_année_de_découverte", null, 50, 50);
      LOG.info("Nombres d'articles trouves " + listeArticleExoplanet.size());
      
      LOG.info("Parcours des articles pour récuperation des infos du modèle Exoplanète");
      for (WikiArticle wikiArticle : listeArticleExoplanet)
      {
         Article jwbfArticle = this.getArticle(wikiArticle.getName());
         try
         {
            Planet planet = Utils.createPlanetFromWikiArticle(jwbfArticle);
            this.starsMapFromWiki.put(wikiArticle.getName(), planet);
         }
         catch (WikiBotException wbe)
         {
            if (wbe.getTypeErreur().equals(WikiBotException.TYPE_ERROR_MODELE_MANQUANT))
            {
               this.starsMapFromWiki.put(wikiArticle.getName(), null);
               
            }
         }
         cpt++;
         if (cpt % 10 == 0)
         {
            LOG.info(cpt + "/" + listeArticleExoplanet.size() + " articles lus");
         }
      }
      
   }
   
   private void consolidationListeGlobale() throws WikiBotException
   {
      int cpt=0;
      for (Planet epePlanet : this.starsMapFromEPE.values())
      {
         String wikiPlanetPossibleMatchName = Utils.possibleMatch(epePlanet,
                  this.starsMapFromWiki.values());
         if (wikiPlanetPossibleMatchName != null)
         {
            LOG.info("Possible couple " + wikiPlanetPossibleMatchName);
            Planet[] couple = new Planet[2];
            couple[0] = epePlanet;
            couple[1] = this.starsMapFromWiki.get(wikiPlanetPossibleMatchName);
            this.starsMapCommon.put(wikiPlanetPossibleMatchName, couple);
         }
         else
         {
            /*
             * Pas trouvé dans la liste des articles wiki repertoriés, càd ceux
             * issus des categs par années de découverte. Maintenant, on va
             * regardé si l'article existe simplement par son nom (autrement
             * dit, article déjà rédigé avec le même nom mais ne faisant pas
             * partie des catégories découverte par année
             */
            String wikiEscapedName = StringEscapeUtils.escapeHtml3(epePlanet.getName());
            wikiEscapedName = wikiEscapedName.replace(" ", "_");
            if (this.isAnArticle(new WikiArticle(null, wikiEscapedName)))
            {
               LOG.info("concordance de nom trouvée : " + wikiEscapedName);
               Article jwbfArticle = this.getArticle(wikiEscapedName);
               Planet planet = Utils.createPlanetFromWikiArticle(jwbfArticle);
               this.starsMapWikiOrphelin.put(epePlanet.getName(), planet);
            }
         }
         if (cpt % 100==0)
         {
            LOG.info("Consolidation : " + cpt + "/" + this.starsMapFromEPE.size());
         }
         cpt++;
      }
      
      LOG.info("Nettoyage article de la liste EPE existants dans wiki mais orphelins");
      /* Ensuite, on supprime les articles de la base EPE qui existe déjà dans wiki mais qui sont orphelins */
      for (Planet foundPlanetWikiOrphelin : this.starsMapWikiOrphelin.values())
      {
         this.starsMapFromEPE.remove(foundPlanetWikiOrphelin.getName());
      }
      
      LOG.info("Nettoyage des listes EPE et wiki des articles possiblement concordants");
      /* Ensuite, on supprime les couples trouvés dans les hashs respectifs */
      for (Planet foundPlanetCouple[] : this.starsMapCommon.values())
      {
         if ((foundPlanetCouple[0] != null) && (foundPlanetCouple[0].getName() != null))
         {
            this.starsMapFromEPE.remove(foundPlanetCouple[0].getName());
         }
         if ((foundPlanetCouple[1] != null) && (foundPlanetCouple[1].getName() != null))
         {
            this.starsMapFromWiki.remove(foundPlanetCouple[1].getName());
         }
      }
   }
   
   @Override
   public String toString()
   {
      String result = "ExoplanetsBot [starsMap=";
      for (String name : this.starsMapFromEPE.keySet())
      {
         result += this.starsMapFromEPE.get(name).toString() + "\n";
      }
      result += "]";
      return result;
      
   }
   
   /**
    * @param args
    */
   public static void main(String[] args) throws Exception
   {
      
      ExoplanetsBot.LOG = new Log();
      // ExoplanetsBot bot = new ExoplanetsBot("proxy.local.asterion.fr", 8080);
      ExoplanetsBot bot = new ExoplanetsBot();
      bot.login("Nanebiard", "manuxx");
      
      /*
       * bot.recupListFromEPE();
       */
      LOG.info("Recuperation de la liste des planetes sur EPE");
      bot.recupListFromEPE();
      LOG.info("Recuperation de la liste des planetes sur Wikipedia");
      bot.recupListFromWiki();
      
      LOG.info("Consolidation de la liste EPE<->Wiki");
      bot.consolidationListeGlobale();
      
      // Article jwbfArticle = bot.getArticle("PSR B1257+12 B");
      // Planet p = Utils.createPlanetFromWikiArticle(jwbfArticle);
      
      // System.out.println(p);
      
      LOG.info("Creation table wiki planet");
      String tabWikiPlanets = Utils.createWikiTable(
               "Planètes ayant seulement un article wikipedia (" + bot.getStarsMapFromWiki().size()
                        + ")", bot.getStarsMapFromWiki());
      
      LOG.info("Creation table EPE planet");
      String tabEPEPlanets = Utils.createWikiTable("Planètes seulement présentes sur EPE ("
               + bot.getStarsMapFromEPE().size() + ")", bot.getStarsMapFromEPE());
      
      LOG.info("Creation table wiki planet orpheline");
      String tabWikiOprhanPlanets = Utils.createWikiTable("Planètes présente sur EPE ayant un article wiki "
               + bot.getStarsMapWikiOrphelin().size() + ")", bot.getStarsMapWikiOrphelin());
      
      LOG.info("Creation table consolidation planet");
      String tabConsolidationPlanets = Utils.createWikiTableConsolidation(
               "Planètes concordantes EPE<->Wikipedia (" + bot.getStarsMapCommon().size() + ")",
               bot.getStarsMapCommon());
      Article draftExoplanets = bot.getArticle("Utilisateur:Nanebiard/Brouillon");
      
      // System.out.println(tabWikiPlanets + "\n\n" + tabEPEPlanets + "\n\n" +
      // tabConsolidationPlanets);
      
      draftExoplanets.setText(tabWikiPlanets + "\n\n" + tabEPEPlanets + "\n\n" + tabWikiOprhanPlanets + "\n\n"
               + tabConsolidationPlanets);
      draftExoplanets.setMinorEdit(true);
      
      LOG.info("Ecriture de l'article sur wikipedia");
      draftExoplanets.save("List des exo planètes qui possèdent un article wikipedia");
      
      // double dec = Utils.degrees2Decimal(new Coordonnees(39, 32, 54));
      // System.out.println("dec:" + dec);
      // Coordonnees coord = Utils.decimal2Degrees(dec);
      // System.out.println("coord:" + coord.getDegrees() + "° " +
      // coord.getMinutes() + "' " + coord.getSeconds() + "''");
      // System.out.println(bot.isAnArticle("Pigon"));
      // System.out.println(bot.toString());
      // System.out.println(listContribs);
   }
   
}
