package com.anandniketanbhadaj.skool360.skool360.Models;

import java.util.ArrayList;

/**
 * Created by admsandroid on 9/4/2017.
 */

public class UnitTestModel {
    private String TestDate;
    private ArrayList<UnitTestModel.Data> dataArrayList;

    public UnitTestModel() {
    }

    public String getTestDate() {
        return TestDate;
    }

    public void setTestDate(String testDate) {
        TestDate = testDate;
    }


    public ArrayList<UnitTestModel.Data> getDataArrayList() {
        return dataArrayList;
    }

    public void setDataArrayList(ArrayList<UnitTestModel.Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

    public class Data {

        private String Subject;
        private String Detail;

        public Data() {
        }

        public String getSubject() {
            return Subject;
        }

        public void setSubject(String subject) {
            Subject = subject;
        }

        public String getDetail() {
            return Detail;
        }

        public void setDetail(String detail) {
            Detail = detail;
        }



    }
}

