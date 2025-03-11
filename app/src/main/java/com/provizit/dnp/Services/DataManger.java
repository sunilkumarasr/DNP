package com.provizit.dnp.Services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.provizit.dnp.AESUtil;
import com.provizit.dnp.Conversions;
import com.provizit.dnp.MVVM.RequestModels.CheckSetupModelRequest;
import com.provizit.dnp.MVVM.RequestModels.OtpSendEmaiModelRequest;
import com.provizit.dnp.MVVM.RequestModels.SetUpLoginModelRequest;
import com.provizit.dnp.MVVM.RequestModels.UpdatePwdModelRequest;
import com.provizit.dnp.Utilities.AzureaddetailsModel;
import com.provizit.dnp.Utilities.EmployeeScheduledetailsModel;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataManger {
    private static final String TAG = "DataManger";

    //    public static final String ROOT_URL1 = "https://devapi.provizit.com/provizit/resources/";
    public static final String ROOT_URL1 = "https://liveapi.provizit.com/provizit/resources/";
    //    public static final String ROOT_URL1 = "http://192.168.1.20:8080/provizit/resources/";
//    public static final String ROOT_URL1 = "http://192.168.1.23:8080/provizit/resources/";
//    public static final String ROOT_URL1 = "https://stcapi.provizit.com/provizit/resources/";
    public static final String IMAGE_URL = "https://provizit.com";
    public static String appLanguage = "en";
    private static DataManger dataManager;
    private final Retrofit retrofit1;
    private final Retrofit retrofit2;
    private final Retrofit retrofitSecurity;

    private DataManger() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        HttpLoggingInterceptor logging1 = new HttpLoggingInterceptor();
        logging1.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient1 = new OkHttpClient.Builder();
        httpClient1.connectTimeout(30, TimeUnit.SECONDS);
        httpClient1.readTimeout(120, TimeUnit.SECONDS);
        httpClient1.writeTimeout(120, TimeUnit.SECONDS);
        httpClient1.addInterceptor(logging1);

        HttpLoggingInterceptor loggingSecurity = new HttpLoggingInterceptor();
        loggingSecurity.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClientSecurity = new OkHttpClient.Builder();

        httpClientSecurity.connectTimeout(30, TimeUnit.SECONDS);
        httpClientSecurity.readTimeout(30, TimeUnit.SECONDS);
        httpClientSecurity.writeTimeout(30, TimeUnit.SECONDS);
        httpClientSecurity.addInterceptor(loggingSecurity);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofitSecurity = new Retrofit.Builder().baseUrl(ROOT_URL1)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClientSecurity.build())
                .build();
        retrofit1 = new Retrofit.Builder().baseUrl(IMAGE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        retrofit2 = new Retrofit.Builder().baseUrl(ROOT_URL1)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient1.build())
                .build();

    }

    public static DataManger getDataManager() {
        if (dataManager == null) {
            dataManager = new DataManger();
        }
        return dataManager;
    }

    public static String Pwd_encrypt(Context context, String pwd, String val) {
        AESUtil aesUtil = new AESUtil(context);
        if (context != null) {
            return aesUtil.encrypt(pwd, val);
        }
        return "";
    }

    public void checkSetup(Callback<Model> cb, Context context, CheckSetupModelRequest data) {
        API apiService = retrofitSecurity.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        System.out.println("checkSetup " + newEncrypt);
        String bearer = "Bearer" + newEncrypt;
        Call<Model> call = apiService.checkSetup(bearer, newEncrypt, data);
        call.enqueue(cb);
    }

    public void appuserlogin(Callback<Model> cb, Context context, JsonObject jsonObject) {
        API apiService = retrofitSecurity.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        System.out.println("appuserlogin " + newEncrypt);
        String bearer = "Bearer" + newEncrypt;
        Log.e(TAG, "appuserlogin:newEncrypt " + newEncrypt);
        Log.e(TAG, "appuserlogin:bearer " + bearer);
        Call<Model> call = apiService.appuserlogin(bearer, newEncrypt, jsonObject);
        call.enqueue(cb);
    }

    public void userADlogin(Callback<Model> cb, Context context, JsonObject jsonObject) {
        API apiService = retrofitSecurity.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        System.out.println("userADlogin " + newEncrypt);
        String bearer = "Bearer" + newEncrypt;
        Log.e(TAG, "userADlogin:newEncrypt " + newEncrypt);
        Log.e(TAG, "userADlogin:bearer " + bearer);
        Call<Model> call = apiService.userADlogin(bearer, newEncrypt, jsonObject);
        call.enqueue(cb);
    }

    public void otpsendemail(Callback<Model> cb, Context context, OtpSendEmaiModelRequest otpSendEmaiModelRequest) {
        API apiService = retrofitSecurity.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        String bearer = "Bearer" + newEncrypt;
        Call<Model> call = apiService.otpsendemail(bearer, newEncrypt, otpSendEmaiModelRequest);
        call.enqueue(cb);
    }

    public void setuplogin(Callback<Model> cb, Context context, SetUpLoginModelRequest setUpLoginModelRequest) {
        API apiService = retrofitSecurity.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        String bearer = "Bearer" + newEncrypt;
        Call<Model> call = apiService.setuplogin(bearer, newEncrypt, setUpLoginModelRequest);
        call.enqueue(cb);
    }

    public void updatepwd(Callback<Model> cb, Context context, UpdatePwdModelRequest updatePwdModelRequest) {
        API apiService = retrofitSecurity.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        String bearer = "Bearer" + newEncrypt;
        Call<Model> call = apiService.updatepwd(bearer, newEncrypt, updatePwdModelRequest);
        call.enqueue(cb);
    }

    public void getuserDetails(Callback<Model> cb, Context context) {
        API apiService = retrofitSecurity.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        String bearer = "Bearer" + newEncrypt;
        Call<Model> call = apiService.getuserDetails(bearer, newEncrypt);
        call.enqueue(cb);
    }

    public void getEmployeeDetails(Callback<JsonObject> cb, Context context, String id) {
        API apiService = retrofitSecurity.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        String bearer = "Bearer" + newEncrypt;
        Call<JsonObject> call = apiService.getEmployeeDetails(bearer, newEncrypt, id);
        call.enqueue(cb);
    }

    public void getmeetings(Callback<Model1> cb, Context context, String type, String emp_id, String start, String end) {
        API apiService = retrofitSecurity.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        String bearer = "Bearer" + newEncrypt;
        Call<Model1> call = apiService.getmeetings(bearer, newEncrypt, type, emp_id, start, end);
        call.enqueue(cb);
    }

    public void getEmployees(Callback<Model1> cb, Context context, String comp_id) {
        API apiService = retrofitSecurity.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        String bearer = "Bearer" + newEncrypt;
        Call<Model1> call = apiService.getEmployees(bearer, newEncrypt, comp_id);
        call.enqueue(cb);
    }

    public void getoutlookappointments(Callback<OutlookModel> cb, String type, String email_id, String comp_id, Long start, Long end) {
        API apiService = retrofitSecurity.create(API.class);
        Call<OutlookModel> call = apiService.getoutlookappointments(type, email_id, comp_id, start, end);
        call.enqueue(cb);
    }

    public void getworkingdaymrddetails(Callback<Model> cb, Context context, String id) {
        API apiService = retrofitSecurity.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        String bearer = "BEARER" + newEncrypt;
        Call<Model> call = apiService.getworkingdaymrddetails(bearer, newEncrypt, id);
        call.enqueue(cb);
    }

    public void getuserDetails1(Callback<Model> cb, Context context, String id) {
        API apiService = retrofit2.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        String bearer = "BEARER" + newEncrypt;
        Call<Model> call = apiService.getuserDetails1(bearer, newEncrypt, id);
        call.enqueue(cb);
    }

    public void getbusyScheduledetails(Callback<EmployeeScheduledetailsModel> cb, Context context, String comp_id, String id, String type) {
        API apiService = retrofit2.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        String bearer = "BEARER" + newEncrypt;
        Call<EmployeeScheduledetailsModel> call = apiService.getbusyScheduledetails(bearer, newEncrypt, comp_id, id, type);
        call.enqueue(cb);
    }

    public void getazureaddetails(Callback<AzureaddetailsModel> cb, Context context, String comp_id) {
        API apiService = retrofitSecurity.create(API.class);
        String newEncrypt = Conversions.encrypt(context, false);
        String bearer = "Bearer" + newEncrypt;
        Call<AzureaddetailsModel> call = apiService.getazureaddetails(bearer, newEncrypt, comp_id);
        call.enqueue(cb);
    }

}
