package com.system.management;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

@MultipartConfig
public class EntryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ManagementController.class.getName());

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inside method doPost..");
		int rows = 0;
		String entryMessage = "";
		try {
		String borrowerType = request.getParameter("select");
		Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		File file = new File(fileName);
		String path = file.getAbsolutePath();
		EntryDetailsDao entrydao = new EntryDetailsDao();
		if(!borrowerType.equals("book")) {
			rows = entrydao.borrowerDetailsEntry(path,borrowerType);
			System.out.println("Entry made to borrower details table sccuessfully:"+ rows+ "rows");
			entryMessage = "Borrower details updated Successfully ";
		}else {
			int ordrId = Integer.valueOf(request.getParameter("entryOrdrId"));
			boolean isPresent = entrydao.checkIfOrdrIdPresent(ordrId);
			if(!isPresent)
				entryMessage =  "Order ID is not present in the system";
			rows = entrydao.bookDetailsEntryFirst(path);
			System.out.println("Entry made to book master and author details table sccuessfully:"+ rows+ "rows");
			rows = entrydao.bookDetailsEntrysecond(path,ordrId);
			System.out.println("Entry made to book details table sccuessfully: "+ rows+ "rows");
			entryMessage = "Book details updated Successfully ";
		}
		request.setAttribute("entryMessage", entryMessage);
		RequestDispatcher rd = request.getRequestDispatcher("homePage.jsp");
		rd.forward(request, response);
		
	} catch (ServletException e) {
		System.out.println("Exception occured in method doPost.." + e.getStackTrace());
	} catch (IOException e) {
		System.out.println("Exception occured in method doPost.." + e.getStackTrace());
	}catch (SQLException e) {
		System.out.println("Exception occured in method doPost.." + e.getStackTrace());
	}catch (Exception e) {
		System.out.println("Exception occured in method doPost.." + e.getStackTrace());
	}
		
}
}
