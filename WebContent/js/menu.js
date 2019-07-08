var prevScrollpos = window.pageYOffset;

$(document).ready(() => {
		
	let dropdown = $(".dropdown");
	let dropContent = $(".dropdown-content");
	
	const setPadding = () => {
		let header = $("header");
		let headerHeight = header.height();

		$(".content-wrapper").css("padding-top", headerHeight + 10);
	};
	
	const drop = () => {
		if(dropContent.css("display") == "none") 
			dropContent.css("display", "flex");
		else 
			dropContent.hide(400)
	};
	
	setPadding();
	
	let mqList = window.matchMedia("(max-width: 1024px)");
	if(mqList.matches)
		dropdown.on("click", drop);
	
	mqList.addListener((e) => {
		if(e.matches) {
			dropdown.on("click", drop);
		} else {
			dropdown.off("click");
		}
	});
	
	window.onscroll = () => {
		let header = $("header");
		let headerHeight = header.height();
		let currentScrollPos = window.pageYOffset;
			
		if (prevScrollpos > currentScrollPos) {
			header.css("top", "0")
		} else {
			if(dropContent.css("display") == "flex")
				dropdown.trigger("click");
			header.css("top", -(headerHeight-5));
		}
		prevScrollpos = currentScrollPos;
	};
	
	window.onresize = setPadding;
	
	$(document).click(() => dropContent.hide(400));

	dropdown.click((e) => e.stopPropagation());

});