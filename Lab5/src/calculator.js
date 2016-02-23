// CALCULATOR.JS
//
//
//

// 
var Calc = {

Model : {
	op1 : "",
	op2 : "",
	mem : 0,
	operator : undefined,
	display : false
},

View : {
  button0 : {id: "0", type: "button", value: "0", onclick:""},
  button1 : {id: "1", type: "button", value: "1", onclick:""},
  button2 : {id: "2", type: "button", value: "2", onclick:""},
  button3 : {id: "3", type: "button", value: "3", onclick:""},
  button4 : {id: "4", type: "button", value: "4", onclick:""},
  button5 : {id: "5", type: "button", value: "5", onclick:""},
  button6 : {id: "6", type: "button", value: "6", onclick:""},
  button7 : {id: "7", type: "button", value: "7", onclick:""},
  button8 : {id: "8", type: "button", value: "8", onclick:""},
  button9 : {id: "9", type: "button", value: "9", onclick:""},
  buttondec : {id: ".", type: "button", value: ".", onclick:""},
  buttonadd : {id: "+", type: "button", value: "+", onclick:""},
  buttonsub : {id: "-", type: "button", value: "-", onclick:""},
  buttonmul : {id: "x", type: "button", value: "x", onclick:""},
  buttondiv : {id: "/", type: "button", value: "/", onclick:""},
  buttoneq : {id: "=", type: "button", value: "=", onclick:""},
  buttonclear : {id: "C", type: "button", value: "C", onclick:""},
  buttonmemclear : {id: "MC", type: "button", value: "MC", onclick:""},
  buttonmemread : {id: "MR", type: "button", value: "MR", onclick:""},
  buttonmemsub : {id: "M-", type: "button", value: "M-", onclick:""},
  buttonmemadd : {id: "M+", type: "button", value: "M+", onclick:""},
  textRow : {id: "textRow", type: "text", value: "", onclick:""}
},

Controller : {

	numberButtonHandler : function(that) {
		if ( Calc.Model.display ) {
			textRow.value = that.value;
			Calc.Model.display = false;
		}
		else {
			textRow.value += that.value;
		}
	},
	
	opButtonHandler : function(that) {
	
		if ( Calc.Model.op1.length == 0 && textRow.value.length == 0 ) {
			return;
		}
		
		if ( Calc.Model.operation == undefined ) {
			Calc.Model.operation = that.value;
			Calc.Model.op1 = textRow.value;
			textRow.value = "";
		}
		else {
			Calc.Model.op2 = textRow.value;
			Calc.Model.op1 = Calc.Controller.processOp();
			Calc.Model.operation = that.value;
			textRow.value = Calc.Model.op1;
			Calc.Model.display = true;
			Calc.Model.op2 = "";
		}
	},
	
	equalButtonHandler : function(that) {
	
		if ( Calc.Model.op1.length > 0 && Calc.Model.operation == undefined ) {
			textRow.value = processOp();
			Calc.Model.op1 = textRow.value;
		}
		else if (Calc.Model.op1.length > 0 && Calc.Model.operation != undefined) {
			Calc.Model.op2 = textRow.value;
			textRow.value = Calc.Controller.processOp();
			Calc.Model.op1 = textRow.value;
		}
		Calc.Model.operation = undefined;
		Calc.Model.display = true;
	},
	
	clearButtonHandler : function(that) {
		Calc.Model.op1 = "";
		Calc.Model.op2 = "";
		Calc.Model.operation = undefined;
		textRow.value = "";
	},
	
	memAddButtonHandler : function(that) {
		Calc.Model.mem += parseFloat(textRow.value);
	},
	
	memSubButtonHandler : function(that) {
		Calc.Model.mem -= parseFloat(textRow.value);
	},
	
	memReadButtonHandler : function(that) {
		textRow.value = Calc.Model.mem.toString();
	},
	
	memClearButtonHandler : function(that) {
		Calc.Model.mem = 0;
	},
	
	processOp : function() {
		switch (Calc.Model.operation) {
			case "+": 	return Calc.Controller.addition();
						break;
			case "-": 	return Calc.Controller.subtraction();
						break;
			case "x": 	return Calc.Controller.multiplication();
						break;
			case "/": 	return Calc.Controller.division();
						break;
						
		}
	},
	
	addition : function() {
		Calc.Model.operation = undefined;
		return (parseFloat(Calc.Model.op1) + parseFloat(Calc.Model.op2)).toString();
	},
	
	subtraction : function() {
		Calc.Model.operation = undefined;
		return (parseFloat(Calc.Model.op1) - parseFloat(Calc.Model.op2)).toString();
	},
	
	multiplication : function() {
		Calc.Model.operation = undefined;
		return (parseFloat(Calc.Model.op1) * parseFloat(Calc.Model.op2)).toString();
	},
	
	division : function() {
		Calc.Model.operation = undefined;
		if ( parseFloat(Calc.Model.op2) == 0 ) {
			return "ERROR: Division by Zero";
		}
		else {
			return (parseFloat(Calc.Model.op1) / parseFloat(Calc.Model.op2)).toString();
		}
	}
},

run : function() {
  Calc.attachHandlers();
  console.log(Calc.display());
  return Calc.display();
},


displayElement : function (element) {
  var s = "<input ";
  s += " id=\"" + element.id + "\"";
  s += " type=\"" + element.type + "\"";
  s += " value= \"" + element.value + "\"";
  s += " onclick= \"" + element.onclick + "\"";
  s += ">";
  return s;

},

display : function() {
  var s;
  s = "<table id=\"myTable\" border=0>"
  s += "<tr><td>" + Calc.displayElement(Calc.View.textRow) + "</td></tr>";
  s += "<tr><td>";
  s += Calc.displayElement(Calc.View.button7);
  s += Calc.displayElement(Calc.View.button8);
  s += Calc.displayElement(Calc.View.button9);
  s += Calc.displayElement(Calc.View.buttonadd);
  s += "</tr></td>";
  s += "<tr><td>";
  s += Calc.displayElement(Calc.View.button4);
  s += Calc.displayElement(Calc.View.button5);
  s += Calc.displayElement(Calc.View.button6);
  s += Calc.displayElement(Calc.View.buttonsub);
  s += "</tr></td>";
  s += "<tr><td>";
  s += Calc.displayElement(Calc.View.button1);
  s += Calc.displayElement(Calc.View.button2);
  s += Calc.displayElement(Calc.View.button3);
  s += Calc.displayElement(Calc.View.buttonmul);
  s += "</tr></td>";
  s += "<tr><td>";
  s += Calc.displayElement(Calc.View.button0);
  s += Calc.displayElement(Calc.View.buttondec);
  s += Calc.displayElement(Calc.View.buttoneq);
  s += Calc.displayElement(Calc.View.buttondiv);
  s += "</tr></td>";
  s += "<tr><td>";
  s += Calc.displayElement(Calc.View.buttonclear);
  s += Calc.displayElement(Calc.View.buttonmemread);
  s += Calc.displayElement(Calc.View.buttonmemsub);
  s += Calc.displayElement(Calc.View.buttonmemadd);  
  s += "</tr></td>";
  s += "<tr><td colspan=\"4\">";
  s += Calc.displayElement(Calc.View.buttonmemclear);
  s += "</td></tr></table>";
  return s;
},

attachHandlers : function() {
	for (var i = 0; i <= 9; i++) {
		Calc.View["button" + i].onclick ="Calc.Controller.numberButtonHandler(this)";
	}
	Calc.View["buttondec"].onclick ="Calc.Controller.numberButtonHandler(this)";
	
	Calc.View["buttonadd"].onclick ="Calc.Controller.opButtonHandler(this)";
	Calc.View["buttonsub"].onclick ="Calc.Controller.opButtonHandler(this)";
	Calc.View["buttonmul"].onclick ="Calc.Controller.opButtonHandler(this)";
	Calc.View["buttondiv"].onclick ="Calc.Controller.opButtonHandler(this)";
	
	Calc.View["buttoneq"].onclick ="Calc.Controller.equalButtonHandler(this)";
	
	Calc.View["buttonclear"].onclick ="Calc.Controller.clearButtonHandler(this)";
	Calc.View["buttonmemclear"].onclick ="Calc.Controller.memClearButtonHandler(this)";
	Calc.View["buttonmemread"].onclick ="Calc.Controller.memReadButtonHandler(this)";
	Calc.View["buttonmemsub"].onclick ="Calc.Controller.memSubButtonHandler(this)";
	Calc.View["buttonmemadd"].onclick ="Calc.Controller.memAddButtonHandler(this)";
},

} // end of Calc;
