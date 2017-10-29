package com.hezare.mmd.WebSide;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by amirhododi on 7/29/2017.
 */


public class Parser {
    public static JSONArray Parse(String root) {
        String prc = root.replaceAll("\\\\", "").replaceAll("^\"|\"$", "");
        JSONArray jArray = null;
        try {
            JSONObject jObject = new JSONObject(prc);
            jArray = jObject.getJSONArray("Root");
        } catch (Exception e) {

        }


        return jArray;
    }


}
