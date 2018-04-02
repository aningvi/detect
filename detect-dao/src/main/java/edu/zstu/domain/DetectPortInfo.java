package edu.zstu.domain;

public class DetectPortInfo {
    private Integer detectPortId;

    private String detectPortDescribe;

    private String detectPortInfo;

    private Integer detectPortIsValid;

    public Integer getDetectPortId() {
        return detectPortId;
    }

    public void setDetectPortId(Integer detectPortId) {
        this.detectPortId = detectPortId;
    }

    public String getDetectPortDescribe() {
        return detectPortDescribe;
    }

    public void setDetectPortDescribe(String detectPortDescribe) {
        this.detectPortDescribe = detectPortDescribe == null ? null : detectPortDescribe.trim();
    }

    public String getDetectPortInfo() {
        return detectPortInfo;
    }

    public void setDetectPortInfo(String detectPortInfo) {
        this.detectPortInfo = detectPortInfo == null ? null : detectPortInfo.trim();
    }

    public Integer getDetectPortIsValid() {
        return detectPortIsValid;
    }

    public void setDetectPortIsValid(Integer detectPortIsValid) {
        this.detectPortIsValid = detectPortIsValid;
    }
}