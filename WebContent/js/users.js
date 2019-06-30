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