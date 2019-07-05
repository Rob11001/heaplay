
function removeFromCart(button) {
	let src = $(button).parent().find(".song-image").children().prop("src");
	let track_id = src.substring(src.indexOf("id")+3,src.indexOf("&"));

	$.ajax({
		"type" : "GET",
		"url" : "/heaplay/addToCart?track_id="+track_id+"&remove=true",
		"success": () => {
			let price = $(button).parent().find(".price").html();
			price = price == "free" ? 0 : price; 
			$(button).parent().remove();
			
			let newValue = (Number.parseFloat($("#sum").html())-Number.parseFloat(price));
			$("#sum").html(newValue);
			
		}
	});
}