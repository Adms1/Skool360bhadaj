package com.anandniketanbhadaj.skool360.skool360.Models;

/**
 * Created by admsandroid on 9/7/2017.
 */

public class PrincipalModel {

    public PrincipalModel() {
    }

    private String Image;
    private String Name;
    private String Type;
    private String Discription;
    private String OrderNo;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }
}
