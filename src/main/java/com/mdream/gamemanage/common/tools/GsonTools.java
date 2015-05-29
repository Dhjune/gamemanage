package com.mdream.gamemanage.common.tools;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class GsonTools {
	
	private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}
	
}
