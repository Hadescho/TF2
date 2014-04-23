package org.elsys.TF2Checker;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.elsys.TF2Checker.models.DBUser;
import org.elsys.TF2Checker.models.SteamUser;
import org.elsys.TF2Checker.models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.BackpackService;
import services.UserService;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.community.SteamId;

@Path("")
public class MainServer {
	
	final static String ApiKey = "5315e3ac4cd7b8bb188b4567";
	
	@POST
	@Path("/userSearch")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public static String GetUserInfo (@Context SecurityContext securityContext, String userName) throws IOException, SteamCondenserException {
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
			return strigifier(myUser).toString();
		}
		System.out.println("Returning myUser");
		System.out.println(securityContext.getUserPrincipal().getName());
	
		JSONObject myJ = strigifier(myUser);
		System.out.println(myJ.toString(1));
		return myJ.toString();
	}
	
	@POST
	@Path("/api/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static void login(String input) throws JSONException, SQLException{
		System.out.println(input);
		JSONObject myObj = new JSONObject(input);
		
		User myUser = UserService.getInstance().getUsers(myObj.getString("email"), myObj.getString("password"));
		
	}
	
	@POST
	@Path("/api/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static void register(String input) throws NumberFormatException, JSONException, SQLException{
		System.out.println(input);
		JSONObject myObj = new JSONObject(input);
		System.out.println(myObj.toString(6));
		User myUser = new User(myObj.getString("email"), 
				myObj.getString("password"), Long.parseLong(myObj.getString("id64")));
		System.out.println(myUser.email + "\t" + myUser.getPassword() + "\t" + myUser.id64);
		UserService.getInstance().createUser(myUser);
		
	}
	
	
	private static JSONObject stringifier(User myUser){
		JSONObject myJ = new JSONObject(myUser);
		myJ.remove("id64");
		myJ.put("id64", String.valueOf(myUser.getId64()));
		return myJ;
	}
	
	/**
	 * @param myUser
	 * @return
	 * @throws JSONException
	 */
	private static JSONObject strigifier(SteamUser myUser) throws JSONException {
		JSONObject myJ = new JSONObject(myUser);
		myJ.remove("id64");
		myJ.put("id64",String.valueOf(myUser.id64));
		myJ.put("backpackValue", String.valueOf(myUser.backpackValue));
		return myJ;
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
		axisX.put("labelAngle",-50);
		retValues.put("axisX",axisX);
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
				dataPoints.put(dataPObject);
			}
			
		dataObj.put("dataPoints", dataPoints);
		data.put(dataObj);
		retValues.put("data",data);
		retValues.put("status",status);
//		~~~~~~~~ CanvasJSEND ~~~~~~~~
		return retValues.toString();
	}
	
	@GET
	@Path("/backpackValues/{idFirst}&{idSecond}")
	@Produces(MediaType.APPLICATION_JSON)
	public static String GetBackpackComparison(@PathParam("idFirst") String first, @PathParam("idSecond") String second) throws SQLException, SteamCondenserException{
		Boolean status = false;
		SteamId steamFirst = SteamId.create(first);
		long idFirst = steamFirst.getSteamId64();
		SteamId steamSecond = SteamId.create(second);
		long idSecond = steamSecond.getSteamId64();
		List<DBUser> backpackValuesFirst = BackpackService.getInstance().getBackpackValues(idFirst);
		List<DBUser> backpackValuesSecond = BackpackService.getInstance().getBackpackValues(idSecond);
		ArrayList<Long > labelsFirst = new ArrayList<Long >();
		ArrayList<Long > labelsSecond = new ArrayList<Long >();
		ArrayList<Float> valuesFirst = new ArrayList<Float>();
		ArrayList<Float>  valuesSecond = new ArrayList<Float>();
		for(int i = 0; i < backpackValuesFirst.size(); i++){
			System.out.println(backpackValuesFirst.get(i).getId64() + "\t\t" + backpackValuesFirst.get(i).getValue() + "\t\t" + backpackValuesFirst.get(i).getFetchDate());
			labelsFirst.add((backpackValuesFirst.get(i).getFetchDate().getTime()));
			valuesFirst.add(backpackValuesFirst.get(i).getValue());
		}
		for(int i = 0; i < backpackValuesSecond.size(); i++){
			System.out.println(backpackValuesSecond.get(i).getId64() + "\t\t" + backpackValuesSecond.get(i).getValue() + "\t\t" + backpackValuesSecond.get(i).getFetchDate());
			labelsSecond.add((backpackValuesSecond.get(i).getFetchDate().getTime()));
			valuesSecond.add(backpackValuesSecond.get(i).getValue());
		}
		JSONObject retValues = new JSONObject();
		JSONObject axisX = new JSONObject();
		axisX.put("labelAngle",-50);
		retValues.put("axisX",axisX);
		JSONArray data = new JSONArray();
		JSONObject dataObj = new JSONObject();
		dataObj.put("type", "line");
		dataObj.put("showInLegend", true);
		dataObj.put("name",steamFirst.getNickname());
		JSONArray dataPoints = new JSONArray();
		for(int i=0; i < labelsFirst.size(); i++){
			status = true;
			JSONObject dataPObject = new JSONObject();
			dataPObject.put("x", labelsFirst.get(i));
			dataPObject.put("y", valuesFirst.get(i));
			dataPoints.put(dataPObject);
		}
		JSONObject dataObjSecond = new JSONObject();
		dataObjSecond.put("type","line");
		dataObjSecond.put("showInLegend",true);
		dataObjSecond.put("name",steamSecond.getNickname());
		JSONArray dataPointsSecond = new JSONArray();
		for(int i=0; i < labelsSecond.size(); i++){
			status = true;
			JSONObject dataPObject = new JSONObject();
			dataPObject.put("x", labelsSecond.get(i));
			dataPObject.put("y", valuesSecond.get(i));
			dataPointsSecond.put(dataPObject);
		}
			
		dataObj.put("dataPoints", dataPoints);
		dataObjSecond.put("dataPoints",dataPointsSecond);
		data.put(dataObj);
		data.put(dataObjSecond);
		retValues.put("data",data);
		retValues.put("status",status);
		
		return retValues.toString();
	}
	
}
