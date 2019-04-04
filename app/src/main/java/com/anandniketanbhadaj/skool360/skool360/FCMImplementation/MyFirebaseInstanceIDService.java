package com.anandniketanbhadaj.skool360.skool360.FCMImplementation;

import android.provider.Settings;
import android.util.Log;

import com.anandniketanbhadaj.skool360.skool360.AsyncTasks.AddDeviceDetailAsyncTask;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //getting old saved token
        String old_token = Utility.getPref(getApplicationContext(), "registration_id");

        if(!old_token.equalsIgnoreCase(refreshedToken)){
            Utility.setPref(getApplicationContext(), "registration_id", refreshedToken);
           // sendRegistrationToServer(refreshedToken);
        }
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token from sevice: " + refreshedToken);
        
    }

    private void sendRegistrationToServer(String token) {
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("StudentID", Utility.getPref(getApplicationContext(), "studid"));
            hashMap.put("DeviceId", Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID));
            hashMap.put("TokenId", token);
            AddDeviceDetailAsyncTask addDeviceDetailAsyncTask = new AddDeviceDetailAsyncTask(hashMap);
            boolean result = addDeviceDetailAsyncTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}