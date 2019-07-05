//Funzioni per far scorrere le track
//Setta il valore per la prossima chiamata
function beginValue(button) {
	let value = button.value*5 -5;
	$(button).parent().find($("input[type=hidden]")).prop("value",value);
}
//Mette il focus alla pagina corrente
function focusButton() {
	let val = $("#currentPage").val();
	$("#"+val).addClass("selected");
	$("#"+val).prop("type","button");
}

function selection(selected,prev,toHide,toShow){
	$(selected).addClass("selected");
	$(prev).removeClass("selected");
	$(toShow).removeClass("hidden");
	$(toHide).addClass("hidden");
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

function addToCart(button) {
	let src = $(".audio").children().prop("src");
	let track_id = src.substring(src.indexOf("id")+3,src.indexOf("&"));
	$.ajax({
		"type":"GET",
		"url": "/heaplay/addCart?track_id="+track_id,
		"success": () => {
			$(button).off();
			let div = $(button).parent();
			$(button).remove();
			$("<span>Aggiunto al carrello</span>").appendTo(div);
		}
	});
}

function getPlaylist(container) {
	let src = $('#user-image').prop("src");
	let user_id = src.substring(src.indexOf("id")+3,src.indexOf("&"));
	$.ajax({
		"type" : "GET",
		"url" : "/heaplay/getPlaylists?id="+user_id,
		"success" : (data) => {
			$(container).empty();
			data = JSON.parse(data);
			for(let i = 0 ; i < data.length; i++){
				//Estrazione del bean
				let bean = data[i];
				createDiv(bean,container,"playlist");
			}
			
			if(data.length == 0)
				$("<p>Non sono presenti playlist</p>").appendTo(container);
		}
	});
}
