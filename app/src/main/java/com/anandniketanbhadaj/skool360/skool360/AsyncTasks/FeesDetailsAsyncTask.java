package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.FeesModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admsandroid on 9/6/2017.
 */

public class FeesDetailsAsyncTask extends AsyncTask<Void, Void, ArrayList<FeesModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public FeesDetailsAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<FeesModel> doInBackground(Void... params) {
        String responseString = null;
        ArrayList<FeesModel> result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetFeesStatus), param);
            result = ParseJSON.parseFeesDetailsJson(responseString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<FeesModel> result) {
        super.onPostExecute(result);
    }
}


