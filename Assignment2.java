package assignment2;

public class Assignment2 {
    
    public static String append(String a, String b){
        if(a==null)return b;
        if(b==null)return a;
        if(a.charAt(a.length()-1)==b.charAt(0))return a+b.substring(1);
        if(a.charAt(a.length()-1)<b.charAt(0))return a+b;
        else return b+a;
    }
    
    public static void main(String[] args) {
        String a = "apple";
        String b = "bean"; 
        System.out.println(append(a,b));
        
    }    
}
