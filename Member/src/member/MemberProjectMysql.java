package member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberProjectMysql{
   public static String session;
   static {
      session = null;
   }
   public static void main(String args[]) throws IOException, SQLException {
      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
      Connection conn = null;
      Statement stmt = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e1) {
         e1.printStackTrace();
      }
      while (true) {
         conn = DriverManager.getConnection("jdbc:mysql://bbr123.cafe24.com:3306/bbr123?characterEncoding=utf8", "bbr123","alstjr95!"); 
         
         System.out.println("=======회원정보시스템=========");
         System.out.println("R:회원가입 L:회원목록 S:ID찾기 D:회원탈퇴 E:회원수정 I:로그인 O:로그아웃");
         System.out.println("==========================");
         System.out.print("메뉴를 선택해주세요: ");
         String protocol = input.readLine();
         
         String idlogin=null;
         String sql=null;
         int cnt = 0;
         
         if (protocol.equals("r") || protocol.equals("R")) {
            // 회원등록
            System.out.println("--------------회원 등록--------------");
            
            if (session!=null) {
               System.out.println("로그인중입니다 ");
               continue; //로그인중일때 회원가입 하지마라 를 continue로 제어를 한다. // 값 한번 입력하고 주석 풀어서 실행
            }
            
            stmt = conn.createStatement();
            System.out.printf("아이디: ");
            String joinId = input.readLine();
            System.out.printf("패드워드: ");
            String joinPw = input.readLine();
            System.out.printf("주소: ");
            String joinAddr = input.readLine();
            System.out.printf("전화번호: ");
            String joinTel = input.readLine();
            sql = "insert into MemberA (id, pw, addr ,tel) values ('"+joinId+"', '"+joinPw +"', '"+joinAddr+"', '"+joinTel+"')";
            cnt = stmt.executeUpdate(sql);
            System.out.println(cnt + "명의 회원 등록 되었습니다");
            stmt.close();
            conn.close();
            
         } else if (protocol.equals("l") || protocol.equals("L")) {
            // 전체출력
            System.out.println();
            
            System.out.println("--------------회원 전체 출력--------------");
            stmt = conn.createStatement();
            sql = "select * from MemberA";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
               String id = rs.getString("id");
               String pw = rs.getString("pw");
               String addr = rs.getString("addr");
               String tel = rs.getString("tel");
               System.out.print("아이디: " + id + "\t\t 비밀번호: " + pw + "\t\t 주소: " + addr + "\t\t 전화번호: " + tel + "\t\t");
               System.out.println();
            }
            stmt.close();
            conn.close();
            System.out.println();
         } else if (protocol.equals("s") || protocol.equals("S")) {
            // 아이디찾기
            System.out.println("회원의 주소를 입력해주세요: ");
            String searchAddr = input.readLine();
            System.out.println("회원의 전화번를 입력해주세요: ");
            String searchTel = input.readLine();
            stmt = conn.createStatement();
            sql = "select id from MemberA where addr='" + searchAddr + "' and tel = '" + searchTel + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.isBeforeFirst()) {
               System.out.println("회원정보가 존재하지 않습니다. 회원가입 부탁드립니다");
            }
            while (rs.next()) {
               String searchId = rs.getString("id");
               System.out.print("회원님의 아이디는 " + searchId + "입니다");
               System.out.println();
            }
            
         }
         else if (protocol.equals("d") || protocol.equals("D")) {
            // 회원탈퇴
            if (session == null) {
               System.out.println("로그인 먼저 해주세요 :)");
               continue;
            }
            stmt = conn.createStatement();
            sql = "select * from MemberA where id='" + session + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.isBeforeFirst()) {
               System.out.println("없는 회원입니다.");
            }
            
            while (rs.next()) {
               stmt = conn.createStatement();
               sql = "delete from MemberA where id='" + session + "'";
               cnt = stmt.executeUpdate(sql);
               String delName = rs.getString("id");
               System.out.println(delName + "님이 탈퇴되었습니다.");
               session = null;
               System.out.println();
            }
            
         } else if (protocol.equals("e") || protocol.equals("E")) {
            // 회원수정
            stmt = conn.createStatement();
            sql = "select * from MemberA where id='"+session+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
               System.out.println("회원아이디\t\t회원태스워드\t\t회원주소\t\t회원전화번호");
               String id = rs.getString("id");
               String pw = rs.getString("pw");
               String addr = rs.getString("addr");
               String tel = rs.getString("tel");
               System.out.println(id + "\t\t" + pw + "\t\t" + addr + "\t\t" + tel + "\t\t");
            }
            System.out.printf("아이디: ");
            String updateId = input.readLine();
            System.out.printf("패드워드: ");
            String updatePw = input.readLine();
            System.out.printf("주소: ");
            String updateAddr = input.readLine();
            System.out.printf("전화번호: ");
            String updateTel = input.readLine();
            
            stmt = conn.createStatement();
            sql = "update MemberA set id='"+updateId+"', pw='"+updatePw+"', addr='"+updateAddr+"', tel='"+updateTel+"' where id='"+session+"'";
            stmt.executeUpdate(sql);
            System.out.println(updateId + "회원님의 정보가 수정되었습니다.");
            session = updateId;
         } else if (protocol.equals("i") || protocol.equals("I")) {
            // 로그인
            if (session!=null) {
               System.out.println("로그인중입니다 ");
               continue; //로그인중일때 회원가입 하지마라 를 continue로 제어를 한다. // 값 한번 입력하고 주석 풀어서 실행
            }
            System.out.print("아이디입력: ");
            String userId = input.readLine();
            System.out.print("패스워드입력: ");
            String userPw = input.readLine();
            String id = null;
            String pw = null;
            
            stmt = conn.createStatement();
            sql = "select id, pw from MemberA where id='"+userId+"' and pw='"+userPw+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
               id = rs.getString("id");
               pw = rs.getString("pw");
            }
            if (!(userId.equals(id))) {
               System.out.println("아이디가 틀렸습니다 ");
               continue;
            } else if (!(userPw.equals(pw))) {
               System.out.println("비밀번호가 틀렸습니다 ");
               continue;
            } else if (userId.equals(id) && userPw.equals(pw)) {
               System.out.println("환영합니다" + userId + "님!! 로그인되었습니다");
               session = userId;
            }
            
            
         } else if (protocol.equals("o") || protocol.equals("O")) {
            // 로그아웃 
            if(session != null) {
               System.out.println("로그아웃 되었습니다. 또 오세요 회원님");
               session = null;
            } else if(session == null) {
               System.out.println("로그인을 먼저 해주세요");
            }
         }
      }
   }
}

/*
System.out.println("찾는 ID 입력: ");
String searchId = input.readLine();
System.out.println("회원아이디\t\t회원태스워드\t\t회원주소\t\t회원전화번호");
String id = null;
String pw = null;
String addr = null;
String tel = null;
try {
   stmt = conn.createStatement();
   String sql = "select id, pw, addr, tel from member where id='"+searchId+"'";
   ResultSet rs = stmt.executeQuery(sql);
   while(rs.next()) {
      id = rs.getString("id");
      pw = rs.getString("pw");
      addr = rs.getString("addr");
      tel = rs.getString("tel");
      
   }
} catch (Exception e) {
   e.printStackTrace();
} 
if(id==null){
   System.out.println("찾는 ID 가 없습니다.");
}
//if (id.equals(session)) {
//   System.out.print("아이디: "+id+"\t\t 비밀번호: "+pw+"\t\t 주소: "+addr+"\t\t 전화번호: "+tel+"\t\t");
//   }
*/


//         if(id.equals("admin") && pw.equals("1234")){
//            session.setAttribute("id",id);
//            response.sendRedirect("index.jsp");
//         }else if(id.equals("admin")){
//            out.println("<script>alert('비밀번호가 틀렸습니다.'); history.back();</script>");
//         }else if(pass.equals("1234")){
//            out.println("<script>alert('아이디가 틀렸습니다.'); history.back();</script>");
//         }else{
//            out.println("<script>alert('아이디와 비밀번호가 틀렸습니다.'); history.back();</script>");
//         }






