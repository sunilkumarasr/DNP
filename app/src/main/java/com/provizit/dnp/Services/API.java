package com.provizit.dnp.Services;

import com.google.gson.JsonObject;
import com.provizit.dnp.MVVM.RequestModels.CheckSetupModelRequest;
import com.provizit.dnp.MVVM.RequestModels.OtpSendEmaiModelRequest;
import com.provizit.dnp.MVVM.RequestModels.SetUpLoginModelRequest;
import com.provizit.dnp.MVVM.RequestModels.UpdatePwdModelRequest;
import com.provizit.dnp.Utilities.AzureaddetailsModel;
import com.provizit.dnp.Utilities.EmployeeScheduledetailsModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface API {

    @GET("masters/getuserDetails")
    Call<Model> getuserDetails(@Header("Authorization") String Bearer, @Header("DeviceId") String header);

    @GET("company/getEmployeeDetails")
    Call<JsonObject> getEmployeeDetails(@Header("Authorization") String Bearer, @Header("DeviceId") String header, @Query("id") String id);

    @POST("setup/otpsendemailclient")
    Call<Model> otpsendemail(@Header("Authorization") String Bearer, @Header("DeviceId") String header, @Body OtpSendEmaiModelRequest otpSendEmaiModelRequest);

    @POST("setup/setuplogin")
    Call<Model> setuplogin(@Header("Authorization") String Bearer, @Header("DeviceId") String header, @Body SetUpLoginModelRequest setUpLoginModelRequest);

    @POST("setup/checkSetup")
    Call<Model> checkSetup(@Header("Authorization") String Bearer, @Header("DeviceId") String header, @Body CheckSetupModelRequest checksetupsodelrequest);

    @POST("login/appuserlogin")
    Call<Model> appuserlogin(@Header("Authorization") String Bearer, @Header("DeviceId") String header, @Body JsonObject jsonBody);

    @POST("login/userADlogin")
    Call<Model> userADlogin(@Header("Authorization") String Bearer, @Header("DeviceId") String header, @Body JsonObject jsonBody);

    @PUT("useractions/updatepwd")
    Call<Model> updatepwd(@Header("Authorization") String Bearer, @Header("DeviceId") String header, @Body UpdatePwdModelRequest updatePwdModelRequest);

    @GET("meeting/getmeetings")
    Call<Model1> getmeetings(@Header("Authorization") String Bearer, @Header("DeviceId") String header, @Query("type") String type, @Query("emp_id") String emp_id, @Query("start") String start, @Query("end") String end);

    @GET("setup/getoutlookappointments")
    Call<OutlookModel> getoutlookappointments(@Query("type") String type, @Query("email_id") String email_id, @Query("comp_id") String comp_id, @Query("start") Long start, @Query("end") Long end);

    @GET("company/getEmployees")
    Call<Model1> getEmployees(@Header("Authorization") String Bearer, @Header("DeviceId") String header, @Query("comp_id") String comp_id);

    @GET("company/getworkingdaymrddetails")
    Call<Model> getworkingdaymrddetails(@Header("Authorization") String Bearer, @Header("DeviceId") String header, @Query("id") String id);

    @GET("masters/getuserDetails")
    Call<Model> getuserDetails1(@Header("Authorization") String Bearer, @Header("DeviceId") String header, @Query("id") String id);

    //Employee_busyScheduledetails
    @GET("company/getbusyScheduledetails")
    Call<EmployeeScheduledetailsModel> getbusyScheduledetails(@Header("Authorization") String Bearer, @Header("DeviceId") String header, @Query("comp_id") String comp_id, @Query("id") String id, @Query("type") String busy);

    // AzureADdetails
    @GET("forms/getazureaddetails")
    Call<AzureaddetailsModel> getazureaddetails(@Header("Authorization") String Bearer, @Header("DeviceId") String header, @Query("comp_id") String comp_id);

}
