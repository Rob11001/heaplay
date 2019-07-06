
function removeFromCart(button) {
	let src = $(button).parent().find(".song-image").children().prop("src");
	let track_id = src.substring(src.indexOf("id")+3,src.indexOf("&"));

	$.ajax({
		"type" : "GET",
		"url" : "/heaplay/addToCart?track_id="+track_id+"&remove=true",
		"beforeSend": () => {
					$(".loading-cart").show();
					$(".cart").hide();
				},
		"complete"  : () => {
					$(".loading-cart").hide();
					$(".cart").show();
		}, 
		"success": () => {
			let price = $(button).parent().find(".price").html();
			price = price == "free" ? 0 : price; 
			let cartDiv = $(".cart");
			let numberOfElements = $(cartDiv).children().length;
			$(button).parent().remove();
			if(price > 0 && numberOfElements > 1 ) {
				let newValue = (Number.parseFloat($("#sum").html())-Number.parseFloat(price));
				$("#sum").html(newValue);
			} else {
				$(cartDiv).empty();
				$("<p>Il tuo carrello è al momento vuoto</p>").appendTo(cartDiv);
			}
		}
	});
}

function purchase() {
	$.ajax({
		"type" : "GET",
		"url" : "/heaplay/purchase",
		"beforeSend": () => {
					$(".loading-cart").show();
					$(".cart").hide();
				},
		"complete"  : () => {
					$(".loading-cart").hide();
					$(".cart").show();
		}, 
		"success": () => {
			$(".cart").empty();
			$("<p>Acquisto completato con successo</p>").appendTo($(".cart"));
		}
	});
}