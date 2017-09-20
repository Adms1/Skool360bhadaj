package com.anandniketanbhadaj.skool360.skool360.Models;

import java.util.ArrayList;

/**
 * Created by admsandroid on 9/5/2017.
 */

public class FeesModel {
    public FeesModel() {
    }

    private String TermTotal;
    private String TermPaid;
    private String TermDuePay;
    private String TermDiscount;
    private String TermLateFee;
    private String Term;

    private ArrayList<Data> dataArrayList;

    public String getTermTotal() {
        return TermTotal;
    }

    public void setTermTotal(String termTotal) {
        TermTotal = termTotal;
    }

    public String getTermPaid() {
        return TermPaid;
    }

    public void setTermPaid(String termPaid) {
        TermPaid = termPaid;
    }

    public String getTermDuePay() {
        return TermDuePay;
    }

    public void setTermDuePay(String termDuePay) {
        TermDuePay = termDuePay;
    }

    public String getTermDiscount() {
        return TermDiscount;
    }

    public void setTermDiscount(String termDiscount) {
        TermDiscount = termDiscount;
    }

    public String getTermLateFee() {
        return TermLateFee;
    }

    public void setTermLateFee(String termLateFee) {
        TermLateFee = termLateFee;
    }

    public String getTerm() {
        return Term;
    }

    public void setTerm(String term) {
        Term = term;
    }

    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }

    public void setDataArrayList(ArrayList<Data> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

    public class Data {

        public Data() {
        }

        private String PreviousBalance;
        private String AdmissionFees;
        private String CautionFees;
        private String TutionFees;
        private String TransportFees;
        private String Imprest;
        private String LateFees;
        private String Discount;
        private String TermFees;
        private String TotalFees;
        private String PaidFees;
        private String TotalPayableFees;
        private String URL;
        private String ButtonVisiblity;

        public String getPreviousBalance() {
            return PreviousBalance;
        }

        public void setPreviousBalance(String previousBalance) {
            PreviousBalance = previousBalance;
        }

        public String getAdmissionFees() {
            return AdmissionFees;
        }

        public void setAdmissionFees(String admissionFees) {
            AdmissionFees = admissionFees;
        }

        public String getCautionFees() {
            return CautionFees;
        }

        public void setCautionFees(String cautionFees) {
            CautionFees = cautionFees;
        }

        public String getTutionFees() {
            return TutionFees;
        }

        public void setTutionFees(String tutionFees) {
            TutionFees = tutionFees;
        }

        public String getTransportFees() {
            return TransportFees;
        }

        public void setTransportFees(String transportFees) {
            TransportFees = transportFees;
        }

        public String getImprest() {
            return Imprest;
        }

        public void setImprest(String imprest) {
            Imprest = imprest;
        }

        public String getLateFees() {
            return LateFees;
        }

        public void setLateFees(String lateFees) {
            LateFees = lateFees;
        }

        public String getDiscount() {
            return Discount;
        }

        public void setDiscount(String discount) {
            Discount = discount;
        }

        public String getTotalFees() {
            return TotalFees;
        }

        public void setTotalFees(String totalFees) {
            TotalFees = totalFees;
        }

        public String getPaidFees() {
            return PaidFees;
        }

        public void setPaidFees(String paidFees) {
            PaidFees = paidFees;
        }

        public String getTotalPayableFees() {
            return TotalPayableFees;
        }

        public void setTotalPayableFees(String totalPayableFees) {
            TotalPayableFees = totalPayableFees;
        }

        public String getTermFees() {
            return TermFees;
        }

        public void setTermFees(String termFees) {
            TermFees = termFees;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public String getButtonVisiblity() {
            return ButtonVisiblity;
        }

        public void setButtonVisiblity(String buttonVisiblity) {
            ButtonVisiblity = buttonVisiblity;
        }


    }
}


