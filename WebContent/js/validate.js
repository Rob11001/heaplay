const validate = (field, regex) => {
	if(!field.value.match(regex)) {
		field.style.borderColor = "red";
		return false;
	}
	field.style.borderColor = "";
	return true;
};

function validateRegister(form) {
	let regexEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	let regexUsername = /^[\w\d]{3,}$/;
	return validate(form["email"], regexEmail) && validate(form["username"], regexUsername);								
}
