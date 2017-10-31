package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.MainPtmSentMessageResponse;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by admsandroid on 10/31/2017.
 */

public class PTMTeacherStudentInsertDetailAsyncTask extends AsyncTask<Void, Void, MainPtmSentMessageResponse> {
    HashMap<String, String> param = new HashMap<String, String>();

    public PTMTeacherStudentInsertDetailAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MainPtmSentMessageResponse doInBackground(Void... params) {
        String responseString = null;
        MainPtmSentMessageResponse mainPtmSentMessageResponse = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.PTMTeacherStudentInsertDetail), param);
            Gson gson = new Gson();
            mainPtmSentMessageResponse = gson.fromJson(responseString, MainPtmSentMessageResponse.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return mainPtmSentMessageResponse;
    }

    @Override
    protected void onPostExecute(MainPtmSentMessageResponse result) {
        super.onPostExecute(result);
    }
}



