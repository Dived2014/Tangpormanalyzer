package com.Dived2014.tanganalyze.crawler;/*
 *国伟
 *2019/3/17
 *10:46
 *
 *
 *
 */

import com.Dived2014.tanganalyze.crawler.Common.Page;
import com.Dived2014.tanganalyze.crawler.Parse.DetailsPagePrase;
import com.Dived2014.tanganalyze.crawler.Parse.DocumentPagePrase;
import com.Dived2014.tanganalyze.crawler.Parse.Parse;
import com.Dived2014.tanganalyze.crawler.Pipeline.ConsolePipeline;
import com.Dived2014.tanganalyze.crawler.Pipeline.Pipeline;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Crawler {

    private final Queue<Page> docQueue = new LinkedBlockingQueue<>();
    private final Queue<Page> detailQueue = new LinkedBlockingQueue<>();

    private final WebClient webClient;

    private final List<Parse> parseList = new LinkedList<>();
    private final List<Pipeline> pipelineList = new LinkedList<>();

    private final ExecutorService executorService;

    public Crawler() {
        this.webClient = new WebClient(BrowserVersion.CHROME);
        this.webClient.getOptions().setJavaScriptEnabled(false);
        this.webClient.getOptions().setCssEnabled(false);

        this.executorService = Executors.newFixedThreadPool(16, new ThreadFactory() {
            private final AtomicInteger id = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Crawler-Thread-" + id.getAndIncrement());
                return thread;
            }
        });
    }

    public void addPage(Page page) {
        this.docQueue.add(page);
    }

    public void start() {
        this.executorService.submit(() -> parse());
        this.executorService.submit(() -> pipeline());
    }

    private void parse() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final Page page = this.docQueue.poll();
            if (page == null) {
                continue;
            }
            this.executorService.submit(() -> {
                try {
                    HtmlPage htmlPage = Crawler.this.webClient.getPage(page.getUrl());
                    page.setHtmlPage(htmlPage);

                    for (Parse parse : Crawler.this.parseList) {
                        parse.parse(page);
                    }
                    if (!page.isDetail()) {

                        Iterator<Page> iterator = page.getSubPage().iterator();
                        while (iterator.hasNext()) {
                            Page subPage = iterator.next();
                            //System.out.println(subPage);
                            Crawler.this.docQueue.add(subPage);
                            iterator.remove();
                        }
                    } else {
                        Crawler.this.detailQueue.add(page);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


        }
    }

    private void pipeline() {
        while (true) {
            final Page page = this.detailQueue.poll();
            if (page == null) {
                continue;
            }
            this.executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (Pipeline pipeline : Crawler.this.pipelineList) {
                        pipeline.pipeline(page);
                    }
                }
            });
        }
    }

    public void addParse(Parse parse) {
        this.parseList.add(parse);
    }

    public void addPipeline(Pipeline pipeline) {
        this.pipelineList.add(pipeline);
    }

    public void stop() {
        if (this.executorService != null && !this.executorService.isShutdown()) {
            this.executorService.shutdown();
        }
    }



}


