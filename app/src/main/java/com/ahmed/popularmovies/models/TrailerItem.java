package com.ahmed.popularmovies.models;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class TrailerItem implements Serializable {

    // List of Keys of the TRAILER JSON
    private static final String TRAILER_ID = "id";
    private static final String TRAILER_iso_6391 = "iso_639_1";
    private static final String TRAILER_iso_31661 = "iso_3166_1";
    private static final String TRAILER_KEY = "key";
    private static final String TRAILER_NAME = "name";
    private static final String TRAILER_SITE = "site";
    private static final String TRAILER_SIZE = "size";
    private static final String TRAILER_type = "type";

	private String site;
	private int size;
	private String iso31661;
	private String name;
	private String id;
	private String type;
	private String iso6391;
	private String key;

	public void setSite(String site){
		this.site = site;
	}

	public String getSite(){
		return site;
	}

	public void setSize(int size){
		this.size = size;
	}

	public int getSize(){
		return size;
	}

	public void setIso31661(String iso31661){
		this.iso31661 = iso31661;
	}

	public String getIso31661(){
		return iso31661;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setIso6391(String iso6391){
		this.iso6391 = iso6391;
	}

	public String getIso6391(){
		return iso6391;
	}

	public void setKey(String key){
		this.key = key;
	}

	public String getKey(){
		return key;
	}

	@NonNull
	@Override
    public String toString() {
        return
                "{" +
                        "\"site = \"" + ":" + "\"" + site + "\"" +
                        ",\"size = \"" + ":" + "\"" + size + "\"" +
                        ",\"iso_3166_1 = \"" + ":" + "\"" + iso31661 + "\"" +
                        ",\"name = \"" + ":" + "\"" + name + ":" + "\"" +
                        ",\"id = \"" + ":" + "\"" + id + ":" + "\"" +
                        ",\"type = \"" + ":" + "\"" + type + ":" + "\"" +
                        ",\"iso_639_1 = \"" + ":" + "\"" + iso6391 + "\"" +
                        ",\"key = \"" + ":" + "\"" + key + "\"" +
                        "}";
    }

    public static TrailerItem getTrailerItemFromJson(JSONObject trailerInfo) {
        TrailerItem trailerItem = new TrailerItem();
        try {
            trailerItem.setId(trailerInfo.getString(TRAILER_ID));
            trailerItem.setIso6391(trailerInfo.getString(TRAILER_iso_6391));
            trailerItem.setIso31661(trailerInfo.getString(TRAILER_iso_31661));
            trailerItem.setKey(trailerInfo.getString(TRAILER_KEY));
            trailerItem.setName(trailerInfo.getString(TRAILER_NAME));
            trailerItem.setSite(trailerInfo.getString(TRAILER_SITE));
            trailerItem.setSize(trailerInfo.getInt(TRAILER_SIZE));
            trailerItem.setType(trailerInfo.getString(TRAILER_type));

            return trailerItem;
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Log.d(TAG, "onPostExecute -: getMovieItemFromJson AGAIN  ->" + resultsItem.toString());
        return trailerItem;
    }

}
