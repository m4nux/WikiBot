package org.wiki.bot.beans;

public class WikiArticle
{
   private String pageid;
   private String name;
   
   public WikiArticle(String pageid, String name)
   {
      super();
      this.pageid = pageid;
      this.name = name;
   }

   public WikiArticle()
   {
      
   }
   
   public String getPageid()
   {
      return pageid;
   }
   public void setPageid(String pageid)
   {
      this.pageid = pageid;
   }
   public String getName()
   {
      return name;
   }
   public void setName(String name)
   {
      this.name = name;
   }

   @Override
   public String toString()
   {
      return "WikiArticle [pageid=" + pageid + ", name=" + name + "]";
   }
   
   
   
}
