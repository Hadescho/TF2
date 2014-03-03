package org.elsys.TF2Checker.models;

import java.io.IOException;

import org.apache.http.entity.SerializableEntity;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.community.SteamId;


public class SteamUser implements java.io.Serializable{


	public String username;
	public String location;
	public String avatarURL;
	public boolean isOnline;
	public boolean isInGame;
	
	public SteamUser(String Steamusername) throws SteamCondenserException{
		SteamId steamId = SteamId.create(Steamusername);
		this.username = steamId.getNickname();
		this.location = steamId.getLocation();
		this.avatarURL = steamId.getAvatarMediumUrl();
		this.isOnline = steamId.isOnline();
		this.isInGame = steamId.isInGame();
	}
	public SteamUser(int i){
		this.username = "User not found";
		this.location = "Non-existing landia";
		this.avatarURL = "http://socialmediatraininginc.com/wp-content/uploads/2012/02/profile-image.jpg";
		this.isOnline = false;
		this.isInGame = false;
	}
	
	public String serialize() throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
		
	}
	
	public String getUsername() {
		return username;
	}

	public String getLocation() {
		return location;
	}

	public String getAvatarURL() {
		return avatarURL;
	}
	public boolean isOnline() {
		return isOnline;
	}

	public boolean isInGame() {
		return isInGame;
	}
}
