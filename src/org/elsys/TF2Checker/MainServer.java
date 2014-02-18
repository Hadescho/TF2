package org.elsys.TF2Checker;

import java.io.IOException;
import java.net.ServerSocket;

import javax.print.attribute.standard.Severity;

import com.github.koraktor.steamcondenser.*;
import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.SteamPlayer;
import com.github.koraktor.steamcondenser.steam.community.SteamId;


public class MainServer {
	public static int PORT = 27015;
	public static ServerSocket serverSocket;
	public static void main(String[] args) throws IOException, SteamCondenserException {

		SteamId player = SteamId.create("Hadescho");
		System.out.println(player.getGames());
		//Logger.errorLog("TestTest");
	}

}
