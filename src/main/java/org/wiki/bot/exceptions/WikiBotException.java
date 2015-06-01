package org.wiki.bot.exceptions;

public class WikiBotException extends Exception
{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
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
   
}
