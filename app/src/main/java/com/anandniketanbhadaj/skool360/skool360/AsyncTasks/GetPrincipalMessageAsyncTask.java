package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.PrincipalModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admsandroid on 9/7/2017.
 */

public class GetPrincipalMessageAsyncTask extends AsyncTask<Void, Void, ArrayList<PrincipalModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetPrincipalMessageAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<PrincipalModel> doInBackground(Void... params) {
        String responseString = null;
        ArrayList<PrincipalModel> result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetPrincipalMessage), param);
            result = ParseJSON.getPrincipalMessageJson(responseString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<PrincipalModel> result) {
        super.onPostExecute(result);
    }
}