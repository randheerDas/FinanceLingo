// Generated from actions/tools/FinancialLingoActions.g4 by ANTLR 4.4

package actions.tools;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

import actions.tools.exception.SymbolNotFoundException;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FinancialLingoActionsParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__10=1, T__9=2, T__8=3, T__7=4, T__6=5, T__5=6, T__4=7, T__3=8, T__2=9, 
		T__1=10, T__0=11, FloatingPointLiteral=12, ID=13, INT=14, NEWLINE=15, 
		WS=16, COMMENT=17;
	public static final String[] tokenNames = {
		"<INVALID>", "'double'", "')'", "','", "'+'", "'*'", "'-'", "'('", "'int'", 
		"'/'", "'='", "'float'", "FloatingPointLiteral", "ID", "INT", "NEWLINE", 
		"WS", "COMMENT"
	};
	public static final int
		RULE_program = 0, RULE_stat = 1, RULE_assignment = 2, RULE_type = 3, RULE_expr = 4, 
		RULE_literal = 5, RULE_funcCall = 6, RULE_exprList = 7;
	public static final String[] ruleNames = {
		"program", "stat", "assignment", "type", "expr", "literal", "funcCall", 
		"exprList"
	};

	@Override
	public String getGrammarFileName() { return "FinancialLingoActions.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }



	 	FinanceInterpreter finInterp;
	    public FinancialLingoActionsParser(TokenStream input, FinanceInterpreter finInterp) {
	        this(input);
	        this.finInterp = finInterp;
	    }

	public FinancialLingoActionsParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(FinancialLingoActionsParser.NEWLINE, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16); stat();
			setState(17); match(NEWLINE);
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

	public static class StatContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(FinancialLingoActionsParser.NEWLINE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_stat);
		try {
			setState(23);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(19); expr(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(20); assignment();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(21); funcCall();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(22); match(NEWLINE);
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

	public static class AssignmentContext extends ParserRuleContext {
		public Token ID;
		public ExprContext expr;
		public FuncCallContext funcCall;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode ID() { return getToken(FinancialLingoActionsParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_assignment);
		int _la;
		try {
			setState(43);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(25); ((AssignmentContext)_localctx).ID = match(ID);
				setState(26); match(T__1);
				setState(33);
				switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
				case 1:
					{
					setState(27); ((AssignmentContext)_localctx).expr = expr(0);
					finInterp.store((((AssignmentContext)_localctx).ID!=null?((AssignmentContext)_localctx).ID.getText():null), ((AssignmentContext)_localctx).expr.v);
					}
					break;
				case 2:
					{
					setState(30); ((AssignmentContext)_localctx).funcCall = funcCall();
					finInterp.store((((AssignmentContext)_localctx).ID!=null?((AssignmentContext)_localctx).ID.getText():null), ((AssignmentContext)_localctx).funcCall.v);
					}
					break;
				}
				}
				break;
			case T__10:
			case T__3:
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(35); type();
				setState(36); ((AssignmentContext)_localctx).ID = match(ID);
				setState(39);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(37); match(T__1);
					setState(38); ((AssignmentContext)_localctx).expr = expr(0);
					}
				}

				finInterp.store((((AssignmentContext)_localctx).ID!=null?((AssignmentContext)_localctx).ID.getText():null), ((AssignmentContext)_localctx).expr.v);
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

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__10) | (1L << T__3) | (1L << T__0))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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
		public Object v;
		public ExprContext a;
		public FuncCallContext funcCall;
		public LiteralContext literal;
		public Token ID;
		public ExprContext expr;
		public Token op;
		public ExprContext b;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode ID() { return getToken(FinancialLingoActionsParser.ID, 0); }
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(48); ((ExprContext)_localctx).funcCall = funcCall();
				((ExprContext)_localctx).v =  ((ExprContext)_localctx).funcCall.v;
				}
				break;
			case 2:
				{
				setState(51); ((ExprContext)_localctx).literal = literal();
				((ExprContext)_localctx).v =  ((ExprContext)_localctx).literal.v;
				}
				break;
			case 3:
				{
				setState(54); ((ExprContext)_localctx).ID = match(ID);
				  // variable reference
					      	String id = (((ExprContext)_localctx).ID!=null?((ExprContext)_localctx).ID.getText():null);
					      	((ExprContext)_localctx).v =  finInterp.containsObject(id) ? finInterp.getVarValue(id) : id;
					    
				}
				break;
			case 4:
				{
				setState(56); match(T__4);
				setState(57); ((ExprContext)_localctx).expr = expr(0);
				setState(58); match(T__9);
				((ExprContext)_localctx).v =  _localctx.v;
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(75);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(73);
					switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						_localctx.a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(63);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(64);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__6 || _la==T__2) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(65); ((ExprContext)_localctx).b = ((ExprContext)_localctx).expr = expr(7);
						((ExprContext)_localctx).v =  finInterp.eval((Double)((ExprContext)_localctx).a.v, (((ExprContext)_localctx).op!=null?((ExprContext)_localctx).op.getType():0), (Double)((ExprContext)_localctx).b.v);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						_localctx.a = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(68);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(69);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__7 || _la==T__5) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(70); ((ExprContext)_localctx).b = ((ExprContext)_localctx).expr = expr(6);
						((ExprContext)_localctx).v =  finInterp.eval((Double)((ExprContext)_localctx).a.v, (((ExprContext)_localctx).op!=null?((ExprContext)_localctx).op.getType():0), (Double)((ExprContext)_localctx).b.v);
						}
						break;
					}
					} 
				}
				setState(77);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} catch (SymbolNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public Object v;
		public Token FloatingPointLiteral;
		public Token INT;
		public TerminalNode INT() { return getToken(FinancialLingoActionsParser.INT, 0); }
		public TerminalNode FloatingPointLiteral() { return getToken(FinancialLingoActionsParser.FloatingPointLiteral, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_literal);
		try {
			setState(82);
			switch (_input.LA(1)) {
			case FloatingPointLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(78); ((LiteralContext)_localctx).FloatingPointLiteral = match(FloatingPointLiteral);
				((LiteralContext)_localctx).v =  Double.parseDouble((((LiteralContext)_localctx).FloatingPointLiteral!=null?((LiteralContext)_localctx).FloatingPointLiteral.getText():null));
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(80); ((LiteralContext)_localctx).INT = match(INT);
				((LiteralContext)_localctx).v =  new Integer((((LiteralContext)_localctx).INT!=null?Integer.valueOf(((LiteralContext)_localctx).INT.getText()):0)).doubleValue();
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

	public static class FuncCallContext extends ParserRuleContext {
		public Object v;
		public Token ID;
		public ExprListContext exprList;
		public ExprListContext exprList() {
			return getRuleContext(ExprListContext.class,0);
		}
		public TerminalNode ID() { return getToken(FinancialLingoActionsParser.ID, 0); }
		public FuncCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcCall; }
	}

	public final FuncCallContext funcCall() throws RecognitionException {
		FuncCallContext _localctx = new FuncCallContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_funcCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84); ((FuncCallContext)_localctx).ID = match(ID);
			setState(85); match(T__4);
			setState(87);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << FloatingPointLiteral) | (1L << ID) | (1L << INT))) != 0)) {
				{
				setState(86); ((FuncCallContext)_localctx).exprList = exprList();
				}
			}

			setState(89); match(T__9);
			((FuncCallContext)_localctx).v =  finInterp.evaluateFunctionCall((((FuncCallContext)_localctx).ID!=null?((FuncCallContext)_localctx).ID.getText():null),(((FuncCallContext)_localctx).exprList!=null?_input.getText(((FuncCallContext)_localctx).exprList.start,((FuncCallContext)_localctx).exprList.stop):null));
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

	public static class ExprListContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ExprListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprList; }
	}

	public final ExprListContext exprList() throws RecognitionException {
		ExprListContext _localctx = new ExprListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_exprList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92); expr(0);
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8) {
				{
				{
				setState(93); match(T__8);
				setState(94); expr(0);
				}
				}
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 6);
		case 1: return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\23g\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\3\5\3\32\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4$\n\4\3\4\3\4\3"+
		"\4\3\4\5\4*\n\4\3\4\3\4\5\4.\n\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6@\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\7\6L\n\6\f\6\16\6O\13\6\3\7\3\7\3\7\3\7\5\7U\n\7\3\b\3\b\3\b\5"+
		"\bZ\n\b\3\b\3\b\3\b\3\t\3\t\3\t\7\tb\n\t\f\t\16\te\13\t\3\t\2\3\n\n\2"+
		"\4\6\b\n\f\16\20\2\5\5\2\3\3\n\n\r\r\4\2\7\7\13\13\4\2\6\6\b\bl\2\22\3"+
		"\2\2\2\4\31\3\2\2\2\6-\3\2\2\2\b/\3\2\2\2\n?\3\2\2\2\fT\3\2\2\2\16V\3"+
		"\2\2\2\20^\3\2\2\2\22\23\5\4\3\2\23\24\7\21\2\2\24\3\3\2\2\2\25\32\5\n"+
		"\6\2\26\32\5\6\4\2\27\32\5\16\b\2\30\32\7\21\2\2\31\25\3\2\2\2\31\26\3"+
		"\2\2\2\31\27\3\2\2\2\31\30\3\2\2\2\32\5\3\2\2\2\33\34\7\17\2\2\34#\7\f"+
		"\2\2\35\36\5\n\6\2\36\37\b\4\1\2\37$\3\2\2\2 !\5\16\b\2!\"\b\4\1\2\"$"+
		"\3\2\2\2#\35\3\2\2\2# \3\2\2\2$.\3\2\2\2%&\5\b\5\2&)\7\17\2\2\'(\7\f\2"+
		"\2(*\5\n\6\2)\'\3\2\2\2)*\3\2\2\2*+\3\2\2\2+,\b\4\1\2,.\3\2\2\2-\33\3"+
		"\2\2\2-%\3\2\2\2.\7\3\2\2\2/\60\t\2\2\2\60\t\3\2\2\2\61\62\b\6\1\2\62"+
		"\63\5\16\b\2\63\64\b\6\1\2\64@\3\2\2\2\65\66\5\f\7\2\66\67\b\6\1\2\67"+
		"@\3\2\2\289\7\17\2\29@\b\6\1\2:;\7\t\2\2;<\5\n\6\2<=\7\4\2\2=>\b\6\1\2"+
		">@\3\2\2\2?\61\3\2\2\2?\65\3\2\2\2?8\3\2\2\2?:\3\2\2\2@M\3\2\2\2AB\f\b"+
		"\2\2BC\t\3\2\2CD\5\n\6\tDE\b\6\1\2EL\3\2\2\2FG\f\7\2\2GH\t\4\2\2HI\5\n"+
		"\6\bIJ\b\6\1\2JL\3\2\2\2KA\3\2\2\2KF\3\2\2\2LO\3\2\2\2MK\3\2\2\2MN\3\2"+
		"\2\2N\13\3\2\2\2OM\3\2\2\2PQ\7\16\2\2QU\b\7\1\2RS\7\20\2\2SU\b\7\1\2T"+
		"P\3\2\2\2TR\3\2\2\2U\r\3\2\2\2VW\7\17\2\2WY\7\t\2\2XZ\5\20\t\2YX\3\2\2"+
		"\2YZ\3\2\2\2Z[\3\2\2\2[\\\7\4\2\2\\]\b\b\1\2]\17\3\2\2\2^c\5\n\6\2_`\7"+
		"\5\2\2`b\5\n\6\2a_\3\2\2\2be\3\2\2\2ca\3\2\2\2cd\3\2\2\2d\21\3\2\2\2e"+
		"c\3\2\2\2\f\31#)-?KMTYc";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}