package com.Dived2014.tanganalyze;

import com.Dived2014.tanganalyze.config.ObjectFactory;
import com.Dived2014.tanganalyze.crawler.Crawler;
import com.Dived2014.tanganalyze.web.WebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        WebController webController = ObjectFactory
                .getInstace().getObject(WebController.class);
        LOGGER.info("Web Server launch......");
        webController.launch();

        if(args.length == 1 && args[0].equals("crawler")){
            Crawler crawler =  ObjectFactory
                    .getInstace().getObject(Crawler.class);
            LOGGER.info("crawler started...");
            crawler.start();
        }

    }
}

