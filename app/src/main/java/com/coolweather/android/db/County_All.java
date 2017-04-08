package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 里吧内 on 2017/4/8.
 */

public class County_All extends DataSupport {

    private String countryName;
    private String provinceName;
    private String cityName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
