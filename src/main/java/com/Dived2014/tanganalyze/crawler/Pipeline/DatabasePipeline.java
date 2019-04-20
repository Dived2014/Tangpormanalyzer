package com.Dived2014.tanganalyze.crawler.Pipeline;/*
 *国伟
 *2019/3/17
 *15:43
 *
 *
 *
 */
//

import com.Dived2014.tanganalyze.crawler.Common.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.sql.DataSource;
import java.sql.*;

public class DatabasePipeline implements Pipeline{

    private final DataSource dateSource ;

    public DatabasePipeline(DataSource dateSource) {
        this.dateSource = dateSource;
    }

    private final Logger logger = LoggerFactory.getLogger(DatabasePipeline.class);

    @Override
    public void pipeline(final Page page) {

        String dynasty = (String) page.getDataSet().getData("dynast");
        String content = (String) page.getDataSet().getData("content");
        String title = (String) page.getDataSet().getData("title");
        String author = (String) page.getDataSet().getData("author");


        //System.out.println("save to databases" + poetryInfo);
        String sql = "insert into poetry_info (title, dynasty, author, content) values (?,?,?,?)";

        try (Connection connection = dateSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);){

            preparedStatement.setString(1,title);
            preparedStatement.setString(2,dynasty);
            preparedStatement.setString(3,author);
            preparedStatement.setString(4,content);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error("SQL exception {}.",e.getMessage());
        }
    }
}
