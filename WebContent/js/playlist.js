//Chiusura della finestra quando si clicka al di fuori della finestra
$(document).ready(() => {
	window.onclick = (event) => {
		let element = event.target;
		let play = $(".playlist-form");
		if($(element).is(play))
			showHide(play);
	};
});

function addToPlaylist(button) {
	let parent = $(button).parent();
	let audioSrc = $(parent).find(".audio").children().prop("src");
	let track_id = audioSrc.substring(audioSrc.indexOf("id")+3,audioSrc.indexOf("&"));
	$("#track_id").val(track_id);
	showHide($(".playlist-form"));
}

function autocompletePlaylist(el,suggestions) {
	let auto = $(el).val();
	let audioSrc = $('#track_id').val();
	let track_id = audioSrc.substring(audioSrc.indexOf("id")+3,audioSrc.indexOf("&"));
	let url = "/heaplay/getPlaylists?autocomplete="+auto+"&track_id="+track_id;
	autocomplete(el,suggestions,url);
}

function removeFromPlaylist(button) {
	let url = $("<form>").prop("action");
	let play_id = url.substring(url.indexOf("id")+3,url.length);
	let parent = $(button).parent();
	url = $(parent).find(".audio").children().prop("src");
	let track_id = url.substring(url.indexOf("id")+3,url.indexOf("&"));

	$.ajax({
		"type":"GET",
		"url": "/heaplay/removeFromPlaylist?track_id="+track_id+"&play_id="+play_id,
		"success" : () => {
			$(parent).remove();
		}
	});
}
