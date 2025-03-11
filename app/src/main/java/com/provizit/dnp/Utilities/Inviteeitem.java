package com.provizit.dnp.Utilities;

import java.io.Serializable;
import java.util.ArrayList;

public class Inviteeitem implements Serializable {
    private String name,lname,mobile,mobilecode,location,company,cphone,address,email,designation ,supertype;
    private int verify,mverify;
    private LocationData locations;
    private RoleDetails roleDetails;
//    private ArrayList<Vehicles> vehicles;
//    private ArrayList<Other> other;
//    private ArrayList<Vehicles> belongings;
    private  CommonObject hierarchys;
    private ArrayList<CommonObject> amenities;
     private ArrayList<String> pic,pics;
    private CommonObject _id;

    public ArrayList<CommonObject> getAmenities() {
        return amenities;
    }

    public void setAmenities(ArrayList<CommonObject> amenities) {
        this.amenities = amenities;
    }

    public String getSupertype() {
        return supertype;
    }

    public ArrayList<String> getPics() {
        return pics;
    }

//    public ArrayList<Other> getOther() {
//        return other;
//    }
//
//    public void setOther(ArrayList<Other> other) {
//        this.other = other;
//    }

    public  CommonObject  getHierarchys() {
        return hierarchys;
    }

    public String getDesignation() {
        return designation;
    }

    public LocationData getLocations() {
        return locations;
    }

    public RoleDetails getRoleDetails() {
        return roleDetails;
    }

    public String getName() {
        return name;
    }

    public String getLname() {
        return lname;
    }

    public String getMobile() {
        return mobile;
    }

    public String getMobilecode() {
        return mobilecode;
    }

    public String getLocation() {
        return location;
    }

    public String getCompany() {
        return company;
    }

    public String getCphone() {
        return cphone;
    }

    public String getAddress() {
        return address;
    }

//    public String getBelongings() {
//        return belongings;
//    }

    public String getEmail() {
        return email;
    }



    public int getVerify() {
        return verify;
    }

    public int getMverify() {
        return mverify;
    }

//    public ArrayList<Vehicles> getVehicles() {
//        return vehicles;
//    }


    public ArrayList<String> getPic() {
        return pic;
    }

    public CommonObject get_id() {
        return _id;
    }
}