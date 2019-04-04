package com.Dived2014.tanganalyze.analyze.dao;/*
 *国伟
 *2019/3/23
 *10:46
 *
 *
 *
 */

import com.Dived2014.tanganalyze.analyze.entity.PoetryInfo;
import com.Dived2014.tanganalyze.analyze.entity.model.AuthorCount;

import java.util.List;

public interface AnalyzeDao {

    List<AuthorCount> analyzeAuthorCount();
    List<PoetryInfo> queryAllPoetry();
}
