package org.wiki.bot.interfaces;

import java.util.List;
import java.util.Map;

import org.wiki.bot.exceptions.WikiBotException;

import net.sourceforge.jwbf.core.contentRep.Article;

public interface IWikiBot
{
   public List<String> getArticleNamesFromCateg(String categ, String pageid, int limit, int depth) throws WikiBotException;
   public List<String> getArticleNamesFromPortal(String portal, int limit) throws WikiBotException;
   public List<String> getBackLinksArticleNames(String articleName) throws WikiBotException;
   public Map<String, String> getUserContribs(String user, int limit) throws WikiBotException;
   public Article getArticle(String articleName);
   public boolean isAnArticle(String articleName) throws WikiBotException;
   
}
