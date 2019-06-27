//Var globale
let audio;
let playImage="images/play-button.png",pauseImage="images/pause-button.png";
let playEl;
const timePadder = (data,pad="0") => (data < 10) ? pad.toString()+data.toString() : data.toString();
const showHide = (el) => {
	if($(el).css("display") === "none")
		$(el).css("display","inline");
	else
		$(el).css("display","none");	
};

//Inizializzazione dei vari handlers
$(document).ready(function init() {
	audio = $("#audio");
	addEventHandlers();
});

function addEventHandlers() {
	$(".load").click(loadAudio);
    $(".pause").click(pauseAudio);
	$(".play").click(startAudio);
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
	let time = audio.prop("currentTime");
	audio.prop("currentTime",time+5);
	if(audio.prop("currentTime") == 0) {
		loadAudio();
		audio.prop("currentTime",time+5);
	}	
	startAudio();
}
 
function backAudio(){
	pauseAudio();
	audio.prop("currentTime",audio.prop("currentTime")-5);
	if(audio.prop("currentTime") == 0) {
		loadAudio();
		audio.prop("currentTime",audio.prop("currentTime")-5);
	}	
	startAudio();
}

function volumeUp(){
    let volume = audio.prop("volume")+0.2;
    if(volume >1)
        volume = 1;
    audio.prop("volume",volume);
}

function volumeDown(){
    let volume = audio.prop("volume")-0.2;
    if(volume <0)
	    volume = 0;
	audio.prop("volume",volume);
}

function setVolume(volume) {
	let val = volume.value;
	audio.prop("volume",val);
}

function replayAudio() {
	audio.prop("loop") ? audio.prop("loop",false) : audio.prop("loop",true);
}

function setCurrentTime(params) {
	let time = $(params).val();
	let min = Math.floor(time/60), sec = Math.round(time%60);
	min = timePadder(min);						
	sec = timePadder(sec);

	$("#time").html( min + ":" + sec);
	audio.prop("currentTime",time);
	if(audio.prop("currentTime") == 0) {
		loadAudio();
		audio.prop("currentTime",time);
	}	
	startAudio();
}

function updateCurrentTime (event) {
	let time = event.currentTime;
	let min = Math.floor(time/60), sec = Math.round(time%60);

	$("#slider").prop("value",Math.floor(time));
	min = timePadder(min);
	sec = timePadder(sec);
	$("#time").html(min + ":" + sec);
}

function showHideBar (el,otherEl) {
	showHide(otherEl);
	showHide(el);
	$(el).css("pointer-events","none");
	$(otherEl).css("pointer-events","auto");
	
}
