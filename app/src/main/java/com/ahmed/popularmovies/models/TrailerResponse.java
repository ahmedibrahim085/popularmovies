package com.ahmed.popularmovies.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class TrailerResponse implements Serializable {
    
    private int id;
	private List<TrailerItem> trailerItemList;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTrailerItemList(List<TrailerItem> trailerItemList){
		this.trailerItemList = trailerItemList;
	}

	public List<TrailerItem> getTrailerItemList(){
		return trailerItemList;
	}

	@NonNull
	@Override
 	public String toString(){
		return "{" + "\"id = \"" + ":"+ "\"" +id + "\"" +
			",\"trailerItemList = \"" + ":"+ "\"" + trailerItemList + "\"" +
			"}";
	}
}