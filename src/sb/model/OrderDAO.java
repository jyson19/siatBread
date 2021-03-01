package sb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import sb.dto.CustomerDTO;
import sb.dto.OrderView;
import sb.util.BreadUtil;

public class OrderDAO {

	// 주문 객체 생성 -> 스레드로 실행됨
	public static boolean orderCreate(CustomerDTO customer) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = BreadUtil.getConnection();
			con.setAutoCommit(false); // 오토커밋 하지않음

			pstmt = con
					.prepareStatement("insert into order_req(order_id, bread_id, customer_id, order_quan, sell_check) "
							+ "select seq_order_req.NEXTVAL, bid, ?, ran_quan, 1 "
							+ "from (select trunc(dbms_random.value(1, count(bread_id))) as bid, trunc(dbms_random.value(1, 30)) as ran_quan "
							+ "from bread)");
			pstmt.setInt(1, customer.getCustomerId());

			int result = pstmt.executeUpdate();

			if (result >= 1) {
				return true;
			}

		} finally {
			BreadUtil.close(con, pstmt);
		}
		return false;
	}

	// 마지막 생성한 주문 객체 전달
	public static HashMap<String, Integer> findLastOrder() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		HashMap<String, Integer> ord = new HashMap<String, Integer>();

		try {
			con = BreadUtil.getConnection();

			pstmt = con.prepareStatement("select name, order_quan " //
					+ "from (select o.order_id, b.name, o.order_quan "//
					+ "from bread b, order_req o " //
					+ "where o.bread_id = b.bread_id " //
					+ "order by o.order_id desc) "//
					+ "where rownum = 1");
			rset = pstmt.executeQuery();
			if (rset.next()) {

				ord.put(rset.getString(1), rset.getInt(2));
			}
		} finally {
			BreadUtil.close(con, pstmt, rset);
		}
		return ord;
	}

	// 주문 조회
	public static ArrayList<OrderView> orderSelectAll() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<OrderView> list = null;
		try {
			con = BreadUtil.getConnection();
			con.setAutoCommit(false); // 오토커밋 하지않음

			pstmt = con.prepareStatement(
					"select o.order_id, o.bread_id, o.customer_id, o.order_quan, o.sell_check, b.name "
							+ "from order_req o, bread b " //
							+ "where o.bread_id = b.bread_id and o.sell_check = 1 "//
							+ "order by o.order_id");
			rset = pstmt.executeQuery();

			list = new ArrayList<OrderView>();
			while (rset.next()) {
				list.add(new OrderView( //
						rset.getInt(1), //
						rset.getInt(2), //
						rset.getInt(3), //
						rset.getInt(4), //
						rset.getInt(5), //
						rset.getString(6)));
			}
		} finally {
			BreadUtil.close(con, pstmt, rset);
		}
		return list;
	}

	// 빵 판매 step1
	// StockIn.quantity > Order.quan ?
	public static List<Integer> quantityCheck() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Integer> list = new ArrayList<>();

		try {
			con = BreadUtil.getConnection();
			con.setAutoCommit(false); // 오토커밋 하지않음

			pstmt = con.prepareStatement("select order_quan, bread_id "//
					+ "from (select o.order_quan, o.bread_id, rownum " //
					+ "from stock_in s, order_req o "//
					+ "where s.bread_id = o.bread_id and o.sell_check = 1 " //
					+ "order by order_id) " //
					+ "where rownum = 1");
			rset = pstmt.executeQuery();

			if (rset.next()) {
				list.add(rset.getInt(1));
				list.add(rset.getInt(2));
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} finally {
			BreadUtil.close(con, pstmt, rset);
		}

		if (list.size() == 2) {
			return list;
		}
		return null;
	}

	// 조건2)
	public static List<Integer> sumCheck(List<Integer> list) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			con = BreadUtil.getConnection();
			con.setAutoCommit(false); // 오토커밋 하지않음

			pstmt = con.prepareStatement("select sum(quantity) from stock_in "//
					+ "where bread_id = ? and exp_date >= SYSDATE and category = 1");
			pstmt.setInt(1, (int) list.get(1));

			rset = pstmt.executeQuery();

			if (rset.next()) {
				list.add(rset.getInt(1));
			}
		} finally {
			BreadUtil.close(con, pstmt, rset);
		}

		if (list.size() == 3) {
			return list;
		}
		return null;
	}

	// 조건3)
	public static boolean stockInRownum(List<Integer> list) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rset = null;
		int result = 0;

		// list(int order_quan, int bread_id, int sum(quantity))
		list.add(null);
		list.add(null);
		list.add(null);
		// list(int order_quan, int bread_id, int sum(quantity), null, null, null)
		try {
			con = BreadUtil.getConnection();

			int rownum = 1;
			int orderQuanNum = list.get(0);
			while (orderQuanNum != 0) {
				pstmt = con.prepareStatement("select stock_id, quantity " //
						+ "from (select stock_id, quantity, category, rownum as rn "//
						+ "from stock_in " //
						+ "where bread_id = ? order by exp_date) a " //
						+ "where a.rn = ?");
				pstmt.setInt(1, list.get(1));
				pstmt.setInt(2, rownum);
				rownum++;

				rset = pstmt.executeQuery();
				result++;

				if (rset.next()) {
					list.set(3, rset.getInt(1));
					list.set(4, rset.getInt(2));
				}
				// list(int order_quan, int bread_id, int sum(quantity), int stock_id, int
				// quantity, int rownum)

				// stockIn 1개 객체 재고 >= 주문수량
				if (list.get(4) >= orderQuanNum) {
					pstmt2 = con.prepareStatement("update stock_in set quantity = ? - ? where stock_id = ?");
					pstmt2.setInt(1, list.get(4));
					pstmt2.setInt(2, orderQuanNum);
					pstmt2.setInt(3, list.get(3));

					pstmt2.executeUpdate();
					result++;

					// list(int order_quan, int bread_id, int sum(quantity), int stock_id, int
					// quantity, int rownum)
					pstmt3 = con.prepareStatement(
							"insert into stock_in(stock_id, bread_id, prod_date, exp_date, quantity, out_date, category) "//
									+ "select seq_stock_in.NEXTVAL, bread_id, prod_date, exp_date, ?, SYSDATE, 2 "//
									+ "from stock_in where stock_id = ?");
					pstmt3.setInt(1, orderQuanNum);
					pstmt3.setInt(2, list.get(3));
					list.set(4, list.get(4) - orderQuanNum);
					orderQuanNum = 0;
					list.set(0, orderQuanNum);

					pstmt3.executeUpdate();

					// stockIn 1개 객체 재고 < 주문수량
				} else if (list.get(4) < orderQuanNum) {
					pstmt2 = con.prepareStatement("update stock_in set category = 2 where stock_id = ?");
					pstmt2.setInt(1, list.get(3));
					orderQuanNum = orderQuanNum - list.get(4);

					pstmt2.executeUpdate();
					result++;
				}

			}
			if (orderQuanNum == 0) {
				pstmt4 = con.prepareStatement("update order_req set sell_check = 2 "//
						+ "where order_id = (select order_id "//
						+ "from (select o.order_quan, o.bread_id, rownum, o.order_id "//
						+ "from stock_in s, order_req o "//
						+ "where s.bread_id = o.bread_id and o.sell_check = 1 "//
						+ "order by order_id) "//
						+ "where rownum = 1)");
				pstmt4.executeUpdate();
			}

			if (result >= 3) {
				return true;
			}
		} finally {
			con.close();
			pstmt.close();
			pstmt2.close();
			pstmt3.close();
			pstmt4.close();
			rset.close();
		}
		return false;
	}
}
