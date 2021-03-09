package com.system.management;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.system.bean.BookDetailsVO;
import com.system.bean.BookRecordVO;
import com.system.bean.ReservedBookDetailsVO;

public class ManagementService {
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	public BookRecordVO borrowerRecordEntry(BookRecordVO bookRecordVO) {
		System.out.println("Inside borrowerDetailsEntry..");
		String borrowStts = "";
		try {
			BookDao bookDao = new BookDao();
			String brrowerTypeLimit = bookDao.getBrrowerType(bookRecordVO.getBorrowerId());
			String[] arry = brrowerTypeLimit.split("~");
			bookRecordVO.setBrwrType(arry[0]);
			bookRecordVO.setBookLimit(Integer.parseInt(arry[1]));
			if ((bookRecordVO.getBrwrType().equals("S") && bookRecordVO.getBookLimit() >= 4)
					|| (bookRecordVO.getBrwrType().equals("F") && bookRecordVO.getBookLimit() >= 6)) {
				bookRecordVO.setMessage("Book limit has crossed");
				return bookRecordVO;
			}

			boolean sts = bookDao.saveBorrowedDetails(bookRecordVO);
			if (sts) {
				bookRecordVO.setMessage("Entry successfully done");
			} else
				bookRecordVO.setMessage("Entry failed");
			}
		 catch (Exception e) {
			System.out.println("Exception occured in method borrowerDetailsEntry.." + e.getStackTrace());
		}

		return bookRecordVO;

	}

	public String checkIfAvailableForBorrower(ReservedBookDetailsVO reserveDtlsVo) {
		System.out.println("Inside checkIfAvailableForBorrower..");
		String borrowStts = "success";
		try {
			BookDao bookDao = new BookDao();
			Date dt1 = new Date();
		
			Date dt2 = reserveDtlsVo.getReservedFor();
			long diff = dt2.getTime() - dt1.getTime();
			int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
			if (diffDays > 30|| diffDays < 0) {
				return "Cannot reserve a book before more than 1 month ";
			} else {
				int bookLimit = bookDao.checkIfAvailableForBorrower(reserveDtlsVo.getReserverId());
				if (bookLimit == 4) {
					return "Book limit has crossed ";
				}
			}
			
			

		} catch (Exception e) {
			// TODO: handle exception
		}
		return borrowStts;
	}

}
