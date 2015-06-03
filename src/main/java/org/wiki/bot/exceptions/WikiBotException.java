package org.wiki.bot.exceptions;

public class WikiBotException extends Exception
{
   
   public final static String[] TYPE_ERROR_MODELE_MANQUANT =
                                                           { "1",
            "Modèle {{Infobox Exoplanète}} manquant"      };
   public final static String[] TYPE_ERROR_AD_DEC_MANQUANT =
                                                           { "2",
            "Modèle \"Infobox Exoplanète\" erroné, Ascension droite ou déclinaison manquantes" };
   
   public final static String[] TYPE_ERROR_SYSTEM          =
                                                           { "3",
            "Erreur system" };
   
   private String               typeErreur                 = "0";
   
   /**
    * 
    */
   private static final long    serialVersionUID           = 1L;
   
   public WikiBotException(String message)
   {
      super(message);
   }
   
   public WikiBotException(String string, String typeErreur)
   {
      super(string);
      this.typeErreur = typeErreur;
   }
   
   public WikiBotException(Throwable th)
   {
      if (th != null)
      {
         this.initCause(th);
         this.setStackTrace(th.getStackTrace());
      }
   }
   
   public WikiBotException(String string, Throwable cause)
   {
      super(string, cause);
   }
   
   public String getTypeErreur()
   {
      return typeErreur;
   }
   
   public void setTypeErreur(String typeErreur)
   {
      this.typeErreur = typeErreur;
   }
   
}
