/**
 * 
 */

var userData;

$(document).ready(function(){
	
	
	$('#search').click(function(){
		
		var userName = $("#searchUserBox").val();
		if (userName != ""){	
		$.ajax({
			method:'POST',
			url:'tf2/userSearch/',
			data: userName,
			contentType: 'text/plain',
			success: function(userData){
				$('body').append('Compleated');
			}
		});
		
		};
	});

	
	
});