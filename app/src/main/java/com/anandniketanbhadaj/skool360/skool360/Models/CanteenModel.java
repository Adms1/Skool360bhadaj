package com.anandniketanbhadaj.skool360.skool360.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class CanteenModel {

    private String FromDate;
    private String ToDate;
    private ArrayList<HashMap<String, String>> canteenData = new ArrayList<>();

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public ArrayList<HashMap<String, String>> getCanteenData() {
        return canteenData;
    }

    public void setCanteenData(ArrayList<HashMap<String, String>> canteenData) {
        this.canteenData = canteenData;
    }

    public CanteenModel() {

    }
}
