/*
Author: Liam O'Shea
Date: June 06 2019
File: MyScanner.java
Description: The following program implements a scanner which reads from standard input and outputs a token
stream to standard output.

 */

import java.util.ArrayList;
import java.util.Scanner;

public class MyScanner {

    private static Scanner in = new Scanner(System.in); //Scanner used only to read individual lines from standard input.
                                                //Implementation of token stream done by scanner written in this file.
    private static String line, token = "";             //line holds a line of input from standard in.
                                                //token is a string which is used to construct tokens with state machine.
    private static char chars[];                        //line will be converted to character array for easier manipulation

    //List of all operators and delimiters
    private static char opDelim[] = {'!','%','&','|','*','-','/','+','=','#','<','>','@','(',')','[',']','{','}',','};

    //Keep track of line, index (col). and state.
    private static int lineNum = 1, state = 0, i;

    public static ArrayList<Token> getTokens(){


        /*
        Description of states in state machine.

        State   Description             Final
        0       Initial                 No
        1       Operator/Delim          Yes
        2       StringLiteralInQuotes   No      //Concludes after final "
        3       IntegerLiteral          Yes
        4       Symbol/Keyword          Yes
         */



        int stringInitial = 0;  //Used to keep track of initial column of tokens greater than 1 character.
        newCharArray();         //Updates array to represent next line;

        ArrayList<Token> tokens = new ArrayList<Token>();

        //Main program loop
        while(true){

            //Initial State
            if(state == 0){

                //Check if we have ended the line, print token if necessary
                if(i >= chars.length){

                    if(token.length() > 0){
                        //System.out.println("line " + lineNum + " col " + (i) + " : " + chars[i-1]);

                        //Create token and add to ArrayList. Adding char to "" creates String.
                        tokens.add(new Token(lineNum, i, "" + chars[i-1]));

                        token = "";
                    }

                    //Get next line if available
                    if(in.hasNext()) {
                        newCharArray();
                        lineNum++;
                    }
                }

                //Check if we have an empty line, if so skip it
                //The character ~ is used a symbol to represent an empty line in this implementation.
                if(chars[i] == '~'){
                    lineNum++;
                    newCharArray();
                }

                //Deal with leading spaces
                if(chars[i] == ' '){
                    i++;
                }

                //Operators and Delimiters
                else if(containsOpDe(chars[i])){
                    //System.out.println("line " + lineNum + " col " + (i+1) + " : " + chars[i]);
                    tokens.add(new Token(lineNum, i+1, "" + chars[i]));

                    i++;
                }

                //String Literals
                else if(chars[i] == '"'){
                    token += '"';
                    stringInitial = i;
                    i++;
                    state = 2;
                }

                //Integer Literals
                //ASCII: 0->9 == 48->57
                else if(chars[i] >= 48 && chars[i] <= 57){
                    token += chars[i];
                    stringInitial = i;
                    i++;
                    state = 3;
                }

                //Symbols/Keywords -- Conditions A-Z, a-z, _ respectively
                else if((chars[i] >= 65 && chars[i] <= 90) || (chars[i] >= 97 && chars[i] <= 122) || chars[i] == 95){
                    token += chars[i];
                    stringInitial = i;
                    i++;
                    state = 4;
                }
            }

            //State 2 - String Literal (Inside Quotations)
            if (state == 2){

                //If inside quotations, add any character to token.
                if(chars[i] != '"'){
                    token += chars[i];
                    i++;
                }

                //Detect end quotations, print token.
                else if(chars[i] == '"'){
                    token += chars[i];
                    i++;

                    //Output token information
                    //System.out.println("line " + lineNum + " col " + (stringInitial + 1) + " : " + token);
                    tokens.add(new Token(lineNum, stringInitial + 1, token));
                    token = "";

                    state = 0;

                }
            }

            //State 3 - Integer Literal
            if (state == 3){

                //Check if we have ended the line, print token if necessary
                if(i >= chars.length){
                    if(token.length() > 0){
                        //System.out.println("line " + lineNum + " col " + (stringInitial + 1) + " : " + token);
                        tokens.add(new Token(lineNum, stringInitial + 1, token));

                        token = "";
                    }

                    //Retrieve next line if available.
                    if(in.hasNext()) {
                        newCharArray();
                        lineNum++;
                        state = 0;
                    }
                }

                //Add to token if character is a numeral
                else if (chars[i] >= 48 && chars[i] <= 57){
                    token += chars[i];
                    i++;
                }

                //Terminate if not a number
                else{

                    //Output token information
                    //System.out.println("line " + lineNum + " col " + (stringInitial + 1) + " : " + token);
                    tokens.add(new Token(lineNum, stringInitial + 1, token));

                    token = "";
                    state = 0;

                }

            }

            //State 4 - Symbol/Keywords
            if (state == 4){

                //Check if we have ended the line, print token if necessary
                if(i >= chars.length){
                    if(token.length() > 0){
                        //System.out.println("line " + lineNum + " col " + (stringInitial + 1) + " : " + token);
                        tokens.add(new Token(lineNum, stringInitial + 1, token));

                        token = "";
                    }

                    //Get next line if available
                    if(in.hasNext()) {
                        newCharArray();
                        lineNum++;
                        state = 0;
                    }
                }

                //Symbols/Keywords -- Conditions A-Z, a-z, 0-9, _ respectively
                else if((chars[i] >= 65 && chars[i] <= 90) || (chars[i] >= 97 && chars[i] <= 122) ||
                        (chars[i] >= 48 && chars[i] <= 57) || chars[i] == 95){

                    token += chars[i];
                    i++;
                }

                else{
                    //Output token information
                    //System.out.println("line " + lineNum + " col " + (stringInitial + 1) + " : " + token);
                    tokens.add(new Token(lineNum, stringInitial + 1, token));
                    token = "";
                    state = 0;
                }


            }

            //Exit condition
            if(!in.hasNext() && token.length() == 0 && i >= line.length()) break;
        }
        return tokens;
    }



    //containsOpDe(char) returns true if input is contained in list of operators and delimiters.
    public static boolean containsOpDe(char input){

        for(int j = 0; j < opDelim.length; j++){
            if (input == opDelim[j]) return true;
        }
        return false;
    }

    //newCharArray() retrieves the next line from standard in and returns a character array of that line.
    public static char[] newCharArray(){
        line = in.nextLine();

        //Check to see if line is empty. If so set char to ~ symbol to indicate it is a line to be skipped.
        if(line.length() > 0) {
            chars = line.toCharArray();
        } else{
            chars = "~".toCharArray();
        }
        i = 0;
        return chars;
    }
}