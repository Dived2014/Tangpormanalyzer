package com.Dived2014.tanganalyze.web;/*
 *国伟
 *2019/4/4
 *14:36
 *
 *
 *
 */

import com.Dived2014.tanganalyze.analyze.entity.model.AuthorCount;
import com.Dived2014.tanganalyze.analyze.entity.model.WordCount;
import com.Dived2014.tanganalyze.analyze.service.AnalyzeService;
import com.google.gson.Gson;
import spark.ResponseTransformer;
import spark.Spark;

import java.util.List;

public class WebController {

    private final AnalyzeService analyzeService;

    public WebController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    public List<AuthorCount> analyzeAuthorCount() {
        return analyzeService.analyzeAuthorCount();
    }

    public List<WordCount> analyzeWordCount() {
        return analyzeService.analyzeWordClout();
    }

    public void launch() {
        ResponseTransformer transformer = new JSONTransformer();
        Spark.get("/analyze/author_count",
                ((request, response) -> analyzeAuthorCount()),
                transformer);
        Spark.get("/analyze/word_cloud",
                ((request, response) -> analyzeWordCount()),
                transformer);
    }

    public static class JSONTransformer implements ResponseTransformer {

        private Gson gson = new Gson();

        @Override
        public String render(Object o) throws Exception {
            return gson.toJson(o);
        }
    }


}
