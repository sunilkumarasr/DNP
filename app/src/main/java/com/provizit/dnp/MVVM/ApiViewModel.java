package com.provizit.dnp.MVVM;
import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.gson.JsonObject;
import com.provizit.dnp.MVVM.RequestModels.CheckSetupModelRequest;
import com.provizit.dnp.MVVM.RequestModels.OtpSendEmaiModelRequest;
import com.provizit.dnp.MVVM.RequestModels.SetUpLoginModelRequest;
import com.provizit.dnp.MVVM.RequestModels.UpdatePwdModelRequest;
import com.provizit.dnp.Services.Model;
import com.provizit.dnp.Services.Model1;

public class ApiViewModel extends ViewModel {
    private static final String TAG = "ApiViewModel";
    //login
    MutableLiveData<Model> checkSetup_response = new MutableLiveData<>();
    MutableLiveData<Model> appuserlogin_response = new MutableLiveData<>();
    MutableLiveData<Model> userADlogin_response = new MutableLiveData<>();
    //send otp
    MutableLiveData<Model> otpsendemail_response = new MutableLiveData<>();
    //setuplogin
    MutableLiveData<Model> setuplogin_response = new MutableLiveData<>();
    //actionnotification
    MutableLiveData<Model> actionnotification_response = new MutableLiveData<>();
    //updatepwd
    MutableLiveData<Model> updatepwd_response = new MutableLiveData<>();
    //employee Details
    MutableLiveData<JsonObject> employeeDetails_response = new MutableLiveData<>();

    ApiRepository apiRepository;
    public ApiViewModel() {
        apiRepository = new ApiRepository();
    }

    //InitialActivity,OtpActivity
    //login
    public void checkSetup(Context context, CheckSetupModelRequest checkSetupmodelrequest) {
        apiRepository.checkSetup(new ApiRepository.ModelResponse() {
            @Override
            public void onResponse(Model loginResponse) {
                checkSetup_response.postValue(loginResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "onResponse: " + "failed");
            }
        }, context, checkSetupmodelrequest);
    }

    //InitialActivity,OtpActivity
    //login
    public void appuserlogin(Context context, JsonObject jsonObject) {
        apiRepository.appuserlogin(new ApiRepository.ModelResponse() {
            @Override
            public void onResponse(Model loginResponse) {
                appuserlogin_response.postValue(loginResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "onResponse: " + "failed");
            }
        }, context, jsonObject);
    }

    //MicrosoftActivity
    //login
    public void userADlogin(Context context, JsonObject jsonObject) {
        apiRepository.userADlogin(new ApiRepository.ModelResponse() {
            @Override
            public void onResponse(Model loginResponse) {
                userADlogin_response.postValue(loginResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "onResponse: " + "failed");
            }
        }, context, jsonObject);
    }

    //InitialActivity
    //send otp
    public void otpsendemail(Context context, OtpSendEmaiModelRequest otpSendEmaiModelRequest) {
        apiRepository.otpsendemail(new ApiRepository.ModelResponse() {
            @Override
            public void onResponse(Model loginResponse) {
                otpsendemail_response.postValue(loginResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "onResponse: " + "failed");
            }
        }, context, otpSendEmaiModelRequest);
    }

    //OtpActivity,SetPasswordActivity
    //setup login
    public void setuplogin(Context context, SetUpLoginModelRequest setUpLoginModelRequest) {
        apiRepository.setuplogin(new ApiRepository.ModelResponse() {
            @Override
            public void onResponse(Model loginResponse) {
                setuplogin_response.postValue(loginResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "onResponse: " + "failed");
            }
        }, context, setUpLoginModelRequest);
    }

    //SetPasswordActivity
    //update pwd
    public void updatepwd(Context context, UpdatePwdModelRequest updatePwdModelRequest) {
        apiRepository.updatepwd(new ApiRepository.ModelResponse() {
            @Override
            public void onResponse(Model loginResponse) {
                updatepwd_response.postValue(loginResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "onResponse: " + "failed");
            }
        }, context, updatePwdModelRequest);
    }

    //VisitorDetailsActivity,MeetingDescriptionActivity
    //Employee Details
    public void getEmployeeDetails(Context context, String Emp_id) {
        apiRepository.getEmployeeDetails(new ApiRepository.JsonObjectResponse() {
            @Override
            public void onResponse(JsonObject l_loginResponse) {
                employeeDetails_response.postValue(l_loginResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "onResponse: " + "failed");
            }
        }, context, Emp_id);
    }


    //login Response
    public LiveData<Model> getcheckSetupResponse() {
        return checkSetup_response;
    }

    //login Response
    public LiveData<Model> getappuserloginResponse() {
        return appuserlogin_response;
    }

    //MicrosoftAd Response
    public LiveData<Model> getuserADloginResponse() {
        return userADlogin_response;
    }

    //otpsend response
    public LiveData<Model> getotpsendemailResponse() {
        return otpsendemail_response;
    }

    //setup login response
    public LiveData<Model> getsetuploginResponse() {
        return setuplogin_response;
    }

    //action notification response
    public LiveData<Model> getactionnotificationResponse() {
        return actionnotification_response;
    }

    //getup date pwd response
    public LiveData<Model> getupdatepwdResponse() {
        return updatepwd_response;
    }

    //get Employee Details
    public LiveData<JsonObject> getEmployeeDetailsResponse() {
        return employeeDetails_response;
    }


}
