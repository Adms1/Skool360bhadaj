package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.ImprestResponse.GetImprestDataModel;
import com.anandniketanbhadaj.skool360.skool360.Models.PTMInboxResponse.MainPtmInboxResponse;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class GetImprestDataAsyncTask extends AsyncTask<Void, Void, GetImprestDataModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetImprestDataAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected GetImprestDataModel doInBackground(Void... params) {
        String responseString = null;
        GetImprestDataModel getImprestDataModel = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetImprest), param);
            Gson gson = new Gson();
            getImprestDataModel = gson.fromJson(responseString, GetImprestDataModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return getImprestDataModel;
    }

    @Override
    protected void onPostExecute(GetImprestDataModel result) {
        super.onPostExecute(result);
    }
}
