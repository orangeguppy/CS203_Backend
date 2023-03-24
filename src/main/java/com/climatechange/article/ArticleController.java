package com.climatechange.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ArticleController {
    ArticleRepository articleRepository;
    ArticleService articleService;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, ArticleService articleService) {
        this.articleRepository = articleRepository;
        this.articleService = articleService;
    }

    // @GetMapping("/view-article/{id}")
    // Article getArticleByArticleTitle(@PathVariable String articleTitle) {
    //     return articleRepository.findByArticleTitle(articleTitle);
    // }

    @RequestMapping("/article/get-all-articles")
    List<Article> getAllArticles() {
        return articleService.findAll();
    }

    @RequestMapping("/article/get-article-by-articleTitle/")
    List<Article> getArticleByArticleTitle() {
        if (articleRepository.findAll().size() < 3) {
            populateArticles();
        }
        return articleService.findAll();
    }

    @RequestMapping("/article/get-article-by-articleTitle/{articleTitle}")
    List<Article> getArticleByArticleTitle(@PathVariable("articleTitle") String articleTitle) {
        List<Article> res = articleRepository.findByArticleTitleContaining(articleTitle);
        System.out.println("working");
        if (res.size() < 3) {
            populateArticles();
        }
        return articleService.getArticleByArticleTitle(articleTitle);
    }

    @RequestMapping("/article/get-article-by-articleID/{articleID}")
    Article getArticleByArticleID(@PathVariable("articleID") Long articleID) {
        return articleService.getArticleByArticleID(articleID);
    }

    public void populateArticles() {
        System.out.println("Adding articles");
        articleRepository.save(new Article("The United Nations definition of Climate Change",
                "Placeholder",
                "https://www.un.org/en/climatechange/what-is-climate-change",
                "https://64.media.tumblr.com/0631871892ec1b3b8fdc86b88270b3c3/tumblr_oxvvpfr3OB1qjpfn1o1_1280.png"));
        articleRepository.save(new Article(
                "The impacts of Climate Change as explained by NASA",
                "The Effects of Climate Change",
                "https://climate.nasa.gov/effects/",
                "https://loveincorporated.blob.core.windows.net/contentimages/gallery/08afbe0f-350d-480d-9421-57274208714e-Black_forest_germany.jpg"
                ));
        articleRepository.save(new Article(
                "The Effects of Climate Change",
                "Placeholder",
                "https://climate.nasa.gov/effects/",
                "https://blog.assets.thediscoverer.com/2020/03/breathtaking-forests.jpg"));
        articleRepository.save(new Article(
                "How You Can Stop Global Warming",
                "Placeholder",
                "https://www.nrdc.org/stories/how-you-can-stop-global-warming",
                "https://assets.nrdc.org/sites/default/files/styles/full_content--retina/public/media-uploads/clis6_797583_2400.jpg?itok=LFgM3j1E"));
        articleRepository.save(new Article(
                "Not too late.",
                "Placeholder",
                "https://www.nrdc.org/stories/how-you-can-stop-global-warming",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZtw-VWKiuYNA_-xNA0Jwjte0oNe1T1Mje5g&usqp=CAU"));
        articleRepository.save(new Article(
                "Misty forests near you",
                "Placeholder",
                "https://www.nrdc.org/stories/how-you-can-stop-global-warming",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRSwuV-1ao9h30z_Mj9AjpUFAwSUzjL6wgmw&usqp=CAU"));
        articleRepository.save(new Article(
                "Lungs of our Earth",
                "Placeholder",
                "https://www.nrdc.org/stories/how-you-can-stop-global-warming",
                "https://secubc.files.wordpress.com/2019/03/cropped-forest-header.jpeg"));
        articleRepository.save(new Article(
                "Secrets at Night",
                "Placeholder",
                "https://www.nrdc.org/stories/how-you-can-stop-global-warming",
                "https://i.ytimg.com/vi/XxEhuSJF780/maxresdefault.jpg"));
    }
}