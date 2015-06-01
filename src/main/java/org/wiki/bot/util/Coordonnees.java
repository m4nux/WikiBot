package org.wiki.bot.util;

public class Coordonnees
{
   private int degrees;
   private int minutes;
   private double seconds;
   
   public Coordonnees(int degrees, int minutes, double seconds)
   {
      super();
      this.degrees = degrees;
      this.minutes = minutes;
      this.seconds = seconds;
   }

   public int getDegrees()
   {
      return degrees;
   }

   public void setDegrees(int degrees)
   {
      this.degrees = degrees;
   }

   public int getMinutes()
   {
      return minutes;
   }

   public void setMinutes(int minutes)
   {
      this.minutes = minutes;
   }

   public double getSeconds()
   {
      return seconds;
   }

   public void setSeconds(float seconds)
   {
      this.seconds = seconds;
   }
   
   
   
}
