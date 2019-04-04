package com.Dived2014.tanganalyze.analyze.service;/*
 *国伟
 *2019/4/4
 *13:18
 *
 *
 *
 */

import com.Dived2014.tanganalyze.analyze.entity.model.AuthorCount;
import com.Dived2014.tanganalyze.analyze.entity.model.WordCount;

import java.util.List;

public interface AnalyzeService {
    List<AuthorCount> analyzeAuthorCount();
    List<WordCount> analyzeWordClout();
}
