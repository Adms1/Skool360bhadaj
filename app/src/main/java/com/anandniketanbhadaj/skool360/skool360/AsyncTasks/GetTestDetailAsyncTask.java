package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.UnitTestModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admsandroid on 9/4/2017.
 */

public class GetTestDetailAsyncTask extends AsyncTask<Void, Void, ArrayList<UnitTestModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetTestDetailAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<UnitTestModel> doInBackground(Void... params) {
        String responseString = null;
        ArrayList<UnitTestModel> result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetTestDetail), param);
            result = ParseJSON.parseTestDetailJson(responseString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<UnitTestModel> result) {
        super.onPostExecute(result);
    }
}
