package org.wiki.bot.logs;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.wiki.bot.exceptions.WikiBotException;
import org.wiki.bot.util.Utils;


/**
 * Classe de log qui initialise un Logger log4j avec un format spécifique et qui
 * redéfinit les méthode info, warn, error, fatal et debug
 * 
 * @author manux
 */
public class Log
{
   private static final String  SEP_TIRET = " - ";
   private String               idTrt     = null;
   
   private static PatternLayout patternLayout;
   
   /*
    * l'objet Logger log4j qui va réellement effectuer les opérations de
    * journalisation
    */
   private Logger               logger;
   
   /* L'identifiant unique du log */
   private String               name;
   
   
   /**
    * Constructeur par défaut
    * @throws WikiBotException 
    */
   public Log() throws WikiBotException
   {
      super();
      this.init();
   }
   
   /**
    * @return idTrt
    */
   public String getIdTrt()
   {
      return idTrt;
   }
   
   /**
    * @param idTrt
    */
   public void setIdTrt(String idTrt)
   {
      this.idTrt = idTrt;
   }

   
   /**
    * Méthode d'initialisation appelée à la fin du constructeur
    * 
    * @throws ECoffreException
    */
   private void init() throws WikiBotException
   {
      
      logger = Logger.getLogger(this.getClass());

      logger.setLevel(niveauLog("I"));

      logger.removeAllAppenders();
      
      patternLayout = new PatternLayout("%d{dd/MM/yyyy HH:mm:ss:SSS} | %-5p | %m%n");
      
      try
      {
         logger.addAppender(new DailyRollingFileAppender(patternLayout, "WikiBot.log", "'.'yyyy-MM-dd"));
      }
      catch (IOException e)
      {
         throw new WikiBotException("Erreur d'initialisation du fichier de log ", e);
      }
      
      logger.addAppender(new ConsoleAppender(patternLayout));
      
   }
   
   /**
    * méthode pour logger un message avec le niveau "INFO"
    */
   synchronized public void info(Object message)
   {
      this.logger.info((this.idTrt == null ? "" : this.idTrt + SEP_TIRET) + message);
   }
   
   /**
    * méthode pour logger un message avec le niveau "WARN"
    */
   synchronized public void warn(Object message)
   {
      this.logger.warn((this.idTrt == null ? "" : this.idTrt + SEP_TIRET) + message);
   }
   
   /**
    * méthode pour logger un message avec le niveau "ERROR"
    */
   synchronized public void error(String message)
   {
      this.logger.error((this.idTrt == null ? "" : this.idTrt + SEP_TIRET) + message);
   }
   
   /**
    * méthode pour logger un message avec le niveau "ERROR"
    */
   synchronized public void error(Exception ex)
   {
      StackTraceElement[] tab = ex.getStackTrace();
      
      String stack = "";
      stack += "Message d'erreur : \n";
      stack += ex.getMessage() + "\n\n";
      stack += "Pile des appels : \n";
      stack += Utils.join("\n", tab);
      this.logger.error((this.idTrt == null ? "" : this.idTrt + SEP_TIRET) + stack);
   }
   
   /**
    * méthode pour logger un message avec le niveau "FATAL"
    */
   synchronized public void fatal(Object message)
   {
      this.logger.fatal((this.idTrt == null ? "" : this.idTrt + SEP_TIRET) + message);
   }
   
   /**
    * méthode pour logger un message avec le niveau "DEBUG"
    */
   synchronized public void debug(Object message)
   {
      this.logger.debug((this.idTrt == null ? "" : this.idTrt + SEP_TIRET) + message);
   }
   
   /**
    * Méthode utilisée pour le paramétrage du niveau de journalisation
    * 
    * @author Fabrice Vignard Date de création : 25 nov. 08
    * @param niveau
    *           Le niveau de journalisation au format "String"
    * @return Le niveau de journalisation au format "Level"
    */
   private static Level niveauLog(String niveau)
   {
      Level level = null;
      if (niveau.equalsIgnoreCase("D"))
      {
         level = Level.DEBUG;
      }
      else if (niveau.equalsIgnoreCase("I"))
      {
         level = Level.INFO;
      }
      else if (niveau.equalsIgnoreCase("W"))
      {
         level = Level.WARN;
      }
      else
      {
         /* le mode par défaut */
         level = Level.DEBUG;
      }
      
      return level;
   }
   
}
