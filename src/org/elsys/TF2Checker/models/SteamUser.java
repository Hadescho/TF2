package org.elsys.TF2Checker.models;

import java.io.IOException;

import org.apache.http.entity.SerializableEntity;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;


public class SteamUser implements java.io.Serializable{
	public String getUsername() {
		return username;
	}

	public String getLocation() {
		return location;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public String username;
	public String location;
	public String avatarURL;
	
	public SteamUser(String username, String location, String avatarURL){
		this.username = username;
		this.location = location;
		this.avatarURL = avatarURL;
	}
	
	public String serialize() throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
		
	}
	
}
