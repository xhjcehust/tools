package com.github.shardsql;

/**
 * @author xiaohengjin <xiaohengjin@kuaishou.com>
 * Created on 2020-07-29
 */
public class SqlModel {

    private String url;

    private String username;

    private String password;

    private int shardStartNo;

    private int shardEndNo;

    private String sql;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getShardStartNo() {
        return shardStartNo;
    }

    public void setShardStartNo(int shardStartNo) {
        this.shardStartNo = shardStartNo;
    }

    public int getShardEndNo() {
        return shardEndNo;
    }

    public void setShardEndNo(int shardEndNo) {
        this.shardEndNo = shardEndNo;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
