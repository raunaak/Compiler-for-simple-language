package assignment2;

import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Assignment2 {
    //Since the language we are compiling has availability of functions,
    //we need a map to store the value of variable. This is our variable map.
    private static HashMap<String, String> variableMap = new HashMap<String, String>();

    //Finally, we introduce functionMap to store the implementation of functions.
    private static HashMap<String, String> functionMap = new HashMap<String, String>();
        
    //this function is called for every line in example.txt (input file)
    //it converts text in line to user friendly format and returns the output for every line
    public static String starter(String s){
        StringTokenizer st = new StringTokenizer(s);
        String b = ""; int index = 0;
        if(st.nextToken().equals("SET")){
            String a = st.nextToken();
            index = s.indexOf('=');
            b = mainCourse(s.substring(index+2));
            variableMap.put(a, b);
        }else{
            //for functions
            //analogous for variables
            String a = st.nextToken();
            index = s.indexOf('=');
            b = s.substring(index+2);
            b = toString(b);
            functionMap.put(a, b);
        }
        return b;
    }
    
    //As the name suggests, mainCourse, it is the crux of this assignment
    //It evaluates the expression by using stacks
    //It works similar to postfix evaluation of stacks
    //When ')' is encountered, elements are popped until '(' character is not reached
    //these elements are given to "append" function to evaluate final expression
    private static String mainCourse(String exp){
        Stack<String> stack = new Stack<String>();
        exp = toString(exp);
        StringTokenizer st = new StringTokenizer(exp);
        while(st.hasMoreTokens()){
            String s = st.nextToken();
            if(!s.equals(")")){
                if(s.startsWith("$")&&!s.endsWith("("))stack.add(getVariableValue(s));
                else if(s.startsWith("$")&&s.endsWith("("))stack.add(s);
                else if(s.equals("("))stack.add(s);
                else stack.add(s.substring(1, s.length()-1));
            }else{
		//changes included for function calls
                ArrayList<String> arr = new ArrayList<String>();
                while(stack.peek().charAt(stack.peek().length()-1)!='(')arr.add(stack.pop());   
                Collections.reverse(arr); 
                stack.add(getFunctionValue(stack.pop(),arr));
            }
        }
        return stack.pop();
    }

    //the main function for handling function calls	    	    
    //it recursively calls itself when ')' is encountered
    //this function is similar to the standard function for evaluating postfix string 	
    private static String getFunctionValue(String s, ArrayList<String> arr){
        if(s.equals("("))return append(((arr.size()>0)?arr.get(0):null),((arr.size()>1)?arr.get(1):null));
        String str = getFunctionValue(s.substring(0, s.length()-1));
        if(str==null)return null;
        Stack<String> stack = new Stack<String>();
        StringTokenizer st = new StringTokenizer(str);
        while(st.hasMoreTokens()){
            String a = st.nextToken();
            if(!a.equals(")")){
                if(a.charAt(0)=='$'){
                    if(a.substring(1).matches("\\d+"))stack.add((arr.size()>Integer.parseInt(a.substring(1))-1)?arr.get(Integer.parseInt(a.substring(1))-1):null);
                    else if(a.charAt(a.length()-1)=='(')stack.add(a);
                    else stack.add(getVariableValue(a)); 
                }else if(a.equals("("))stack.add(a);
                else stack.add(a.substring(1, a.length()-1));
            }else{
                ArrayList<String> arr2 = new ArrayList<String>();
                while(stack.peek().charAt(stack.peek().length()-1)!='(')arr2.add(stack.pop());
                Collections.reverse(arr2); 
                stack.add(getFunctionValue(stack.pop(),arr2));
            }   
        }
        return stack.pop();
    }
    
    //Since the language we are compiling has availability of functions,
    //we need a function to get the value of variable. This is our truth function for variables:)
    private static String getVariableValue(String s){
        return variableMap.get(s.substring(1));
    }

    //returns the implementation of function via "functionmap" defined above 	    
    private static String getFunctionValue(String s){
        return functionMap.get(s.substring(1));
    }
    
    //Our stack needs user friendly input to function easily
    //this function isolates '(' and ')' characters
    private static String toString(String s){
        return s.replaceAll("\\(", "\\( ").replaceAll("\\)", " \\)");
    }
        
    //this is the basic append function defining the usage of language.
    //it performs the following function
    //If the last letter of the first string is the same as the first letter of the second string, the result is a concatenation, but without one of the copies of that common letter.
    //If the last letter of the first string occurs earlier in the alphabet than the first letter of the second string, the result is a simple concatenation.
    //If the last letter of the first string occurs later in the alphabet than the first letter of the second string, the result is a concatenation with the second string appearing before the first.
    //If either of the strings is null, the output is the other string.
    private static String append(String a, String b){
        if(a==null)return b;
        if(b==null)return a;
        if(a.charAt(a.length()-1)==b.charAt(0))return a+b.substring(1);
        if(a.charAt(a.length()-1)<b.charAt(0))return a+b;
        else return b+a;
    }
    
    //our main function only reads example.txt(input file) line by line
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\HP\\Documents\\NetBeansProjects\\assignment2\\src\\assignment2\\example.txt"));
        String line = null; String val = ""; int i=0;
        while((line=br.readLine())!=null)val = starter(line);
        System.out.println(val);
    }    
    
}
