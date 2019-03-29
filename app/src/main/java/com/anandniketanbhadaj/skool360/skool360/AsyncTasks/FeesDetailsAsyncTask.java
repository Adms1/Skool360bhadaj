package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.FeesModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by admsandroid on 9/6/2017.
 */

public class FeesDetailsAsyncTask extends AsyncTask<Void, Void, FeesModel> {
    HashMap<String, String> param = new HashMap<>();

    public FeesDetailsAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected FeesModel doInBackground(Void... params) {
        String responseString = null;
        FeesModel result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetFeesStatus), param);
            Gson gson = new Gson();
            result = gson.fromJson(responseString, FeesModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(FeesModel result) {
        super.onPostExecute(result);
    }
}


