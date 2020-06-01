public class Token {

    private int line, col;
    private String token, type;

    private String keywords[] = new String[]{"true", "false", "nil", "let", "letrec", "def", "set", "lambda", "if", "elseif",
            "else", "guard", "catch", "raise"};

    public Token(int l, int c, String token){
        line = l;
        col = c;
        this.token = token;
        type = getType();
    }

    public String getTokenVal(){
        return token;
    }

    private boolean isKeyword(){
        for(int i = 0; i < keywords.length; i++){
            if(keywords[i].equals(token)) return true;
        }
        return false;
    }

    public String getType(){

        //Three types to keep track of, symbol, integer, and string
        //Check against a regex to determine

        //Strings enclosed in double quotes    ".*"
        //Integers are only numbers [0-9][0-9]*
        //Symbols are [a-zA-Z_][0-9a-zA-Z_]* and NOT keywords

        //Everything else is "other"

        //Strings
        if(token.matches("\".*\"")){
            type = "string";
        }

        //Integers
        else if(token.matches("[0-9][0-9]*")){
            type = "integer";
        }

        //Symbols
        else if(token.matches("[a-zA-Z_][0-9a-zA-Z_]*") && !isKeyword()){
            type = "symbol";
        }

        else{
            type = "other";
        }



        return type;
    }



}
