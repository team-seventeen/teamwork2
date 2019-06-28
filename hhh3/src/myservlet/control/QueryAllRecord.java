package myservlet.control;
import mybean.data.DataByPage;
import com.sun.rowset.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class QueryAllRecord extends HttpServlet{
   CachedRowSetImpl rowSet=null;
   public void init(ServletConfig config) throws ServletException{
      super.init(config);
      try {  Class.forName("com.mysql.jdbc.Driver");
      }
      catch(Exception e){} 
   }
   public void doPost(HttpServletRequest request,HttpServletResponse response) 
                        throws ServletException,IOException{
      request.setCharacterEncoding("gb2312");
      String idNumber= request.getParameter("fenleiNumber");
      if(idNumber==null) 
          idNumber="0";
      int id = Integer.parseInt(idNumber);
      HttpSession session=request.getSession(true); 
      Connection con=null; 
      DataByPage dataBean=null;
      try{ 
           dataBean=(DataByPage)session.getAttribute("dataBean");
           if(dataBean==null){
              dataBean=new DataByPage();  //����Javabean����
              session.setAttribute("dataBean",dataBean);
           }
      }
      catch(Exception exp){
           dataBean=new DataByPage();  
           session.setAttribute("dataBean",dataBean);
      } 
      String uri="jdbc:mysql://localhost:3306/shop?"+"user=root&password=123456&characterEncoding=gb2312";
      try{ 
           con=DriverManager.getConnection(uri,"root","123456");
           Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                                ResultSet.CONCUR_READ_ONLY);
           ResultSet rs=sql.executeQuery("SELECT * FROM cosmeticForm where id = "+id);
           rowSet=new CachedRowSetImpl();   //�����м�����
           rowSet.populate(rs);
           dataBean.setRowSet(rowSet);      //�м���ݴ洢��dataBean��  
           con.close();                     //�ر�����
           System.out.println("QueryAllRecord - select");
      }
      catch(SQLException exp){}
      response.sendRedirect("byPageShow.jsp");//�ض���byPageShow.jsp
   } 
   public void doGet(HttpServletRequest request,
              HttpServletResponse response) 
                        throws ServletException,IOException{
       doPost(request,response);
   }
}
