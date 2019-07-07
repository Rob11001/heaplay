var prevScrollpos = window.pageYOffset;

$(document).ready(() => {
	
	let matches = window.matchMedia("only screen and (max-width: 420px)");
	if(matches) {
		$(".dropdown").on("click", () => {
			console.log("Test ");
			if($(".dropdown-content").css("display") == "none") {
				$(".dropdown-content").css("display", "flex");
				$(".dropdown-content").css("height", "auto");
			}
			else {
				$(".dropdown-content").css("height", "0");
				$(".dropdown-content").css("display", "none");
			}
	    });
	}
	
	window.onscroll = () => {
		let header = $("header");
		let headerHeight = header.height();
		let currentScrollPos = window.pageYOffset;
			
		if (prevScrollpos > currentScrollPos) {
			header.css("top", "0")
		} else {
			if(matches && $(".dropdown-content").css("display") == "flex")
				$(".dropdown").trigger("click", 1);
			header.css("top", -(headerHeight-5));
		}
		prevScrollpos = currentScrollPos;
	}
	
	


});