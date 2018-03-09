package com.maria.patrunjel.smartimageeditor.retained.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;

public class EditViewRetainedFragment extends Fragment
{
    // data object we want to retain
    private Bitmap image;
    private String filter;
    private Integer redValue,greenValue,blueValue;
    private Float brightness;
    private String menuView;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public Bitmap getImage(){
        return image;
    }

    public void setImage(Bitmap image){
        this.image = image;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Integer getGreenValue() {
        return greenValue;
    }

    public void setGreenValue(Integer greenValue) {
        this.greenValue = greenValue;
    }

    public Integer getBlueValue() {
        return blueValue;
    }

    public void setBlueValue(Integer blueValue) {
        this.blueValue = blueValue;
    }

    public Integer getRedValue() {
        return redValue;
    }

    public void setRedValue(Integer redValue) {
        this.redValue = redValue;
    }

    public Float getBrightness() {
        return brightness;
    }

    public void setBrightness(Float brightness) {
        this.brightness = brightness;
    }

    public String getMenuView(){
        return menuView;
    }

    public void setMenuView(String menuView){
        this.menuView = menuView;
}



}