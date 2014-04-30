

$(document).ready(function(){
	
	
	$.ajax({
		method:'POST',
		url:'tf2/userSearch/',
		data: "currentUser",
		contentType: 'text/plain',
		dataType: 'json',
		success: handleUserData
	});
	
	$("#changeUserId").click(function(){
		console.log('clicked!');
		var newId = $("#changeIdBox").val();
		var strUrl = 'tf2/updateUser/' + newId.toString();
		console.log('url');
		$.ajax({
			method:'PUT',
			url:strUrl,
			success: function(){
				location.reload();
			}
		});
	});
	$("#logout").click(function(){
		$.ajax({
			method:'POST',
			url:'tf2/logout',
			success: function(){
				window.location.href="../org.elsys.TF2Checker/index.html";
			}
		});
	});
});



function handleUserData(user){
	var usernameHolder = $("#username");
	var userLocationHolder = $("#userLocation");
	var userAvatarHolder = $("#avatar");
	var userOnlineStatusHolder = $('#onlineStatus');
	var userBackpackValueHolder = $('#bpVal');
	var userID64Holder = $("#id64");
	var statusCol;
	var onlineText;
	if (user.isOnline){
		statusCol = "#00B800";
		onlineText = "User is online.";
	}
	else if(user.isInGame){
		statusCol = "#1804CC";
		onlineText = "User is in a game.";
	}
	else{
		statusCol = "#A3A375";
		onlineText = "User is offline.";
	}
	
	if (user.backpackValue == -1){
		user.backpackValue = "Unable to find backpack!";
	}
	console.log(user.avatarURL);
	usernameHolder.prepend(user.username);
	userLocationHolder.append(user.location);
	userAvatarHolder.append("<img src='" + user.avatarURL +"' class='img-rounded  avatarBig'></img>");
	userOnlineStatusHolder.append("<p style='color:" + statusCol + "'>" + onlineText + "</p>");
	userBackpackValueHolder.append(user.backpackValue);
	userID64Holder.append(user.id64);
	
	if (user.backpackValue >= 0 )
	{
		drawChart(user.id64);
	}	
}

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
	var chartContainer = $("#");
	var chart = new CanvasJS.Chart("userChart",formatDates(backpackJSON));
	chart.render();
}

function handleComparison(comparisonJSON)
{
	successDrawChart(comparisonJSON);
}

function formatDates(backpackJSON)
{
	for(var i = 0; i<backpackJSON["data"][0]["dataPoints"].length; i++)
	{
		backpackJSON["data"][0]["dataPoints"][i].x = new Date(backpackJSON["data"][0]["dataPoints"][i].x);
		if(backpackJSON["data"][1] !== undefined)
		{
			backpackJSON["data"][1]["dataPoints"][i].x = new Date(backpackJSON["data"][1]["dataPoints"][i].x);
		}
	}
	return backpackJSON;
}