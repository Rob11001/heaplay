$(function() {
	$('#autocomplete').autocomplete({			//Collegamento al input text
		serviceUrl: "getTags",					//Servlet da chiamare
		type : "get",							//Tipo di richiesta	
		focus : true							
		});
    
		
});
				
//Mostra e nasconde il tag html su cui è fissato
function ShowAndHide(status) {					
		if(status == 1)
			$("#divPrice").css("display","block");
		else
			$("#divPrice").css("display","none");
	}		 