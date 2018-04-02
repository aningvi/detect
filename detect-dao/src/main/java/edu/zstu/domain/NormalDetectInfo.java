package edu.zstu.domain;

public class NormalDetectInfo {
    private Integer id;

    private Integer normalDetectRollCode;

    private Integer normalCurDetectIP;

    private Integer normalDetectStartTime;

    private Integer lastNormalDetectTotalTime;

    private Byte normalProbRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNormalDetectRollCode() {
        return normalDetectRollCode;
    }

    public void setNormalDetectRollCode(Integer normalDetectRollCode) {
        this.normalDetectRollCode = normalDetectRollCode;
    }

    public Integer getNormalCurDetectIP() {
        return normalCurDetectIP;
    }

    public void setNormalCurDetectIP(Integer normalCurDetectIP) {
        this.normalCurDetectIP = normalCurDetectIP;
    }

    public Integer getNormalDetectStartTime() {
        return normalDetectStartTime;
    }

    public void setNormalDetectStartTime(Integer normalDetectStartTime) {
        this.normalDetectStartTime = normalDetectStartTime;
    }

    public Integer getLastNormalDetectTotalTime() {
        return lastNormalDetectTotalTime;
    }

    public void setLastNormalDetectTotalTime(Integer lastNormalDetectTotalTime) {
        this.lastNormalDetectTotalTime = lastNormalDetectTotalTime;
    }

    public Byte getNormalProbRate() {
        return normalProbRate;
    }

    public void setNormalProbRate(Byte normalProbRate) {
        this.normalProbRate = normalProbRate;
    }
}