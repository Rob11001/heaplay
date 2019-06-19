$(function() {
	var availableTags = ["Marco","Roberto","Giuseppe"];	//Capire come effettuare una richiesta al DB in modo da avere i vari tag come array
	$( "#tags" ).autocomplete({ 
    	focus:true,   
        source: availableTags
    });		
});
	   		 
function ShowAndHide(status) {	
		if(status == 1)
			$("#divPrice").css("display","block");
		else
			$("#divPrice").css("display","none");
	}		 