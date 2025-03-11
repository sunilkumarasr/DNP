package com.provizit.dnp.MVVM;
import android.content.Context;
import android.util.Log;
import com.google.gson.JsonObject;
import com.provizit.dnp.MVVM.RequestModels.CheckSetupModelRequest;
import com.provizit.dnp.MVVM.RequestModels.OtpSendEmaiModelRequest;
import com.provizit.dnp.MVVM.RequestModels.SetUpLoginModelRequest;
import com.provizit.dnp.MVVM.RequestModels.UpdatePwdModelRequest;
import com.provizit.dnp.Services.DataManger;
import com.provizit.dnp.Services.Model;
import com.provizit.dnp.Services.Model1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {
    private static final String TAG = "ApiRepository";

    //InitialActivity
    //login
    public void checkSetup(ModelResponse logresponse, Context context, CheckSetupModelRequest checkSetupmodelrequest){
        DataManger dataManager = DataManger.getDataManager();
        dataManager.checkSetup(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()){
                    logresponse.onResponse(response.body());
                    Log.e(TAG, "onResp"+"checkSetup0" );
                }else {
                    Log.e(TAG, "onResp"+"checkSetup1" );
                    logresponse.onFailure(new Throwable(response.message()));
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "onResp"+"checkSetup2" );
                logresponse.onFailure(new Throwable(t));
            }
        },context,checkSetupmodelrequest);
    }

    //InitialActivity
    //login
    public void appuserlogin(ModelResponse logresponse, Context context, JsonObject jsonObject){
        DataManger dataManager = DataManger.getDataManager();
        dataManager.appuserlogin(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()){
                    logresponse.onResponse(response.body());
                    Log.e(TAG, "onResp"+"appuserlogin0" );
                }else {
                    Log.e(TAG, "onResp"+"appuserlogin1" );
                    logresponse.onFailure(new Throwable(response.message()));
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "onResp"+"appuserlogin2" );
                logresponse.onFailure(new Throwable(t));
            }
        },context,jsonObject);
    }

    //InitialActivity
    //login
    public void userADlogin(ModelResponse logresponse, Context context, JsonObject jsonObject){
        DataManger dataManager = DataManger.getDataManager();
        dataManager.userADlogin(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()){
                    logresponse.onResponse(response.body());
                    Log.e(TAG, "onResp"+"userADlogin0" );
                }else {
                    Log.e(TAG, "onResp"+"userADlogin1" );
                    logresponse.onFailure(new Throwable(response.message()));
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "onResp"+"userADlogin2" );
                logresponse.onFailure(new Throwable(t));
            }
        },context,jsonObject);
    }

    //InitialActivity
    //send otp
    public void otpsendemail(ModelResponse logresponse, Context context, OtpSendEmaiModelRequest otpSendEmaiModelRequest){
        DataManger dataManager = DataManger.getDataManager();
        dataManager.otpsendemail(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()){
                    logresponse.onResponse(response.body());
                    Log.e(TAG, "onResp"+"otpsendemail0" );
                }else {
                    Log.e(TAG, "onResp"+"otpsendemail1" );
                    logresponse.onFailure(new Throwable(response.message()));
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "onResp"+"otpsendemail2" );
                logresponse.onFailure(new Throwable(t));
            }
        },context,otpSendEmaiModelRequest);
    }

    //OtpActivity
    //setup login
    public void setuplogin(ModelResponse logresponse, Context context, SetUpLoginModelRequest setUpLoginModelRequest){
        DataManger dataManager = DataManger.getDataManager();
        dataManager.setuplogin(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()){
                    logresponse.onResponse(response.body());
                    Log.e(TAG, "onResp"+"otpsendemail0" );
                }else {
                    Log.e(TAG, "onResp"+"otpsendemail1" );
                    logresponse.onFailure(new Throwable(response.message()));
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "onResp"+"otpsendemail2" );
                logresponse.onFailure(new Throwable(t));
            }
        },context,setUpLoginModelRequest);
    }

    //SetPasswordActivity
    //update pwd
    public void updatepwd(ModelResponse logresponse, Context context, UpdatePwdModelRequest updatePwdModelRequest){
        DataManger dataManager = DataManger.getDataManager();
        dataManager.updatepwd(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()){
                    logresponse.onResponse(response.body());
                    Log.e(TAG, "onResp"+"updatepwd0" );
                }else {
                    Log.e(TAG, "onResp"+"updatepwd1" );
                    logresponse.onFailure(new Throwable(response.message()));
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "onResp"+"updatepwd2" );
                logresponse.onFailure(new Throwable(t));
            }
        },context,updatePwdModelRequest);
    }


    //VisitorDetailsActivity,MeetingDescriptionActivity
    //Employee Details
    public void getEmployeeDetails(JsonObjectResponse jsonObjectResponse, Context context,String Emp_id){
        DataManger dataManager = DataManger.getDataManager();
        dataManager.getEmployeeDetails(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    jsonObjectResponse.onResponse(response.body());
                    Log.e(TAG, "onResp"+"getEmployeeDetails0" );
                }else {
                    Log.e(TAG, "onResp"+"getEmployeeDetails1" );
                    jsonObjectResponse.onFailure(new Throwable(response.message()));
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onResp"+"getEmployeeDetails2" );
                jsonObjectResponse.onFailure(new Throwable(t));
            }
        },context,Emp_id);
    }


    public interface ModelResponse{
        void onResponse(Model loginResponse);
        void onFailure(Throwable t);
    }

    public interface JsonObjectResponse{
        void onResponse(JsonObject jsonObjectResponse);
        void onFailure(Throwable t);
    }



}
