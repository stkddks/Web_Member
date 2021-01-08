package member.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberFunction {
   public static void register(BufferedReader input, Connection conn, Statement stmt, int cnt)
         throws IOException, SQLException {

      System.out.println("아이디입력:");
      String id = input.readLine();

      System.out.println("패스워드입력:");
      String pwd = input.readLine();

      System.out.println("주소입력:");
      String addr = input.readLine();

      System.out.println("전화번호 입력:");
      String tel = input.readLine();

      stmt = conn.createStatement();
      String sql = "INSERT INTO member(ID, PWD, ADDR, TEL) VALUES('" + id + "', '" + pwd + "', '" + addr + "', '"
            + tel + "')";

      cnt = stmt.executeUpdate(sql);
      System.out.println(cnt + "명 회원등록 되었습니다.");
      stmt.close();
      conn.close();

   }

   public static void list(Connection conn, Statement stmt) throws SQLException {
      System.out.println("회원아이디\t 회원패스워드 \t회원주소 \t\t회원전화번호");

      stmt = conn.createStatement();

      String sql = "SELECT * FROM MEMBER";
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
         String id = rs.getString("id");
         String pwd = rs.getString("pwd");
         String addr = rs.getString("addr");
         String tel = rs.getString("tel");
         System.out.print(id + "\t" + pwd + "\t" + addr + "\t" + tel + "\n");
      }
      stmt.close();
      conn.close();
   }

   public static void search(BufferedReader input, Connection conn, Statement stmt) throws IOException, SQLException {
      System.out.println("찾는 ID 입력:");

      String searchId = input.readLine();
      stmt = conn.createStatement();

      String sql = "SELECT * FROM MEMBER WHERE ID = '" + searchId + "'";
      ResultSet rs = stmt.executeQuery(sql);

      if (!rs.isBeforeFirst()) {
         System.out.println("찾는 아이디가 없습니다.");
         return;
      }

      System.out.println("회원아이디\t 회원패스워드 \t회원주소 \t\t회원전화번호");
      while (rs.next()) {
         String id = rs.getString("id");
         String pwd = rs.getString("pwd");
         String addr = rs.getString("addr");
         String tel = rs.getString("tel");
         System.out.print(id + "\t" + pwd + "\t" + addr + "\t" + tel + "\n");
      }
      stmt.close();
      conn.close();

   }

   public static void delete(Statement stmt, Connection conn, String session) throws SQLException {
      System.out.println("회원 탈퇴");

      stmt = conn.createStatement();
      String sql = "DELETE FROM MEMBER WHERE ID = '" + session + "'";
      int cnt = stmt.executeUpdate(sql);
      if (cnt >= 1) {
         System.out.println("회원 탈퇴되었습니다.");
      }

   }

   public static void update(Statement stmt, Connection conn, BufferedReader input, int cnt, String session)
         throws IOException, SQLException {
      System.out.println("회원 정보 수정");
      try {
         stmt = conn.createStatement();
         String sql = "SELECT * FROM MEMBER WHERE ID = '" + session + "'";

         ResultSet rs = stmt.executeQuery(sql);

         while (rs.next()) {
            System.out.println("회원아이디\t 회원패스워드 \t회원주소 \t\t회원전화번호");
            System.out.print(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
                  + rs.getString(4) + "\n");
         }

         System.out.println("아이디입력:");
         String id = input.readLine();

         System.out.println("패스워드입력:");
         String pwd = input.readLine();

         System.out.println("주소 입력:");
         String addr = input.readLine();

         System.out.println("전화번호 입력:");
         String tel = input.readLine();

         stmt = conn.createStatement();
         sql = "UPDATE MEMBER SET ID = '" + id + "', PWD = '" + pwd + "', ADDR = '" + addr + "', TEL = '" + tel
               + "' WHERE ID = '" + session + "'";

         cnt = stmt.executeUpdate(sql);
         System.out.println(cnt + "건 회원이 수정되었습니다.");
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static String login(Statement stmt, BufferedReader input, Connection conn, String session)
         throws IOException, SQLException {

      System.out.println("아이디입력:");
      String id = input.readLine();
      System.out.println("패스워드입력:");
      String pwd = input.readLine();

      stmt = conn.createStatement();
      String sql = "SELECT ID FROM MEMBER WHERE ID = '" + id + "' AND PWD = '" + pwd + "'";

      ResultSet rs = stmt.executeQuery(sql);

      if (!rs.isBeforeFirst()) {
         System.out.println("아이디가 존재하지 않습니다.");
      }

      while (rs.next()) {
         System.out.println("로그인 성공");
         session = id;
      }
      
      return session;
   }

   public static String logout(String session) {
      session = null;
      System.out.println("로그아웃 되었습니다.");
      
      return session;
   }
}