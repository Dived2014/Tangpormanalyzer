package com.Dived2014.tanganalyze.crawler.Parse;/*
 *国伟
 *2019/3/17
 *11:31
 *
 *
 *
 */

import com.Dived2014.tanganalyze.crawler.Common.Page;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;


public class DocumentPagePrase implements Parse {

    @Override
    public void parse(final Page page) {
        if (page.isDetail()) {
            return;
        }
        final AtomicInteger count = new AtomicInteger(0);
        HtmlPage htmlPage = page.getHtmlPage();
        htmlPage.getBody().getElementsByAttribute("div",
                "class", "typecont")
                .forEach(div -> {
                    DomNodeList<HtmlElement> nodeList = div.getElementsByTagName("a");
                    nodeList.forEach(
                            aNode -> {
                                String path = aNode.getAttribute("href");
//                                System.out.println(path);
//                                count.getAndIncrement();
                                page.getSubPage().add(new Page(page.getBase(),path,true));
                            }
                    );
                });

    }
}
