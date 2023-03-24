package com.climatechange.article;

import java.util.List;

public interface ArticleService {
    // return list of all articles
    List<Article> findAll();

    // returns List of articles corresponding to search input
    List<Article> getArticleByArticleTitle(String articleTitle);

    // returns article by articleID
    Article getArticleByArticleID(Long articleID);
}
