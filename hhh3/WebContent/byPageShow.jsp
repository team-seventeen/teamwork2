<%@ page contentType="text/html;charset=GB2312" %>
<%@ page import="mybean.data.DataByPage" %>
<%@ page import="com.sun.rowset.*" %>
<jsp:useBean id="dataBean" class="mybean.data.DataByPage" scope="session"/>
<%@ include file="head.txt" %></HEAD>
<HTML><Body background=image/back.jpg><center>
<BR>��ǰ��ʾ�������ǣ�
  <table border=2>
  <tr>
    <th>ͼ���ʶ��</th>
    <th>ͼ������</th>
    <th>ͼ����Ϣ</th>
    <th>ͼ��۸�</th>
    <th>�鿴����</th>
    <td><font color=blue>��ӵ����ﳵ</font></td>
  </tr>
<jsp:setProperty name="dataBean" property="pageSize" param="pageSize"/>
<jsp:setProperty name="dataBean" property="currentPage" param="currentPage"/>
<%    
      CachedRowSetImpl rowSet=dataBean.getRowSet();
      if(rowSet==null) {
         out.print("û���κβ�ѯ��Ϣ���޷����");
         return;  
      }
      rowSet.last(); 
      int totalRecord=rowSet.getRow();
      out.println("ȫ����¼��"+totalRecord); //ȫ����¼��
      int pageSize=dataBean.getPageSize();  //ÿҳ��ʾ�ļ�¼��
      int totalPages = dataBean.getTotalPages();
      if(totalRecord%pageSize==0)
           totalPages = totalRecord/pageSize;//��ҳ��
      else
           totalPages = totalRecord/pageSize+1;
      dataBean.setPageSize(pageSize);
      dataBean.setTotalPages(totalPages);
      if(totalPages>=1) {
         if(dataBean.getCurrentPage()<1)
              dataBean.setCurrentPage(dataBean.getTotalPages());
         if(dataBean.getCurrentPage()>dataBean.getTotalPages())
              dataBean.setCurrentPage(1);
         int index=(dataBean.getCurrentPage()-1)*pageSize+1;
         rowSet.absolute(index);  //��ѯλ���ƶ���currentPageҳ��ʼλ��
         boolean boo=true;
         for(int i=1;i<=pageSize&&boo;i++) { 
            String number=rowSet.getString(1);
            String name=rowSet.getString(2);
            String maker=rowSet.getString(3);
            String price=rowSet.getString(4);
            String goods =
            "("+number+","+name+","+maker+
             ","+price+")#"+price;//���ڹ��ﳵ����۸�,β׺��"#�۸�ֵ"
            goods = goods.replaceAll("\\p{Blank}","");
            String button="<form  action='putGoodsServlet' method = 'post'>"+
                     "<input type ='hidden' name='java' value= "+goods+">"+
                     "<input type ='submit'  value='���빺�ﳵ' ></form>";
            String detail="<form  action='showDetail.jsp' method = 'post'>"+
                     "<input type ='hidden' name='xijie' value= "+number+">"+
                     "<input type ='submit'  value='�鿴ϸ��' ></form>";
            out.print("<tr>");
            out.print("<td>"+number+"</td>");
            out.print("<td>"+name+"</td>");
            out.print("<td>"+maker+"</td>");
            out.print("<td>"+price+"</td>");
            out.print("<td>"+detail+"</td>");
            out.print("<td>"+button+"</td>");
            out.print("</tr>");
            boo=rowSet.next();
         }
     }
%>
 </table>
 <BR>ÿҳ�����ʾ<jsp:getProperty name="dataBean" property="pageSize"/>����Ϣ
 <BR>��ǰ��ʾ��<Font color=blue>
     <jsp:getProperty name="dataBean" property="currentPage"/>
   </Font>ҳ,����
   <Font color=blue><jsp:getProperty name="dataBean" property="totalPages"/>
   </Font>ҳ��
<Table>
  <tr><td><FORM action="" method=post>
          <Input type=hidden name="currentPage" value=
         "<%=dataBean.getCurrentPage()-1 %>">
           <Input type=submit name="g" value="��һҳ"></FORM></td>
      <td><FORM action="" method=post>
          <Input type=hidden name="currentPage"
           value="<%=dataBean.getCurrentPage()+1 %>">
          <Input type=submit name="g" value="��һҳ"></FORM></td></tr>
 <tr><td> <FORM action="" method=post>
          ÿҳ��ʾ<Input type=text name="pageSize" value =1 size=3>
          ����¼<Input type=submit name="g" value="ȷ��"></FORM></td>
      <td> <FORM action="" method=post>
           ����ҳ�룺<Input type=text name="currentPage" size=2 >
           <Input type=submit name="g" value="�ύ"></FORM></td></tr>
</Table>
</Center>
</BODY></HTML>
