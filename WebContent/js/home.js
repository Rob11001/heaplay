$(document).ready(() => {
	//Ajax che mette nella home le track più votate
	$.ajax({
		"type":"GET",
		"url" : "/heaplay/getLikedTracks",
		"success": (data) => {
				//Parsing dell'oggetto JSON	
				let beans = JSON.parse(data);
				let container = $("#songs");
				const header = "<p>Canzoni più votate</p>"
				$(container).empty();	
				$(header).appendTo($(container));
				//Creazione dei div
				for(let i = 0 ; i < beans.length; i++){
					//Estrazione del bean
					let bean = beans[i];
					createDiv(bean,container,"track");
				}	
				addEventHandlers();
			}
	});
	//Ajax che mette nella home le playlist più ascoltate
	$.ajax({
		"type":"GET",
		"url" : "/heaplay/getBestPlaylists",
		"success": (beans) => {
				let container = $("#playlists");
				const header = "<p>Playlist più ascoltate</p>"
				$(container).empty();	
				$(header).appendTo($(container));
				//Creazione dei div
				for(let i = 0 ; i < beans.length; i++){
					//Estrazione del bean
					let bean = beans[i];
					createDiv(bean,container,"playlist");
				}	
			}
	});
	
});