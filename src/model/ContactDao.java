package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

public class ContactDao {
	private String driver = "oracle.jdbc.OracleDriver";
	private String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private String dbuid = "test";
	private String dbpwd = "1234";
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public ContactDao() {
		open();
	}

	private void open() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, dbuid, dbpwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if(!conn.isClosed()) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 연락처 개인 조회
	public ContactVo getContact(int id) {
		ContactVo vo  = null;
		try {
			String  sql  = "SELECT NAME, TEL, RELATION, EMAIL, ADDRESS";
			sql +=	" FROM CONTACT_ADDR"; 
			sql += 	" WHERE ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				String name = rs.getString("NAME");
				String tel = rs.getString("TEL");
				String relation = rs.getString("RELATION");
				String email = rs.getString("EMAIL");
				String address = rs.getString("ADDRESS");
			
				vo = new ContactVo(id, name, tel, relation, email, address) ;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( !rs.isClosed() )    rs.close();
				if( !pstmt.isClosed() ) pstmt.close();
			} catch (SQLException e) {
			}
		}
		return  vo;
	}
	
	// 연락처 목록 조회
	public Vector getContactList() {
		Vector  v = new Vector(); 
		
		try {
			String  sql =  "SELECT ID, NAME, TEL, RELATION";
			sql        +=  " FROM CONTACT_ADDR ORDER BY ID";			
			pstmt       =  conn.prepareStatement(sql);
			
			rs         =   pstmt.executeQuery();
			while(rs.next()) {				
				Vector member = new Vector();
				member.add( rs.getInt("ID") );
				member.add( rs.getString("NAME") );
				member.add( rs.getString("TEL") );
				member.add( rs.getString("RELATION") );
		        v.add( member );
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			try {
				if( !rs.isClosed() )    rs.close();
				if( !pstmt.isClosed() ) pstmt.close();
			} catch (SQLException e) {
			}
		}	
		return  v;
	}
	
	// 연락처 추가
	public int insertContact(ContactVo vo) {
		int aftcnt = 0;
		
		try {
			String sql  = "INSERT INTO CONTACT_ADDR";
			sql        += " (ID, NAME, TEL, RELATION, EMAIL, ADDRESS)";
			sql        += "  VALUES ((SELECT NVL(MAX(ID),0)+1 FROM CONTACT_ADDR),?,?,?,?,?)";
			pstmt       = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName() );
			pstmt.setString(2, vo.getTel() );
			pstmt.setString(3, vo.getRelation() );
			pstmt.setString(4, vo.getEmail() );
			pstmt.setString(5, vo.getAddress() );
			
			aftcnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( !pstmt.isClosed() ) pstmt.close();
			} catch (SQLException e) {
			}
		}
		return aftcnt;
	}
	
	// 연락처 수정
	public int updateContact(ContactVo vo) {
		int aftcnt = 0;
		try {
			String sql  = "UPDATE CONTACT_ADDR";
			sql        += " SET NAME = ?,"; 
			sql        += " TEL = ?,"; 
			sql        += " RELATION = ?,"; 
			sql        += " EMAIL = ?,"; 
			sql        += " ADDRESS = ?"; 
			sql        += " WHERE ID = ? "; 
			pstmt       =  conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getTel());
			pstmt.setString(3, vo.getRelation());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getAddress());
			pstmt.setInt(6, vo.getId());
			
			aftcnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( !pstmt.isClosed() ) pstmt.close();
			} catch (SQLException e) {
			}
		}
		return aftcnt;
	}

	// 연락처 삭제
	public int deleteContact(ContactVo vo) {
		int aftcnt  = 0;
		try {
			String sql = "DELETE FROM CONTACT_ADDR WHERE ID = ?";
			pstmt      =  conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getId() );
			
			aftcnt  = pstmt.executeUpdate();
						
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( !pstmt.isClosed() ) pstmt.close();
			} catch (SQLException e) {
			}
		}
		
		return aftcnt;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
