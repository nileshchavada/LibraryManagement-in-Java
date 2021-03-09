package com.system.management;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.system.bean.BookDetailsVO;
import com.system.bean.BookOrderDetailsVO;
import com.system.bean.BookRecordVO;
import com.system.bean.ReservedBookDetailsVO;

public class BookDao {

	public ArrayList<BookDetailsVO> checkAvailability(BookDetailsVO bookDetailsVO) throws SQLException {
		ArrayList<BookDetailsVO> bDtlslist = new ArrayList<BookDetailsVO>();
		Connection con = null;
		CallableStatement sttmnt = null;
		try {
			System.out.println();
			String sql = "call library_database.sp_find_book(?,?,?)";
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareCall(sql);
			if( bookDetailsVO.getAuthrName()!= null)
				sttmnt.setString(1, "%" + bookDetailsVO.getAuthrName() + "%");
			else
				sttmnt.setString(1, "");
			if( bookDetailsVO.getAuthrName()!= null)
				sttmnt.setString(2, "%" + bookDetailsVO.getAuthrName() + "%");
			else
				sttmnt.setString(2, "");
			if( bookDetailsVO.getBookName()!= null)
				sttmnt.setString(3, "%" + bookDetailsVO.getBookName() + "%");
			else
				sttmnt.setString(3, "");
			
			ResultSet rs = sttmnt.executeQuery();
			while (rs.next()) {
				BookDetailsVO obj = new BookDetailsVO();
				obj.setBookId(rs.getLong(1));
				obj.setBookName(rs.getString(2));
				obj.setAuthrName(rs.getString(3) + " " + rs.getString(4));
				obj.setEdition(rs.getInt(5));
				obj.setPublication(rs.getString(6));
				obj.setType(rs.getString(7));
				obj.setLanguage(rs.getString(8));
				obj.setDepartment(rs.getString(9));
				obj.setStatus(rs.getString(10));
				bDtlslist.add(obj);
			}

		} catch (Exception e) {
			System.out.println("Exception occured in method checkAvailability.." + e.getStackTrace());
		} finally {
			con.close();
			sttmnt.close();
		}

		return bDtlslist;

	}

	public int isBookAvailable(Long bookId) throws SQLException {
		Connection con = null;
		PreparedStatement sttmnt = null;
		int count = 0;
		try {

			String sql = "SELECT available FROM library_database.book_master where book_name_id = "
					+ "(SELECT bm.book_name_id FROM library_database.book_details bd, library_database.author_book_mapping bm WHERE bd.map_id = bm.map_id and bd.book_id = ? and book_status='available') ";
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareStatement(sql);
			sttmnt.setLong(1, bookId);
			ResultSet rs = sttmnt.executeQuery();
			while (rs.next()) {
				count = Integer.valueOf(rs.getString(1));
			}

		} catch (SQLException e) {
			System.out.println("Exception occured in method isBookAvailable.." + e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Exception occured in method isBookAvailable.." + e.getStackTrace());
		} finally {
			con.close();
			sttmnt.close();
		}
		return count;
	}

	public String getBrrowerType(String borrowerId) throws SQLException {
		Connection con = null;
		PreparedStatement sttmnt = null;
		String brwrTypLimit = "";
		try {

			String sql = "select borrower_typ, book_limit from library_database.user_details where borrower_id=?";
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareStatement(sql);
			sttmnt.setString(1, borrowerId);
			ResultSet rs = sttmnt.executeQuery();
			while (rs.next()) {
				brwrTypLimit = rs.getString(1) + "~" + rs.getString(2);
			}

		} catch (SQLException e) {
			System.out.println("Exception occured in method getBrrowerType.." + e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Exception occured in method getBrrowerType.." + e.getStackTrace());
		} finally {
			con.close();
			sttmnt.close();
		}

		return brwrTypLimit;
	}

	public boolean saveBorrowedDetails(BookRecordVO bookRecordVO) throws SQLException {
		boolean success = false;
		int update = 0;
		Connection con = null;
		CallableStatement sttmnt = null;
		try {

			String sql = "call library_database.sp_borrowed_books_record(?,?,?,?,?,?,?)";
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareCall(sql);
			sttmnt.setLong(1, bookRecordVO.getBookId());
			sttmnt.setString(2, bookRecordVO.getBorrowerId());
			sttmnt.setTimestamp(3, bookRecordVO.getDtOfIssue());
			sttmnt.setTimestamp(4, bookRecordVO.getDtOfReturn());
			sttmnt.setString(5, "borrowed");
			sttmnt.setString(6, bookRecordVO.getBrwrType());
			sttmnt.setString(7, bookRecordVO.getIssuedBy());
			update = sttmnt.executeUpdate();

			if (update > 0)
				success = true;
			else
				success = false;
		} catch (SQLException e) {
			System.out.println("Exception occured in method isBookAvailable.." + e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Exception occured in method saveBorrowedDetails.." + e.getStackTrace());
			if (con != null)
				con.rollback();
		} finally {
			con.close();
			sttmnt.close();

		}

		return success;

	}

	public String getRecord(BookRecordVO bookRecordVO) throws SQLException {
		Connection con = null;
		PreparedStatement sttmnt = null;
		try {

			String sql = " SELECT record_id FROM library_database.borrowed_books_record where book_id =? and borrower_id=? ";
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareStatement(sql);
			sttmnt.setLong(1, bookRecordVO.getBookId());
			sttmnt.setString(2, bookRecordVO.getBorrowerId());
			ResultSet rs = sttmnt.executeQuery();
			while (rs.next()) {
				bookRecordVO.setLogId(rs.getString(1));
			}

		} catch (Exception e) {
			System.out.println("Exception occured in method getFineCalculation.." + e.getStackTrace());
		} finally {
			con.close();
			sttmnt.close();

		}

		return bookRecordVO.getLogId();

	}

	public BookRecordVO getFineCalculation(BookRecordVO bookRecordVO) throws SQLException {
		Connection con = null;
		CallableStatement sttmnt = null;
		try {

			String sql = " call library_database.sp_calc_fine(?,?,?,?) ";
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareCall(sql);
			sttmnt.setLong(1, bookRecordVO.getBookId());
			sttmnt.setString(2, bookRecordVO.getBorrowerId());
			sttmnt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			sttmnt.setString(4, "returned");
			ResultSet rs = sttmnt.executeQuery();
			while (rs.next()) {
				bookRecordVO.setLogId(rs.getString(1));
				bookRecordVO.setBookId(rs.getLong(2));
				bookRecordVO.setBorrowerId(rs.getString(3));
				bookRecordVO.setDtOfIssue(rs.getTimestamp(4));
				bookRecordVO.setFine(rs.getString(5));
				bookRecordVO.setBookStts(rs.getString(6));
			}

		} catch (Exception e) {
			System.out.println("Exception occured in method getFineCalculation.." + e.getStackTrace());
			if (con != null)
				con.rollback();
		} finally {
			con.close();
			sttmnt.close();

		}

		return bookRecordVO;

	}

	public boolean saveReservedDetails(ReservedBookDetailsVO reDetailsVO) throws SQLException {
		Connection con = null;
		PreparedStatement sttmnt = null;
		boolean success = false;
		try {

			String sql = "call library_database.sp_reserve_details(?,?,?,?)";
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareStatement(sql);
			sttmnt.setInt(1, reDetailsVO.getBookNmId());
			sttmnt.setString(2, reDetailsVO.getReserverId());
			sttmnt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			sttmnt.setDate(4, reDetailsVO.getReservedFor());
			int rs = sttmnt.executeUpdate();
			if (rs > 0)
				success = true;

		} catch (Exception e) {
			System.out.println("Exception occured in method getFineCalculation.." + e.getStackTrace());
			if (con != null)
				con.rollback();
		} finally {
			con.close();
			sttmnt.close();

		}

		return success;
	}

	public boolean saveOrderDetails(BookOrderDetailsVO ordrDetailsVO) throws SQLException {
		Connection con = null;
		CallableStatement sttmnt = null;
		boolean success = false;
		try {

			String sql = "call library_database.sp_order_details(?,?,?,?,?,?,?)";

			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareCall(sql);
			sttmnt.setString(1, ordrDetailsVO.getOrderId());
			sttmnt.setString(2, ordrDetailsVO.getStore());
			sttmnt.setString(3, ordrDetailsVO.getAcquiredTyp());
			sttmnt.setString(4, ordrDetailsVO.getStatus());
			sttmnt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			sttmnt.setInt(6, ordrDetailsVO.getCopies());
			sttmnt.setDate(7, ordrDetailsVO.getToBeReturned());
			int rs = sttmnt.executeUpdate();
			if (rs > 0)
				success = true;

		} catch (Exception e) {
			System.out.println("Exception occured in method saveOrderDetails.." + e.getStackTrace());
		} finally {
			con.close();
			sttmnt.close();

		}

		return success;
	}

	public int checkIfAvailableForBorrower(String borrwerId) throws SQLException {
		Connection con = null;
		PreparedStatement sttmnt = null;
		int bookLimit = 0;
		try {

			String sql = "SELECT book_limit FROM library_database.user_details where borrower_id=?";
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareStatement(sql);
			sttmnt.setString(1, borrwerId);
			ResultSet rs = sttmnt.executeQuery();
			while (rs.next()) {
				bookLimit = Integer.valueOf(rs.getString(1));
			}

		} catch (Exception e) {
			System.out.println("Exception occured in method checkIfAvailableForBorrower.." + e.getStackTrace());
		} finally {
			con.close();
			sttmnt.close();
		}
		return bookLimit;
	}

	public ArrayList<BookDetailsVO> isBookNmAvailable(String bookName, Date reservedFor) throws SQLException {
		Connection con = null;
		PreparedStatement sttmnt = null;
		ArrayList<BookDetailsVO> bookArrayList = new ArrayList<BookDetailsVO>();
		try {
			String sql = "call library_database.sp_reservation_check(?)";
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareStatement(sql);
			sttmnt.setString(1, "%" + bookName + "%");
			ResultSet rs = sttmnt.executeQuery();
			while (rs.next()) {
				BookDetailsVO bookvo = new BookDetailsVO();
				bookvo.setBookNameId(Integer.valueOf(rs.getString(1)));
				bookvo.setBookName(rs.getString(2));
				bookvo.setType(rs.getString(3));
				bookvo.setCount(rs.getInt(4));
				bookArrayList.add(bookvo);
			}

		} catch (Exception e) {
			System.out.println("Exception occured in method isBookAvailable.." + e.getStackTrace());
		} finally {
			con.close();
			sttmnt.close();
		}
		return bookArrayList;
	}

	public ArrayList<BookDetailsVO> isRestrictedBookAvailable(String bookName) throws SQLException {
		Connection con = null;
		PreparedStatement sttmnt = null;
		ArrayList<BookDetailsVO> bookArrayList = new ArrayList<BookDetailsVO>();
		try {
			String sql = "SELECT DISTINCT bm.book_name_id, bm.name, bd.book_type, (SELECT COUNT(*) FROM library_database.book_details bdm, library_database.book_master bmm WHERE bdm.map_id = bd.map_id AND bmm.book_name_id = bm.book_name_id AND bdm.book_type = bd.book_type) AS count FROM library_database.book_details bd, library_database.book_master bm, library_database.author_book_mapping m WHERE bd.book_type <> 'regular' AND bm.name LIKE ? AND m.book_name_id = bm.book_name_id AND bd.map_id = m.map_id AND bm.available <> 0"; 
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareStatement(sql);
			sttmnt.setString(1, "%" + bookName + "%");
			ResultSet rs = sttmnt.executeQuery();
			while (rs.next()) {
				BookDetailsVO bookvo = new BookDetailsVO();
				bookvo.setBookNameId(Integer.valueOf(rs.getString(1)));
				bookvo.setBookName(rs.getString(2));
				bookvo.setType(rs.getString(3));
				bookvo.setCount(rs.getInt(4));
				bookArrayList.add(bookvo);
			}

		} catch (Exception e) {
			System.out.println("Exception occured in method isBookAvailable.." + e.getStackTrace());
		} finally {
			con.close();
			sttmnt.close();
		}
		return bookArrayList;
	}

	public boolean saveReservation(ReservedBookDetailsVO reserveVo) throws SQLException {
		Connection con = null;
		PreparedStatement sttmnt = null;
		boolean success = false;
		try {
			String sql = "call library_database.sp_reserve_details(?,?,?,?,?)";
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareStatement(sql);
			sttmnt.setInt(1, reserveVo.getBookNmId());
			sttmnt.setString(2, reserveVo.getReserverId());
			sttmnt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			sttmnt.setDate(4, reserveVo.getReservedFor());
			sttmnt.setString(5, reserveVo.getBookTyp());
			int rs = sttmnt.executeUpdate();
			if (rs > 0)
				success = true;

		} catch (Exception e) {
			System.out.println("Exception occured in method isBookAvailable.." + e.getStackTrace());
		} finally {
			con.close();
			sttmnt.close();
		}
		return success;
	}

	public ArrayList<ReservedBookDetailsVO> getReservedBookDtls(Long bookId) throws SQLException {
		Connection con = null;
		PreparedStatement sttmnt = null;
		ArrayList<ReservedBookDetailsVO> reserveDtls = new ArrayList<ReservedBookDetailsVO>();
		try {
			String sql = "SELECT rd.request_id, bm.name, rd.reservedfor FROM library_database.reserve_details rd, library_database.book_master bm WHERE rd.book_name_id = bm.book_name_id and rd.book_name_id = (SELECT bm.book_name_id FROM library_database.book_details bd, library_database.author_book_mapping bm WHERE bd.map_id = bm.map_id and bd.book_id = ?);";
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareStatement(sql);
			sttmnt.setLong(1, bookId);
			ResultSet rs = sttmnt.executeQuery();
			while (rs.next()) {
				ReservedBookDetailsVO reservevo = new ReservedBookDetailsVO();
				reservevo.setReqId(rs.getInt(1));
				reservevo.setBookNm(rs.getString(2));
				reservevo.setReservedFor(rs.getDate(3));
				reserveDtls.add(reservevo);
			}

		} catch (Exception e) {
			System.out.println("Exception occured in method isBookAvailable.." + e.getStackTrace());
		} finally {
			con.close();
			sttmnt.close();
		}
		return reserveDtls;

	}

	public ArrayList<ReservedBookDetailsVO> reservationDetails(String bookName, String borrowerId) throws SQLException {
		Connection con = null;
		PreparedStatement sttmnt = null;
		String sql = "";
		ArrayList<ReservedBookDetailsVO> reserveDtls = new ArrayList<ReservedBookDetailsVO>();
		try {
		 sql = "SELECT rd.request_id, bm.name, rd.reservedfor FROM library_database.reserve_details rd, "
					+ "library_database.book_master bm WHERE rd.reserver_id = ? "
					+ "AND rd.bk_nm_id = bm.book_name_id AND bm.name LIKE ?";
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareStatement(sql);
			sttmnt.setString(1, borrowerId);
			sttmnt.setString(2, bookName);

			ResultSet rs = sttmnt.executeQuery();
			while (rs.next()) {
				ReservedBookDetailsVO reservevo = new ReservedBookDetailsVO();
				reservevo.setReqId(rs.getInt(1));
				reservevo.setBookNm(rs.getString(2));
				reservevo.setReservedFor(rs.getDate(3));
				reserveDtls.add(reservevo);
			}

		} catch (Exception e) {
			System.out.println("Exception occured in method isBookAvailable.." + e.getStackTrace());
		} finally {
			con.close();
			sttmnt.close();
		}
		return reserveDtls;

	}

	public boolean updateReservation(ReservedBookDetailsVO resVo, String action) throws SQLException {
		Connection con = null;
		PreparedStatement sttmnt = null;
		boolean success = false;
		int rs = 0;
		try {
			String sql1 = "update library_database.reserve_details set  reservedfor = ?  where request_id = ?";
			String sql2 = "delete FROM library_database.reserve_details where request_id =?;";
			con = DataSourceFactory.getConnection();
			if (action.equals("update")) {
				sttmnt = con.prepareStatement(sql1);
				sttmnt.setDate(1, resVo.getReservedFor());
				sttmnt.setInt(2, resVo.getReqId());
				rs = sttmnt.executeUpdate();
			} else {
				sttmnt = con.prepareStatement(sql2);
				sttmnt.setInt(1, resVo.getReqId());
				rs = sttmnt.executeUpdate();
			}
			if (rs > 0)
				success = true;

		} catch (Exception e) {
			System.out.println("Exception occured in method isBookAvailable.." + e.getStackTrace());
		} finally {
			con.close();
			sttmnt.close();
		}
		return success;

	}

	public boolean multipleReservation( ReservedBookDetailsVO reserveVo) throws
	  SQLException { 
		Connection con = null;
		CallableStatement sttmnt = null;
		boolean success = false;
		int rs = 0;
		try {
			String sql = "call library_database.sp_multiple_reservation(?,?,?,?,?,?)";
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareCall(sql);
			sttmnt.setInt(1, reserveVo.getBookNmId());
			sttmnt.setString(2, reserveVo.getReserverId());
			sttmnt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			sttmnt.setDate(4, reserveVo.getReservedFor());
			sttmnt.setString(5, reserveVo.getBookTyp());
			sttmnt.setDate(6, reserveVo.getReservedTill());
			rs = sttmnt.executeUpdate();
			if (rs > 0)
				success = true;
		} catch (Exception e) {
			System.out.println("Exception occured in method isBookAvailable.." + e.getStackTrace());
		} finally {
			con.close();
			sttmnt.close();
		}
		return success;
	}
	
}
