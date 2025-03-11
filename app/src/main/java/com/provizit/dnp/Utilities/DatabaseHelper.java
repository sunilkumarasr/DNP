package com.provizit.dnp.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    public static final String DATABASE_NAME = "Provizit.db";
    public static String companyname;
    public static String companyImg="";
    public static final String EMPTABLE = "emp_table";
    public static final String COMPANYTABLE = "company_table";
    public static final String AMENITIES = "amenities";
    public static final String WORKFLOWTABLE = "workflow_table";
    public static final String LOCATIONTABLE = "location_table";
    public static final String ROLETABLE = "role_table";
    public static final String TOKENS = "token_table";
    public static final String RECCURING = "Reccuring";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERID";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "AUTHKEY";
    public static final String COL_5 = "STATUS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TOKENS + " (ID INTEGER,TYPE TEXT,VALUE TEXT,TOKEN TEXT,STATUS INTEGER)");
        db.execSQL("create table " + COMPANYTABLE + " (ID INTEGER,NAME TEXT,EMAIL TEXT,MOBILE TEXT,START TEXT,END1 TEXT)");
        db.execSQL("create table " + WORKFLOWTABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,W_ID TEXT)");
        db.execSQL("create table " + AMENITIES + " (NAME TEXT)");
        db.execSQL("create table " + LOCATIONTABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,NAME TEXT,LOCATION TEXT,PHONE TEXT,LINE TEXT,PHONECODE TEXT,COMPANYNAME TEXT)");
        //29/04/2020  NAME LOCATION PHONECODE PHONE LINE
         db.execSQL("create table " + ROLETABLE + " (ID INTEGER,NAME TEXT,DESCR TEXT,LOCATION TEXT,HIERARCHY TEXT,APPROVER TEXT,MEETING TEXT,REPORTS TEXT,SMEETING TEXT,DVISITORS TEXT,OPERATIONS TEXT,RMEETING TEXT,UVISITORS TEXT,BYDEFAULT TEXT,CHECKIN TEXT,AWORKFLOW TEXT,SECURITY TEXT)");
        db.execSQL("create table " + EMPTABLE + " (ID INTEGER,EMP_ID TEXT,NAME TEXT,DESIGNATION TEXT,EMAIL TEXT,MOBILE TEXT,MOBILECODE TEXT,ROLEID TEXT,ROLENAME TEXT,LOCATION TEXT,H_IID TEXT,H_ID TEXT,APPROVER TEXT,EMPIMG TEXT)");
        db.execSQL("create table " + RECCURING + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,RECCURINGID TEXT,DATES_STAMPS TEXT,SELECTION_TYPE TEXT,FROM_DATE_MILLS TEXT,TO_DATE_MILLS TEXT,DTSTART TEXT,DTEND TEXT,WEEKENDS TEXT,FROM_DAY TEXT,FROM_MONTH TEXT,FROM_YEAR TEXT,TO_DAY TEXT,TO_MONTH TEXT,TO_YEAR TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TOKENS);
        db.execSQL("DROP TABLE IF EXISTS " + AMENITIES);
        db.execSQL("DROP TABLE IF EXISTS " + EMPTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ROLETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COMPANYTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WORKFLOWTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LOCATIONTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECCURING);
         onCreate(db);
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TOKENS, null);
        return res;
    }
    public void insertAddress(ArrayList<LocationData> locationDataArrayList,String Companyname) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + LOCATIONTABLE);
        ArrayList<LocationData> locationDataArrayList1 = new ArrayList<>();
        locationDataArrayList1 = locationDataArrayList;

        for (int i = 0; i < locationDataArrayList1.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("NAME", locationDataArrayList1.get(i).getName());
            contentValues.put("LOCATION", locationDataArrayList1.get(i).getLocation());
            contentValues.put("PHONE", locationDataArrayList1.get(i).getPhone());
            contentValues.put("LINE", locationDataArrayList1.get(i).getLine());
            contentValues.put("PHONECODE", locationDataArrayList1.get(i).getPhonecode());
            contentValues.put("COMPANYNAME", Companyname);
            long result = db.insert(LOCATIONTABLE, null, contentValues);
        }
    }
    public void clearDatabase(String your_table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TOKENS);
        // Repeat this for each table or write logic to delete all tables
        db.close();
    }
    public void insertAmenities(ArrayList<CommonObject> commonObjectArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();


        db.execSQL("delete from " + AMENITIES);
//            // show message


        ArrayList<CommonObject> commonObjectArrayList1 = new ArrayList<>();
        commonObjectArrayList1 = commonObjectArrayList;

        for (int i = 0; i < commonObjectArrayList1.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("NAME", commonObjectArrayList1.get(i).getName());
            long result = db.insert(AMENITIES, null, contentValues);
        }
    }
    public void update(String Table_name,String ColumnName,String newValue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ColumnName, newValue);
        long result =  db.update(Table_name, cv,  "ID = ?", new String[] {"1"});
        System.out.println("Update img "+Table_name);
        System.out.println("Update img "+newValue);
        System.out.println("Update img "+ColumnName);
        System.out.println("Update img "+result);

    }
    public ArrayList<CommonObject> getCompanyAmenities() {
        ArrayList<CommonObject> commonObjectArrayList= new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + AMENITIES, null);
        if (res.getCount() == 0) {

           // show message
            Log.e("Error", "Nothing found");
            return commonObjectArrayList;
        }
        while (res.moveToNext()) {
            System.out.println(AMENITIES + res.getCount());
            CommonObject commonObject = new CommonObject();
            commonObject.setName(res.getString(0));

            commonObjectArrayList.add(commonObject);
        }
        return commonObjectArrayList;
    }
    public ArrayList<LocationData> getCompanyAddress() {
        ArrayList<LocationData> locationDataArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + LOCATIONTABLE, null);
        if (res.getCount() == 0) {

//            // show message
            Log.e("Error", "Nothing found");
            return locationDataArrayList;
        }
        while (res.moveToNext()) {
            System.out.println(LOCATIONTABLE + res.getCount());
            LocationData locationData = new LocationData();
            locationData.setName(res.getString(1));
            locationData.setLocation(res.getString(2));
            locationData.setPhone(res.getString(3));
            locationData.setLine(res.getString(4));
            locationData.setPhonecode(res.getString(5));
            locationData.setCompanyname(res.getString(6));
            locationDataArrayList.add(locationData);
        }
        return locationDataArrayList;
    }

    public Integer deleteData(String table_name, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_name, "ID = ?", new String[]{id});
    }


    public boolean insertEmp(String id, EmpData empData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + EMPTABLE);
        EmpData empData1 = new EmpData();
        empData1 = empData;
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", 1);
        contentValues.put("EMP_ID", id);
        contentValues.put("NAME", empData1.getName());
        contentValues.put("DESIGNATION", empData1.getDesignation());
        contentValues.put("EMAIL", empData1.getEmail());
        contentValues.put("MOBILE", empData1.getMobile());
        contentValues.put("MOBILECODE", empData1.getMobilecode());
        contentValues.put("ROLEID", empData1.getRoleid());
        contentValues.put("ROLENAME", empData1.getRolename());
        contentValues.put("LOCATION", empData1.getLocation());
        contentValues.put("H_IID", empData1.getHierarchy_indexid());
        contentValues.put("H_ID", empData1.getHierarchy_id());
        contentValues.put("APPROVER", empData1.getApprover());
        if(empData1.getPic() != null && empData1.getPic().size() != 0){

            contentValues.put("EMPIMG", empData1.getPic().get(empData1.getPic().size()-1));
        }
        else {
            contentValues.put("EMPIMG","");
        }


        long result = db.insert(EMPTABLE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }



    public boolean insertRole(RoleDetails roleDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + ROLETABLE);
        RoleDetails roleDetails1 = new RoleDetails();
        roleDetails1 = roleDetails;
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", 1);
        contentValues.put("NAME", roleDetails1.getName());
        contentValues.put("DESCR", roleDetails1.getDesc());
        contentValues.put("LOCATION", roleDetails1.getLocation());
        contentValues.put("HIERARCHY", roleDetails1.getHierarchy());
        contentValues.put("APPROVER", roleDetails1.getApprover());
        contentValues.put("MEETING", roleDetails1.getMeeting());
        contentValues.put("REPORTS", roleDetails1.getReports());
        contentValues.put("SMEETING", roleDetails1.getMeeting());
        contentValues.put("DVISITORS", roleDetails1.getDvisitors());
        contentValues.put("OPERATIONS", roleDetails1.getOperations());
        contentValues.put("RMEETING", roleDetails1.getRmeeting());
        contentValues.put("UVISITORS", roleDetails1.getUvisitors());
        contentValues.put("BYDEFAULT", roleDetails1.getBydefault());
        contentValues.put("CHECKIN", roleDetails1.getCheckin());
        contentValues.put("AWORKFLOW", roleDetails1.getAworkflow());
        contentValues.put("SECURITY", roleDetails1.getSecurity());
        long result = db.insert(ROLETABLE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean insertTokenDetails(String type, String value, String token, int staus) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TOKENS);


        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", 1);
        contentValues.put("TYPE", type);
        contentValues.put("VALUE", value);
        contentValues.put("TOKEN", token);
        contentValues.put("STATUS", staus);
        long result = db.insert(TOKENS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public EmpData getEmpdata() {
        EmpData empData = new EmpData();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + EMPTABLE, null);
        if (res.getCount() == 0) {
//            // show message
            Log.e("Error", "Nothing found");
            return empData;
        }
        while (res.moveToNext()) {
//          (ID INTEGER,EMP_ID TEXT,NAME TEXT,DESIGNATION TEXT,EMAIL TEXT,MOBILE TEXT,MOBILECODE TEXT,ROLEID TEXT,ROLENAME TEXT,LOCATION TEXT,H_IID TEXT,H_ID TEXT,APPROVER TEXT)
            empData.setEmp_id(res.getString(1));
            empData.setName(res.getString(2));
            empData.setDesignation(res.getString(3));
            empData.setEmail(res.getString(4));
            empData.setMobile(res.getString(5));
            empData.setMobilecode(res.getString(6));
            empData.setRoleid(res.getString(7));
            empData.setRolename(res.getString(8));
            empData.setLocation(res.getString(9));
            empData.setHierarchy_indexid(res.getString(10));
            empData.setHierarchy_id(res.getString(11));
            empData.setApprover(res.getString(12));
            empData.setEmp_image1(res.getString(13));
        }
        return empData;
    }
    public RoleDetails getRole() {

        RoleDetails roleDetails = new RoleDetails();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + ROLETABLE, null);
        if (res.getCount() == 0) {
//            // show message
            Log.e("Error", "Nothing found");
            return roleDetails;
        }
        while (res.moveToNext()) {
//       ,  TEXT,  TEXT,  TEXT,  TEXT,  TEXT,  TEXT,  TEXT,  TEXT,  TEXT,  TEXT,  TEXT,  TEXT,  TEXT,  TEXT,  TEXT,  TEXT)");

//        ,DESC,LOCATION,HIERARCHY,APPROVER,MEETING,REPORTS,SMEETING,VISITORS,DVISITORS,OPERATIONS,RMEETING,UVISITORS,BYDEFAULT,CHECKIN,AWORKFLOW,SECURITY
             roleDetails.setName(res.getString(1));
            roleDetails.setDesc(res.getString(2));
            roleDetails.setLocation(res.getString(3));
            roleDetails.setHierarchy(res.getString(4));
            roleDetails.setApprover(res.getString(5));
            roleDetails.setMeeting(res.getString(6));
            roleDetails.setReports(res.getString(7));
            roleDetails.setSmeeting(res.getString(8));
//            roleDetails.setVisitors(res.getString(9));
            roleDetails.setDvisitors(res.getString(9));
            roleDetails.setOperations(res.getString(10));
            roleDetails.setRmeeting(res.getString(11));
            roleDetails.setUvisitors(res.getString(12));
            roleDetails.setBydefault(res.getString(13));
            roleDetails.setCheckin(res.getString(14));
            roleDetails.setAworkflow(res.getString(15));
            roleDetails.setSecurity(res.getString(16));
        }
        return roleDetails;
    }
    public void logout(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TOKENS);
    }




}