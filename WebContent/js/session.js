const encodeSessionId = (url) => {
	let currentUrl = window.location.href;
	let sessionId = currentUrl.substring(currentUrl.indexOf(";jsessionid"),currentUrl.length);
	if(currentUrl.length != sessionId.length) {
		url = url +sessionId;
	}
	
	return url;
};

