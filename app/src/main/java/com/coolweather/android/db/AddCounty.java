package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by limingjian on 17/4/5.
 */

public class AddCounty extends DataSupport {
    private String countyName;

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
}
