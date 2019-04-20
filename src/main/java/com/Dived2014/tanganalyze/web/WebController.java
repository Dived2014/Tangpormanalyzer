package com.Dived2014.tanganalyze.web;/*
 *国伟
 *2019/4/4
 *14:36
 *
 *
 *
 */

import com.Dived2014.tanganalyze.analyze.entity.model.AuthorCount;
import com.Dived2014.tanganalyze.analyze.entity.model.SingleWord;
import com.Dived2014.tanganalyze.analyze.entity.model.WordCount;
import com.Dived2014.tanganalyze.analyze.service.AnalyzeService;
import com.Dived2014.tanganalyze.config.ObjectFactory;
import com.Dived2014.tanganalyze.crawler.Crawler;
import com.google.gson.Gson;
import spark.ResponseTransformer;
import spark.Spark;

import java.util.List;

public class WebController {

    private final AnalyzeService analyzeService;

    public WebController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    private List<AuthorCount> analyzeAuthorCount() {
        return analyzeService.analyzeAuthorCount();
    }

    private List<WordCount> analyzeWordCount() {
        return analyzeService.analyzeWordClout();
    }

    private List<SingleWord> analyzeSingleWord(){
        return analyzeService.analyzeSingleWord();
    }
    public void launch() {
        ResponseTransformer transformer = new JSONTransformer();

        Spark.staticFileLocation("/static");

        Spark.get("/analyze/author_count",
                ((request, response) -> analyzeAuthorCount()),
                transformer);
        Spark.get("/analyze/word_cloud",
                ((request, response) -> analyzeWordCount()),
                transformer);
        Spark.get("/analyze/single_word",
                ((request, response) -> analyzeSingleWord()),
                transformer);
        Spark.get("/crawler/stop",((request, response) -> {
            Crawler c =  ObjectFactory.getInstace().getObject(Crawler.class);
            c.stop();
            return "Stop crawler";
        }));
    }

    public static class JSONTransformer implements ResponseTransformer {

        private Gson gson = new Gson();

        @Override
        public String render(Object o) throws Exception {
            return gson.toJson(o);
        }
    }


}
