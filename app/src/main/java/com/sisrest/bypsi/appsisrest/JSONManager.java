package com.sisrest.bypsi.appsisrest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Alxndr on 02/12/2016.
 */

public class JSONManager {
/*
    public static JSONObject getJSONfromURL(String url) {
        InputStream is = null;
        String result = "";
        JSONObject json = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
        }

        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
        }

        return json;
    }*/
}