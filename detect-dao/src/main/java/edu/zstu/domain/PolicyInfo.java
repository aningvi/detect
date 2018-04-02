package edu.zstu.domain;

public class PolicyInfo {
    private Integer policyId;

    private Integer deviceId;

    private Integer taskId;

    private Byte policyType;

    private Integer detectNum;

    private Integer detectStartTime;

    private Integer curDetectStartTime;

    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Byte getPolicyType() {
        return policyType;
    }

    public void setPolicyType(Byte policyType) {
        this.policyType = policyType;
    }

    public Integer getDetectNum() {
        return detectNum;
    }

    public void setDetectNum(Integer detectNum) {
        this.detectNum = detectNum;
    }

    public Integer getDetectStartTime() {
        return detectStartTime;
    }

    public void setDetectStartTime(Integer detectStartTime) {
        this.detectStartTime = detectStartTime;
    }

    public Integer getCurDetectStartTime() {
        return curDetectStartTime;
    }

    public void setCurDetectStartTime(Integer curDetectStartTime) {
        this.curDetectStartTime = curDetectStartTime;
    }
}