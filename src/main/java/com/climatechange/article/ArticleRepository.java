package com.climatechange.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findAll();

    List<Article> findByArticleTitleContaining(String articleTitle);

    Article findByArticleID (Long articleID);
}
