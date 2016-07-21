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
    private static HashMap<String, String> variableMap = new HashMap<String, String>();

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
    
    private static String getTrueValue(String s){
        return variableMap.get(s.substring(1));
    }
    
    private static String toString(String s){
        return s.replaceAll("\\(", "\\( ").replaceAll("\\)", " \\)");
    }
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\HP\\Documents\\NetBeansProjects\\assignment2\\src\\assignment2\\example.txt"));
        String line = null; String val = "";
        while((line=br.readLine())!=null)val = starter(line);        
        System.out.println(val);
    }    
        
    private static String append(String a, String b){
        if(a==null)return b;
        if(b==null)return a;
        if(a.charAt(a.length()-1)==b.charAt(0))return a+b.substring(1);
        if(a.charAt(a.length()-1)<b.charAt(0))return a+b;
        else return b+a;
    }
    
}
