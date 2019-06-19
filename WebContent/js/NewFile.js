function createBox() {	
	var status = $("divPrice").attr("display");
	if(status === "none")
		$("divPrice").css("display:block");
	else
		$("divPrice").css("display:none");
}