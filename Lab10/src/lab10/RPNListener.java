// Generated from RPN.g4 by ANTLR 4.5.2

  import java.util.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RPNParser}.
 */
public interface RPNListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RPNParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(RPNParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link RPNParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(RPNParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link RPNParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(RPNParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link RPNParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(RPNParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link RPNParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(RPNParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RPNParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(RPNParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link RPNParser#op}.
	 * @param ctx the parse tree
	 */
	void enterOp(RPNParser.OpContext ctx);
	/**
	 * Exit a parse tree produced by {@link RPNParser#op}.
	 * @param ctx the parse tree
	 */
	void exitOp(RPNParser.OpContext ctx);
	/**
	 * Enter a parse tree produced by {@link RPNParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic(RPNParser.ArithmeticContext ctx);
	/**
	 * Exit a parse tree produced by {@link RPNParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic(RPNParser.ArithmeticContext ctx);
	/**
	 * Enter a parse tree produced by {@link RPNParser#boolOp}.
	 * @param ctx the parse tree
	 */
	void enterBoolOp(RPNParser.BoolOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link RPNParser#boolOp}.
	 * @param ctx the parse tree
	 */
	void exitBoolOp(RPNParser.BoolOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link RPNParser#relOp}.
	 * @param ctx the parse tree
	 */
	void enterRelOp(RPNParser.RelOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link RPNParser#relOp}.
	 * @param ctx the parse tree
	 */
	void exitRelOp(RPNParser.RelOpContext ctx);
}