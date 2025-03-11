package com.provizit.dnp.Utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.provizit.dnp.Services.DataManger;
import com.provizit.dnp.Services.Model;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyDetails extends AsyncTask<String, Integer, String> {
    String company_name;
    DatabaseHelper myDb;
    Context activity;

    public CompanyDetails(Context context) {
        activity = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(company_name + " PreExceute", "On pre Exceute......");
    }

    protected String doInBackground(String... strings) {
        myDb = new DatabaseHelper(activity);
        company_name = strings[0];
        Log.d(company_name + " DoINBackGround", "On doInBackground...");
        DataManger dataManager = DataManger.getDataManager();
        dataManager.getuserDetails(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                final Model model = response.body();
//                 progressDialog.dismiss();
                if (model != null) {
//                  approve_btn.setEnabled(true);
                    Integer statuscode = model.getResult();
                    Integer successcode = 200, failurecode = 401, not_verified = 404;
                    if (statuscode.equals(failurecode)) {
                        Log.d(company_name + " subash", "On doInBackground...");
                    } else if (statuscode.equals(successcode)) {
                        CompanyData items = model.getItems();
//                        final boolean b = myDb.(items);
                        Log.d(statuscode+"" , items.toString());
                        DatabaseHelper.companyname = items.getName();
                        System.out.println("sizesf "+items.getPic().size());
                        if(items.getPic().size() != 0){
                            DatabaseHelper.companyImg = items.getPic().get(0);
                        }
                        ArrayList<LocationData> companyAddressArrayList = new ArrayList<>();
                        companyAddressArrayList = items.getAddress();
                        myDb.insertAddress(companyAddressArrayList,items.getName());
                    } else if (statuscode.equals(not_verified)) {
                        Log.d(company_name + " DoINBackGround", "On doInBackground...");
                    }
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
            }
        }, activity);
        return "You are at PostExecute";
    }

    protected void onProgressUpdate(Integer... a) {
        super.onProgressUpdate(a);
        Log.d("tab" + " onProgressUpdate", "You are in progress update ... " + a[0]);
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("asf" + " onPostExecute", "" + result);
    }

}
