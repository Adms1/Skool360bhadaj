package com.anandniketanbhadaj.skool360.skool360.Models;

public class ImprestDataModel {

    private String Message;
    private String Date;
    private String OpeningBalance;
    private String DeductAmount;
    private String Balance;
    private String MyBalance;
    private String OpeningBalanceTop;

    public String getOpeningBalanceTop() {
        return OpeningBalanceTop;
    }

    public void setOpeningBalanceTop(String openingBalanceTop) {
        OpeningBalanceTop = openingBalanceTop;
    }

    public String getMyBalance() {
        return MyBalance;
    }

    public void setMyBalance(String myBalance) {
        MyBalance = myBalance;
    }

    public ImprestDataModel() {
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getOpeningBalance() {
        return OpeningBalance;
    }

    public void setOpeningBalance(String openingBalance) {
        OpeningBalance = openingBalance;
    }

    public String getDeductAmount() {
        return DeductAmount;
    }

    public void setDeductAmount(String deductAmount) {
        DeductAmount = deductAmount;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }
}
