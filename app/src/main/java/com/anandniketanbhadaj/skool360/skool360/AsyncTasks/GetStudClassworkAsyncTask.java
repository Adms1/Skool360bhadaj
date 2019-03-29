package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.ClassWorkModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

public class GetStudClassworkAsyncTask extends AsyncTask<Void, Void, ArrayList<ClassWorkModel>> {
    HashMap<String, String> param = new HashMap<>();

    public GetStudClassworkAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
        protected ArrayList<ClassWorkModel> doInBackground(Void... params) {
            String responseString = null;
            ArrayList<ClassWorkModel> result = null;
            try {
                responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetClasswork), param);
                result = ParseJSON.parseStudClassworkJson(responseString);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<ClassWorkModel> result) {
            super.onPostExecute(result);
        }
    }