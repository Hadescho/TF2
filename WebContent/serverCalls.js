var user;
function handleUserData(userData){
	userData = "\"" + userData + "\""
	user = jQuery.parseJSON(userData);
	var borderCol;
	console.log(user[0].Display);
	if (user.isOnline){
		borderCol = "#00B800";
	}
	else if(user.isInGame){
		borderCol = "blue";
	}
	else{
		borderCol = "#334c4c";
	}
	$(".userInfoContainer").append("<p class='.text-center' id='username'> " +user.username+ "</p>" +
		//"<img src='" + user.avatarURL +"' class='img-rounded col-lg-4' style='border 3px solid " + borderCol + "'>"+
		"<p class='col-lg-6'> + " + user.location + "</p>");
	$(".userInfoContainer").show();
	alert('shouldHaveDoneIT');
}

$(document).ready(function(){
	
	
	$('#search').click(function(){
		
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