package com.system.management;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EntryDetailsDao {

	public int borrowerDetailsEntry(String filePath, String borrowerTyp) throws SQLException, ParseException {
		Connection con = null;
		CallableStatement statement = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

		int rows = 0;
		try {
			FileInputStream inputStream = new FileInputStream(filePath);

			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();

			con = DataSourceFactory.getConnection();
			con.setAutoCommit(false);
			String sql = "call library_database.sp_borrower_insert(?,?,?,?,?,?,?,?)";
			statement = con.prepareCall(sql);

			rowIterator.next(); // skip the header row
			while (rowIterator.hasNext()) {
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();

					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {
					case 0:
						Long studentID = (long) nextCell.getNumericCellValue();
						statement.setLong(1, studentID);
						break;

					case 1:
						String firstName = nextCell.getStringCellValue();
						statement.setString(2, firstName);
						break;
					case 2:
						String lastName = nextCell.getStringCellValue();
						statement.setString(3, lastName);
						break;
					case 3:
						Date parsedDate = sdf.parse(nextCell.getDateCellValue().toString());
						statement.setString(4, formatter.format(parsedDate));
						break;
					case 4:
						String dprtmnt = nextCell.getStringCellValue();
						statement.setString(5, dprtmnt);
						break;
					case 5:
						Long contact = (long) nextCell.getNumericCellValue();
						statement.setLong(6, contact);
						break;
					case 6:
						String email = nextCell.getStringCellValue();
						statement.setString(7, email);
						break;
					}

				}
				if (borrowerTyp.equals("student"))
					statement.setString(8, "S");
				else
					statement.setString(8, "F");
				statement.addBatch();
			}

			workbook.close();

			// execute the remaining queries
			rows = statement.executeBatch().length;

			con.commit();
			con.setAutoCommit(true);
			con.close();


		} catch (IOException ex1) {
			System.out.println("Error reading file in student entry");
			ex1.printStackTrace();
		} catch (SQLException ex2) {
			System.out.println("Database error in student entry");
			ex2.printStackTrace();
		}
		return rows;

	}

	public int facultyDetailsEntry(String filePath) throws SQLException {
		Connection con = null;
		int rows = 0;
		try {
			FileInputStream inputStream = new FileInputStream(filePath);

			Workbook workbook = new XSSFWorkbook(inputStream);

			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();
			con = DataSourceFactory.getConnection();
			con.setAutoCommit(false);

			String sql = "insert into library_database.book_temp (book_name, copies, authr_fname, authr_lname, edition ,publication, lang, department, typ) values(?,?,?,?,?,?,?,?,?)";

			PreparedStatement statement = con.prepareStatement(sql);

			rowIterator.next(); // skip the header row

			while (rowIterator.hasNext()) {
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();

					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {
					case 0:
						String name = nextCell.getStringCellValue();
						statement.setString(1, name);
						break;
					case 1:
						double copies = nextCell.getNumericCellValue();
						statement.setDouble(2, copies);
						break;
					/*
					 * case 2: String firstName = nextCell.getStringCellValue();
					 * statement.setString(3, firstName); break; case 3: String lastName =
					 * nextCell.getStringCellValue(); statement.setString(4, lastName); break; case
					 * 4: double edition = nextCell.getNumericCellValue(); statement.setDouble(5,
					 * edition); break; case 5: String publication = nextCell.getStringCellValue();
					 * statement.setString(6, publication); break; case 6: String language =
					 * nextCell.getStringCellValue(); statement.setString(7, language); break; case
					 * 7: String department = nextCell.getStringCellValue(); statement.setString(8,
					 * department); break; case 8: String type = nextCell.getStringCellValue();
					 * statement.setString(9, type); break;
					 * 
					 */
					}

				}

				statement.addBatch();
			}

			workbook.close();

			// execute the remaining queries
			rows = statement.executeBatch().length;

			con.commit();
			con.setAutoCommit(true);
			con.close();


		} catch (IOException ex1) {
			System.out.println("Error reading file");
			ex1.printStackTrace();
		} catch (SQLException ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}
		return rows;

	}

	public int bookDetailsEntryFirst(String filePath) throws SQLException {
		Connection con = null;
		int rows = 0;
		try {
			FileInputStream inputStream = new FileInputStream(filePath);

			Workbook workbook = new XSSFWorkbook(inputStream);

			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();
			con = DataSourceFactory.getConnection();
			con.setAutoCommit(false);

			String sql = "call sp_book_insert (?,?,?,?,?,?,?,?,?)";

			CallableStatement statement = con.prepareCall(sql);

			rowIterator.next(); // skip the header row

			while (rowIterator.hasNext()) {
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();

					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {
					case 0:
						String name = nextCell.getStringCellValue();
						statement.setString(1, name);
						break;
					case 1:
						double copies = nextCell.getNumericCellValue();
						statement.setDouble(2, copies);
						break;

					case 2:
						String firstName = nextCell.getStringCellValue();
						statement.setString(3, firstName);
						break;
					case 3:
						String lastName = nextCell.getStringCellValue();
						statement.setString(4, lastName);
						break;

					case 4:
						double edition = nextCell.getNumericCellValue();
						statement.setDouble(5, edition);
						break;
					case 5:
						String publication = nextCell.getStringCellValue();
						statement.setString(6, publication);
						break;
					case 6:
						String language = nextCell.getStringCellValue();
						statement.setString(7, language);
						break;
					case 7:
						String department = nextCell.getStringCellValue();
						statement.setString(8, department);
						break;
					case 8:
						String type = nextCell.getStringCellValue();
						statement.setString(9, type);
						break;

					}

				}

				statement.addBatch();
			}

			workbook.close();

			// execute the remaining queries
			rows = statement.executeBatch().length;

			con.commit();
			con.setAutoCommit(true);
			con.close();


		} catch (IOException ex1) {
			System.out.println("Error reading file");
			ex1.printStackTrace();
		} catch (SQLException ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}
		return rows;

	}
	public int bookDetailsEntrysecond(String filePath, int ordrId) throws SQLException {
		Connection con = null;
		int rows = 0;
		try {
			FileInputStream inputStream = new FileInputStream(filePath);

			Workbook workbook = new XSSFWorkbook(inputStream);

			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = firstSheet.iterator();
			con = DataSourceFactory.getConnection();
			con.setAutoCommit(false);

			String sql = "call sp_book_details (?,?,?,?,?,?,?,?,?,?)";

			CallableStatement statement = con.prepareCall(sql);

			rowIterator.next(); // skip the header row

			while (rowIterator.hasNext()) {
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();

					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {
					case 0:
						String name = nextCell.getStringCellValue();
						statement.setString(1, name);
						break;
					case 1:
						double copies = nextCell.getNumericCellValue();
						statement.setDouble(2, copies);
						break;

					case 2:
						String firstName = nextCell.getStringCellValue();
						statement.setString(3, firstName);
						break;
					case 3:
						String lastName = nextCell.getStringCellValue();
						statement.setString(4, lastName);
						break;

					case 4:
						double edition = nextCell.getNumericCellValue();
						statement.setDouble(5, edition);
						break;
					case 5:
						String publication = nextCell.getStringCellValue();
						statement.setString(6, publication);
						break;
					case 6:
						String language = nextCell.getStringCellValue();
						statement.setString(7, language);
						break;
					case 7:
						String department = nextCell.getStringCellValue();
						statement.setString(8, department);
						break;
					case 8:
						String type = nextCell.getStringCellValue();
						statement.setString(9, type);
						break;
					

					}

				}
				statement.setInt(10, ordrId);
				statement.addBatch();
			}

			workbook.close();

			// execute the remaining queries
			rows = statement.executeBatch().length;

			con.commit();
			con.setAutoCommit(true);
			con.close();


		} catch (IOException ex1) {
			System.out.println("Error reading file");
			ex1.printStackTrace();
		} catch (SQLException ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}catch (Exception ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}
		return rows;

	}
	public boolean checkIfOrdrIdPresent(int ordrId) throws SQLException {
		Connection con = null;
		PreparedStatement sttmnt = null;
		boolean success = false;
		try {

			String sql = "SELECT count(*) FROM library_database.order_details where order_id= ? ";
					
			con = DataSourceFactory.getConnection();
			sttmnt = con.prepareStatement(sql);
			sttmnt.setInt(1, ordrId);
			ResultSet rs  = sttmnt.executeQuery();
			if(rs.next()) {
				int count = Integer.valueOf(rs.getString(1));
				if(count>0)
					success = true;
			}
			
		}catch (Exception e) {
			System.out.println("Exception occured in method saveOrderDetails.." + e.getStackTrace());
			if(con!=null)
	            con.rollback();
		}  finally {
			con.close();
			sttmnt.close();

		}

		return success;

	}
	
}
