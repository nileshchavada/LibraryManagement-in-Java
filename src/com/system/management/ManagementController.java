package com.system.management;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.system.bean.AdminDetails;
import com.system.bean.BookDetailsVO;
import com.system.bean.BookOrderDetailsVO;
import com.system.bean.BookRecordVO;
import com.system.bean.ReservedBookDetailsVO;

public class ManagementController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("inside doGet...");
		String action = request.getServletPath();
		try {
			switch (action) {
			case "/login":
				login(request, response);
				break;
			case "/borrow":
				issueBook(request, response);
				break;
			case "/return":
				returnBook(request, response);
				break;
			case "/reserve":
				reserveBook(request, response);
				break;
			case "/order":
				orderDetails(request, response);
				break;
			case "/confirmReserve":
				confirmReservation(request, response);
				break;
			case "/updateReserve":
				updateReservation(request, response);
				break;
			case "/multipleReserve":
				multipleReservation(request, response);
				break;
			default:
				findBook(request, response);
				break;
			}
		} catch (ServletException e) {
			System.out.println("Exception occured in method.." + e.getStackTrace());
		} catch (IOException e) {
			System.out.println("Exception occured in method." + e.getStackTrace());
		} catch (SQLException e) {
			System.out.println("Exception occured in method." + e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Exception occured in method.." + e.getStackTrace());
		}
	}

	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDao userDao = new UserDao();
		String id = request.getParameter("userId").toUpperCase();
		String password = request.getParameter("pswd");
		AdminDetails user = userDao.validateUserLogin(id, password);
		String destPage = "loginPage.jsp";

		if (user.getAdminId() != null) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getAdminId());
			destPage = "homePage.jsp";
		} else {
			String message = "Invalid id/password";
			request.setAttribute("message", message);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		dispatcher.forward(request, response);
	}

	private void findBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		System.out.println("Inside method findBook..");
		BookDetailsVO bookdtlsVo = new BookDetailsVO();
		String selectOptn = (String) request.getParameter("select");
		if (selectOptn.equalsIgnoreCase("book"))
			bookdtlsVo.setBookName((String) request.getParameter("aName"));
		else
			bookdtlsVo.setAuthrName((String) request.getParameter("aName"));
		BookDao bookDao = new BookDao();
		ArrayList<BookDetailsVO> bookArrayList = bookDao.checkAvailability(bookdtlsVo);
		if (!bookArrayList.isEmpty())
			request.setAttribute("bookArrayList", bookArrayList);
		else
			request.setAttribute("noRecord", "No Books Found ");
		RequestDispatcher rd = request.getRequestDispatcher("homePage.jsp");
		rd.forward(request, response);

	}

	private void issueBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException, ParseException {
		BookRecordVO bookRecordVO = new BookRecordVO();
		bookRecordVO.setBookId(Long.valueOf(request.getParameter("bookId")));
		bookRecordVO.setBorrowerId(request.getParameter("borrowerId"));
		Timestamp issueDate = new Timestamp ((dateFormat.parse(request.getParameter("issueDate"))).getTime());
		bookRecordVO.setDtOfIssue(issueDate);
		Timestamp returnDate = new Timestamp ((dateFormat.parse(request.getParameter("returnDate"))).getTime());
		bookRecordVO.setDtOfReturn(returnDate);
		if (request.getParameter("typeCheck") != null) {
			bookRecordVO.setTyp("Restricted");
		} else {
			bookRecordVO.setTyp("Regular");
		}

		bookRecordVO.setIssuedBy(request.getParameter("userId"));
		ManagementService mService = new ManagementService();
		BookDao bookDao = new BookDao();
		int availability = bookDao.isBookAvailable(bookRecordVO.getBookId());
		if (availability == 0) {
			ArrayList<ReservedBookDetailsVO> reserveDtlsList = bookDao.getReservedBookDtls(bookRecordVO.getBookId());
			bookRecordVO.setMessage("Book is not available");
			request.setAttribute("reserveDtlsList", reserveDtlsList);
		} else {
			bookRecordVO = mService.borrowerRecordEntry(bookRecordVO);
		}

		request.setAttribute("bookRecordVO", bookRecordVO);
		RequestDispatcher rd = request.getRequestDispatcher("homePage.jsp");
		rd.forward(request, response);

	}

	private void returnBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		BookRecordVO bookRecordVO = new BookRecordVO();
		BookDao bookDao = new BookDao();
		bookRecordVO.setBookId(Long.valueOf(request.getParameter("bookId")));
		bookRecordVO.setBorrowerId(request.getParameter("borrowerId"));
		String recordId = bookDao.getRecord(bookRecordVO);
		if (recordId != null) {
			bookRecordVO = bookDao.getFineCalculation(bookRecordVO);
			request.setAttribute("bookRecordVO", bookRecordVO);
		} else
			request.setAttribute("returnMessage", "No Borrowed record");

		RequestDispatcher rd = request.getRequestDispatcher("homePage.jsp");
		rd.forward(request, response);

	}

	private void reserveBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		ReservedBookDetailsVO reserveVo = new ReservedBookDetailsVO();
		ManagementService mService = new ManagementService();
		BookDao bookDao = new BookDao();
		String success = "";
		reserveVo.setBookNm(request.getParameter("bookNm"));
		reserveVo.setReserverId(request.getParameter("borrowerId"));
		String action = request.getParameter("reservTyp");
		ArrayList<ReservedBookDetailsVO> reserveDtlsList = null;
		String reqId = request.getParameter("cancelReqId");
		if (action.equals("alter")) {
			reserveDtlsList = bookDao.reservationDetails(reserveVo.getBookNm(), reserveVo.getReserverId());
			request.setAttribute("reserveDtlsList", reserveDtlsList);
		} else {
			String isPresent ="";
			ArrayList<BookDetailsVO> restrictedbookArrayList = null;
			ArrayList<BookDetailsVO> bookAvailList = null;
			reserveVo.setReservedFor(Date.valueOf(request.getParameter("reserveDate")));
			if(action.equals("multiple"))  {
				reserveVo.setReservedTill(Date.valueOf(request.getParameter("reserveTill")));
				restrictedbookArrayList = bookDao.isRestrictedBookAvailable(reserveVo.getBookNm());
				if (restrictedbookArrayList.isEmpty()) {
					success = "Book is not available";
				} else {
					request.setAttribute("restrictedbookArrayList", restrictedbookArrayList);
					success = "";
					request.setAttribute("multiple", "multiple");
				}
			}else {
			 isPresent = mService.checkIfAvailableForBorrower(reserveVo);
			
			if (isPresent.equals("success")) {
				bookAvailList = bookDao.isBookNmAvailable(reserveVo.getBookNm(), reserveVo.getReservedFor());
				if (bookAvailList.isEmpty()) {
					success = "Book is not available";
				} else {
					request.setAttribute("bookAvailList", bookAvailList);
					success = "";
				}
			} else
				success = isPresent;
			}
		}
		request.setAttribute("reserveMessage", success);
		request.setAttribute("reserveVo", reserveVo);
		RequestDispatcher rd = request.getRequestDispatcher("homePage.jsp");
		rd.forward(request, response);

	}

	private void confirmReservation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		ReservedBookDetailsVO reserveVo = new ReservedBookDetailsVO();
		BookDao bookDao = new BookDao();
		String msg = "";
		reserveVo.setBookNmId(Integer.valueOf(request.getParameter("bookHdnNmId")));
		reserveVo.setReserverId(request.getParameter("brwrHdnId"));
		reserveVo.setBookTyp(request.getParameter("bookTyp"));
		reserveVo.setReservedFor(Date.valueOf(request.getParameter("rsrvHdnFor")));
		boolean success = bookDao.saveReservation(reserveVo);
		if (success)
			msg = "reservation success";
		else
			msg = "rservation failed";

		request.setAttribute("reserveMessage", msg);
		request.setAttribute("reserveVo", reserveVo);
		RequestDispatcher rd = request.getRequestDispatcher("homePage.jsp");
		rd.forward(request, response);

	}

	private void updateReservation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		ReservedBookDetailsVO reserveVo = new ReservedBookDetailsVO();
		String action = request.getParameter("modifyTyp");
		ReservedBookDetailsVO reserve = new ReservedBookDetailsVO();
		reserve.setReqId(Integer.valueOf(request.getParameter("cancelReqId")));
		reserve.setReservedFor(Date.valueOf(request.getParameter("modifiedDt")));
		boolean success = false;
		BookDao bookDao = new BookDao();
		success = bookDao.updateReservation(reserve, action);

		request.setAttribute("reserveMessage", "succesfully updated");
		RequestDispatcher rd = request.getRequestDispatcher("homePage.jsp");
		rd.forward(request, response);

	}

	private void orderDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		BookOrderDetailsVO ordrDetailsVO = new BookOrderDetailsVO();
		ordrDetailsVO.setAcquiredTyp(request.getParameter("acqTyp"));
		ordrDetailsVO.setOrderId(request.getParameter("ordrId"));
		ordrDetailsVO.setCopies(Integer.valueOf(request.getParameter("copies")));
		ordrDetailsVO.setStore(request.getParameter("storeNm"));
		if (!request.getParameter("ordrDt").isEmpty()) {
			ordrDetailsVO.setToBeReturned(Date.valueOf(request.getParameter("ordrDt")));
			ordrDetailsVO.setStatus("borrowed");
		} else
			ordrDetailsVO.setStatus("acquired");
		BookDao bookDao = new BookDao();
		boolean success = bookDao.saveOrderDetails(ordrDetailsVO);
		if (success)
			request.setAttribute("orderMessage", "Successfully updated");

		RequestDispatcher rd = request.getRequestDispatcher("homePage.jsp");
		rd.forward(request, response);

	}

	private void multipleReservation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		BookDao bookDao = new BookDao();
		ReservedBookDetailsVO reserveVo = new ReservedBookDetailsVO();
		reserveVo.setReservedFor(Date.valueOf(request.getParameter("dateId")));
		reserveVo.setReservedTill(Date.valueOf(request.getParameter("tillId")));
		reserveVo.setReserverId(request.getParameter("brwrId"));
		reserveVo.setBookNmId(Integer.valueOf(request.getParameter("bookNameId")));
		boolean success = bookDao.multipleReservation(reserveVo);
		request.setAttribute("reserveMessage", "Successfully done");

		RequestDispatcher rd = request.getRequestDispatcher("homePage.jsp");
		rd.forward(request, response);

	}

}
