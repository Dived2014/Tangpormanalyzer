package com.Dived2014.tanganalyze.config;/*
 *国伟
 *2019/4/4
 *12:14
 *
 *
 *
 */

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class ConfigProperties {
    private String crawlerBase;
    private String crawlerPath;
    private boolean crawlerDetail;

    private String dbUsername;
    private String dbPassWord;
    private String dbUrl;
    private String dbDriverClass;

    private boolean enableConsole = false;

    public ConfigProperties() {
        InputStream inputStream = ConfigProperties.class.getClassLoader()
                .getResourceAsStream("config.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
            this.crawlerBase = (String) p.get("crawler.base");
            this.crawlerPath = (String) p.get("crawler.path");
            this.crawlerDetail = Boolean.parseBoolean(String.valueOf(p.get("crawler.detail")));
            this.dbUsername = (String) p.get("db.username");
            this.dbPassWord = (String) p.get("db.password");
            this.dbUrl = (String) p.get("db.url");
            this.dbDriverClass = (String) p.get("db.driver_class");

            this.enableConsole = Boolean.valueOf(
                    String.valueOf(p.getProperty("config.enable_console", String.valueOf(false))));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
