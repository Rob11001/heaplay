$("#prova").click (() => {
	$.ajax({
		"type":"GET",
		"url" : "getTracks",
		"success":(data) => {
			alert("Fine");
		}
	});

});