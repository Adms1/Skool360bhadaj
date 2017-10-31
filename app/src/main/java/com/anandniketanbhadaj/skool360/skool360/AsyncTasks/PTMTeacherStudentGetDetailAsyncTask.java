package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.PTMInboxResponse.MainPtmInboxResponse;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by admsandroid on 10/31/2017.
 */

public class PTMTeacherStudentGetDetailAsyncTask extends AsyncTask<Void, Void, MainPtmInboxResponse> {
    HashMap<String, String> param = new HashMap<String, String>();

    public PTMTeacherStudentGetDetailAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MainPtmInboxResponse doInBackground(Void... params) {
        String responseString = null;
        MainPtmInboxResponse mainPtmInboxResponse = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.PTMTeacherStudentGetDetail), param);
            Gson gson = new Gson();
            mainPtmInboxResponse = gson.fromJson(responseString, MainPtmInboxResponse.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return mainPtmInboxResponse;
    }

    @Override
    protected void onPostExecute(MainPtmInboxResponse result) {
        super.onPostExecute(result);
    }
}
