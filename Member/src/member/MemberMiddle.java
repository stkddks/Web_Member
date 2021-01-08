package member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MemberMiddle  {
   public static String session;
   static {
	   session=null;
   }
	public static void main(String[] args) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		Connection conn=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "khbclass", "dkdlxl");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		while(true) {//반복문
			System.out.println("R:회원가입 S:ID찾기D:회원탈퇴E:회원수정I:로그인O:로그아웃L:회원목록");
			String idlogin=null;
			String sql=null;
			Statement stmt=null;
			try {
				String protocol = input.readLine();
				if(protocol.equals("R")||protocol.equals("r")) {//회원가입
					try {
						stmt = conn.createStatement();
						sql ="select id from member";
						ResultSet rs = stmt.executeQuery(sql);
						while(rs.next()) {
							idlogin = rs.getString("id");						
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(idlogin.equals(session)) {
						System.out.println("로그인중입니다.");
						continue;
					}
					System.out.println("아이디입력:");
					String id = input.readLine();
					System.out.println("패스워드입력:");
					String pw = input.readLine();
					System.out.println("주소입력:");
					String addr=input.readLine();
					System.out.println("전화번호입력:");
					String tel = input.readLine();
					int cnt=0;
					try {
						stmt = conn.createStatement();
						sql = "insert into member(id,pw,addr,tel) values('"+id+"','"+pw+"','"+addr+"','"+tel+"')";
						cnt = stmt.executeUpdate(sql);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					System.out.println(cnt+"건 회원등록되었습니다.");
				}//회원가입
				else if(protocol.equals("S")||protocol.equals("s")) {//ID찾기
					System.out.println("찾는 ID 입력:");
					String idSearch= input.readLine();
					System.out.println("회원아이디\t회원패스워드\t회원주소\t회원전화번호\n");
					String id = null;
					String pw = null;
					String addr = null;
					String tel = null;
					try {
						stmt = conn.createStatement();
						sql = "select id,pw,addr,tel from member where id='"+idSearch+"'";
						ResultSet rs = stmt.executeQuery(sql);
						while(rs.next()) {
							id = rs.getString("id");
							pw = rs.getString("pw");
							addr = rs.getString("addr");
							tel = rs.getString("tel");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}	
					if(id==null) {
						System.out.println("찾는아이디가 없습니다.");
						continue;
					}
				 System.out.print(id+"\t"+pw+"\t"+addr+"\t"+tel+"\n"); 
					
				}//ID찾기
				else if(protocol.equals("D")||protocol.equals("d")) {//회원탈퇴
					String id = null;
					if(session==null) {
						System.out.println("로그인하세요");
						continue;						
					}
					try {
						System.out.println("정말 탈퇴하시겠습니까y/n?");
						String result = input.readLine();
						if(result.equals("y")) {
							stmt = conn.createStatement();
							sql = "delete from member where id = '"+session+"'";
							int cnt = stmt.executeUpdate(sql);
							session=null;
						}else {
							continue;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					System.out.println("회원탈퇴되었습니다.");					
				}//회원탈퇴
				else if(protocol.equals("E")||protocol.equals("e")) {//회원수정
					if(session==null) {
						System.out.println("로그인하세요");
						continue;
					}
					System.out.print("회원아이디\t회원패스워드\t회원주소\t회원전화번호\n");
					String id = null;
					String pw = null;
					String addr = null;
					String tel = null;
					try {
						stmt = conn.createStatement();
						sql = "select id,pw,addr,tel from member where id='"+session+"'";
						ResultSet rs = stmt.executeQuery(sql);
						while(rs.next()) {
							 id = rs.getString("id");
							 pw = rs.getString("pw");
							 addr = rs.getString("addr");
							 tel = rs.getString("tel");						
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(id.equals(session)) {
						System.out.print(id+"\t"+pw+"\t"+addr+"\t"+tel+"\n");
						System.out.println("회원아이디입력:");
						String idUpdate = input.readLine();
						System.out.println("회원패스워드입력:");
						String pwUpdate = input.readLine();
						System.out.println("회원주소입력:");
						String addrUpdate = input.readLine();
						System.out.println("회원전화번호입력:");
						String telUpdate = input.readLine();
						try {
							stmt = conn.createStatement();
							sql = "update member set id='"+idUpdate+"',pw='"+pwUpdate+"',addr='"+addrUpdate+"',tel='"+telUpdate+"' where id='"+session+"'";
							int cnt = stmt.executeUpdate(sql);
							session=idUpdate;
							System.out.println(cnt+"명회원이 수정되었습니다.");
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}//회원수정
				else if(protocol.equals("I")||protocol.equals("i")) {//로그인
					if(session!=null) {
						System.out.println("로그인중입니다.");
						continue;
					}
					System.out.println("아이디입력:");
					String loginId = input.readLine();
					System.out.println("패스워드입력:");
					String loginPw = input.readLine();
					String id = null;
					String pw = null;
					try {
						stmt = conn.createStatement();
						sql = "select id,pw from member where id='"+loginId+"'";
						ResultSet rs = stmt.executeQuery(sql);
						while(rs.next()) {
							id=rs.getString("id");
							pw=rs.getString("pw");						
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(!(loginId.equals(id))) {
						System.out.println("아이디가 틀렸습니다.");
						continue;
					}
					if(!(loginPw.equals(pw))) {
						System.out.println("패스워드가 틀렸습니다.");
						continue;
					}
					System.out.println("환영합니다. 로그인되었습니다.");
					session=loginId;	
				}//로그인
				else if(protocol.equals("O")||protocol.equals("o")) {//로그아웃
				   session=null;
				   System.out.println("로그아웃되었습니다.");
				}//로그아웃
				else if(protocol.equals("L")||protocol.equals("l")) {//회원목록
					System.out.print("회원아이디\t회원패스워드\t회원주소\t회원전화번호\n");
					try {
						stmt = conn.createStatement();
						sql = "select id,pw,addr,tel from member";
						ResultSet rs = stmt.executeQuery(sql);
						while(rs.next()) {
							String id = rs.getString("id");
							String pw = rs.getString("pw");
							String addr = rs.getString("addr");
							String tel =  rs.getString("tel");
							System.out.print(id+"\t"+pw+"\t\t"+addr+"\t"+tel+"\n");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}//회원목록
			} catch (IOException e) {				
				e.printStackTrace();
			}
			
		}//반복문

	}

}
