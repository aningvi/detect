package edu.zstu.domain;

public class IPAddress {
    private Integer id;

    private Long IPStart;

    private Long IPEnd;

    private String IPEnglish;

    private String IPChina;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIPStart() {
        return IPStart;
    }

    public void setIPStart(Long IPStart) {
        this.IPStart = IPStart;
    }

    public Long getIPEnd() {
        return IPEnd;
    }

    public void setIPEnd(Long IPEnd) {
        this.IPEnd = IPEnd;
    }

    public String getIPEnglish() {
        return IPEnglish;
    }

    public void setIPEnglish(String IPEnglish) {
        this.IPEnglish = IPEnglish == null ? null : IPEnglish.trim();
    }

    public String getIPChina() {
        return IPChina;
    }

    public void setIPChina(String IPChina) {
        this.IPChina = IPChina == null ? null : IPChina.trim();
    }
}