var user;

var API_KEY = "5315e3ac4cd7b8bb188b4567";

function handleBPData(bpData){
	var userBPinfo = bpData;
	userBPinfo.Display();
}

function handleUserData(userData){
	//userData = "\"" + userData + "\""
	user = userData;
	var borderCol;
	var onlineText;
	//console.log(user[0].Display);
	if (user.isOnline){
		borderCol = "#00B800";
		onlineText = "User is online."
	}
	else if(user.isInGame){
		borderCol = "#1804CC";
		onlineText = "User is in a game."
	}
	else{
		borderCol = "#334c4c";
		onlineText = "User is offline."
	}
	
	if (user.backpackValue == -1){
		user.backpackValue = "Unable to find backpack!"
	}
	
	$(".userInfoContainer").html("");
	$(".userInfoContainer").append("<p class='.text-center' id='username'> " +user.username+ "</p>" +
		"<img src='" + user.avatarURL +"' class='img-rounded col-lg-4'>"+ 
		"<p class='col-lg-6'>" + user.location + "</p>" +
		"<p class='col-lg-4' style=' color: "+ borderCol +" '>" + onlineText + "</p>"+
		"<p> Backpack Value: " + user.backpackValue + "</p>");
	$(".userInfoContainer").show();
	console.log("id64 : " + user.id64);
	if (user.backpackValue >= 0 )
	{
		drawChart(user.id64);
	}	
}

$(document).ready(function(){
	
	
	$('#search').click(function(){
		$("#chartContainer").html("");
		var userName = $("#searchUserBox").val();
		if (userName !== ""){	
			$.ajax({
				method:'POST',
				url:'tf2/userSearch/',
				data: userName,
				contentType: 'text/plain',
				success: handleUserData
			});
		}
	});
});

function drawChart(id64){
	$.ajax({
		method: 'GET',
		url: 'tf2/backpackValues/' + id64,
		dataType: 'json',
		success: successDrawChart
	});
}

function successDrawChart(backpackJSON)
{
	var deb = 0;
	// var ctx = $("#BackpackChart").get(0).getContext("2d");
	// var data = backpackJSON;
	// console.log(data);
	// var chart = new Chart(ctx).Line(data);
	// for(var key in backpackJSON["data"][0]["dataPoints"])
	// {

	// 	key.x = new Date(key.x);
	// }
	for(var i = 0; i<backpackJSON["data"][0]["dataPoints"].length; i++)
	{
		backpackJSON["data"][0]["dataPoints"][i].x = new Date(backpackJSON["data"][0]["dataPoints"][i].x);
	}
	var chart = new CanvasJS.Chart("chartContainer",backpackJSON);
	chart.render();
}