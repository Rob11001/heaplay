{
	$(".search-button").click(() => {	//Listener della ricerca
		let url = "/heaplay/search?q="+$(".search-box").val()+"&filter="+$(".search-select").val();/*Inserire filtro*/	//url creato dinamicamente (probabilmente bisogna filtrare ciò che è stato scritto dal utente)
 		$.ajax({
			"type":"GET",
			"url" : url,
			"beforeSend": () => {$(".loading").show(); $("#content").hide();},
			"complete"  : () => {$(".loading").hide(); $("#content").show();},
			"success": (data) => {
				let typeOfSearch = url.substring(url.indexOf("&filter")+8,url.length); //Controllo il tipo di ricerca
				const headerDiv ="<p>Elementi trovati : "+data.length+"</p>";
				//Estrazione del container e rimozione degli elementi precedenti/inserimento dell'header
				let container = $("#content");
				$(container).empty();
				$(headerDiv).appendTo($(container));	
				
				for(let i = 0 ; i < data.length; i++){
					//Estrazione del bean
					let bean = data[i];
					//Vari div in base al bean
					const trackDiv = "<div class='song'> <audio preload='auto' class='audio' ontimeupdate='updateCurrentTime(this)' > <source src=/heaplay/'getAudio?id="+ bean.id +"&extension="+bean.trackExt+"' type='audio/"+bean.trackExt+"'></audio><div class='song-image'><img width='100px' src='/heaplay/getImage?id="+ bean.id +"&extension="+bean.trackExt+"' alt='Errore'></div><div class='author'><span>"+bean.name+"</span><span>"+bean.author+"</span></div><div class='controls'><button class='back'><img src='/heaplay/images/back-button.png' width='25px'></button><button class='play'><img src='/heaplay/images/play-button.png' width='25px'></button><button class='forward'><img src='/heaplay/images/forward-button.png' width='25px'></button><button class='pause'><img src='/heaplay/images/pause-button.png' width='25px'></button><button class='replay'><img src='/heaplay/images/replay-button.png' width='25px'></button><button class='slidebar'><span class='song-time'>00:00</span><input type='range' name ='slider' step='1' class='slider-bar' onchange='setCurrentTime(this)' value='0' min='0'  max='"+bean.duration+"'><span>"+timePadder(Math.floor(bean.duration/60)) +":"+timePadder(Math.floor(bean.duration%60)) + "</span></button><button class='volume-button' ><img src='/heaplay/images/volume-button.png' width='25px'></button><input type='range' name ='volume' step='.1' class='volume' onchange='setVolume(this)' value='1' min='0'  max='1' ></div></div>";
					const tagDiv ="";
					const userDiv="";
					const playlistDiv="";
					//Scelta del div da usare
					let div = typeOfSearch == "track" ? trackDiv : typeOfSearch == "user" ? userDiv : typeOfSearch == "tag" ? tagDiv : playlistDiv;
					//Creazione del div e inserimento
					let ob = $(div);
					$(ob).appendTo($(container));
					//Necessita di risettare la src nel tag audio per permettere di poter scaricare la track chiamando la servlet
					if(typeOfSearch == "track")
						ob.find(".audio").prop("src","/heaplay/getAudio?id="+ bean.id +"&extension="+bean.trackExt)
				}	
				//Aggiunta dei vari handlers
				if(typeOfSearch == "track")
					addEventHandlers();
			}
		});
	});
}


//Funziona più o meno bene
function autocompleteSearch(el,suggestions) {
	let url = "/heaplay/search?q="+$(el).val()+"&filter="+$(".search-select").val()+"&auto=true";
	if($(el).val().length > 1) {
		$.ajax({
			"type":"GET",
			"url" : url,
			"cache":false,
			"success": (data) => {
				$(suggestions).empty();
				for(let i=0;i<data.length;i++) {
					let option = "<option value='"+data[i]+"'>"+data[i]+"</option>";
					$(option).appendTo(suggestions);
				}	
			}
		});
	}

}
