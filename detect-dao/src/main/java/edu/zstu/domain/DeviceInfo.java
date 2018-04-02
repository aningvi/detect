package edu.zstu.domain;

public class DeviceInfo {
    private Integer deviceId;

    private String deviceDescribe;

    private Integer deviceGroupId;

    private Integer normalStartIP;

    private Integer normalEndIP;

    private Integer normalDetectRollCode;

    private Integer deviceBeatInfo;

    private Integer currentRunStatus;

    private Integer specialDetectStartTime;

    private Integer specialDetectRollCode;

    private Integer specialDetectRate;

    private Integer lastSpecialDetectTotalTime;

    private Byte deviceCtrlCmd;

    private Byte netWorkGroupId;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceDescribe() {
        return deviceDescribe;
    }

    public void setDeviceDescribe(String deviceDescribe) {
        this.deviceDescribe = deviceDescribe == null ? null : deviceDescribe.trim();
    }

    public Integer getDeviceGroupId() {
        return deviceGroupId;
    }

    public void setDeviceGroupId(Integer deviceGroupId) {
        this.deviceGroupId = deviceGroupId;
    }

    public Integer getNormalStartIP() {
        return normalStartIP;
    }

    public void setNormalStartIP(Integer normalStartIP) {
        this.normalStartIP = normalStartIP;
    }

    public Integer getNormalEndIP() {
        return normalEndIP;
    }

    public void setNormalEndIP(Integer normalEndIP) {
        this.normalEndIP = normalEndIP;
    }

    public Integer getNormalDetectRollCode() {
        return normalDetectRollCode;
    }

    public void setNormalDetectRollCode(Integer normalDetectRollCode) {
        this.normalDetectRollCode = normalDetectRollCode;
    }

    public Integer getDeviceBeatInfo() {
        return deviceBeatInfo;
    }

    public void setDeviceBeatInfo(Integer deviceBeatInfo) {
        this.deviceBeatInfo = deviceBeatInfo;
    }

    public Integer getCurrentRunStatus() {
        return currentRunStatus;
    }

    public void setCurrentRunStatus(Integer currentRunStatus) {
        this.currentRunStatus = currentRunStatus;
    }

    public Integer getSpecialDetectStartTime() {
        return specialDetectStartTime;
    }

    public void setSpecialDetectStartTime(Integer specialDetectStartTime) {
        this.specialDetectStartTime = specialDetectStartTime;
    }

    public Integer getSpecialDetectRollCode() {
        return specialDetectRollCode;
    }

    public void setSpecialDetectRollCode(Integer specialDetectRollCode) {
        this.specialDetectRollCode = specialDetectRollCode;
    }

    public Integer getSpecialDetectRate() {
        return specialDetectRate;
    }

    public void setSpecialDetectRate(Integer specialDetectRate) {
        this.specialDetectRate = specialDetectRate;
    }

    public Integer getLastSpecialDetectTotalTime() {
        return lastSpecialDetectTotalTime;
    }

    public void setLastSpecialDetectTotalTime(Integer lastSpecialDetectTotalTime) {
        this.lastSpecialDetectTotalTime = lastSpecialDetectTotalTime;
    }

    public Byte getDeviceCtrlCmd() {
        return deviceCtrlCmd;
    }

    public void setDeviceCtrlCmd(Byte deviceCtrlCmd) {
        this.deviceCtrlCmd = deviceCtrlCmd;
    }

    public Byte getNetWorkGroupId() {
        return netWorkGroupId;
    }

    public void setNetWorkGroupId(Byte netWorkGroupId) {
        this.netWorkGroupId = netWorkGroupId;
    }
}