package com.climatechange.article;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;




@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {
    @Mock
    private ArticleRepository articles;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    void getArticleByArticleTitle_ArticleExists_ReturnArticle() {
        List<Article> allArticles = generateArticles();
        Article searchArticle = allArticles.get(2);
        List<Article> toReturn = new ArrayList<>();
        toReturn.add(searchArticle);

        when(articles.findByArticleTitleContaining(searchArticle.getArticleTitle())).thenReturn(toReturn);
        List<Article> foundArticle = articleService.getArticleByArticleTitle(searchArticle.getArticleTitle());

        assertEquals(foundArticle, toReturn);
        verify(articles).findByArticleTitleContaining(searchArticle.getArticleTitle());
    }

    @Test
    void getArticleByArticleID_ArticleExists_ReturnArticle() {
        List<Article> allArticles = generateArticles();
        Article searchArticle = allArticles.get(2);

        when(articles.findByArticleID(searchArticle.getArticleID())).thenReturn(searchArticle);
        Article foundArticle = articleService.getArticleByArticleID(searchArticle.getArticleID());

        assertEquals(foundArticle, searchArticle);
        verify(articles).findByArticleID(searchArticle.getArticleID());
    }

    private static Article generateArticle() {
        return new Article("testingTitle", "tester", "tester", "www.google.com");
    }
    private static List<Article> generateArticles() {
        List<Article> articles = new ArrayList<>();
        for (long i = 1; i <= 3 ; i++) {
            articles.add(generateArticle());
        }
        return articles;
    }
}
