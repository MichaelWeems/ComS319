// Generated from RPN.g4 by ANTLR 4.5.2

  import java.util.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RPNParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, INT=13, CHAR=14, AND=15, OR=16, NOT=17, WS=18;
	public static final int
		RULE_start = 0, RULE_statement = 1, RULE_expr = 2, RULE_op = 3, RULE_arithmetic = 4, 
		RULE_boolOp = 5, RULE_relOp = 6;
	public static final String[] ruleNames = {
		"start", "statement", "expr", "op", "arithmetic", "boolOp", "relOp"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'+'", "'-'", "'*'", "'/'", "'%'", "'<'", "'<='", "'=='", 
		"'!='", "'>'", "'>='", null, null, "'&&'", "'||'", "'!'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, "INT", "CHAR", "AND", "OR", "NOT", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "RPN.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


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

	public RPNParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StartContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).exitStart(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			System.out.println("------------------------------------------\nResults:");
			setState(16); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(15);
				statement();
				}
				}
				setState(18); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==INT || _la==CHAR );
			}
			System.out.println("\n------------------------------------------");
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(21); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(20);
				expr();
				}
				}
				setState(23); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==INT || _la==CHAR );
			setState(25);
			match(T__0);
			getResult();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public Token INT;
		public Token CHAR;
		public List<TerminalNode> INT() { return getTokens(RPNParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(RPNParser.INT, i);
		}
		public List<ArithmeticContext> arithmetic() {
			return getRuleContexts(ArithmeticContext.class);
		}
		public ArithmeticContext arithmetic(int i) {
			return getRuleContext(ArithmeticContext.class,i);
		}
		public List<TerminalNode> CHAR() { return getTokens(RPNParser.CHAR); }
		public TerminalNode CHAR(int i) {
			return getToken(RPNParser.CHAR, i);
		}
		public List<BoolOpContext> boolOp() {
			return getRuleContexts(BoolOpContext.class);
		}
		public BoolOpContext boolOp(int i) {
			return getRuleContext(BoolOpContext.class,i);
		}
		public List<RelOpContext> relOp() {
			return getRuleContexts(RelOpContext.class);
		}
		public RelOpContext relOp(int i) {
			return getRuleContext(RelOpContext.class,i);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_expr);
		int _la;
		try {
			setState(72);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(30); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(28);
					((ExprContext)_localctx).INT = match(INT);
					pushInt((((ExprContext)_localctx).INT!=null?Integer.valueOf(((ExprContext)_localctx).INT.getText()):0));
					}
					}
					setState(32); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==INT );
				setState(37); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(34);
					arithmetic();
					runOp();
					}
					}
					setState(39); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5))) != 0) );
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(45); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					setState(45);
					switch (_input.LA(1)) {
					case INT:
						{
						setState(41);
						((ExprContext)_localctx).INT = match(INT);
						pushInt((((ExprContext)_localctx).INT!=null?Integer.valueOf(((ExprContext)_localctx).INT.getText()):0));
						}
						break;
					case CHAR:
						{
						setState(43);
						((ExprContext)_localctx).CHAR = match(CHAR);
						pushBool((((ExprContext)_localctx).CHAR!=null?((ExprContext)_localctx).CHAR.getText():null));
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(47); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==INT || _la==CHAR );
				setState(55); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					setState(55);
					switch (_input.LA(1)) {
					case AND:
					case OR:
					case NOT:
						{
						setState(49);
						boolOp();
						runLogOp();
						}
						break;
					case T__6:
					case T__7:
					case T__8:
					case T__9:
					case T__10:
					case T__11:
						{
						setState(52);
						relOp();
						runOp();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(57); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << AND) | (1L << OR) | (1L << NOT))) != 0) );
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(61); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(59);
					((ExprContext)_localctx).CHAR = match(CHAR);
					pushBool((((ExprContext)_localctx).CHAR!=null?((ExprContext)_localctx).CHAR.getText():null));
					}
					}
					setState(63); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==CHAR );
				setState(68); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(65);
					boolOp();
					runLogOp();
					}
					}
					setState(70); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AND) | (1L << OR) | (1L << NOT))) != 0) );
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpContext extends ParserRuleContext {
		public ArithmeticContext arithmetic() {
			return getRuleContext(ArithmeticContext.class,0);
		}
		public RelOpContext relOp() {
			return getRuleContext(RelOpContext.class,0);
		}
		public OpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).enterOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).exitOp(this);
		}
	}

	public final OpContext op() throws RecognitionException {
		OpContext _localctx = new OpContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_op);
		try {
			setState(76);
			switch (_input.LA(1)) {
			case T__1:
			case T__2:
			case T__3:
			case T__4:
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(74);
				arithmetic();
				}
				break;
			case T__6:
			case T__7:
			case T__8:
			case T__9:
			case T__10:
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				relOp();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithmeticContext extends ParserRuleContext {
		public ArithmeticContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmetic; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).enterArithmetic(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).exitArithmetic(this);
		}
	}

	public final ArithmeticContext arithmetic() throws RecognitionException {
		ArithmeticContext _localctx = new ArithmeticContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_arithmetic);
		try {
			setState(88);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				match(T__1);
				op = "+";
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 2);
				{
				setState(80);
				match(T__2);
				op = "-";
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				match(T__3);
				op = "*";
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 4);
				{
				setState(84);
				match(T__4);
				op = "/";
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 5);
				{
				setState(86);
				match(T__5);
				op = "%";
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolOpContext extends ParserRuleContext {
		public BoolOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).enterBoolOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).exitBoolOp(this);
		}
	}

	public final BoolOpContext boolOp() throws RecognitionException {
		BoolOpContext _localctx = new BoolOpContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_boolOp);
		try {
			setState(96);
			switch (_input.LA(1)) {
			case AND:
				enterOuterAlt(_localctx, 1);
				{
				setState(90);
				match(AND);
				logOp = "&&";
				}
				break;
			case OR:
				enterOuterAlt(_localctx, 2);
				{
				setState(92);
				match(OR);
				logOp = "||";
				}
				break;
			case NOT:
				enterOuterAlt(_localctx, 3);
				{
				setState(94);
				match(NOT);
				logOp = "!";
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelOpContext extends ParserRuleContext {
		public RelOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).enterRelOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RPNListener ) ((RPNListener)listener).exitRelOp(this);
		}
	}

	public final RelOpContext relOp() throws RecognitionException {
		RelOpContext _localctx = new RelOpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_relOp);
		try {
			setState(110);
			switch (_input.LA(1)) {
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(98);
				match(T__6);
				op = "<";
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 2);
				{
				setState(100);
				match(T__7);
				op = "<=";
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 3);
				{
				setState(102);
				match(T__8);
				op = "==";
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 4);
				{
				setState(104);
				match(T__9);
				op = "!=";
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 5);
				{
				setState(106);
				match(T__10);
				op = ">";
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 6);
				{
				setState(108);
				match(T__11);
				op = ">=";
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\24s\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3\2\6\2\23\n\2\r\2\16\2"+
		"\24\3\3\6\3\30\n\3\r\3\16\3\31\3\3\3\3\3\3\3\4\3\4\6\4!\n\4\r\4\16\4\""+
		"\3\4\3\4\3\4\6\4(\n\4\r\4\16\4)\3\4\3\4\3\4\3\4\6\4\60\n\4\r\4\16\4\61"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\6\4:\n\4\r\4\16\4;\3\4\3\4\6\4@\n\4\r\4\16\4"+
		"A\3\4\3\4\3\4\6\4G\n\4\r\4\16\4H\5\4K\n\4\3\5\3\5\5\5O\n\5\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6[\n\6\3\7\3\7\3\7\3\7\3\7\3\7\5\7c\n\7"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\bq\n\b\3\b\2\2\t\2"+
		"\4\6\b\n\f\16\2\2\u0083\2\20\3\2\2\2\4\27\3\2\2\2\6J\3\2\2\2\bN\3\2\2"+
		"\2\nZ\3\2\2\2\fb\3\2\2\2\16p\3\2\2\2\20\22\b\2\1\2\21\23\5\4\3\2\22\21"+
		"\3\2\2\2\23\24\3\2\2\2\24\22\3\2\2\2\24\25\3\2\2\2\25\3\3\2\2\2\26\30"+
		"\5\6\4\2\27\26\3\2\2\2\30\31\3\2\2\2\31\27\3\2\2\2\31\32\3\2\2\2\32\33"+
		"\3\2\2\2\33\34\7\3\2\2\34\35\b\3\1\2\35\5\3\2\2\2\36\37\7\17\2\2\37!\b"+
		"\4\1\2 \36\3\2\2\2!\"\3\2\2\2\" \3\2\2\2\"#\3\2\2\2#\'\3\2\2\2$%\5\n\6"+
		"\2%&\b\4\1\2&(\3\2\2\2\'$\3\2\2\2()\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*K\3\2"+
		"\2\2+,\7\17\2\2,\60\b\4\1\2-.\7\20\2\2.\60\b\4\1\2/+\3\2\2\2/-\3\2\2\2"+
		"\60\61\3\2\2\2\61/\3\2\2\2\61\62\3\2\2\2\629\3\2\2\2\63\64\5\f\7\2\64"+
		"\65\b\4\1\2\65:\3\2\2\2\66\67\5\16\b\2\678\b\4\1\28:\3\2\2\29\63\3\2\2"+
		"\29\66\3\2\2\2:;\3\2\2\2;9\3\2\2\2;<\3\2\2\2<K\3\2\2\2=>\7\20\2\2>@\b"+
		"\4\1\2?=\3\2\2\2@A\3\2\2\2A?\3\2\2\2AB\3\2\2\2BF\3\2\2\2CD\5\f\7\2DE\b"+
		"\4\1\2EG\3\2\2\2FC\3\2\2\2GH\3\2\2\2HF\3\2\2\2HI\3\2\2\2IK\3\2\2\2J \3"+
		"\2\2\2J/\3\2\2\2J?\3\2\2\2K\7\3\2\2\2LO\5\n\6\2MO\5\16\b\2NL\3\2\2\2N"+
		"M\3\2\2\2O\t\3\2\2\2PQ\7\4\2\2Q[\b\6\1\2RS\7\5\2\2S[\b\6\1\2TU\7\6\2\2"+
		"U[\b\6\1\2VW\7\7\2\2W[\b\6\1\2XY\7\b\2\2Y[\b\6\1\2ZP\3\2\2\2ZR\3\2\2\2"+
		"ZT\3\2\2\2ZV\3\2\2\2ZX\3\2\2\2[\13\3\2\2\2\\]\7\21\2\2]c\b\7\1\2^_\7\22"+
		"\2\2_c\b\7\1\2`a\7\23\2\2ac\b\7\1\2b\\\3\2\2\2b^\3\2\2\2b`\3\2\2\2c\r"+
		"\3\2\2\2de\7\t\2\2eq\b\b\1\2fg\7\n\2\2gq\b\b\1\2hi\7\13\2\2iq\b\b\1\2"+
		"jk\7\f\2\2kq\b\b\1\2lm\7\r\2\2mq\b\b\1\2no\7\16\2\2oq\b\b\1\2pd\3\2\2"+
		"\2pf\3\2\2\2ph\3\2\2\2pj\3\2\2\2pl\3\2\2\2pn\3\2\2\2q\17\3\2\2\2\21\24"+
		"\31\")/\619;AHJNZbp";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}