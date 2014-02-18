package org.elsys.TF2Checker;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.community.SteamId;

public class ManualTests {

	public static void main(String[] args) throws SteamCondenserException {
		// TODO Auto-generated method stub
		SteamId player = SteamId.create("Hadescho");
		System.out.println(player.getLocation());

	}

}
