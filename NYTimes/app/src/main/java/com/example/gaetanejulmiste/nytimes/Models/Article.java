package com.example.gaetanejulmiste.nytimes.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gaetanejulmiste on 5/15/16.
 */
public class Article implements Serializable{

    String webUrl;
    String thumbNail;
    String headline;


    public String getWebUrl() {
        return webUrl;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public String getHeadline() {
        return headline;
    }

    public Article(JSONObject jsonObject){

        try{

            this.webUrl =jsonObject.getString("web_url");
            this.headline =jsonObject.getJSONObject("headline").getString("main");

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if (multimedia.length() >0){
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbNail = "http://www.nytimes.com/"+ multimediaJson.getString("url");

            }else {
                this.thumbNail="";
            }
        }catch (JSONException e){}
    }


    public static ArrayList<Article> fromJsonArray(JSONArray array){

        ArrayList<Article> results = new ArrayList<>();
        for (int x=0 ; x <array.length(); x++){
            try{
                results.add(new Article(array.getJSONObject(x)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
      return results;
    }






}
