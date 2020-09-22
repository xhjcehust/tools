package com.github.shardsql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.alibaba.fastjson.JSON;

/**
 * @author xiaohengjin <xiaohengjin@kuaishou.com>
 * Created on 2020-07-28
 */
public class SqlRunner {

    private JdbcTemplate jdbcTemplate;

    public void initDataSource(SqlModel sqlModel) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(sqlModel.getUrl(),
                sqlModel.getUsername(), sqlModel.getPassword());
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void update(SqlModel sqlModel) {
        for (int shardId = sqlModel.getShardStartNo(); shardId <= sqlModel.getShardEndNo(); shardId++) {
            String sql = MessageFormat.format(sqlModel.getSql(), shardId);
            System.out.println(sql);
            jdbcTemplate.update(sql);
        }
    }

    public void query(SqlModel sqlModel) {
        for (int shardId = sqlModel.getShardStartNo(); shardId <= sqlModel.getShardEndNo(); shardId++) {
            String sql = MessageFormat.format(sqlModel.getSql(), shardId);
            System.out.println(sql);
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            if (list.size() > 0) {
                System.out.println(JSON.toJSONString(list));
            }
        }
    }

    public void go(SqlModel sqlModel) {
        if (sqlModel == null) {
            System.err.println("sql config is invalid");
            return;
        }

        if (sqlModel.getShardEndNo() < sqlModel.getShardStartNo() ) {
            System.err.println("shardNum is invalid");
            return;
        }

        if (StringUtils.isEmpty(sqlModel.getSql())) {
            System.err.println("sql is empty");
            return;
        }

        if (sqlModel.getSql().startsWith("select") || sqlModel.getSql().startsWith("SELECT")) {
            query(sqlModel);
        } else {
            update(sqlModel);
        }
    }

    public SqlModel parseSqlConfig(String filePath) {
        File file = new File(filePath);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return JSON.parseObject(sbf.toString(), SqlModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("sqlrunner <sql file>");
            return;
        }
        SqlRunner sqlRunner = new SqlRunner();
        SqlModel sqlModel = sqlRunner.parseSqlConfig(args[0]);
        sqlRunner.initDataSource(sqlModel);
        sqlRunner.go(sqlModel);
    }

}
