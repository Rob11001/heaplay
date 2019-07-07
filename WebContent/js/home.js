$(document).ready(() => {
	//Ajax che mette nella home le track più votate
	$.ajax({
		"type":"GET",
		"url" : "/heaplay/getLikedTracks",
		"success": (data) => {
				//Parsing dell'oggetto JSON	
				let beans = JSON.parse(data);
				let container = $("#songs");
				const header = "<h3>Canzoni più votate</h3>"
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
				const header = "<h3>Playlist più ascoltate</h3>"
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