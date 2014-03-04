package org.elsys.TF2Checker;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.print.attribute.standard.Severity;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.elsys.TF2Checker.models.SteamUser;

import com.github.koraktor.steamcondenser.*;
import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.SteamPlayer;
import com.github.koraktor.steamcondenser.steam.community.GameAchievement;
import com.github.koraktor.steamcondenser.steam.community.GameStats;
import com.github.koraktor.steamcondenser.steam.community.SteamGame;
import com.github.koraktor.steamcondenser.steam.community.SteamId;

@Path("")
public class MainServer {
	
	final static String ApiKey = "5315e3ac4cd7b8bb188b4567";
	
	@POST
	@Path("/userSearch")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public static SteamUser GetUserInfo (String userName) throws IOException, SteamCondenserException {
		SteamUser myUser;
		try{
			//SteamId user = SteamId.create(userName);
			myUser = new SteamUser(userName);
		}
		catch(SteamCondenserException ex){
			myUser = new SteamUser(-1);
			return myUser;
		}
		System.out.println("Returning myUser");
		return myUser;
	}

}
