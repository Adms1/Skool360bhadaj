package com.anandniketanbhadaj.skool360.skool360.Models.ImprestResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 1/2/2018.
 */

public class FinalArrayImprest {
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
