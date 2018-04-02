package edu.zstu.domain;

public class ProtocolInfo {
    private Integer id;

    private Integer protocolId;

    private String protocolDescribe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(Integer protocolId) {
        this.protocolId = protocolId;
    }

    public String getProtocolDescribe() {
        return protocolDescribe;
    }

    public void setProtocolDescribe(String protocolDescribe) {
        this.protocolDescribe = protocolDescribe == null ? null : protocolDescribe.trim();
    }
}