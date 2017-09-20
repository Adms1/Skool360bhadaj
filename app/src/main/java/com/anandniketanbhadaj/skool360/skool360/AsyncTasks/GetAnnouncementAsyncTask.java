package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.AnnouncementModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

public class GetAnnouncementAsyncTask extends AsyncTask<Void, Void, ArrayList<AnnouncementModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetAnnouncementAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
        protected ArrayList<AnnouncementModel> doInBackground(Void... params) {
            String responseString = null;
            ArrayList<AnnouncementModel> result = null;
            try {
                responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetAnnouncement), param);
                result = ParseJSON.parseAnnouncementJson(responseString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<AnnouncementModel> result) {
            super.onPostExecute(result);
        }
    }