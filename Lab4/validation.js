
function clickIt(form){
	var n = form.elements[1].value;
	var g = form.elements[2].value;
	var a = form.elements[3].value;
	var e = form.elements[4].value;
	var p = form.elements[5].value;
	
	out = "";
	
	if(!/\s*(?=\w+)/.test(n)){
		out += "Name Required (alphanumerical only)\n\n";
	}
	if(/none/.test(g)){
		out += "Gender Required\n\n";
	}
	if(!/[\W\w]/.test(a)){
		out += "Address Required\n\n";
	}
	if(!/\w+@\w+[.][a-z]+/.test(e)){
		out += "Email Required (alphanumeric only in 'xxx@xxx.xxx' format)\n\n";
	}
	if(!(/\d{3}\-\d{3}\-\d{4}/.test(p)|/\d{10}/.test(p)|!/[\W\w]/.test(p))){
		out += "Phone must be in 'xxx-xxx-xxxx' " +
				"or 'xxxxxxxxxx' format";
	}
	if(!/\w+/.test(out)){
		alert("Info Validated!");
	}
	else{
		alert(out);
	}
	
}