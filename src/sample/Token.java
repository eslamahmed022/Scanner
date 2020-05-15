package sample;

public class Token {
	/**
	 * Token class represents a Token in a Scanner
	 * a token has a type which is IF,THEN,NUMBER,IDENTIFIER ... etc
	 * and has a value which represents the actual string
	 * Examples: (Value:Type)
	 *  (if:IF)
	 *  (459:NUMBER)
	 *  (ahmed:IDENTIFER)
	 */
	

	
	
	public enum TokenType {
		IF, THEN, ELSE, END, REPEAT, UNTIL, READ, WRITE,
		PLUS, MINUS, TIMES, DIVISION, EQUAL, LESSTHAN, GREATERTHAN,
		OPENBRACKET, CLOSEDBRACKET, SEMICOLON, ASSIGN,
		NUMBER, IDENTIFIER, END_OF_FILE,
		error,
		 RIGHT_SQAURE_BRACKET, LEFT_SQUARE_BRACKET,BEGIN,RETURN,MAIN,REAL,INT,STRING,EQUAL_EQUAL,NOT_EQUAL,Special_Symbols
	}


	private String Tokenvalue;
	private TokenType tokenType;
	
	public Token() {
		Tokenvalue = "";
		tokenType = null;
	}
	
	/**
	 * Construct a new Token 
	 * @param tokenType	Type of the token must be from TokenType enum
	 * @param Tokenvalue	The string value of the token
	 */
	public Token(TokenType tokenType, String Tokenvalue){
		this.tokenType = tokenType;
		this.Tokenvalue = Tokenvalue;
	}
	
	/**
	 * @return the token value
	 */
	public String getTokenvalue() {
		return Tokenvalue;
	}

	/**
	 * @param tokenvalue the token value to set
	 */
	public void setTokenvalue(String tokenvalue) {
		this.Tokenvalue = tokenvalue;
	}

	/**
	 * @return the type of the token
	 */
	public TokenType getTokenType() {
		return tokenType;
	}

	/**
	 * @param tokenType the type to set
	 */
	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * Returns a string representing the token 
	 */
	public String toString(){
		return (Tokenvalue.isEmpty()? "" : Tokenvalue + "   ")
				+ tokenType.name();
	}

	private   TokenType value;
	private   TokenType Type;
	private String input=new String();

	public void setInput(String input) {

		this.input = input;
	}
	public String getInput() {

		return input;
	}
	public TokenType getValue() {
		return value;
	}

	public void setValue(TokenType value) {
		this.value = value;
	}
	public void findType(String input){
		if(input.length()==0){
			return;
		}
		if(input.compareTo("if")==0||input.compareTo("IF")==0){
			value = Token.TokenType.IF;
		}else if(input.compareTo("then")==0||input.compareTo("THEN")==0){
			value = Token.TokenType.THEN;
		}else if(input.compareTo("else")==0||input.compareTo("ELSE")==0){
			value = Token.TokenType.ELSE;
		}else if(input.compareTo("end")==0||input.compareTo("END")==0){
			value = Token.TokenType.END;
		}else if(input.compareTo("repeat")==0||input.compareTo("REPEAT")==0){
			value = Token.TokenType.REPEAT;
		}else if(input.compareTo("until")==0||input.compareTo("UNTIL")==0){
			value = Token.TokenType.UNTIL;
		}else if(input.compareTo("read")==0||input.compareTo("READ")==0){
			value = Token.TokenType.READ;
		}else if(input.compareTo("begin")==0||input.compareTo("BEGIN")==0){
			value = Token.TokenType.BEGIN;
		}else if(input.compareTo("write")==0||input.compareTo("WRITE")==0){
			value = Token.TokenType.WRITE;
		}
		else if(input.compareTo("return")==0||input.compareTo("RETURN")==0){
			value = Token.TokenType.RETURN;
		}
		else if(input.compareTo("main")==0||input.compareTo("MAIN")==0){
			value = Token.TokenType.MAIN;
		}
		else if(input.compareTo("real")==0||input.compareTo("REAL")==0){
			value = Token.TokenType.REAL;
		}
		else if(input.compareTo("int")==0||input.compareTo("INT")==0){
			value = Token.TokenType.INT;
		}
		else if(input.compareTo("string")==0||input.compareTo("STRING")==0){
			value = Token.TokenType.STRING;
		}
		else if(input.compareTo("+")==0){
			value = Token.TokenType.PLUS;
		}else if(input.compareTo("-")==0){
			value = Token.TokenType.MINUS;
		}else if(input.compareTo("*")==0){
			value = Token.TokenType.TIMES;
		}else if(input.compareTo("/")==0){
			value = Token.TokenType.DIVISION;
		}else if(input.compareTo("=")==0){
			value = Token.TokenType.EQUAL;
		}else if(input.compareTo("<")==0){
			value = Token.TokenType.LESSTHAN;
		}else if(input.compareTo(">")==0){
			value = Token.TokenType.GREATERTHAN;
		}
		else if(input.compareTo("(")==0){
			value = Token.TokenType.OPENBRACKET;
		}else if(input.compareTo(")")==0){
			value = Token.TokenType.CLOSEDBRACKET;
		}else if(input.compareTo(";")==0){
			value = Token.TokenType.SEMICOLON;
		}else if(input.compareTo(":=")==0){
			value = Token.TokenType.ASSIGN;

		}else if(input.compareTo("==")==0){
			value = Token.TokenType.EQUAL_EQUAL;}
		else if(input.compareTo("!=")==0){
			value = Token.TokenType.NOT_EQUAL;}
		else if(input.compareTo("{")==0){
			value = Token.TokenType.Special_Symbols;}
		else if(input.compareTo("}")==0){
			value = Token.TokenType.Special_Symbols;}
		else if(input.compareTo(";")==0){
			value = Token.TokenType.SEMICOLON;}
		else if(input.compareTo("[")==0){
			value = Token.TokenType.LEFT_SQUARE_BRACKET;}
		else if(input.compareTo("]")==0){
			value = Token.TokenType.RIGHT_SQAURE_BRACKET;}
		else {
			if(!Character.isLetter(input.charAt(0))&&!Character.isDigit(input.charAt(0))){
				value = Token.TokenType.error;
				return;
			}
			if(Character.isDigit(input.charAt(0))){
				for(int i=0;i<input.length();i++){

					if(!Character.isDigit(input.charAt(i))){
						if(i==input.length()-1&&input.charAt(i)==';'){
							continue;
						}
						value = Token.TokenType.error;
						return;
					}

				}
				value = Token.TokenType.NUMBER;
				return;
			}
			for(int i=0;i<input.length();i++){

				if(!Character.isLetter(input.charAt(i))){
					if(i==input.length()-1&&input.charAt(i)==';'){
						continue;
					}
					value = Token.TokenType.error;
					return;
				}

			}

			value = Token.TokenType.IDENTIFIER;
			return;
		}


	}


}
	