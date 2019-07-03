{
	const showHide = (x,y) => { 
		$(x).show();
		$(y).hide();
	};

	$(".search-button").click(() => {	//Listener della ricerca
		let url = "/heaplay/search?q="+$(".search-box").val()+"&filter="+$(".search-select").val();//url creato dinamicamente (probabilmente bisogna filtrare ciò che è stato scritto dal utente)
		if($(".search-box").val().toString() != "") { 
			$.ajax({
				"type":"GET",
				"url" : url,
				"beforeSend": () => {
					$(".content-wrapper > *").not(".search-content").remove();
					showHide($(".loading"),$("#content"));
				},
				"complete"  : () => {showHide($("#content"),$(".loading"))},
				"success": (data) => {
					let typeOfSearch = url.substring(url.indexOf("&filter")+8,url.length); //Controllo il tipo di ricerca
					const headerDiv ="<p>Elementi trovati : <span id='found'>"+data.length+"</span></p>";
					
					//Estrazione del container e rimozione degli elementi precedenti/inserimento dell'header
					let container = $("#content");
					$(container).empty();
					$(headerDiv).appendTo($(container));	
				
					for(let i = 0 ; i < data.list.length; i++){
						//Estrazione del bean
						let bean = data.list[i];
						createDiv(bean,container,typeOfSearch);
					}	
					//Aggiunta dei vari handlers
					if(typeOfSearch == "track" || typeOfSearch == "tag")
						addEventHandlers();
				}
			});
		}	
	});

	$(window).scroll(() => {
		let container = $("#content");
		let numberOfElements = $(container).children().length - 1;
		
		//Effettuo la chiamata solo quando ho già effettuato una ricerca e ho raggiunto il bottom della pagina
		if(numberOfElements > 0  && ($(window).scrollTop() + $(window).height() >= $(document).height()-1)) {
			let url = "/heaplay/search?q="+$(".search-box").val()+"&startFrom="+numberOfElements.toString()+"&filter="+$(".search-select").val(); //url creato dinamicamente (probabilmente bisogna filtrare ciò che è stato scritto dal utente)
			let found = parseInt($("#found").text(),10); //Numero di elementi trovati dalla ricerca
			//Effettuo la chiamata se esistono ancora elementi da caricare
			if($(".search-box").val().toString() != "" && found > numberOfElements) { 
				$.ajax({
					"type":"GET",
					"url" : url,
					"success": (data) => {
						let typeOfSearch = url.substring(url.indexOf("&filter")+8,url.length); //Controllo il tipo di ricerca
						numberOfElements = $(container).children().length - 1;
						
						//Ulteriore controllo 
						if(found > numberOfElements) {
							for(let i = 0 ; i < data.list.length; i++){
								//Estrazione del bean
								let bean = data.list[i];
								//Creazione del div
								createDiv(bean,container,typeOfSearch);	
							}	
							//Aggiunta dei vari handlers
							if(typeOfSearch == "track" || typeOfSearch == "tag")
								addEventHandlers();
						}
					}
				});
			}
		}	
	});

}
//Dato un bean crea il div corrispondente
function createDiv(bean,container,typeOfSearch) {
	//Vari div in base al bean	Completarli
	const trackDiv = "<div class='song'> <audio preload='none' class='audio' ontimeupdate='updateCurrentTime(this)' > <source src='/heaplay/getAudio?id="+ bean.id +"&extension="+bean.trackExt+"' type='audio/"+bean.trackExt+"'></audio><div class='song-image'><img width='100px' src='/heaplay/getImage?id="+ bean.id +"&extension="+bean.trackExt+"' alt='Errore'></div><div class='song-info'><div class='info'><span><a href='heaplay/"+bean.author+"/"+bean.name+"'>"+bean.name+"</a></span><br><span>Di <a href='/heaplay/'"+bean.author+"'>"+bean.author+"</a></span></div><div class='controls'><button class='play'><i class='fa fa-play color-white'></i></button><button class='slidebar'><span class='song-time'>00:00</span><input type='range' name ='slider' step='1' class='slider slider-bar' onchange='setCurrentTime(this)' value='0' min='0'  max='"+bean.duration+"'><span>"+timePadder(Math.floor(bean.duration/60)) +":"+timePadder(Math.floor(bean.duration%60)) + "</span></button><button class='volume-button' ><i class='fa fa-volume-up'></i></button><input type='range' name ='volume' step='.1' class='volume slider' onchange='setVolume(this)' value='1' min='0'  max='1' ></div></div>";
	const userDiv="";
	const playlistDiv="";
	
	//Scelta del div da usare
	let div = (typeOfSearch == "track"|| typeOfSearch == "tag" ) ? trackDiv : typeOfSearch == "user" ? userDiv : playlistDiv;
	
	//Creazione del div e inserimento
	let ob = $(div);
	$(ob).appendTo($(container));
	
	//Necessita di risettare la src nel tag audio per permettere di poter scaricare la track chiamando la servlet
	if(typeOfSearch == "track" || typeOfSearch == "tag")
		ob.find(".audio").prop("src","/heaplay/getAudio?id="+ bean.id +"&extension="+bean.trackExt);
}


//Esegue una chiamata ad ogni pressione del tasto 
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

//Funzione per effettuare la ricerca al click del tasto Enter
function searchOnEnterButton(e) {
	if(e.keyCode === 13) //keyCode per il tasto Enter
		$(".search-button").trigger("click");
}
