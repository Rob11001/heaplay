var prevScrollpos = window.pageYOffset;

$(document).ready(() => {
	
	const dropdown = (show) => {
		if(show == undefined) {
			if($(".dropdown-content").css("display") == "none") {
				$(".dropdown-content").css("display", "flex");
				$(".dropdown-content").css("height", "auto");
			}
			else {
				$(".dropdown-content").css("height", "0");
				$(".dropdown-content").css("display", "none");
			}
		}
		else if(show) {
			$(".dropdown-content").css("display", "flex");
			$(".dropdown-content").css("height", "auto");
		}
		else {
			$(".dropdown-content").css("height", "0");
			$(".dropdown-content").css("display", "none");
		}
	};
	
	let mql = window.matchMedia('only screen and (max-width:420px)');
	
	mql.addListener((mq) => {
		if (mq.matches) {
            $(".dropdown .dropbtn").click(() => {
            	dropdown();
            });
        } else {
        	$(".dropdown .dropbtn").off("click");
        }
	});
	
	window.onscroll = () => {
		let header = $("header");
		let headerHeight = header.height();
		let currentScrollPos = window.pageYOffset;
			
		if (prevScrollpos > currentScrollPos) {
			header.css("top", "0")
		} else {
			dropdown(false);
			header.css("top", -headerHeight-5);
		}
		prevScrollpos = currentScrollPos;
	}
	
	


});