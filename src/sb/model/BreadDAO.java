package sb.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sb.dto.BreadDTO;
import sb.util.BreadUtil;

public class BreadDAO {

	// 빵 데이터 삽입
	public static void injectionDB() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<BreadDTO> arr = new ArrayList<BreadDTO>();
		int min = 3, max = 7;
		int price = 1500, maxprice = 30000;

		try {
			con = BreadUtil.getConnection();
			con.setAutoCommit(false); 
			
			String[] ar = null;
			BufferedReader br = Files.newBufferedReader(Paths.get("C:\\00.siat\\01.java\\siatBread\\document\\breadlistcsvplus.csv"));
			String str ="";
			br.readLine();
	            
	            while((str = br.readLine()) != null){
	            	int a=(int) (Math.random() * (max - min + 1) + min);
	            	int b= Math.round((int) (Math.random() * (maxprice - price + 1) + price)/100)*100;
	            	ar = str.split(","); 

	            	for(String v : ar) { 
		            	pstmt = con.prepareStatement("insert into bread values(seq_bread.NEXTVAL, ?, ?, ?)");
		            	pstmt.setString(1, ar[0]);
		    			pstmt.setInt(2, a);
		    			pstmt.setInt(3, b);
		    			pstmt.executeUpdate();

		    			pstmt.close();
		    			//ORA-01000: maximum open cursors exceeded 300개의 데이터 추가후 오류가 뜸 -최대 열기 커버의 수 초과
	            	}
	            }
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();		

		} catch (IOException e) {
			e.printStackTrace();		

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BreadUtil.close(con, pstmt);
		}
	}

	// 생산 가능한 빵 목록 조회
	public static ArrayList<BreadDTO> getAllBreadDAO() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<BreadDTO> list = null;
		try {
			con = BreadUtil.getConnection();
			con.setAutoCommit(false); // 오토커밋 하지않음

			pstmt = con.prepareStatement("select * from bread order by bread_id");
			rset = pstmt.executeQuery();

			list = new ArrayList<BreadDTO>();
			while (rset.next()) {
				list.add(new BreadDTO( //
						rset.getInt(1), //
						rset.getString(2), //
						rset.getInt(3), //
						rset.getInt(4)));
			}
		} finally {
			BreadUtil.close(con, pstmt, rset);
		}
		return list;
	}

	// 신제품 등록
	public static boolean newMakeBread(BreadDTO bread) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = BreadUtil.getConnection();
			con.setAutoCommit(false); // 오토커밋 하지않음
			
			pstmt = con.prepareStatement("insert into bread values(seq_bread.NEXTVAL, ?, ?, ?)");
			pstmt.setString(1, bread.getBreadName());
			pstmt.setInt(2, bread.getExpDate());
			pstmt.setInt(3, bread.getPrice());

			int result = pstmt.executeUpdate();

			if (result == 1) {
				return true;
			}
			
		} finally {
			BreadUtil.close(con, pstmt);
		}
		return false;
	}

	// 제품 키워드 검색
	// select * from bread where name like %?%
	public static ArrayList<BreadDTO> getBreadKeyword(String keyword) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<BreadDTO> list = null;
		try {
			con = BreadUtil.getConnection();
			con.setAutoCommit(false); // 오토커밋 하지않음
			
			// 원하고자하는 검색 값이 %로 감싸지지 않음
//			pstmt = con.prepareStatement("select * from bread where name like %?%");
//			pstmt.setString(1, keyword);

			// 앞에 혹은 뒤에는 %를 추가할수 있지만
			// 양쪽으로 감싸는데 실패
//			String sql = String.format("select * from bread where name like '%%s%'", keyword);
//			pstmt = con.prepareStatement(sql);

			// 2중 concat을 통해
			// 원하고자하는 키워드로 검색 성공
			pstmt = con.prepareStatement("select * from bread where name like ? order by bread_id");
			String sql = "%";
			sql = (sql.concat(keyword)).concat("%");
			pstmt.setString(1, sql);

			rset = pstmt.executeQuery();

			list = new ArrayList<BreadDTO>();
			while (rset.next()) {
				list.add(new BreadDTO( //
						rset.getInt(1), //
						rset.getString(2), //
						rset.getInt(3), //
						rset.getInt(4)));
			}
		} finally {
			BreadUtil.close(con, pstmt, rset);
		}
		return list;
	}

	// ID로 제품 검색
	public static BreadDTO getBreadId(int breadId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		BreadDTO bread = null;

		try {
			con = BreadUtil.getConnection();
			con.setAutoCommit(false); // 오토커밋 하지않음
			
			pstmt = con.prepareStatement("select * from bread where bread_id=?");
			pstmt.setInt(1, breadId);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				bread = new BreadDTO(rset.getInt(1), rset.getString(2), rset.getInt(3), rset.getInt(4));
			}
		} finally {
			BreadUtil.close(con, pstmt, rset);
		}
		return bread;
	}

	// 제품 변경
	public static boolean breadUpdate(int breadId, String breadName, int expDate, int price) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = BreadUtil.getConnection();
			con.setAutoCommit(false); // 오토커밋 하지않음
			
			pstmt = con.prepareStatement("update bread set name = ?, exp_date = ?, price = ? where bread_id = ?");
			pstmt.setString(1, breadName);
			pstmt.setInt(2, expDate);
			pstmt.setInt(3, price);
			pstmt.setInt(4, breadId);
			
			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} finally {
			BreadUtil.close(con, pstmt);
		}
		return false;
	}

	// 빵 등록 취소
	public static boolean breadDeleteId(int breadId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = BreadUtil.getConnection();
			con.setAutoCommit(false); // 오토커밋 하지않음
			
			pstmt = con.prepareStatement("delete from bread where bread_id = ?");
			pstmt.setInt(1, breadId);
			
			int result = pstmt.executeUpdate();
			System.out.println(result);
			if (result == 1) {
				return true;
			}
		} finally {
			BreadUtil.close(con, pstmt);
		}
		return false;
	}
}
