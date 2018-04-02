package zstu.utils.network;

import java.io.Serializable;

/**
 * User: Aning
 */
public class IPVO implements Serializable{
    private long start;

    private long iend;

    private Integer provinceId;

    private Integer cityId;

    private Integer districtId;

    private String isp;


    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getIend() {
        return iend;
    }

    public void setIend(long iend) {
        this.iend = iend;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }
}
