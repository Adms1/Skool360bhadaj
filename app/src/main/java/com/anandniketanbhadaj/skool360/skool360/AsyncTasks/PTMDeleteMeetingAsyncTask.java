package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.MainPtmSentDeleteResponse;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by admsandroid on 10/31/2017.
 */

public class PTMDeleteMeetingAsyncTask extends AsyncTask<Void, Void, MainPtmSentDeleteResponse> {
    HashMap<String, String> param = new HashMap<String, String>();

    public PTMDeleteMeetingAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MainPtmSentDeleteResponse doInBackground(Void... params) {
        String responseString = null;
        MainPtmSentDeleteResponse mainPtmSentDeleteResponse = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.PTMDeleteMeeting), param);
            Gson gson = new Gson();
            mainPtmSentDeleteResponse = gson.fromJson(responseString, MainPtmSentDeleteResponse.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return mainPtmSentDeleteResponse;
    }

    @Override
    protected void onPostExecute(MainPtmSentDeleteResponse result) {
        super.onPostExecute(result);
    }
}

