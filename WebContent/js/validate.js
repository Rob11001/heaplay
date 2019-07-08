function validateForm() {
	var valid = true;
	let formError = $(".form-error");
	
	$("form input").not("input[name='tags'], #divPrice:hidden input[name='price']").each((i, el) => {
		console.log(el.name);
		if(el.value == "") {
			el.style.borderColor = "red";
			valid = false;
		}
		else
			el.style.borderColor = "";
	});
	
	if(!valid){
		formError.html("Inserisci tutti i campi");
	}
	else {
		formError.html("");
	}
	
	
	const validateOnRegex = (field, regex, afterTest) => {
		if(!field.value.match(regex)) {
			field.style.borderColor = "red";
			valid = false;
			afterTest(false);
		}
		else {
			field.style.borderColor = "";
			afterTest(true);
		}
	};
	
	let username = $("input[name='username']");
	let email = $("input[name='email']");
	let password = $("input[name='password']");
	let repeatPassword = $("input[name='repeat-password']");
	let priceDiv = $("#divPrice");
	let price = $("input[name='price']"); 
	let tagField = $("input[name='tags']");
	let tags = $(".tag");
	let tagButton = $("#tagButton");
	
	let regexEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	let regexUsername = /^[\w\d]{3,}$/;
	let regexPrice = /^\d{0,1}(\.\d{1,2})?$/;

	
	if(username.length != 0) {
		validateOnRegex(username[0], regexUsername, (matches) => {
			if(matches) {
				username.next(".field-error").remove();
			}
			else if (username.next(".field-error").length == 0) {
				username.after("<span class='field-error'>L'username deve contenere almeno tre caratteri</span>");
			}
			
		});
	}
	
	if(email.length != 0) {
		validateOnRegex(email[0], regexEmail, (matches) => {
			if(matches) {
				email.next(".field-error").remove();
			}
			else if (email.next(".field-error").length == 0) {
				email.after("<span class='field-error'>L' e-mail specificata non è valida</span>");
			}
			
		});
	}
	
	if(repeatPassword.length != 0) {
		if(password.val() == repeatPassword.val()) {
			repeatPassword.css("border-color", "")
			repeatPassword.next(".field-error").remove();
		}
		else {
			valid = false;
			if(repeatPassword.next(".field-error").length == 0) {
				repeatPassword.css("border-color", "red");
				repeatPassword.after("<span class='field-error'>Le due password devono combaciare</span>");
			}
		}
	}
	
	if(price.length != 0) {
		if(priceDiv.css("display") != "none" && price.val() != "") {
			validateOnRegex(price[0], regexPrice, (matches) => {
				if(matches) {
					price.next(".field-error").remove()
				}
				else if (price.next(".field-error").length == 0){
					price.after("<span class='field-error'>Prezzo massimo: 9.99</span>")
				}
			});
		}
	}
	
	if(tags.length == 0) {
		valid = false;
		if(tagButton.next(".field-error").length == 0){
			tagField.css("border-color", "red");
			tagButton.after("<span class='field-error'>Devi inserire almeno un tag</span>")
		}
	} else {
		tagField.css("border-color", "");
		tagButton.next(".field-error").remove();
	}
	
	return valid;		
};
