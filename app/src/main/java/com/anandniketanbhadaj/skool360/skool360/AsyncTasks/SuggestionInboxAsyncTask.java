package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.Suggestion.SuggestionInboxModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;
import com.google.gson.Gson;

import java.util.HashMap;

public class SuggestionInboxAsyncTask extends AsyncTask<Void, Void, SuggestionInboxModel> {
    HashMap<String, String> param = new HashMap<>();

    public SuggestionInboxAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected SuggestionInboxModel doInBackground(Void... params) {
        String responseString = null;
        SuggestionInboxModel mainPtmInboxResponse = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetSuggestion), param);
            Gson gson = new Gson();
            mainPtmInboxResponse = gson.fromJson(responseString, SuggestionInboxModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return mainPtmInboxResponse;
    }

    @Override
    protected void onPostExecute(SuggestionInboxModel result) {
        super.onPostExecute(result);
    }
}

