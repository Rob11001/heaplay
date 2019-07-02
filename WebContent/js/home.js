$(document).ready(() => {
	//Ajax che mette nella home le track più votate
	$.ajax({
		"type":"GET",
		"url" : "/heaplay/getLikedTracks",
		"success": (data) => {
				//Parsing dell'oggetto JSON	
				let beans = JSON.parse(data);
				let typeOfSearch = "track";
				let container = $("#songs");
				const header = "<p>Canzoni più votate</p>"
				$(container).empty();	
				$(header).appendTo($(container));
				//Creazione dei div
				for(let i = 0 ; i < beans.length; i++){
					//Estrazione del bean
					let bean = beans[i];
					createDiv(bean,container,typeOfSearch);
				}	
				//Aggiunta dei vari handlers
				if(typeOfSearch == "track" || typeOfSearch == "tag")
					addEventHandlers();
			}
	});
});