package com.anandniketanbhadaj.skool360.skool360.AsyncTasks;

import android.os.AsyncTask;

import com.anandniketanbhadaj.skool360.skool360.Models.PaymentLedgerModel;
import com.anandniketanbhadaj.skool360.skool360.Utility.AppConfiguration;
import com.anandniketanbhadaj.skool360.skool360.Utility.ParseJSON;
import com.anandniketanbhadaj.skool360.skool360.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admsandroid on 9/6/2017.
 */

public class GetPaymentLedgerAsyncTask extends AsyncTask<Void, Void, ArrayList<PaymentLedgerModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetPaymentLedgerAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<PaymentLedgerModel> doInBackground(Void... params) {
        String responseString = null;
        ArrayList<PaymentLedgerModel> result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetPaymentLedger), param);
            result = ParseJSON.parsePaymentLedgerJson(responseString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<PaymentLedgerModel> result) {
        super.onPostExecute(result);
    }
}

