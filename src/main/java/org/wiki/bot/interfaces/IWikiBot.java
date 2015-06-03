package org.wiki.bot.interfaces;

import java.util.List;
import java.util.Map;

import org.wiki.bot.beans.WikiArticle;
import org.wiki.bot.exceptions.WikiBotException;

import net.sourceforge.jwbf.core.contentRep.Article;

public interface IWikiBot
{
   public List<WikiArticle> getArticleNamesFromCateg(String categ, String pageid, int limit, int depth) throws WikiBotException;
   public List<WikiArticle> getArticleNamesFromPortal(String portal, int limit) throws WikiBotException;
   public List<WikiArticle> getBackLinksArticleNames(String articleName) throws WikiBotException;
   public Map<String, WikiArticle> getUserContribs(String user, int limit) throws WikiBotException;
   public Article getArticle(String articleName);
   public boolean isAnArticle(WikiArticle articleName) throws WikiBotException;
   
}
