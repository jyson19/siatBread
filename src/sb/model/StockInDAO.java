package sb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sb.dto.BreadDTO;
import sb.dto.StockInQuantityView;
import sb.util.BreadUtil;

public class StockInDAO {

	public static Object getAllBread() {
		return "test code";
	}

	// 빵 제작(BreadDTO -> StockIn)
	public static boolean breadCreate(ArrayList<BreadDTO> breads) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = BreadUtil.getConnection();
			int result = 0;
			for (int i = 0; i < breads.size(); i++) {
				pstmt = con.prepareStatement(
						"INSERT INTO stock_in (stock_id, bread_id, prod_date, exp_date, quantity, out_date, category) "
								+ "SELECT seq_stock_in.NEXTVAL, ?, sysdate, sysdate + exp_date, 10, '', 1 FROM bread where name = ?");
				pstmt.setInt(1, breads.get(i).getBreadId());
				pstmt.setString(2, breads.get(i).getBreadName());
				result = pstmt.executeUpdate();
			}
			if (result >= 1) {
				return true;
			}
		} finally {
			BreadUtil.close(con, pstmt);
		}
		return false;
	}

	// 빵 폐기처리(StockIn -> StockOut, 3)
	public static boolean stockInDisposal() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = BreadUtil.getConnection();
			pstmt = con.prepareStatement("UPDATE stock_in SET out_date = SYSDATE, category = 4 "//
					+ "WHERE exp_date < SYSDATE");

			int result = pstmt.executeUpdate();
			if (result >= 1) {
				return true;
			}
		} finally {
			BreadUtil.close(con, pstmt);
		}
		return false;
	}

	// 재고 빵 조회
	public static ArrayList<StockInQuantityView> breadStockInDAO() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<StockInQuantityView> list = null;
		try {
			con = BreadUtil.getConnection();
			con.setAutoCommit(false);

			pstmt = con.prepareStatement("select s.stock_id, b.name, s.exp_date, s.quantity "//
					+ "from stock_in s, bread b where s.bread_id = b.bread_id and s.category = 1");
			rset = pstmt.executeQuery();

			list = new ArrayList<StockInQuantityView>();
			while (rset.next()) {
				list.add(new StockInQuantityView( //
						rset.getInt(1), //
						rset.getString(2), //
						rset.getString(3), //
						rset.getInt(4)));
			}
		} finally {
			BreadUtil.close(con, pstmt, rset);
		}
		return list;
	}

	// 총 매출손실 조회
	public static int salesLoss() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			con = BreadUtil.getConnection();
			pstmt = con.prepareStatement("select sum(price*quantity) " //
					+ "from bread b, stock_in s " //
					+ "where b.bread_id = s.bread_id and s.category in(3,4)");
			rset = pstmt.executeQuery();

			int loss = 0;
			while (rset.next()) {
				loss = rset.getInt(1);
				return loss;
			}
		} finally {
			BreadUtil.close(con, pstmt, rset);
		}
		return 0;
	}

	public static int salesProfit() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			con = BreadUtil.getConnection();
			pstmt = con.prepareStatement("select sum(price*quantity) " //
					+ "from bread b, stock_in s " //
					+ "where b.bread_id = s.bread_id and s.category = 2");
			rset = pstmt.executeQuery();

			int loss = 0;
			while (rset.next()) {
				loss = rset.getInt(1);
				return loss;
			}
		} finally {
			BreadUtil.close(con, pstmt, rset);
		}
		return 0;
	}
}
