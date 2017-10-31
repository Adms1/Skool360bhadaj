package com.anandniketanbhadaj.skool360.skool360.Fragments;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.PTMTeacherResponse.PTMStudentWiseTeacher;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by admsandroid on 10/31/2017.
 */

class PTMStudentWiseTeacherAsyncTask extends AsyncTask<Void, Void, PTMStudentWiseTeacher> {
    HashMap<String, String> param = new HashMap<String, String>();

    public PTMStudentWiseTeacherAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected PTMStudentWiseTeacher doInBackground(Void... params) {
        String responseString = null;
        PTMStudentWiseTeacher ptmStudentWiseTeacher = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.PTMStudentWiseTeacher), param);
            Gson gson = new Gson();
            ptmStudentWiseTeacher = gson.fromJson(responseString, PTMStudentWiseTeacher.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ptmStudentWiseTeacher;
    }

    @Override
    protected void onPostExecute(PTMStudentWiseTeacher result) {
        super.onPostExecute(result);
    }
}

