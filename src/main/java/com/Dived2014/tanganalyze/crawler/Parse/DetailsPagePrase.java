package com.Dived2014.tanganalyze.crawler.Parse;/*
 *国伟
 *2019/3/17
 *10:28
 *
 *
 *
 */

import com.Dived2014.tanganalyze.crawler.Common.Page;

import com.gargoylesoftware.htmlunit.html.*;

public class DetailsPagePrase implements Parse {

    private String titleXpath = "//div[@class='cont']/h1/text()";
    private String dynastyXpath = "//div[@class='cont']/p/a[1]";
    private String authorXpath = "//div[@class='cont']/p/a[2]";
    private String contentXpath = "//div[@class='cont']/[@class='contson']";

    @Override
    public void parse(Page page) {
        if (!page.isDetail()) {
            return;
        }


        /**
         * /html/body/div[3]/div[1]/div[1]/div[1]/h1
         * body > div.main3 > div.left > div:nth-child(1) > div.cont > h1
         */
        HtmlPage htmlPage = page.getHtmlPage();
        String titlePath = "//div[@class='sons']//div[@class='cont']/h1/text()";
        DomText titleDom = (DomText) htmlPage.getByXPath(titlePath).get(0);
        String title = titleDom.asText();

        String authorPath = "//div[@class='cont']/p/a[2]";
        HtmlAnchor authorDom = (HtmlAnchor) htmlPage.getByXPath(authorPath).get(0);
        String author = authorDom.asText();

        String dynastyPath = "//div[@class='cont']/p/a[1]";
        HtmlAnchor dynastDom = (HtmlAnchor) htmlPage.getByXPath(dynastyPath).get(0);
        String dynast = dynastDom.asText();

        String contentPath = "//div[@class='cont']/div[@class='contson']";
        HtmlDivision contentDom = (HtmlDivision) htmlPage.getByXPath(contentPath).get(0);
        String content = contentDom.asText();

//        PoetryInfo poetryInfo = new PoetryInfo();
//        poetryInfo.setAuthor(author);
//        poetryInfo.setDynasty(dynast);
//        poetryInfo.setTitle(title);
//        poetryInfo.setContent(content);

        page.getDataSet().putData("author",author);
        page.getDataSet().putData("dynast",dynast);
        page.getDataSet().putData("title",title);
        page.getDataSet().putData("content",content);


    }
}
