package org.elsys.TF2Checker;


import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.print.attribute.standard.Severity;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.elsys.TF2Checker.models.SteamUser;
import org.elsys.TF2Checker.models.DBUser;
import org.json.JSONArray;
import org.json.JSONObject;

import services.BackpackService;

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
			if (userName.matches("[0-9]+") && userName.length() > 2) 
			{
				Long id64 = Long.parseLong(userName);
				myUser = new SteamUser(id64);
			}
			else
			{
				myUser = new SteamUser(userName);
			}
		}
		catch(SteamCondenserException ex){
			myUser = new SteamUser(-1);
			return myUser;
		}
		System.out.println("Returning myUser");
		return myUser;
	}
	
	@GET
	@Path("/backpackValues/{id64}")
	@Produces(MediaType.APPLICATION_JSON)
	public static String GetBackpackValues(@PathParam("id64") long id64) throws ParseException, SQLException{
		Boolean status = false;
		List<DBUser> backpackValues = BackpackService.getInstance().getBackpackValues(id64);
		ArrayList<Long > labels = new ArrayList<Long >();
		ArrayList<Float> values = new ArrayList<Float >();
		for(int i=0; i < backpackValues.size(); i++){
			System.out.println(backpackValues.get(i).getId64() + "\t\t" + backpackValues.get(i).getValue() + "\t\t" + backpackValues.get(i).getFetchDate());
			labels.add((backpackValues.get(i).getFetchDate().getTime()));
			values.add(backpackValues.get(i).getValue());
			
		}
		JSONObject retValues = new JSONObject();
		JSONObject axisX = new JSONObject();
		//axisX.put("interval", 10);
		//axisX.put("intervalType","time");
		axisX.put("labelAngle",-50);
		retValues.put("axisX",axisX);
//		~~~~~~~~ ChartJS ~~~~~~~~~~
//		JSONArray arr = new JSONArray();
//		JSONObject datasets = new JSONObject();
//		retValues.accumulate("labels", labels);
//		datasets.accumulate("fillColor", "rgba(220,220,220,0.5)");
//		datasets.accumulate("strokeColor", "rgba(220,220,220,1)");
//		datasets.accumulate("pointColor", "rgba(220,150,150,1)");
//		datasets.accumulate("pointStrokeColor", "#fff");
//		datasets.accumulate("data", values);
//		arr.put(datasets);
//		retValues.accumulate("datasets" , datasets);
//		System.out.println(retValues.toString());
//		~~~~~~~~ ChartJSEnd ~~~~~~~~~~
//		~~~~~~~~ CanvasJS ~~~~~~~~~~
		JSONArray data = new JSONArray();
		JSONObject dataObj = new JSONObject();
			dataObj.put("type", "line");
			JSONArray dataPoints = new JSONArray();
			for(int i=0; i < labels.size(); i++){
				status = true;
				JSONObject dataPObject = new JSONObject();
				dataPObject.put("x", labels.get(i));
				dataPObject.put("y", values.get(i));
				//dataPObject.put("label", new Date(labels.get(i)));
				dataPoints.put(dataPObject);
			}
			
		dataObj.put("dataPoints", dataPoints);
		data.put(dataObj);
		retValues.put("data",data);
		retValues.put("status",status);
//		~~~~~~~~ CanvasJSEND ~~~~~~~~
		return retValues.toString();
	}

}
