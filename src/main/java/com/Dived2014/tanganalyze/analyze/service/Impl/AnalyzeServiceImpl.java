package com.Dived2014.tanganalyze.analyze.service.Impl;/*
 *国伟
 *2019/4/4
 *13:21
 *
 *
 *
 */

import com.Dived2014.tanganalyze.analyze.dao.AnalyzeDao;
import com.Dived2014.tanganalyze.analyze.entity.PoetryInfo;
import com.Dived2014.tanganalyze.analyze.entity.model.AuthorCount;
import com.Dived2014.tanganalyze.analyze.entity.model.WordCount;
import com.Dived2014.tanganalyze.analyze.service.AnalyzeService;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.sql.Connection;
import java.util.*;

public class AnalyzeServiceImpl implements AnalyzeService {

    private final AnalyzeDao analyzeDao;

    public AnalyzeServiceImpl(AnalyzeDao analyzeDao) {
        this.analyzeDao = analyzeDao;
    }

    @Override
    public List<AuthorCount> analyzeAuthorCount() {
        List<AuthorCount> list = analyzeDao.analyzeAuthorCount();
        Collections.sort(list, Comparator.comparingInt(AuthorCount::getCount));
        return list;
    }

    @Override
    public List<WordCount> analyzeWordClout() {

        List<PoetryInfo> list = analyzeDao.queryAllPoetry();
        List<Term> terms = new ArrayList<>();

        Map<String, Integer> map = new HashMap<>();

        for (PoetryInfo poetryInfo : list) {
            String title = poetryInfo.getTitle();
            String content = poetryInfo.getContent();

            terms.addAll(NlpAnalysis.parse(title).getTerms());
            terms.addAll(NlpAnalysis.parse(content).getTerms());

            Iterator iterator = terms.iterator();
            while (iterator.hasNext()) {
                Term temp = (Term) iterator.next();
                if (temp.getNatureStr() == null
                        || temp.getNatureStr().equals("w")) {
                    iterator.remove();
                    continue;
                }
                if (temp.getRealName().length() < 2) {
                    iterator.remove();
                    continue;
                }

                String realName = temp.getRealName();
                Integer count = 0;
                if (map.containsKey(realName)) {
                    count = map.get(realName) + 1;
                } else {
                    count = 1;
                }
                map.put(realName, count);
            }


        }

        List<WordCount> ret = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            WordCount wordCount = new WordCount();
            wordCount.setCount(entry.getValue());
            wordCount.setWord(entry.getKey());
            ret.add(wordCount);
        }
        return ret;
    }


}
