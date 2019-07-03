//Funzioni per far scorrere le track
//Setta il valore per la prossima chiamata
function beginValue(button) {
	let value = button.value*5 -5;
	$(button).parent().find($("input[type=hidden]")).prop("value",value);
}
//Mette il focus alla pagina corrente
function focusButton() {
	let val = $("input[type=hidden]").val();
	$("#"+val).addClass("selected");
	$("#"+val).prop("type","button");
}

function selection(selected,prev){
	$(selected).addClass("selected");
	$(prev).removeClass("selected");
	if($(selected).html() == "Brani") {
		$(".user-tracks").removeClass("hidden");
		$(".user-playlist").addClass("hidden");
	}
	else {
		$(".user-tracks").addClass("hidden");
		$(".user-playlist").removeClass("hidden");
	}
}

function inputFile(input) {
	$(input).trigger("click");
	$("#srcImg").removeClass("hidden");
}


$(document).ready(() => {
	// Capire come prendere dall'input il file e inviarlo alla servlet per salvare l'immagine
	$("#image").change(function(e){
		let file = e.currentTarget.files["0"];
		let objectUrl = window.URL.createObjectURL(file);
		$("#user-image").prop("src", objectUrl);
	});		
});
