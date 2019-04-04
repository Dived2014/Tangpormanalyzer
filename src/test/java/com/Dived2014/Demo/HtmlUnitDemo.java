package com.Dived2014.Demo;/*
 *国伟
 *2019/3/17
 *10:01
 *
 *
 *
 */

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;

public class HtmlUnitDemo {
    public static void main(String[] args) {
        try(WebClient webClient = new WebClient(BrowserVersion.CHROME)){
            try {
                webClient.getOptions().setJavaScriptEnabled(false);
                webClient.getOptions().setCssEnabled(false);
                HtmlPage page = webClient.getPage(
                        "https://so.gushiwen.org/shiwenv_45c396367f59.aspx");
//                HtmlElement htmlElement = page.getBody();
//                String text = htmlElement.asXml();
//                System.out.println(text);

                //HtmlPage htmlPage = page.getHtmlPage();
                String titlePath = "//div[@class='sons']//div[@class='cont']/h1/text()";
                DomText titleDom = (DomText) page.getByXPath(titlePath).get(0);
                String title = titleDom.asText();

                String authorPath = "//div[@class='cont']/p/a[2]";
                HtmlAnchor authorDom = (HtmlAnchor) page.getByXPath(authorPath).get(0);
                String author = authorDom.asText();

                String dynastyPath = "//div[@class='cont']/p/a[1]";
                HtmlAnchor dynastDom = (HtmlAnchor) page.getByXPath(dynastyPath).get(0);
                String dynast = dynastDom.asText();

                String contentPath = "//div[@class='cont']/div[@class='contson']";
                HtmlDivision contentDom = (HtmlDivision) page.getByXPath(contentPath).get(0);
                String content = contentDom.asText();

                System.out.println(title);
                System.out.println(dynast);
                System.out.println(content);
                System.out.println(author);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
