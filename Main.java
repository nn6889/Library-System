public class Main{
   public static void main (String [] args){
      ResearchDatabase rs = new ResearchDatabase("jdbc:mysql://127.0.0.1:3307/finalproject", "root", "sip12e12");
     
      UInterface ui = new UInterface(rs);
       
   }
}