package com.somya.appdevproject;

public class activeTender {
    String Title;
    String Description;
    String Tandc;

    public activeTender(){
        //empty constructor needed to create tenders from firstore database
    }

    public activeTender(String Title,String Description,String Tandc){
        this.Title=Title;
        this.Description=Description;
        this.Tandc=Tandc;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getTandc() {
        return Tandc;
    }
}
