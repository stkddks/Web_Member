package member.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberFunctionMain {
   public static String session;
   static {
      session = null;
   }

   public static void main(String[] args) {
      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
      Connection conn = null;
      Statement stmt = null;
      int cnt = 0;

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }

      while (true) {
         System.out.println("R:회원가입 L:회원목록 S:ID찾기 D:회원탈퇴 E:회원수정 I:로그인 O:로그아웃");
         String protocol = null;

         try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "khbclass", "dkdlxl");
         } catch (SQLException e) {
            e.printStackTrace();
         }

         try {
            protocol = input.readLine();
         } catch (IOException e) {
            e.printStackTrace();
         }

         if (protocol.equals("R") || protocol.equals("r")) {
            if (session != null) {
               System.out.println("로그인한 상태에서는 회원가입이 불가합니다.");
               continue;
            }
            try {
               MemberFunction.register(input, conn, stmt, cnt);
            } catch (IOException e) {
               e.printStackTrace();
            } catch (SQLException e) {
               e.printStackTrace();
            }

         } else if (protocol.equals("L") || protocol.equals("l")) {
            try {
               MemberFunction.list(conn, stmt);
            } catch (SQLException e) {
               e.printStackTrace();
            }

         } else if (protocol.equals("S") || protocol.equals("s")) {
            try {
               MemberFunction.search(input, conn, stmt);
            } catch (IOException e) {
               e.printStackTrace();
            } catch (SQLException e) {
               e.printStackTrace();
            }

         } else if (protocol.equals("D") || protocol.equals("d")) {
            if (session == null) {
               System.out.println("로그인이 필요합니다.");
               continue;
            }
            try {
               MemberFunction.delete(stmt, conn, protocol);
            } catch (SQLException e) {
               e.printStackTrace();
            }

         } else if (protocol.equals("E") || protocol.equals("e")) {
            if(session == null) {
               System.out.println("로그인 상태에서만 가능합니다.");
               continue;
            }
            
            try {
               MemberFunction.update(stmt, conn, input, cnt, session);
            } catch (IOException e) {
               e.printStackTrace();
            } catch (SQLException e) {
               e.printStackTrace();
            }

         } else if (protocol.equals("I") || protocol.equals("i")) {
            if (session != null) {
               System.out.println("로그인중입니다.");
               continue;
            }

            try {
               session = MemberFunction.login(stmt, input, conn, session);
            } catch (IOException e) {
               e.printStackTrace();
            } catch (SQLException e) {
               e.printStackTrace();
            }

         } else if (protocol.equals("O") || protocol.equals("o")) {
            session = MemberFunction.logout(session);
         }
      }
   }

}