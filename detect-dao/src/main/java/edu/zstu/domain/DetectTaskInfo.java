package edu.zstu.domain;

public class DetectTaskInfo {
    private Integer detectTaskId;

    private String detectTaskDescribe;

    private String detectTaskDomain;

    private Integer detectStartIP;

    private Integer detectEndIP;

    private Integer detectIPTotalNum;

    private Integer detectPortId;

    private Byte detectTaskType;

    private Byte detectIsValid;

    private String detectFileName;

    private Integer detectIntervalTime;

    private Integer detectAppProtocal;

    private String detectFileNameDescribe;

    public Integer getDetectTaskId() {
        return detectTaskId;
    }

    public void setDetectTaskId(Integer detectTaskId) {
        this.detectTaskId = detectTaskId;
    }

    public String getDetectTaskDescribe() {
        return detectTaskDescribe;
    }

    public void setDetectTaskDescribe(String detectTaskDescribe) {
        this.detectTaskDescribe = detectTaskDescribe == null ? null : detectTaskDescribe.trim();
    }

    public String getDetectTaskDomain() {
        return detectTaskDomain;
    }

    public void setDetectTaskDomain(String detectTaskDomain) {
        this.detectTaskDomain = detectTaskDomain == null ? null : detectTaskDomain.trim();
    }

    public Integer getDetectStartIP() {
        return detectStartIP;
    }

    public void setDetectStartIP(Integer detectStartIP) {
        this.detectStartIP = detectStartIP;
    }

    public Integer getDetectEndIP() {
        return detectEndIP;
    }

    public void setDetectEndIP(Integer detectEndIP) {
        this.detectEndIP = detectEndIP;
    }

    public Integer getDetectIPTotalNum() {
        return detectIPTotalNum;
    }

    public void setDetectIPTotalNum(Integer detectIPTotalNum) {
        this.detectIPTotalNum = detectIPTotalNum;
    }

    public Integer getDetectPortId() {
        return detectPortId;
    }

    public void setDetectPortId(Integer detectPortId) {
        this.detectPortId = detectPortId;
    }

    public Byte getDetectTaskType() {
        return detectTaskType;
    }

    public void setDetectTaskType(Byte detectTaskType) {
        this.detectTaskType = detectTaskType;
    }

    public Byte getDetectIsValid() {
        return detectIsValid;
    }

    public void setDetectIsValid(Byte detectIsValid) {
        this.detectIsValid = detectIsValid;
    }

    public String getDetectFileName() {
        return detectFileName;
    }

    public void setDetectFileName(String detectFileName) {
        this.detectFileName = detectFileName == null ? null : detectFileName.trim();
    }

    public Integer getDetectIntervalTime() {
        return detectIntervalTime;
    }

    public void setDetectIntervalTime(Integer detectIntervalTime) {
        this.detectIntervalTime = detectIntervalTime;
    }

    public Integer getDetectAppProtocal() {
        return detectAppProtocal;
    }

    public void setDetectAppProtocal(Integer detectAppProtocal) {
        this.detectAppProtocal = detectAppProtocal;
    }

    public String getDetectFileNameDescribe() {
        return detectFileNameDescribe;
    }

    public void setDetectFileNameDescribe(String detectFileNameDescribe) {
        this.detectFileNameDescribe = detectFileNameDescribe == null ? null : detectFileNameDescribe.trim();
    }
}