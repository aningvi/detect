package edu.zstu.domain;

public class DeviceGroupInfo {
    private Integer deviceGroupId;

    private String deviceGroupDescribe;

    private Byte detectUDPOrTCP;

    private Integer detectPortId;

    private Short controlCmd;

    private Integer cmdChangeTime;

    public Integer getDeviceGroupId() {
        return deviceGroupId;
    }

    public void setDeviceGroupId(Integer deviceGroupId) {
        this.deviceGroupId = deviceGroupId;
    }

    public String getDeviceGroupDescribe() {
        return deviceGroupDescribe;
    }

    public void setDeviceGroupDescribe(String deviceGroupDescribe) {
        this.deviceGroupDescribe = deviceGroupDescribe == null ? null : deviceGroupDescribe.trim();
    }

    public Byte getDetectUDPOrTCP() {
        return detectUDPOrTCP;
    }

    public void setDetectUDPOrTCP(Byte detectUDPOrTCP) {
        this.detectUDPOrTCP = detectUDPOrTCP;
    }

    public Integer getDetectPortId() {
        return detectPortId;
    }

    public void setDetectPortId(Integer detectPortId) {
        this.detectPortId = detectPortId;
    }

    public Short getControlCmd() {
        return controlCmd;
    }

    public void setControlCmd(Short controlCmd) {
        this.controlCmd = controlCmd;
    }

    public Integer getCmdChangeTime() {
        return cmdChangeTime;
    }

    public void setCmdChangeTime(Integer cmdChangeTime) {
        this.cmdChangeTime = cmdChangeTime;
    }
}