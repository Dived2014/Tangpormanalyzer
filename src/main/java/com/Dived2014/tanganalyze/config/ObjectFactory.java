package com.Dived2014.tanganalyze.config;/*
 *国伟
 *2019/4/4
 *13:57
 *
 *
 *
 */

import com.Dived2014.tanganalyze.analyze.dao.AnalyzeDao;
import com.Dived2014.tanganalyze.analyze.dao.impl.AnalyzeDaoImpl;
import com.Dived2014.tanganalyze.analyze.service.AnalyzeService;
import com.Dived2014.tanganalyze.analyze.service.Impl.AnalyzeServiceImpl;
import com.Dived2014.tanganalyze.crawler.Common.Page;
import com.Dived2014.tanganalyze.crawler.Crawler;
import com.Dived2014.tanganalyze.crawler.Parse.DetailsPagePrase;
import com.Dived2014.tanganalyze.crawler.Parse.DocumentPagePrase;
import com.Dived2014.tanganalyze.crawler.Pipeline.ConsolePipeline;
import com.Dived2014.tanganalyze.crawler.Pipeline.DatabasePipeline;
import com.Dived2014.tanganalyze.web.WebController;
import com.alibaba.druid.pool.DruidDataSource;
import com.gargoylesoftware.htmlunit.WebClient;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

public final class ObjectFactory {

    private static final ObjectFactory instace = new ObjectFactory();

    private final Map<Class, Object> objectHashMap = new HashMap<>();

    private ObjectFactory() {
        InitConfigProperties();
        InitDataSource();
        InitCrawler();
        InitWebController();
        printObjectList();

    }

    private void InitWebController() {
        DataSource dataSource = getObject(DataSource.class);
        AnalyzeDao analyzeDao = new AnalyzeDaoImpl(dataSource);
        AnalyzeService analyzeService = new AnalyzeServiceImpl(analyzeDao);
        WebController webController = new WebController(analyzeService);

        objectHashMap.put(WebController.class,webController);
    }

    private void InitCrawler() {
        ConfigProperties configProperties = getObject(ConfigProperties.class);
        DataSource dataSource = getObject(DataSource.class);
        Page page = new Page(configProperties.getCrawlerBase(),
                configProperties.getCrawlerPath(), configProperties.isCrawlerDetail());
        Crawler crawler = new Crawler();
        crawler.addParse(new DocumentPagePrase());
        crawler.addParse(new DetailsPagePrase());
        if (configProperties.isEnableConsole()) {
            crawler.addPipeline(new ConsolePipeline());
        }
        crawler.addPipeline(new DatabasePipeline(dataSource));
        crawler.addPage(page);

        objectHashMap.put(Crawler.class, crawler);

    }

    private void InitDataSource() {
        ConfigProperties configProperties = getObject(ConfigProperties.class);

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(configProperties.getDbUsername());
        dataSource.setPassword(configProperties.getDbPassWord());
        dataSource.setDriverClassName(configProperties.getDbDriverClass());
        dataSource.setUrl(configProperties.getDbUrl());

        objectHashMap.put(DataSource.class, dataSource);
    }

    private void InitConfigProperties() {
        ConfigProperties configProperties = new ConfigProperties();
        objectHashMap.put(ConfigProperties.class, configProperties);
    }

    public <T> T getObject(Class classz) {
        if (!objectHashMap.containsKey(classz)) {
            throw new IllegalArgumentException();
        }
        return (T) objectHashMap.get(classz);
    }

    public static ObjectFactory getInstace() {
        return instace;
    }

    private void printObjectList() {
        for (Map.Entry<Class, Object> entry : objectHashMap.entrySet()) {
            System.out.println(String.format("[%s] \t ==> [%s]",
                    entry.getKey().getCanonicalName(),
                    entry.getValue().getClass().getCanonicalName()));
        }
    }
}
