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
	$("#duration").val(Math.floor(seconds));
    URL.revokeObjectURL(objectUrl);
});

$("#audio").change(function(e){
	var file = e.currentTarget.files["0"];
	objectUrl = window.URL.createObjectURL(file);
    $("#audioFake").prop("src", objectUrl);
});

}