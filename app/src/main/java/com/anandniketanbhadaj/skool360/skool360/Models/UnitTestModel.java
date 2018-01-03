package com.anandniketanbhadaj.skool360.skool360.Models;

import java.util.ArrayList;

/**
 * Created by admsandroid on 9/4/2017.
 */

public class UnitTestModel {
    private String TestDate;
    private ArrayList<Data> dataArrayList;

    public UnitTestModel() {
    }

    public String getTestDate() {
        return TestDate;
    }

    public void setTestDate(String testDate) {
        TestDate = testDate;
    }


    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }

    public void setDataArrayList(ArrayList<Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

}

