package com.Dived2014.tanganalyze.crawler.Pipeline;/*
 *国伟
 *2019/3/17
 *10:45
 *
 *
 *
 */

import com.Dived2014.tanganalyze.crawler.Common.DataSet;
import com.Dived2014.tanganalyze.crawler.Common.Page;

import java.util.Map;

public class ConsolePipeline implements Pipeline {

    @Override
    public void pipeline(final Page page) {
        Map<String,Object> data = page.getDataSet().getData();
        System.out.println(data);
    }
}
