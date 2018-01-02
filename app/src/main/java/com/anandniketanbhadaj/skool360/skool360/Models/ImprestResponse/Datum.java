package com.anandniketanbhadaj.skool360.skool360.Models.ImprestResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 1/2/2018.
 */

public class Datum {
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("OpeningBalance")
    @Expose
    private String openingBalance;
    @SerializedName("DeductAmount")
    @Expose
    private String deductAmount;
    @SerializedName("Balance")
    @Expose
    private String balance;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(String openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(String deductAmount) {
        this.deductAmount = deductAmount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

}
