package myservlet.control;
import mybean.data.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class HandleRegister extends HttpServlet {
   public void init(ServletConfig config) throws ServletException { 
      super.init(config);
      try {  Class.forName("com.mysql.jdbc.Driver");
      }
      catch(Exception e){} 
   }
   public String handleString(String s)
   {   try{ byte bb[]=s.getBytes("iso-8859-1");
            s=new String(bb);
       }
       catch(Exception ee){} 
       return s;  
   }
   public  void  doPost(HttpServletRequest request,HttpServletResponse response) 
                        throws ServletException,IOException {
      String uri="jdbc:mysql://127.0.0.1:3306/shop?"+
                             "user=root&password=123456&characterEncoding=gb2312";
      Connection con; 
      PreparedStatement sql; 
      
      // <jsp:useBean id="userBean" class="Register����������"
      // 			scope="request" />
      Register userBean=new Register();  //������Javabeanģ��
      request.setAttribute("userBean",userBean);
      // ��ʱ������ ����?���
      // �Ϸ���У��󣬱��浽��Ա����
      String logname=request.getParameter("logname").trim();
      String password=request.getParameter("password").trim();
      String again_password=request.getParameter("again_password").trim();
      String phone=request.getParameter("phone").trim();
      String address=request.getParameter("address").trim();
      String realname=request.getParameter("realname").trim();
      if(logname==null)
           logname="";
      if(password==null)
           password="";
      if(!password.equals(again_password)) { 
         userBean.setBackNews("�������벻ͬ��ע��ʧ�ܣ�");
         RequestDispatcher dispatcher= 
         request.getRequestDispatcher("inputRegisterMess.jsp");
         dispatcher.forward(request, response);//ת��
         return;
      }
      boolean isLD=true;
      for(int i=0;i<logname.length();i++){
          char c=logname.charAt(i);
           if(!((c<='z'&&c>='a')||(c<='Z'&&c>='A')||(c<='9'&&c>='0'))) 
             isLD=false;
      } 
      boolean boo=logname.length()>0&&password.length()>0&&isLD;
      String backNews="";
      try{   con=DriverManager.getConnection(uri);
             String insertCondition="INSERT INTO user VALUES (?,?,?,?,?)";
             sql=con.prepareStatement(insertCondition);
             if(boo)
             { sql.setString(1,handleString(logname));
               sql.setString(2,handleString(password));
               sql.setString(3,handleString(phone));
               sql.setString(4,handleString(address));
               sql.setString(5,handleString(realname));
               int m=sql.executeUpdate();
               if(m!=0){
                  backNews="ע��ɹ�";
    // <jsp:setProperty name="userBean" property="logname" param="logname" />
   // <jsp:setProperty name="userBean" property="*" />
                  
                  userBean.setLogname(logname);
                  userBean.setBackNews(backNews);
                  userBean.setPhone(handleString(phone));
                  userBean.setAddress(handleString(address));
                  userBean.setRealname(handleString(realname));
               }
             }
             else {
                 backNews="��Ϣ��д��������������зǷ��ַ�";
                 userBean.setBackNews(backNews);  
             }
             con.close();
      }
      catch(SQLException exp){
             backNews="�û�Ա���ѱ�ʹ�ã����������"+exp;
             userBean.setBackNews(backNews); 
      }
      RequestDispatcher dispatcher= 
      request.getRequestDispatcher("inputRegisterMess.jsp");
      dispatcher.forward(request, response);//ת��
   }
   public  void  doGet(HttpServletRequest request,HttpServletResponse response)
                        throws ServletException,IOException {
      doPost(request,response);
   }
}
