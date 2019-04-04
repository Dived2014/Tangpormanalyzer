package com.Dived2014.tanganalyze.crawler.Common;/*
 *国伟
 *2019/3/17
 *10:26
 *
 *
 *
 */

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Page {
    private final String base;
    private final   String path;
    private HtmlPage htmlPage;
    private final boolean detail;
    private Set<Page> subPage = new HashSet<>();
    private DataSet dataSet = new DataSet();



    public String getUrl() {
        return this.getBase() + this.getPath();
    }

}
