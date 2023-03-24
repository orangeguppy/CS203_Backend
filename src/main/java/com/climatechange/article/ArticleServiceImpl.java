package com.climatechange.article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articles;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articles) {
        this.articles = articles;
    }

    @Override
    public List<Article> findAll() {
        return articles.findAll();
    }

    @Override
    public List<Article> getArticleByArticleTitle(String searchArticleTitle) {
        return articles.findByArticleTitleContaining(searchArticleTitle);
    }

    @Override
    public Article getArticleByArticleID(Long articleID) {
        return articles.findByArticleID(articleID);
    }
}
