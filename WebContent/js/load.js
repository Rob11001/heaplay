{
	$(".search-button").click(() => {	//Listener della ricerca
		let url = "search?q="+$(".search-box").val()+"&filter=";/*Inserire filtro*/	//url creato dinamicamente (probabilmente bisogna filtrare ciò che è stato scritto dal utente)
 		$.ajax({
			"type":"GET",
			"url" : url,
			"success": (data) => {
				let typeOfSearch = url.substring(url.indexOf("&filter")+8,url.length); //Controllo il tipo di ricerca
				for(let i = 0 ; i < data.length; i++){
					//Estrazione del bean
					let bean = data[i];	
					//Vari div in base al bean
					const trackDiv = "<div class='song'> <audio preload='auto' class='audio' ontimeupdate='updateCurrentTime(this)' > <source src='getAudio?id="+ bean.id +"&extension="+bean.trackExt+"' type='audio/"+bean.trackExt+"'></audio><div class='song-image'><img width='100px' src='getImage?id="+ bean.id +"&extension="+bean.trackExt+"' alt='Errore'></div><div class='author'><span>"+bean.name+"</span><span>"+bean.author+"</span></div><div class='controls'><button class='back'><img src='images/back-button.png' width='25px'></button><button class='play'><img src='images/play-button.png' width='25px'></button><button class='forward'><img src='images/forward-button.png' width='25px'></button><button class='pause'><img src='images/pause-button.png' width='25px'></button><button class='replay'><img src='images/replay-button.png' width='25px'></button><button class='slidebar'><span class='song-time'>00:00</span><input type='range' name ='slider' step='1' class='slider-bar' onchange='setCurrentTime(this)' value='0' min='0'  max='"+bean.duration+"'><span>"+timePadder(Math.floor(bean.duration/60)) +":"+timePadder(Math.floor(bean.duration%60)) + "</span></button><button class='volume-button' ><img src='images/volume-button.png' width='25px'></button><input type='range' name ='volume' step='.1' class='volume' onchange='setVolume(this)' value='1' min='0'  max='1' ></div></div>";
					const tagDiv ="";
					const userDiv="";
					const playlistDiv="";
					//Scelta del div da usare
					let div = typeOfSearch == "track" ? trackDiv : typeOfSearch == "user" ? userDiv : typeOfSearch == "tag" ? tagDiv : playlistDiv;
					//Creazione del div e inserimento
					let ob = $(div);
					$(ob).appendTo($("#content"));
					//Necessita di risettare la src nel tag audio per permettere di poter scaricare la track chiamando la servlet
					if(typeOfSearch == "track")
						ob.find(".audio").prop("src","getAudio?id="+ bean.id +"&extension="+bean.trackExt)
				}	
				//Aggiunta dei vari handlers
				if(typeOfSearch == "track")
					addEventHandlers();
			}
		});
	});
}
