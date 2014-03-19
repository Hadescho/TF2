package org.elsys.TF2Checker.models;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.http.entity.SerializableEntity;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import services.BackpackService;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.community.SteamId;


public class SteamUser implements java.io.Serializable{


	public String username;
	public String location;
	public String avatarURL;
	public String id64;
	public String backpackValue;
	public boolean isOnline;
	public boolean isInGame;
	
	public SteamUser(String Steamusername) throws SteamCondenserException, IOException{
		long bpVal;
		SteamId steamId = SteamId.create(Steamusername);
		this.username = steamId.getNickname();
		this.location = steamId.getLocation();
		this.avatarURL = steamId.getAvatarMediumUrl();
		this.isOnline = steamId.isOnline();
		this.isInGame = steamId.isInGame();
		bpVal = steamId.getSteamId64();
		this.id64 = String.valueOf(bpVal);
		this.backpackValue = String.valueOf(this.BackpackValue());
		BackpackService.getInstance().createBackpackValue(new DBUser(this.id64, bpVal));
	}
	public SteamUser(int i){
		this.username = "User not found";
		this.location = "Non-existing landia";
		this.avatarURL = "http://socialmediatraininginc.com/wp-content/uploads/2012/02/profile-image.jpg";
		this.isOnline = false;
		this.isInGame = false;
		this.id64 = "0";
		this.backpackValue = "0";
	}
	
	public float BackpackValue() throws IOException{
		
		String link = "http://backpack.tf/api/IGetUsers/v3/?steamids=" + this.id64;
		URL url = new URL(link);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setUseCaches(false);
		connection.setDoInput(true);
	    connection.setDoOutput(true);
	    DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
	    wr.flush();
	    
	    InputStream is = connection.getInputStream();
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	    String line;
	    StringBuffer response = new StringBuffer(); 
	    while((line = rd.readLine()) != null) {
	    	response.append(line);
	        response.append('\r');
	    }
	    rd.close();
		System.out.println(response.toString());
		JSONObject BPresponse = new JSONObject(response.toString());
		BPresponse = BPresponse.getJSONObject("response");
		if(BPresponse.getInt("success") == 1){
			JSONArray players = BPresponse.getJSONArray("players");
			JSONObject user = players.getJSONObject(0);
			if (this.username.equals(user.getString("name"))){
				//System.out.println((float) user.getDouble("backpack_value"));
				try{
					JSONObject tfBackpack = user.getJSONObject("backpack_value");
					return (float) tfBackpack.getDouble("440");
				}
				catch(Exception ex){
					return -1.0f;
				}
				
			}
		}
		
		return -1.00f;
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
