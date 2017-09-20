package com.anandniketanbhadaj.skool360.skool360.Models;

public class AnnouncementModel {

    private String CreateDate;
    private String AnnoucementDescription;
    private String AnnoucementPDF;

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getAnnoucementDescription() {
        return AnnoucementDescription;
    }

    public void setAnnoucementDescription(String annoucementDescription) {
        AnnoucementDescription = annoucementDescription;
    }

    public String getAnnoucementPDF() {
        return AnnoucementPDF;
    }

    public void setAnnoucementPDF(String annoucementPDF) {
        AnnoucementPDF = annoucementPDF;
    }

    public AnnouncementModel() {

    }
}
