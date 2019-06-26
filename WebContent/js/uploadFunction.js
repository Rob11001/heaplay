$(function() {
	$('#autocomplete').autocomplete({			//Collegamento al input text
		serviceUrl: "getTags",					//Servlet da chiamare
		type : "get"							//Tipo di richiesta							
		});
});
				
//Mostra e nasconde il tag html su cui è fissato
function ShowAndHide(status) {					
		if(status == 1)
			$("#divPrice").css("display","block");
		else
			$("#divPrice").css("display","none");
	}		
	

{
var objectUrl;
$("#audioFake").on("canplaythrough", function(e){
    var seconds = e.currentTarget.duration;
    var duration = moment.duration(seconds, "seconds");
    
    var time = "";
    var hours = duration.hours();
    if (hours > 0) { time = hours + ":" ; }
    
    time = time + duration.minutes() + ":" + duration.seconds();
    alert(time);
    
    URL.revokeObjectURL(objectUrl);
});

$("#audio").change(function(e){
    var file = e.currentTarget.files[0];
    objectUrl = URL.createObjectURL(file);
    $("#audio").prop("src", objectUrl);
});

}