//Var globale
var audio;
//Inizializzazione dei vari handlers
$(document).ready(function init() {
	audio = $("#audio");
	addEventHandlers();
});

function addEventHandlers() {
	$(".load").click(loadAudio);
    $(".play").click(startAudio);
	$(".pause").click(pauseAudio);
	$(".forward").click(forwardAudio);
	$(".back").click(backAudio);
    $(".volume-up").click(volumeUp);
	$(".volume-down").click(volumeDown);
	$(".replay").click(replayAudio);
}

//Handlers
function loadAudio(){
	audio.trigger('load');
}

function startAudio(){
    audio.trigger('play');
}

function pauseAudio(){
    audio.trigger('pause');
}
//Non usato
function stopAudio(){
    pauseAudio();
    audio.prop("currentTime",0);
}

function forwardAudio(){
    pauseAudio();
	var time = audio.prop("currentTime");
	audio.prop("currentTime",time+5);
	if(audio.prop("currentTime") == 0) {
		audio.trigger("load");
		audio.prop("currentTime",time+5);
	}	
	startAudio();
}
 
function backAudio(){
	pauseAudio();
	audio.prop("currentTime",audio.prop("currentTime")-5);
	if(audio.prop("currentTime") == 0) {
		audio.trigger("load");
		audio.prop("currentTime",audio.prop("currentTime")-5);
	}	
	startAudio();
}

function volumeUp(){
    var volume = audio.prop("volume")+0.2;
    if(volume >1)
        volume = 1;
    audio.prop("volume",volume);
}

function volumeDown(){
    var volume = audio.prop("volume")-0.2;
    if(volume <0)
	    volume = 0;
	audio.prop("volume",volume);
}

function replayAudio() {
	audio.prop("loop") ? audio.prop("loop",false) : audio.prop("loop",true);
}

function setCurrentTime(params) {
	var time = $(params).val();
	let min = Math.floor(time/60), sec = Math.round(time%60);
	min = min < 10 ? "0"+min.toString() : min;						//Serve per formattare i numeri (non esiste un metodo in Javascript per farlo...)
	sec = sec < 10 ? "0"+sec.toString() : sec;

	$("#time").html( min + ":" + sec);
	audio.prop("currentTime",time);
	if(audio.prop("currentTime") == 0) {
		audio.trigger("load");
		audio.prop("currentTime",time);
	}	
	startAudio();
}

function updateCurrentTime (event) {
	var time = event.currentTime;
	let min = Math.floor(time/60), sec = Math.round(time%60);

	$("#slider").prop("value",Math.floor(time));
	min = min < 10 ? "0"+min.toString() : min;
	sec = sec < 10 ? "0"+sec.toString() : sec;
	$("#time").html(min + ":" + sec);
}