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
                if(s.startsWith("$"))stack.add(getTrueValue(s));
                else if(s.equals("("))stack.add(s);
                else stack.add(s.substring(1, s.length()-1));
            }else{
                ArrayList<String> arr = new ArrayList<String>();
                while(!stack.peek().equals("("))arr.add(stack.pop());
                stack.pop();
                Collections.reverse(arr); 
                stack.add(append(((arr.size()>0)?arr.get(0):null),((arr.size()>1)?arr.get(1):null)));
            }
        }
        return stack.pop();
    }
    
    //Since the language we are compiling has availability of functions,
    //we need a function to get the value of variable. This is our truth function :)
    private static String getTrueValue(String s){
        return variableMap.get(s.substring(1));
    }
    
    //Our stack needs user friendly input to function easily
    //this function isolates '(' and ')' characters
    private static String toString(String s){
        return s.replaceAll("\\(", "\\( ").replaceAll("\\)", " \\)");
    }
    
    //our main function only reads example.txt(input file) line by line
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\HP\\Documents\\NetBeansProjects\\assignment2\\src\\assignment2\\example.txt"));
        String line = null; String val = "";
        while((line=br.readLine())!=null)val = starter(line);        
        System.out.println(val);
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
    
}
