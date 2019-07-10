const encodeSessionId = (url) => {
	//Prendo l'url
	let currentUrl = window.location.href;
	//Definisco la fine del session id se presente
	let end = currentUrl.indexOf("#") != -1 ? currentUrl.indexOf("#") : currentUrl.length;
	end = currentUrl.indexOf("?") != -1 ? currentUrl.indexOf("?") : end;
	let sessionId = currentUrl.substring(currentUrl.indexOf(";jsessionid"),end);
	//Encode del sessionId
	if(currentUrl.length != sessionId.length) 
		url = url +sessionId;
	
	return url;
};

