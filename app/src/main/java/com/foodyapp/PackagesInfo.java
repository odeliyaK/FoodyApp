package com.foodyapp;

public class PackagesInfo {

    private Integer packageID;
    private Integer HouseholdID;
    private String status;

    public PackagesInfo(Integer packageID, Integer householdID, String status) {
        this.packageID = packageID;
        HouseholdID = householdID;
        this.status = status;
    }

    public void setPackageID(Integer packageID) {
        this.packageID = packageID;
    }

    public void setHouseholdID(Integer householdID) {
        HouseholdID = householdID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPackageID() {
        return packageID;
    }

    public Integer getHouseholdID() {
        return HouseholdID;
    }

    public String getStatus() {
        return status;
    }
}
