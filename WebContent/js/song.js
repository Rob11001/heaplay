//Var globale
let audio,volume,slider,time,playButton;
const timePadder = (data,pad="0") => (data < 10) ? pad.toString()+data.toString() : data.toString();
const showHide = (el) => {
	if($(el).css("display") === "none")
		$(el).css("display","inline");
	else
		$(el).css("display","none");	
};

//Inizializzazione dei vari handlers
$(document).ready(function init() {
	addEventHandlers();
});
//Aggiunta degli handlers per tutte le classi
function addEventHandlers() {
	$(".load").click(loadAudio);
    $(".pause").click(pauseAudio);
	$(".play").click(startAudio);
	$(".forward").click(forwardAudio);
	$(".back").click(backAudio);
	$(".replay").click(replayAudio);
}

//Handlers
function loadAudio(){
	audio.trigger('load');
}
//Metodo chiamato per inizializzare
function startAudio(e){
	if(audio != undefined || audio != null)
	 	stopAudio();
	let parent = $(e.currentTarget).parent().parent(); //Risalgo di gerarchie
	init(parent);
	playAudio();
}
//Inizializzazione
function init(parent) {
	audio = $(parent).find(".audio");
	volume = $(parent).find(".volume");
	$(volume).css("display","inline");
	slider = $(parent).find(".slider-bar");
	time = $(parent).find(".song-time");
	playButton = $(parent).find(".play");
	$(playButton).off();
	$(playButton).click(playAudio);
	$(slider).prop("value",0);
}

function playAudio() {
	audio.trigger('play');
}

function pauseAudio(){
    audio.trigger('pause');
}
//Metodo per resettare l'audio
function stopAudio(){
    pauseAudio();
	reset();
}

function reset() {
	audio.prop("currentTime",0);
	playButton.off();
	playButton.click(startAudio);
	$(slider).prop("value",0);
	$(time).html("00:00");
	$(volume).css("display","none");
}

function forwardAudio(){
    pauseAudio();
	let time = audio.prop("currentTime");
	audio.prop("currentTime",time+5);
	if(audio.prop("currentTime") == 0) {
		loadAudio();
		audio.prop("currentTime",time+5);
	}	
	playAudio();
}
 
function backAudio(){
	pauseAudio();
	audio.prop("currentTime",audio.prop("currentTime")-5);
	if(audio.prop("currentTime") == 0) {
		loadAudio();
		audio.prop("currentTime",audio.prop("currentTime")-5);
	}	
	playAudio();
}

function setVolume(el) {
	if($(el).is($(volume))) {
		let val = el.value;
		audio.prop("volume",val);
	}else
		$(el).prop("value",1);
}

function replayAudio() {
	audio.prop("loop") ? audio.prop("loop",false) : audio.prop("loop",true);
}

function setCurrentTime(params) {
	if($(params).is($(slider))){			//Metodo is per vedere se due tag html sono identici
		let currentTime = $(params).val();
		let min = Math.floor(currentTime/60), sec = Math.round(currentTime%60);
		min = timePadder(min);						
		sec = timePadder(sec);
	
		$(time).html( min + ":" + sec);
		audio.prop("currentTime",currentTime);
		if(audio.prop("currentTime") == 0) {
			loadAudio();
			audio.prop("currentTime",time);
		}	
		playAudio();
	}
	else
		$(params).prop("value",0);
}

function updateCurrentTime (event) {
	let currentTime = event.currentTime;
	let min = Math.floor(currentTime/60), sec = Math.round(currentTime%60);
	min = timePadder(min);
	sec = timePadder(sec);

	$(slider).prop("value",Math.floor(currentTime));
	$(time).html(min + ":" + sec);
}

function showHideBar (el,otherEl) {
	showHide(otherEl);
	showHide(el);
	$(el).css("pointer-events","none");
	$(otherEl).css("pointer-events","auto");
	
}