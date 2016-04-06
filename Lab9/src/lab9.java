// Generated from lab9.g4 by ANTLR 4.5.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class lab9 extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		TAG=1, EMAIL_BLOCK=2, DATE_BLOCK=3, PHONE_BLOCK=4, JCB_BLOCK=5, WS=6;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"DIGIT", "TWO_DIGITS", "THREE_DIGITS", "FOUR_DIGITS", "ALPHA", "X_EXCLUDE", 
		"TAG_OPEN", "TAG_CLOSE", "TAG_END", "NAME_SPECIALS", "NAME_CHARS", "NAME_START", 
		"NAME", "TAG", "LOCALPART", "LOCALPART_CHARS", "PERIOD", "DOMAINPART", 
		"DOMAINPART_CHARS", "EMAIL", "EMAIL_BLOCK", "DAY_ONE", "DAY_TENS", "DAY_MAX", 
		"DAY", "MONTH_ONE", "MONTH_TEN", "MONTH", "YEAR_START", "YEAR_MAX", "YEAR", 
		"DATE", "DATE_BLOCK", "PHONE_DASHED", "PHONE_BRACKETED", "PHONE_SPACED", 
		"PHONE_DOTTED", "PHONE", "PHONE_BLOCK", "VISA", "VISA_NEW", "VISA_OLD", 
		"MASTERCARD", "AMERICAN_EXPRESS", "DINERS_CLUB", "DISCOVER", "JCB_15", 
		"JCB_16", "JCB", "CREDIT_CARD", "JCB_BLOCK", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "TAG", "EMAIL_BLOCK", "DATE_BLOCK", "PHONE_BLOCK", "JCB_BLOCK", 
		"WS"
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


	public lab9(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "lab9.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 13:
			TAG_action((RuleContext)_localctx, actionIndex);
			break;
		case 20:
			EMAIL_BLOCK_action((RuleContext)_localctx, actionIndex);
			break;
		case 32:
			DATE_BLOCK_action((RuleContext)_localctx, actionIndex);
			break;
		case 38:
			PHONE_BLOCK_action((RuleContext)_localctx, actionIndex);
			break;
		case 50:
			JCB_BLOCK_action((RuleContext)_localctx, actionIndex);
			break;
		case 51:
			WS_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void TAG_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 System.out.println("Name: " + getText()); 
			break;
		}
	}
	private void EMAIL_BLOCK_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			 System.out.println("Email: " + getText()); 
			break;
		}
	}
	private void DATE_BLOCK_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			 System.out.println("Date: " + getText()); 
			break;
		}
	}
	private void PHONE_BLOCK_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:
			 System.out.println("PHONE: " + getText()); 
			break;
		}
	}
	private void JCB_BLOCK_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 4:
			 System.out.println("Credit Card: " + getText()); 
			break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 5:
			skip();
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\b\u019c\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5"+
		"\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\5\13\u0087\n\13"+
		"\3\f\3\f\5\f\u008b\n\f\3\r\3\r\5\r\u008f\n\r\3\16\3\16\7\16\u0093\n\16"+
		"\f\16\16\16\u0096\13\16\3\17\3\17\5\17\u009a\n\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\6\20\u00a2\n\20\r\20\16\20\u00a3\3\20\5\20\u00a7\n\20\3\20"+
		"\3\20\6\20\u00ab\n\20\r\20\16\20\u00ac\7\20\u00af\n\20\f\20\16\20\u00b2"+
		"\13\20\3\21\3\21\3\22\3\22\3\23\6\23\u00b9\n\23\r\23\16\23\u00ba\3\24"+
		"\3\24\3\24\5\24\u00c0\n\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27"+
		"\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\5\32\u00d5\n\32\3\33"+
		"\3\33\3\33\3\34\3\34\3\34\3\35\3\35\5\35\u00df\n\35\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3 \3 \5 \u00ee\n \3!\3!\3!\3!\3!\3"+
		"!\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3%\3%\3%\3"+
		"%\3%\3%\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\5\'\u0118\n\'\3(\3(\3(\3)\3"+
		")\3)\5)\u0120\n)\3*\3*\3*\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3,\3"+
		",\3,\3,\3,\3,\3,\3,\3,\3-\3-\3-\3-\5-\u013f\n-\3-\3-\3-\3-\3-\3-\3-\3"+
		"-\3.\3.\3.\3.\3.\5.\u014e\n.\3.\5.\u0151\n.\3.\3.\3.\3.\3.\3.\3.\3/\3"+
		"/\3/\3/\3/\3/\3/\3/\5/\u0162\n/\3/\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3"+
		"\60\3\60\3\60\3\60\3\60\5\60\u0173\n\60\3\60\3\60\3\60\3\60\3\60\3\60"+
		"\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62"+
		"\5\62\u0189\n\62\3\63\3\63\3\63\3\63\3\63\3\63\5\63\u0191\n\63\3\64\3"+
		"\64\3\64\3\65\6\65\u0197\n\65\r\65\16\65\u0198\3\65\3\65\3\u0094\2\66"+
		"\3\2\5\2\7\2\t\2\13\2\r\2\17\2\21\2\23\2\25\2\27\2\31\2\33\2\35\3\37\2"+
		"!\2#\2%\2\'\2)\2+\4-\2/\2\61\2\63\2\65\2\67\29\2;\2=\2?\2A\2C\5E\2G\2"+
		"I\2K\2M\2O\6Q\2S\2U\2W\2Y\2[\2]\2_\2a\2c\2e\2g\7i\b\3\2\16\3\2\62;\4\2"+
		"C\\c|\6\2CY[\\cy{|\4\2/\60aa\t\2##&&(/<=??aa\u0080\u0080\3\2\63;\3\2\63"+
		"\64\3\2\62\63\3\2\62\64\3\2\63\67\4\2\62\65\67\67\5\2\13\f\17\17\"\"\u018f"+
		"\2\35\3\2\2\2\2+\3\2\2\2\2C\3\2\2\2\2O\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\3"+
		"k\3\2\2\2\5m\3\2\2\2\7p\3\2\2\2\tt\3\2\2\2\13y\3\2\2\2\r{\3\2\2\2\17}"+
		"\3\2\2\2\21\177\3\2\2\2\23\u0081\3\2\2\2\25\u0086\3\2\2\2\27\u008a\3\2"+
		"\2\2\31\u008e\3\2\2\2\33\u0090\3\2\2\2\35\u0099\3\2\2\2\37\u00a1\3\2\2"+
		"\2!\u00b3\3\2\2\2#\u00b5\3\2\2\2%\u00b8\3\2\2\2\'\u00bf\3\2\2\2)\u00c1"+
		"\3\2\2\2+\u00c5\3\2\2\2-\u00c8\3\2\2\2/\u00cb\3\2\2\2\61\u00ce\3\2\2\2"+
		"\63\u00d4\3\2\2\2\65\u00d6\3\2\2\2\67\u00d9\3\2\2\29\u00de\3\2\2\2;\u00e0"+
		"\3\2\2\2=\u00e6\3\2\2\2?\u00ed\3\2\2\2A\u00ef\3\2\2\2C\u00f5\3\2\2\2E"+
		"\u00f8\3\2\2\2G\u00fe\3\2\2\2I\u0107\3\2\2\2K\u010d\3\2\2\2M\u0117\3\2"+
		"\2\2O\u0119\3\2\2\2Q\u011c\3\2\2\2S\u0121\3\2\2\2U\u0129\3\2\2\2W\u0131"+
		"\3\2\2\2Y\u013e\3\2\2\2[\u0150\3\2\2\2]\u0161\3\2\2\2_\u0172\3\2\2\2a"+
		"\u017b\3\2\2\2c\u0188\3\2\2\2e\u0190\3\2\2\2g\u0192\3\2\2\2i\u0196\3\2"+
		"\2\2kl\t\2\2\2l\4\3\2\2\2mn\5\3\2\2no\5\3\2\2o\6\3\2\2\2pq\5\3\2\2qr\5"+
		"\3\2\2rs\5\3\2\2s\b\3\2\2\2tu\5\3\2\2uv\5\3\2\2vw\5\3\2\2wx\5\3\2\2x\n"+
		"\3\2\2\2yz\t\3\2\2z\f\3\2\2\2{|\t\4\2\2|\16\3\2\2\2}~\7>\2\2~\20\3\2\2"+
		"\2\177\u0080\7@\2\2\u0080\22\3\2\2\2\u0081\u0082\7>\2\2\u0082\u0083\7"+
		"\61\2\2\u0083\24\3\2\2\2\u0084\u0087\5\3\2\2\u0085\u0087\t\5\2\2\u0086"+
		"\u0084\3\2\2\2\u0086\u0085\3\2\2\2\u0087\26\3\2\2\2\u0088\u008b\5\13\6"+
		"\2\u0089\u008b\5\25\13\2\u008a\u0088\3\2\2\2\u008a\u0089\3\2\2\2\u008b"+
		"\30\3\2\2\2\u008c\u008f\7a\2\2\u008d\u008f\5\r\7\2\u008e\u008c\3\2\2\2"+
		"\u008e\u008d\3\2\2\2\u008f\32\3\2\2\2\u0090\u0094\5\31\r\2\u0091\u0093"+
		"\5\27\f\2\u0092\u0091\3\2\2\2\u0093\u0096\3\2\2\2\u0094\u0095\3\2\2\2"+
		"\u0094\u0092\3\2\2\2\u0095\34\3\2\2\2\u0096\u0094\3\2\2\2\u0097\u009a"+
		"\5\17\b\2\u0098\u009a\5\23\n\2\u0099\u0097\3\2\2\2\u0099\u0098\3\2\2\2"+
		"\u009a\u009b\3\2\2\2\u009b\u009c\5\33\16\2\u009c\u009d\5\21\t\2\u009d"+
		"\u009e\b\17\2\2\u009e\36\3\2\2\2\u009f\u00a2\5\13\6\2\u00a0\u00a2\5!\21"+
		"\2\u00a1\u009f\3\2\2\2\u00a1\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a1"+
		"\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00b0\3\2\2\2\u00a5\u00a7\5#\22\2\u00a6"+
		"\u00a5\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00aa\3\2\2\2\u00a8\u00ab\5\13"+
		"\6\2\u00a9\u00ab\5!\21\2\u00aa\u00a8\3\2\2\2\u00aa\u00a9\3\2\2\2\u00ab"+
		"\u00ac\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00af\3\2"+
		"\2\2\u00ae\u00a6\3\2\2\2\u00af\u00b2\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b0"+
		"\u00b1\3\2\2\2\u00b1 \3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3\u00b4\t\6\2\2"+
		"\u00b4\"\3\2\2\2\u00b5\u00b6\7\60\2\2\u00b6$\3\2\2\2\u00b7\u00b9\5\'\24"+
		"\2\u00b8\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00bb"+
		"\3\2\2\2\u00bb&\3\2\2\2\u00bc\u00c0\5\13\6\2\u00bd\u00c0\5\3\2\2\u00be"+
		"\u00c0\4/\60\2\u00bf\u00bc\3\2\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00be\3\2"+
		"\2\2\u00c0(\3\2\2\2\u00c1\u00c2\5\37\20\2\u00c2\u00c3\7B\2\2\u00c3\u00c4"+
		"\5%\23\2\u00c4*\3\2\2\2\u00c5\u00c6\5)\25\2\u00c6\u00c7\b\26\3\2\u00c7"+
		",\3\2\2\2\u00c8\u00c9\7\62\2\2\u00c9\u00ca\t\7\2\2\u00ca.\3\2\2\2\u00cb"+
		"\u00cc\t\b\2\2\u00cc\u00cd\t\2\2\2\u00cd\60\3\2\2\2\u00ce\u00cf\7\65\2"+
		"\2\u00cf\u00d0\t\t\2\2\u00d0\62\3\2\2\2\u00d1\u00d5\5-\27\2\u00d2\u00d5"+
		"\5/\30\2\u00d3\u00d5\5\61\31\2\u00d4\u00d1\3\2\2\2\u00d4\u00d2\3\2\2\2"+
		"\u00d4\u00d3\3\2\2\2\u00d5\64\3\2\2\2\u00d6\u00d7\7\62\2\2\u00d7\u00d8"+
		"\t\7\2\2\u00d8\66\3\2\2\2\u00d9\u00da\7\63\2\2\u00da\u00db\t\n\2\2\u00db"+
		"8\3\2\2\2\u00dc\u00df\5\65\33\2\u00dd\u00df\5\67\34\2\u00de\u00dc\3\2"+
		"\2\2\u00de\u00dd\3\2\2\2\u00df:\3\2\2\2\u00e0\u00e1\7\64\2\2\u00e1\u00e2"+
		"\7\62\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4\t\2\2\2\u00e4\u00e5\t\2\2\2"+
		"\u00e5<\3\2\2\2\u00e6\u00e7\7\64\2\2\u00e7\u00e8\7\63\2\2\u00e8\u00e9"+
		"\7\62\2\2\u00e9\u00ea\7\62\2\2\u00ea>\3\2\2\2\u00eb\u00ee\5;\36\2\u00ec"+
		"\u00ee\5=\37\2\u00ed\u00eb\3\2\2\2\u00ed\u00ec\3\2\2\2\u00ee@\3\2\2\2"+
		"\u00ef\u00f0\5\63\32\2\u00f0\u00f1\7\61\2\2\u00f1\u00f2\59\35\2\u00f2"+
		"\u00f3\7\61\2\2\u00f3\u00f4\5? \2\u00f4B\3\2\2\2\u00f5\u00f6\5A!\2\u00f6"+
		"\u00f7\b\"\4\2\u00f7D\3\2\2\2\u00f8\u00f9\5\7\4\2\u00f9\u00fa\7/\2\2\u00fa"+
		"\u00fb\5\7\4\2\u00fb\u00fc\7/\2\2\u00fc\u00fd\5\t\5\2\u00fdF\3\2\2\2\u00fe"+
		"\u00ff\7*\2\2\u00ff\u0100\5\7\4\2\u0100\u0101\7+\2\2\u0101\u0102\7\"\2"+
		"\2\u0102\u0103\3\2\2\2\u0103\u0104\5\7\4\2\u0104\u0105\7/\2\2\u0105\u0106"+
		"\5\t\5\2\u0106H\3\2\2\2\u0107\u0108\5\7\4\2\u0108\u0109\7\"\2\2\u0109"+
		"\u010a\5\7\4\2\u010a\u010b\7\"\2\2\u010b\u010c\5\t\5\2\u010cJ\3\2\2\2"+
		"\u010d\u010e\5\7\4\2\u010e\u010f\7\60\2\2\u010f\u0110\5\7\4\2\u0110\u0111"+
		"\7\60\2\2\u0111\u0112\5\t\5\2\u0112L\3\2\2\2\u0113\u0118\5G$\2\u0114\u0118"+
		"\5E#\2\u0115\u0118\5K&\2\u0116\u0118\5I%\2\u0117\u0113\3\2\2\2\u0117\u0114"+
		"\3\2\2\2\u0117\u0115\3\2\2\2\u0117\u0116\3\2\2\2\u0118N\3\2\2\2\u0119"+
		"\u011a\5M\'\2\u011a\u011b\b(\5\2\u011bP\3\2\2\2\u011c\u011f\7\66\2\2\u011d"+
		"\u0120\5S*\2\u011e\u0120\5U+\2\u011f\u011d\3\2\2\2\u011f\u011e\3\2\2\2"+
		"\u0120R\3\2\2\2\u0121\u0122\5\7\4\2\u0122\u0123\7/\2\2\u0123\u0124\5\t"+
		"\5\2\u0124\u0125\7/\2\2\u0125\u0126\5\t\5\2\u0126\u0127\7/\2\2\u0127\u0128"+
		"\5\t\5\2\u0128T\3\2\2\2\u0129\u012a\5\5\3\2\u012a\u012b\7/\2\2\u012b\u012c"+
		"\5\7\4\2\u012c\u012d\7/\2\2\u012d\u012e\5\7\4\2\u012e\u012f\7/\2\2\u012f"+
		"\u0130\5\t\5\2\u0130V\3\2\2\2\u0131\u0132\t\13\2\2\u0132\u0133\5\5\3\2"+
		"\u0133\u0134\7/\2\2\u0134\u0135\5\t\5\2\u0135\u0136\7/\2\2\u0136\u0137"+
		"\5\t\5\2\u0137\u0138\7/\2\2\u0138\u0139\5\t\5\2\u0139X\3\2\2\2\u013a\u013b"+
		"\7\65\2\2\u013b\u013f\7\66\2\2\u013c\u013d\7\65\2\2\u013d\u013f\79\2\2"+
		"\u013e\u013a\3\2\2\2\u013e\u013c\3\2\2\2\u013f\u0140\3\2\2\2\u0140\u0141"+
		"\5\5\3\2\u0141\u0142\7/\2\2\u0142\u0143\5\t\5\2\u0143\u0144\7/\2\2\u0144"+
		"\u0145\5\t\5\2\u0145\u0146\7/\2\2\u0146\u0147\5\7\4\2\u0147Z\3\2\2\2\u0148"+
		"\u0151\t\f\2\2\u0149\u014a\7\65\2\2\u014a\u014e\78\2\2\u014b\u014c\7\65"+
		"\2\2\u014c\u014e\7:\2\2\u014d\u0149\3\2\2\2\u014d\u014b\3\2\2\2\u014e"+
		"\u014f\3\2\2\2\u014f\u0151\5\3\2\2\u0150\u0148\3\2\2\2\u0150\u014d\3\2"+
		"\2\2\u0151\u0152\3\2\2\2\u0152\u0153\7/\2\2\u0153\u0154\5\7\4\2\u0154"+
		"\u0155\7/\2\2\u0155\u0156\5\t\5\2\u0156\u0157\7/\2\2\u0157\u0158\5\t\5"+
		"\2\u0158\\\3\2\2\2\u0159\u015a\78\2\2\u015a\u015b\7\62\2\2\u015b\u015c"+
		"\7\63\2\2\u015c\u0162\7\63\2\2\u015d\u015e\78\2\2\u015e\u015f\7\67\2\2"+
		"\u015f\u0160\3\2\2\2\u0160\u0162\5\5\3\2\u0161\u0159\3\2\2\2\u0161\u015d"+
		"\3\2\2\2\u0162\u0163\3\2\2\2\u0163\u0164\7/\2\2\u0164\u0165\5\t\5\2\u0165"+
		"\u0166\7/\2\2\u0166\u0167\5\t\5\2\u0167\u0168\7/\2\2\u0168\u0169\5\t\5"+
		"\2\u0169^\3\2\2\2\u016a\u016b\7\64\2\2\u016b\u016c\7\63\2\2\u016c\u016d"+
		"\7\65\2\2\u016d\u0173\7\63\2\2\u016e\u016f\7\63\2\2\u016f\u0170\7:\2\2"+
		"\u0170\u0171\7\62\2\2\u0171\u0173\7\62\2\2\u0172\u016a\3\2\2\2\u0172\u016e"+
		"\3\2\2\2\u0173\u0174\3\2\2\2\u0174\u0175\7/\2\2\u0175\u0176\5\t\5\2\u0176"+
		"\u0177\7/\2\2\u0177\u0178\5\t\5\2\u0178\u0179\7/\2\2\u0179\u017a\5\7\4"+
		"\2\u017a`\3\2\2\2\u017b\u017c\7\65\2\2\u017c\u017d\7\67\2\2\u017d\u017e"+
		"\3\2\2\2\u017e\u017f\5\5\3\2\u017f\u0180\7/\2\2\u0180\u0181\5\t\5\2\u0181"+
		"\u0182\7/\2\2\u0182\u0183\5\t\5\2\u0183\u0184\7/\2\2\u0184\u0185\5\t\5"+
		"\2\u0185b\3\2\2\2\u0186\u0189\5_\60\2\u0187\u0189\5a\61\2\u0188\u0186"+
		"\3\2\2\2\u0188\u0187\3\2\2\2\u0189d\3\2\2\2\u018a\u0191\5Q)\2\u018b\u0191"+
		"\5W,\2\u018c\u0191\5Y-\2\u018d\u0191\5[.\2\u018e\u0191\5]/\2\u018f\u0191"+
		"\5c\62\2\u0190\u018a\3\2\2\2\u0190\u018b\3\2\2\2\u0190\u018c\3\2\2\2\u0190"+
		"\u018d\3\2\2\2\u0190\u018e\3\2\2\2\u0190\u018f\3\2\2\2\u0191f\3\2\2\2"+
		"\u0192\u0193\5e\63\2\u0193\u0194\b\64\6\2\u0194h\3\2\2\2\u0195\u0197\t"+
		"\r\2\2\u0196\u0195\3\2\2\2\u0197\u0198\3\2\2\2\u0198\u0196\3\2\2\2\u0198"+
		"\u0199\3\2\2\2\u0199\u019a\3\2\2\2\u019a\u019b\b\65\7\2\u019bj\3\2\2\2"+
		"\35\2\u0086\u008a\u008e\u0094\u0099\u00a1\u00a3\u00a6\u00aa\u00ac\u00b0"+
		"\u00ba\u00bf\u00d4\u00de\u00ed\u0117\u011f\u013e\u014d\u0150\u0161\u0172"+
		"\u0188\u0190\u0198\b\3\17\2\3\26\3\3\"\4\3(\5\3\64\6\3\65\7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}