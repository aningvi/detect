package edu.zstu.domain;

public class LogLevelInfo {
    private Integer id;

    private Integer levelId;

    private String levelDescribe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getLevelDescribe() {
        return levelDescribe;
    }

    public void setLevelDescribe(String levelDescribe) {
        this.levelDescribe = levelDescribe == null ? null : levelDescribe.trim();
    }
}