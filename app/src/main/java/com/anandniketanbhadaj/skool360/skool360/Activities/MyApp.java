package com.anandniketanbhadaj.skool360.skool360.Activities;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.Utility;
import com.anandniketanbhadaj.skool360.skool360.Utility.webServices;
import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApp extends Application {


    public static Context mAppcontext;
    private String android_id;

    public static Context getAppContext() {
        return mAppcontext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // FontsOverride.setDefaultFont(this, "DEFAULT", "font/opensans_regular.ttf");
        mAppcontext = getApplicationContext();

        Log.d("Token", Utility.getPref(getApplicationContext(), "registration_id"));
        Utility.setPref(mAppcontext, "user_birthday_wish", "0");
        android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d("devicdId", android_id);
//        FontsOverride.setDefaultFont(this, "MONOSPACE", "font/TitilliumWeb-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SERIF", "font/TitilliumWeb-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SANS_SERIF", "font/TitilliumWeb-Regular.ttf");

        try {
//            new GetAPIURLTask(mAppcontext).execute();

//            if (!Utility.isNetworkConnected(mAppcontext)) {
//                progressDialog = new ProgressDialog(mContext);
//                progressDialog.setMessage("Please Wait...");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//            }

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfiguration.BASEURL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            webServices apiService = retrofit.create(webServices.class);

            Call<JsonObject> call = apiService.getBaseUrl(AppConfiguration.GET_API_URL);
            call.enqueue(new Callback<JsonObject>() {

                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull retrofit2.Response<JsonObject> response) {
//                    Utils.dismissDialog();
                    if (response.body() == null) {
                        Utility.ping(mAppcontext, "Something went wrong");
                        return;
                    }
                    if (response.body().get("succcess") == null) {
                        Utility.ping(mAppcontext, "Something went wrong");
                        return;
                    }
                    if (response.body().get("succcess").getAsString().equalsIgnoreCase("0")) {
                        Utility.ping(mAppcontext, "Something went wrong");
                        return;
                    }
                    if (response.body().get("succcess").getAsString().equalsIgnoreCase("1")) {
                        Utility.setPref(mAppcontext, "live_base_url", response.body().get("appsUrl").getAsString());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
//                    Utility.dismissDialog();
                    t.printStackTrace();
                    t.getMessage();
                    Utility.ping(mAppcontext, "Something went wrong");
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
