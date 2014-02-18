package org.elsys.TF2Checker;

import java.io.IOException;
import java.net.ServerSocket;

import javax.net.ssl.HttpsURLConnection;
import javax.print.attribute.standard.Severity;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.koraktor.steamcondenser.*;
import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.SteamPlayer;
import com.github.koraktor.steamcondenser.steam.community.SteamId;


public class MainServer {
	@GET
	@Path("/userSearch")
	@Produces(MediaType.APPLICATION_JSON)
	public static SteamId GetUserInfo () throws IOException, SteamCondenserException {

		return SteamId.create("Hadescho");
		
		
	}

}
