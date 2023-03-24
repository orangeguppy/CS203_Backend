package com.climatechange.article; 
 
import javax.persistence.*; 
import javax.validation.constraints.*; 
 
@Entity 
public class Article { 
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long articleID; 
 
    @NotNull @Size(min = 5, max = 200) 
    private String articleTitle; 
 
    @Size(min = 5, max = 500) 
    private String articleDescription; 
 
    @Size(min = 5, max = 500) 
    private String articleURL; 
 
    @Size(min = 5, max = 500) 
    private String articleImgURL; 
 
    public Article () { 
    } 
 
    public Article (String articleTitle, String articleDescription, String articleURL, String articleImgURL) { 
        this.articleTitle = articleTitle; 
        this.articleDescription = articleDescription; 
        this.articleURL = articleURL; 
        this.articleImgURL = articleImgURL; 
    } 
 
    public Long getArticleID () {return articleID; } 
 
    public String getArticleTitle () {return articleTitle; } 
 
    public String getArticleDescription () {return articleDescription; } 
 
    public String getArticleURL () {return articleURL; } 
 
    public String getArticleImgURL () {return articleImgURL; } 
 
    public String toString() { 
        return String.format("articleID = <%d>, Article Title = <%s>, Article Description = <%s>", articleID, articleTitle, articleDescription); 
    } 
}
