grammar RPN;

@header {
  import java.util.*;
}

@members {
  /////////////////////////////////////////////////////////////////////////////////////////////
  // NUMERIC CALCULATOR
  Stack<Integer> stack = new Stack<Integer>();
  int stack_size = 0;
  
  String op = "\0";
  
  void add(int a, int b){
    int result = a + b;
    //System.out.println("Added (" + a + ") and (" + b + "), result : " + result );
    pushInt(result);
	//System.out.println("---------------");
  }
  
  void subtract(int a, int b){
    int result = a - b;
    //System.out.println("Subtracted (" + a + ") and (" + b + "), result : " + result );
    pushInt(result);
	//System.out.println("---------------");
  }
  
  void multiply(int a, int b){
    int result = a * b;
    //System.out.println("Multiplied (" + a + ") and (" + b + "), result : " + result );
    pushInt(result);
	//System.out.println("---------------");
  }
  
  void divide(int a, int b){
    int result = a / b;
    //System.out.println("Divided (" + a + ") and (" + b + "), result : " + result );
    pushInt(result);
	//System.out.println("---------------");
  }
  
  void mod(int a, int b){
    int result = a % b;
    //System.out.println("Modded (" + a + ") and (" + b + "), result : " + result );
    pushInt(result);
	//System.out.println("---------------");
  }
  
  void pushInt(int num){
    stack.push(num);
	stack_size++;
	//System.out.println("Pushed (" + num + ") to the stack");
  }
  
  void reset_intStack(){
    stack_size = 0;
	while(!stack.empty()){
	  //System.out.println("Popping: " + stack.pop());
	  stack.pop();
	}
  }
  
  void runOp(){
    
	if ( stack_size >= 2 ){
	  int b = stack.pop();
	  int a = stack.pop();
	  stack_size -= 2;
	  //System.out.println("---------------");
	  //System.out.println("Popped (" + b + ") and (" + a + ") from the stack");
	  
	  switch (op) {
	    case "+": add(a, b); break;
	    case "-": subtract(a, b); break;
	    case "*": multiply(a, b); break;
	    case "/": divide(a, b); break;
	    case "%": mod(a, b); break;
		case "<":   relation(a,b,op); break;
		case "<=": relation(a,b,op); break;
		case ">":   relation(a,b,op); break;
		case ">=": relation(a,b,op); break;
		case "==": relation(a,b,op); break;
		case "!=":  relation(a,b,op); break;
		case "\0" : System.out.println("FAILURE: no operator set");  break; //terminate();
	    default: System.out.println("FAILURE: incorrect operator (" + op + ")");   break; //terminate();
	  }
	  op = "\0";
	}
	else{
	  System.out.println("INVALID FORMAT: Not enough operands for the operator (" + op + ")");
	   terminate();
	}
  }
  
  void getResult(){
    if (stack_size == 1){
	  get_stackResult();
	}
	else if (logstack_size == 1) {
	  get_logstackResult();
	}
	else {
	  System.out.println("INVALID FORMAT: Too many/few operands left in final result");
	  terminate();
	}
  }
  
  void get_logstackResult(){
    //System.out.println("Result: " + logstack.pop());
	System.out.print(logstack.pop() + "; ");
	//System.out.println("\n\n");
	reset_logicalStack();
  }
  
  void get_stackResult(){
    //System.out.println("Result: " + stack.pop());
    System.out.print(stack.pop() + "; ");
	//System.out.println("\n\n");
	reset_intStack();
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////
  // LOGICAL CALCULATOR
  
  Stack<String> logstack = new Stack<String>();
  int logstack_size = 0;
  
  String logOp = "\0";
  
  boolean getBool(String bool){
    boolean res = false;
    if (bool.equals("true")){ res = true; }
	return res;
  }
  
  void finishBoolExpr(boolean result, String operation, String a, String b){
    String res = "false";
	if (result){ res = "true"; }

    //System.out.println(operation + " (" + a + ") and (" + b + "), result : " + res );
    pushBool(res);
	//System.out.println("---------------");
  }
  
  void and(String a, String b){
    boolean left = getBool(a);
    boolean right = getBool(b);
	
	boolean result = left && right;
	
    finishBoolExpr(result, "Anded", a, b);
  }
  
  void or(String a, String b){
    boolean left = getBool(a);
    boolean right = getBool(b);
	
	boolean result = left || right;
	
    finishBoolExpr(result, "Ored", a, b);
  }
  
  void not(String a){
    boolean left = getBool(a);
	
	boolean result = !left;
	
	String b = "";
	
    finishBoolExpr(result, "Notted", a, b);
  }
  
  void relation(int a, int b, String operator){
  
    boolean result = false;
	switch (operator) {
	  case "<":  result = a < b;
	    break;
	  case "<=":  result = a <= b;
	    break;
	  case ">":  result = a > b;
	    break;
	  case ">=":  result = a >= b;
	    break;
	  case "==":  result = a == b;
	    break;
	  case "!=":  result = a != b;
	    break;
      case "\0" : System.out.println("FAILURE: no operator set"); break; //terminate();
	  default: System.out.println("FAILURE: incorrect operator (" + operator + ")"); break; // terminate();
	
	}
	
	
	finishBoolExpr(result, operator, Integer.toString(a), Integer.toString(b));
  }
  
  void pushBool(String bool){
    logstack.push(bool);
	logstack_size++;
	//System.out.println("Pushed (" + bool + ") to the stack");
  }
  
  void runLogOp(){
    
	if (logOp.equals("!")) {
	  String a = logstack.pop();
	  logstack_size--;
	  //System.out.println("---------------");
	  //System.out.println("Popped (" + a + ") from the stack");
	  not(a);
	}
	else if ( logstack_size >= 2 ){
	  String b = logstack.pop();
	  String a = logstack.pop();
	  logstack_size -= 2;
	  //System.out.println("---------------");
	  //System.out.println("Popped (" + b + ") and (" + a + ") from the stack");
	  
	  switch (logOp) {
	    case "&&":  and(a, b); break;
	    case "||":   or(a, b); break;
		case "\0" : System.out.println("FAILURE: no operator set"); break; //terminate();
	    default: System.out.println("FAILURE: incorrect operator (" + logOp + ")");   break; //terminate();
	  }
	  logOp = "\0";
	}
	else{
	  System.out.println("INVALID FORMAT: Not enough operands for the operator (" + logOp + ")");
	   terminate();
	}
  }
  
  void reset_logicalStack(){
    logstack_size = 0;
	while(!logstack.empty()){
	  //System.out.println("Popping: " + logstack.pop());
	  logstack.pop();
	}
  }
  
  void terminate(){
    System.out.println("TERMINATING EXECUTION");
	System.exit(1);
  }
}
 	 
// Parser rules
start
  @after {System.out.println("\n------------------------------------------");}
    : {System.out.println("------------------------------------------\nResults:");} ( statement )+
  ;
  
statement
    : ((expr)+ ';' {getResult();})
  ;

expr
    : ( INT {pushInt($INT.int);} )+ ( arithmetic {runOp();} )+
	| ( INT {pushInt($INT.int);} | CHAR {pushBool($CHAR.text);} )+  ( boolOp {runLogOp();} | relOp {runOp();} )+
	| ( CHAR {pushBool($CHAR.text);} )+  ( boolOp {runLogOp();})+
  ;
  
op 
    : arithmetic | relOp
  ;
  
arithmetic
    :  '+' {op = "+";}
    |  '-' {op = "-";}
    |  '*' {op = "*";}
    |  '/' {op = "/";}
    |  '%' {op = "%";}
  ;
  
boolOp
    :  '&&' {logOp = "&&";}
    |  '||' {logOp = "||";}
    |  '!' {logOp = "!";}
  ;

relOp
    :  '<'   {op = "<";}
    |  '<=' {op = "<=";}
    |  '==' {op = "==";}
    |  '!='  {op = "!=";}
    |  '>'   {op = ">";}
    |  '>=' {op = ">=";}
  ;
  
// Lexer rules
INT : [0-9]+  ;
CHAR : [a-zA-Z]+ ;
AND : '&&';
OR : '||';
NOT : '!';
WS : [ \r\t\n]+ -> skip ;
