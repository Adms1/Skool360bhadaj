package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.CanteenModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

public class CanteenAsyncTask extends AsyncTask<Void, Void, ArrayList<CanteenModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public CanteenAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
        protected ArrayList<CanteenModel> doInBackground(Void... params) {
            String responseString = null;
            ArrayList<CanteenModel> result = null;
            try {
                responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetCanteenMenu), param);
                result = ParseJSON.parseCanteenJson(responseString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<CanteenModel> result) {
            super.onPostExecute(result);
        }
    }