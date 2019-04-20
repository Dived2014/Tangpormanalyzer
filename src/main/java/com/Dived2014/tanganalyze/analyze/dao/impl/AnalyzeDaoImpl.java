package com.Dived2014.tanganalyze.analyze.dao.impl;/*
 *国伟
 *2019/4/4
 *12:56
 *
 *
 *
 */

import com.Dived2014.tanganalyze.analyze.dao.AnalyzeDao;
import com.Dived2014.tanganalyze.analyze.entity.PoetryInfo;
import com.Dived2014.tanganalyze.analyze.entity.model.AuthorCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeDaoImpl implements AnalyzeDao {

    private final DataSource dataSource;
    private final Logger logger = LoggerFactory.getLogger(AnalyzeDaoImpl.class);

    public AnalyzeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<AuthorCount> analyzeAuthorCount() {
        List<AuthorCount> list = new ArrayList<>();
        String sql = "select author, count(*) as count from poetry_info group by author";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                AuthorCount authorCount = new AuthorCount();
                authorCount.setAuthor(resultSet.getString("author"));
                authorCount.setCount(resultSet.getInt("count"));
                list.add(authorCount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<PoetryInfo> queryAllPoetry() {
        String sql = "select title,dynasty,author,content from poetry_info";
        List<PoetryInfo> list = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                PoetryInfo poetryInfo = new PoetryInfo();
                poetryInfo.setTitle(resultSet.getString("title"));
                poetryInfo.setAuthor(resultSet.getString("author"));
                poetryInfo.setDynasty(resultSet.getString("dynasty"));
                poetryInfo.setContent(resultSet.getString("content"));
                list.add(poetryInfo);
            }


        } catch (SQLException e) {
            logger.error("SQL exception .{}",e.getMessage());
        }
        return list;
    }
}
