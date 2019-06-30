//Funzioni di servizio
const searchElement = (list,el) => {
	for(let i = 0; i < list.length ; i++) {
		if($(list[i]).val() === el)
			return true;
	}		
	return false;
};

const removeTag = (object) =>{
	$(object).remove();
};

const addTag = (button) => {
	let divParent = $(button).parent();
	let val = $(divParent).find("#autocomplete").val();
	let list = document.getElementsByName("tag");
	if(val !== "" && !searchElement(list,val))
		$( "<input type='text' class='tagElement' size=10 readonly='readonly' name='tag' value='"+val+"' onclick='removeTag(this)'>" ).appendTo( divParent );
};



//Aggiunta dei vari handlers al caricamento
$(document).ready(() => {
	let objectUrl;
	$("#audioFake").on("canplaythrough", function(e){
		let seconds = e.currentTarget.duration;
		$("#duration").val(Math.floor(seconds));
		URL.revokeObjectURL(objectUrl);
	});

	$("#audio").change(function(e){
		let file = e.currentTarget.files["0"];
		objectUrl = window.URL.createObjectURL(file);
		$("#audioFake").prop("src", objectUrl);
	});
});	

//Function di autocompletamento
$(function() {
	$('#autocomplete').autocomplete({			//Collegamento al input text
		serviceUrl: "getTags",					//Servlet da chiamare
		type : "get"							//Tipo di richiesta							
	});
});
				
//Mostra e nasconde il tag html su cui è fissato
function ShowAndHide(status) {					
		if(status == 1)
			$("#divPrice").removeClass("hidden");
		else
			$("#divPrice").addClass("hidden");
}		

function validateUpload(form) {
	let list = document.getElementsByName("tag");
	let regex = /^\d{0,2}(\.\d{1,2})?$/
	if(list.length <= 0 || !validate(form["price"],regex))
		return false;
	return true;
}

// {
// var objectUrl;
// $("#audioFake").on("canplaythrough", function(e){
//     let seconds = e.currentTarget.duration;
// 	$("#duration").val(Math.floor(seconds));
//     URL.revokeObjectURL(objectUrl);
// });

// $("#audio").change(function(e){
// 	let file = e.currentTarget.files["0"];
// 	objectUrl = window.URL.createObjectURL(file);
//     $("#audioFake").prop("src", objectUrl);
// });

// }