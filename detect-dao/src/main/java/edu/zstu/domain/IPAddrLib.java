package edu.zstu.domain;

public class IPAddrLib {
    private String ip_start;

    private String ip_end;

    private Long ip_start_num;

    private Long ip_end_num;

    private String continent;

    private String country;

    private String province;

    private String city;

    private String district;

    private String isp;

    private String area_code;

    private String country_english;

    private String country_code;

    private String longitude;

    private String latitude;

    public String getIp_start() {
        return ip_start;
    }

    public void setIp_start(String ip_start) {
        this.ip_start = ip_start == null ? null : ip_start.trim();
    }

    public String getIp_end() {
        return ip_end;
    }

    public void setIp_end(String ip_end) {
        this.ip_end = ip_end == null ? null : ip_end.trim();
    }

    public Long getIp_start_num() {
        return ip_start_num;
    }

    public void setIp_start_num(Long ip_start_num) {
        this.ip_start_num = ip_start_num;
    }

    public Long getIp_end_num() {
        return ip_end_num;
    }

    public void setIp_end_num(Long ip_end_num) {
        this.ip_end_num = ip_end_num;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent == null ? null : continent.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp == null ? null : isp.trim();
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code == null ? null : area_code.trim();
    }

    public String getCountry_english() {
        return country_english;
    }

    public void setCountry_english(String country_english) {
        this.country_english = country_english == null ? null : country_english.trim();
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code == null ? null : country_code.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }
}