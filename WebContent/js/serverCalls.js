var user;

var API_KEY = "5315e3ac4cd7b8bb188b4567";

function handleBPData(bpData){
	var userBPinfo = bpData;
	userBPinfo.Display();
}

function handleUserData(userData){
	//userData = "\"" + userData + "\""
	var user = userData;
	console.log(user);
	//user.backpackValue = user.backpackValue.parseFloat();
	var borderCol;
	var onlineText;
	//console.log(user[0].Display);
	if (user.isOnline){
		borderCol = "#00B800";
		onlineText = "User is online.";
	}
	else if(user.isInGame){
		borderCol = "#1804CC";
		onlineText = "User is in a game.";
	}
	else{
		borderCol = "#A3A375";
		onlineText = "User is offline.";
	}
	
	if (user.backpackValue == -1){
		user.backpackValue = "Unable to find backpack!";
	}
	
	
	$(".userInfoContainer").append("<p class='text-center' id='username'> " +user.username+ "</p>" +
		"<img src='" + user.avatarURL +"' class='img-rounded col-lg-4 avatar'>"+ 
		"<p class='col-lg-6'>" + user.location + "</p>" +
		"<p class='col-lg-4' style=' color: "+ borderCol +" '>" + onlineText + "</p>"+
		"<p> Backpack Value: " + user.backpackValue + "</p>");
	$(".userInfoContainer").fadeIn(1000);
	console.log("id64 : " + user.id64);
	if (user.backpackValue >= 0 )
	{
		drawChart(user.id64);
	}	
}

$(document).ready(function(){
	
	
	$('#search').click(function(){
		$("#indexJumbo").fadeOut(500);
		$(".userInfoContainer").fadeOut(500);
		$("#chartContainer").html("");
		$(".userInfoContainer").html("");
		var searchBox = $("#searchUserBox");
		var userName = searchBox.val();
		if (userName !== "")
			if (!(userName.indexOf(',') + 1 || userName.indexOf(' ') + 1 || userName.indexOf('&') + 1)){
				console.log('Normal search');
				$.ajax({
					method:'POST',
					url:'tf2/userSearch/',
					data: userName,
					contentType: 'text/plain',
					dataType: 'json',
					success: handleUserData
			});
		}else{
			var userNames = userName.split(/[\s,&]+/);
			console.log(userNames);
			if (userNames.length > 2){
				searchBox.val('Working with only 1 or 2 ids (for now).');
				return;
			}
			console.log('Comparison search');
			$.ajax({
				method:'GET',
				url:'tf2/backpackValues/' + userNames[0] + '&' + userNames[1],
				dataType: 'json',
				success: handleComparison
			});	
			}
	});
	$('#registerBtn').click(function(){
	
//		var myUser = {};
//		myUser["email"] = $("#email").val();
//		myUser["password"] = $("#pass").val();
//		myUser["id64"] = $("#id64").val();
		var toSend = {
			email: $("#email").val(),
			password: $("#pass").val(),
			id64: $('#id64').val(),
		};
		toSend = JSON.stringify(toSend);
		console.log(toSend.toString());
		$.ajax({
			method:'POST',
			url:'tf2/api/register',
			data: toSend,
			contentType: 'application/json',
			success: function(){
				console.log("Sent");
				window.location.href="../org.elsys.TF2Checker/index.html"
			}
		});
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
	var chart = new CanvasJS.Chart("chartContainer",formatDates(backpackJSON));
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