package sample;
import java.io.FileNotFoundException;

public class Parser {
	private Scanner scanner;
	private SyntaxTree tree;
	/**
	 * Constructs a Parser to Parse the file located in inputFilePath
	 * @param inputFilePath the path of the file containing the program written in TINY language
	 */
	public Parser(String inputFilePath) {
		try {
			//create scanner object to scan the file located in inputfilePath
			this.scanner = new Scanner(inputFilePath);
			this.tree = new SyntaxTree();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found.");
		}
	}


	private int stmt_sequence() throws SyntaxErrorException{
		int temp = statement();
		int res = temp;
		Token token = scanner.getCurrentToken();
		while(token.getTokenType() == Token.TokenType.SEMICOLON){
			match(Token.TokenType.SEMICOLON);
			int newTemp = statement();
			tree.addChild(temp, newTemp);
			tree.sameRank(temp, newTemp);
			temp = newTemp;
			token = scanner.getCurrentToken();
		}
		return res;
	}
	
	private int statement() throws SyntaxErrorException{
		Token stmt = scanner.getCurrentToken();
		switch (stmt.getTokenType()){
		case IF:
			return if_stmt();
		case REPEAT:
			return repeat_stmt();
		case IDENTIFIER:
			return assign_stmt();
		case READ:
			return read_stmt();
		case WRITE:
			return write_stmt();
		default:
			throw(new SyntaxErrorException("Syntax error near token \"" + scanner.getCurrentToken().toString()+ "\""));
		}
	}
	
	private int if_stmt() throws SyntaxErrorException {
		match(Token.TokenType.IF);
		int ifNode = tree.makeIFNode();
		tree.addChild(ifNode, exp());
		match(Token.TokenType.THEN);
		tree.addChild(ifNode, stmt_sequence());
		Token token = scanner.getCurrentToken();
		if (token.getTokenType() == Token.TokenType.ELSE){//
			match(Token.TokenType.ELSE);
			tree.addChild(ifNode, stmt_sequence());
		}
		match(Token.TokenType.END);
		return ifNode;
	}

	private int repeat_stmt() throws SyntaxErrorException {
		match(Token.TokenType.REPEAT);
		int repeatNode = tree.makeRepeatNode();
		int bodyNode = stmt_sequence();
		match(Token.TokenType.UNTIL);
		int condNode = exp();
		tree.addChild(repeatNode, bodyNode);
		tree.addChild(repeatNode, condNode );



		return  repeatNode;
	}

	private int assign_stmt() throws SyntaxErrorException{
		Token identifier = scanner.getCurrentToken();
		match(Token.TokenType.IDENTIFIER);
		match(Token.TokenType.ASSIGN);
		int assignNode = tree.makeAssignNode(identifier.getTokenvalue());
		tree.addChild(assignNode, exp());
		return assignNode;
	}
	
	private int read_stmt() throws SyntaxErrorException{
		match(Token.TokenType.READ);
		Token identifier = scanner.getCurrentToken();
		match(Token.TokenType.IDENTIFIER);
		return tree.makeReadNode(identifier.getTokenvalue());
	}
	
	private int write_stmt() throws SyntaxErrorException{
		match(Token.TokenType.WRITE);
		int writeNode = tree.makeWriteNode();
		tree.addChild(writeNode, exp());
		return writeNode;
	}
	
	private int exp() throws SyntaxErrorException{
		int temp = simple_exp();
		Token currentToken = scanner.getCurrentToken();
		while(currentToken.getTokenType() == Token.TokenType.LESSTHAN ||
				currentToken.getTokenType() == Token.TokenType.EQUAL ||
				currentToken.getTokenType() == Token.TokenType.GREATERTHAN){
			int opNode = comparisonOp();
			tree.addChild(opNode, temp);
			tree.addChild(opNode, simple_exp());
			temp = opNode;
			currentToken = scanner.getCurrentToken();
		}
		return temp;
	}
	
	private int comparisonOp() throws SyntaxErrorException{
		Token token = scanner.getCurrentToken();
		switch(token.getTokenType()){
		case LESSTHAN:
			match(Token.TokenType.LESSTHAN);
			return tree.makeOPNode("<");
		case EQUAL:
			match(Token.TokenType.EQUAL);
			return tree.makeOPNode("=");
		case GREATERTHAN:
			match(Token.TokenType.GREATERTHAN);
			return tree.makeOPNode(">");
		default:
			throw(new SyntaxErrorException("Syntax error near token \"" + scanner.getCurrentToken().toString()+ "\""));
		}
	} 
	
	private int simple_exp() throws SyntaxErrorException{
		int temp = term();
		Token currentToken = scanner.getCurrentToken();
		while(currentToken.getTokenType() == Token.TokenType.PLUS ||
				currentToken.getTokenType() == Token.TokenType.MINUS){
			int opNode = addOp();
			tree.addChild(opNode, temp);
			tree.addChild(opNode, term());
			temp = opNode;
			currentToken = scanner.getCurrentToken();
		}
		return temp;
	}
	
	private int addOp() throws SyntaxErrorException{
		Token token = scanner.getCurrentToken();
		switch(token.getTokenType()){
		case PLUS:
			match(Token.TokenType.PLUS);
			return tree.makeOPNode("+");
		case MINUS:
			match(Token.TokenType.MINUS);
			return tree.makeOPNode("-");
		default:
			throw(new SyntaxErrorException("Syntax error near token \"" + scanner.getCurrentToken().toString()+ "\""));
		}
	} 

	
	private int term() throws SyntaxErrorException{
		int temp = factor();
		Token currentToken = scanner.getCurrentToken();
		while(currentToken.getTokenType() == Token.TokenType.DIVISION ||
				currentToken.getTokenType() == Token.TokenType.TIMES){
			int opNode = mulOp();
			tree.addChild(opNode, temp);
			tree.addChild(opNode, factor());
			temp = opNode;
			currentToken = scanner.getCurrentToken();
		}
		return temp;
	}
	
	private int mulOp() throws SyntaxErrorException{
		Token token = scanner.getCurrentToken();
		 
		switch(token.getTokenType()){
		case DIVISION:
			match(Token.TokenType.DIVISION);
			return tree.makeOPNode("/");
		case TIMES:
			match(Token.TokenType.TIMES);
			return tree.makeOPNode("*");
		default:
			throw(new SyntaxErrorException("Syntax error near token \"" + scanner.getCurrentToken().toString()+ "\""));
		}
	}
	
	private int factor() throws SyntaxErrorException{
		Token currentToken = scanner.getCurrentToken();
		int temp;
		switch(currentToken.getTokenType()){
		case OPENBRACKET:
			match(Token.TokenType.OPENBRACKET);
			temp = exp();
			match(Token.TokenType.CLOSEDBRACKET);
			break;
		case NUMBER:
			temp = tree.makeConstNode(currentToken.getTokenvalue());
			match(Token.TokenType.NUMBER);
			break;
		case IDENTIFIER:
			temp = tree.makeIDNode(currentToken.getTokenvalue());
			match(Token.TokenType.IDENTIFIER);
			break;
		default:
			throw(new SyntaxErrorException("Syntax error near token \"" + scanner.getCurrentToken().toString()+ "\""));
		}
		return temp;
	}
	
	
	private void match(Token.TokenType type) throws SyntaxErrorException{
		Token token = scanner.getCurrentToken();
		if(token.getTokenType() == type)
			scanner.advanceInput();
		else
			throw(new SyntaxErrorException("Syntax error. Expected " + type + " instead got token \"" + scanner.getCurrentToken().toString()+ "\""));
	}
        
        /**
         * Start parsing the file until the end of file
         * @param type
         * @throws SyntaxErrorException 
         */
	public void start(String type) throws SyntaxErrorException
	{
            stmt_sequence();
            tree.end();
	}
        
        public SyntaxTree getTree(){
            return tree;
        }
}
