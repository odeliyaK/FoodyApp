package com.foodyapp.model;

public class PackagesInfo {

    private String packageID;
    private String HouseholdID;
    private String houseName;
    private String houseAddress;

    public PackagesInfo(String packageID, String householdID) {
        this.packageID = packageID;
        HouseholdID = householdID;
    }

    public PackagesInfo(String packageID, String householdID, String houseName, String houseAddress) {
        this.packageID = packageID;
        HouseholdID = householdID;
        this.houseName = houseName;
        this.houseAddress = houseAddress;
    }

    public PackagesInfo() {
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public void setHouseholdID(String householdID) {
        HouseholdID = householdID;
    }

    public String getPackageID() {
        return packageID;
    }

    public String getHouseholdID() {
        return HouseholdID;
    }


    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

}
