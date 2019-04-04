package com.Dived2014.tanganalyze;

import com.Dived2014.tanganalyze.config.ObjectFactory;
import com.Dived2014.tanganalyze.crawler.Crawler;

import com.Dived2014.tanganalyze.web.WebController;
import spark.Spark;


public class Application {
    public static void main(String[] args) {
//        Crawler crawler =  ObjectFactory.getInstace().getObject(Crawler.class);
//        crawler.start();

        WebController webController = ObjectFactory
                .getInstace().getObject(WebController.class);
        webController.launch();
    }
}

