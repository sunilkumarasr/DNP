package com.provizit.dnp.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CommonObject implements Serializable {
   private String $oid,name,$numberLong,value;

    public String getValue() {
        return value;
    }

    public String get$numberLong() {
        return $numberLong;
    }

    public String getName() {
        return name;
    }

    public String get$oid() {
        return $oid;
    }

    public void set$oid(String $oid) {
        this.$oid = $oid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void set$numberLong(String $numberLong) {
        this.$numberLong = $numberLong;
    }

    public JSONObject get_id(){
        JSONObject jo = new JSONObject();
        try {
            jo.put("$oid", this.$oid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo;
    }
    public JSONObject getCreated_time(){
        JSONObject jo = new JSONObject();
        try {
            jo.put("$numberLong", this.$numberLong);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo;
    }


}
