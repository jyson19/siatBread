package sb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sb.dto.BreadDTO;
import sb.dto.CustomerDTO;
import sb.util.BreadUtil;

// 주문 객체(손님)
public class CustomerDAO {
	// - 해당 메소드 실행시, 랜덤시간(1~3분) 간격으로 주문객체 생성
	// - 해당 주문 객체는 제품 등록된 리스트에서 랜덤생성됨
	//
	// 1. BreadDTO
	// breadId : 존재하는 breadId 중 1개 선택
	// 빵 이름, 유통기한, 가격 : BreadDTO로부터 가져옴
	//
	// 2. StockInDTO
	// quantity : 개수 랜덤(1 ~ 10개)

	// 고객 객체 생성
	public static boolean customerCreate() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = BreadUtil.getConnection();

			pstmt = con.prepareStatement("insert into customer values(seq_customer.NEXTVAL, '', '')");
			int result = pstmt.executeUpdate();

			if (result == 1) {
				return true;
			}
		} finally {
			BreadUtil.close(con, pstmt);
		}
		return false;
	}

	// 마지막 생성한 고객 객체 전달
	public static CustomerDTO findCustomer() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		CustomerDTO customer = null;

		try {
			con = BreadUtil.getConnection();

			pstmt = con.prepareStatement("SELECT customer_id, price_sum, pur_date "//
					+ "FROM (SELECT customer_id, price_sum, pur_date, rownum "//
					+ "FROM customer "//
					+ "ORDER BY customer_id desc) "//
					+ "WHERE rownum = 1");
			rset = pstmt.executeQuery();
			if (rset.next()) {
				customer = new CustomerDTO(rset.getInt(1), rset.getInt(2), rset.getString(3));
			}
		} finally {
			BreadUtil.close(con, pstmt, rset);
		}
		return customer;
	}

}
