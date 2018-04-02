package edu.zstu.domain;

public class DeviceLogInfo {
    private Integer id;

    private Integer logTime;

    private String logInfo;

    private Byte logLevel;

    private Integer detectRollCode;

    private Integer detectDeviceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLogTime() {
        return logTime;
    }

    public void setLogTime(Integer logTime) {
        this.logTime = logTime;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo == null ? null : logInfo.trim();
    }

    public Byte getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Byte logLevel) {
        this.logLevel = logLevel;
    }

    public Integer getDetectRollCode() {
        return detectRollCode;
    }

    public void setDetectRollCode(Integer detectRollCode) {
        this.detectRollCode = detectRollCode;
    }

    public Integer getDetectDeviceId() {
        return detectDeviceId;
    }

    public void setDetectDeviceId(Integer detectDeviceId) {
        this.detectDeviceId = detectDeviceId;
    }
}