package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.CreateLeaveModel;
import com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus.ExamModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;
import com.google.gson.Gson;

import java.util.HashMap;

public class GetLeaveDataAsyncTask extends AsyncTask<Void, Void,ExamModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetLeaveDataAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ExamModel doInBackground(Void... params) {
        String responseString = null;
        ExamModel response =null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetStudentLeaveRequest), param);
            Gson gson = new Gson();
            response = gson.fromJson(responseString, ExamModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(ExamModel result) {
        super.onPostExecute(result);
    }
}

