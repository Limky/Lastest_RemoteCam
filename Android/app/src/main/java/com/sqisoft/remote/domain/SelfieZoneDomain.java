package com.sqisoft.remote.domain;

/**
 * Created by SQISOFT on 2016-11-08.
 */
public class SelfieZoneDomain {

    private String index;
    private String name;
    private String zoneDesc;
    private String zoneImageUrl;

    public String getZoneDesc() {
        return zoneDesc;
    }

    public void setZoneDesc(String zoneDesc) {
        this.zoneDesc = zoneDesc;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZoneImageUrl() {
        return zoneImageUrl;
    }

    public void setZoneImageUrl(String zoneImageUrl) {
        this.zoneImageUrl = zoneImageUrl;
    }


}
