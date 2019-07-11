var prevScrollpos = window.pageYOffset;

const cartDrop = (flag) => {
	if(flag == true) {
		let div = $(".fa-shopping-cart").parent();
		if(div != undefined) {
			//Rimozione precedente
			$(div).remove();
			//Creazione del nuovo
			let parent = $(".dropdown-content");
			$($(parent).children()[$(parent).children().length-1]).before("<a href='"+encodeSessionId("/heaplay/cart")+"'>Carrello</a>");
		}
	} else {
		let div = $(".dropdown-content").children()[2];
		if(div != undefined) {
			//Rimozione precedente
			$(div).remove();
			//Creazione nuovo
			$("<a href="+encodeSessionId("/heaplay/cart")+"'><i class='fa fa-shopping-cart'></i></a>").appendTo($("nav.user"));
		}
	}
};




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
	
	let mqListTablet = window.matchMedia("(max-width: 1024px)");
	if(mqListTablet.matches)
		dropdown.on("click", drop);
	
	mqListTablet.addListener((e) => {
		if(e.matches) {
			dropdown.on("click", drop);
		} else {
			dropdown.off("click");
			dropContent.css("display", "");
		}
	});
	
	let mqListMobile = window.matchMedia("(max-width: 420px)");
	if(mqListMobile.matches)
		cartDrop(true);
	
	mqListMobile.addListener((e) => {
		if(e.matches) {
			cartDrop(true);
		} else {
			cartDrop(false);
		}
	});
	
	window.onscroll = () => {
		let header = $("header");
		let headerHeight = header.height();
		let currentScrollPos = window.pageYOffset;
			
		if (prevScrollpos > currentScrollPos) {
			header.css("top", "0")
		} else if(currentScrollPos > headerHeight) {
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