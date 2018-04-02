package edu.zstu.domain;

public class IPPoolInfo {
    private Integer id;

    private Integer IPInfo;

    private Integer deviceId;

    private String MACInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIPInfo() {
        return IPInfo;
    }

    public void setIPInfo(Integer IPInfo) {
        this.IPInfo = IPInfo;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getMACInfo() {
        return MACInfo;
    }

    public void setMACInfo(String MACInfo) {
        this.MACInfo = MACInfo == null ? null : MACInfo.trim();
    }
}