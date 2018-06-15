package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.FeesResponseModel.FeesMainResponse;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by admsandroid on 9/5/2017.
 */

public class FeesAsyncTask extends AsyncTask<Void, Void,FeesMainResponse> {
    HashMap<String, String> param = new HashMap<String, String>();

    public FeesAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected FeesMainResponse doInBackground(Void... params) {
        String responseString = null;
        FeesMainResponse result=null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetFeesStatus), param);
            Gson gson = new Gson();
            result = gson.fromJson(responseString, FeesMainResponse.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(FeesMainResponse result) {
        super.onPostExecute(result);
    }
}

