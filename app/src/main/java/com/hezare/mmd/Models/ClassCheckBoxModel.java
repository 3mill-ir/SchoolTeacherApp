package com.hezare.mmd.Models;

import android.view.View;

/**
 * Created by amirhododi on 8/2/2017.
 */
public class ClassCheckBoxModel {
    private String title, color, darsname, classid, BarnameHaftegiId;
    private View v;
    private int roz, zang;

    public ClassCheckBoxModel() {
    }

    public ClassCheckBoxModel(String title, String color, View v, int roz, int zang, String darsname, String classid, String BarnameHaftegiId) {
        this.title = title;
        this.color = color;
        this.v = v;
        this.roz = roz;
        this.zang = zang;
        this.darsname = darsname;
        this.classid = classid;
        this.BarnameHaftegiId = BarnameHaftegiId;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public String getDarsname() {
        return darsname;
    }

    public void setDarsname(String darsname) {
        this.darsname = darsname;
    }

    public String getBarnameHaftegiId() {
        return BarnameHaftegiId;
    }

    public void setBarnameHaftegiId(String BarnameHaftegiId) {
        this.BarnameHaftegiId = BarnameHaftegiId;
    }


    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }


    public View getView() {
        return v;
    }

    public void setView(View v) {
        this.v = v;
    }


    public int getRoz() {
        return roz;
    }

    public void setRoz(int roz) {
        this.roz = roz;
    }


    public int getZang() {
        return zang;
    }

    public void setZang(int zang) {
        this.zang = zang;
    }


}