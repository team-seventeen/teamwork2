package mybean.data;
import java.util.*;

public class Login {
   String logname="",
          backNews="δ��¼";
   LinkedList<String> car; //�û��Ĺ��ﳵ
   public Login() {
      car = new LinkedList<String>();
   }
   public void setLogname(String logname){  
      this.logname = logname;
   }
   public String getLogname(){
      return logname;
   }
   public void setBackNews(String s) {
      backNews = s;
   } 
   public String getBackNews(){
      return backNews;
   }
   public LinkedList<String> getCar() { 
      return car;
   }
}
