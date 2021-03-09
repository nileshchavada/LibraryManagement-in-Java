<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.system.bean.BookDetailsVO"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Library Management</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.26.0/moment.min.js"></script>
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
<link href="style.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div class="container">
		<h3 class='text-center text-primary bg-gradient-warning'
			style="text-shadow: 2px 2px white;">LIBRARY MANAGEMENT SYSTEM</h3>
		<div class="text-right">
			<a href="/LibraryManagement/loginPage.jsp"
				style="text-align: right; color: red;">logout</a>
		</div>


		<ul class="nav nav-tabs" id="myTab">
			<li class="active"><a href="#search" data-toggle="tab">SEARCH</a></li>
			<li><a href="#borrow" data-toggle="tab">BORROW</a></li>
			<li><a href="#return" data-toggle="tab">RETURN</a></li>
			<li><a href="#reserve" data-toggle="tab">RESERVE</a></li>
			<li><a href="#dataEntry" data-toggle="tab">ENTRY</a></li>
			<li><a href="#order" data-toggle="tab">ORDER</a></li>

		</ul>


		<div class="tab-content" id="tabs">

			<!-- start : div for search tab -->
			<div class="tab-pane active" id="search">
				<br>
				<p class="text-primary">Search by Book name or Author Name</p>
				<form role="form" action="search">

					<c:if test="${not empty searchMessage}">
						<div class="alert alert-error" id="errorId">
							<i class="fa fa-info-circle">&nbsp;</i>${searchMessage}</div>
					</c:if>
					<div class="row">
						<div class="form-group col-lg-5">
							<label class="text-primary" for="searchBy">Search by: </label> <select
								id="searchBy" name="select">
								<option selected value="book">Book name</option>
								<option value="author">Author name</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-lg-5 ">
							<input type="text" class="form-control input-sm" id="aName"
								placeholder="Enter Name" name="aName">
						</div>
					</div>

					<button type="submit" class="btn btn-primary btn-xs"
						onClick='activateTab("search")'>Check For Availability</button>
				</form>
				<input type="hidden" id="userId" name="userId" value="${userId}">

				<c:if test="${not empty noRecord}">
					<div class="alert alert-error" id="errorId">
						<i class="fa fa-info-circle">&nbsp;</i>${noRecord}</div>
				</c:if>

				<c:if test="${not empty bookArrayList}">
					<div class="table-container">
						<h4 class="text-primary">Book Availability</h4>
						<br>
						<table class="table table-striped ">
							<thead>
								<tr>
									<th class="col-md-2">Book ID</th>
									<th class="col-md-2">Book Name</th>
									<th class="col-md-2">Author Name</th>
									<th class="col-md-2">Edition</th>
									<th class="col-md-2">Publication</th>
									<th class="col-md-2">Language</th>
									<th class="col-md-2">department</th>
									<th class="col-md-2">Type</th>
									<th class="col-md-2">Status</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="book" items="${bookArrayList}">
									<tr>
										<td>${book.bookId}</td>
										<td>${book.bookName}</td>
										<td>${book.authrName}</td>
										<td>${book.edition}</td>
										<td>${book.publication}</td>
										<td>${book.language}</td>
										<td>${book.department}</td>
										<td>${book.type}</td>
										<td>${book.status}</td>
									</tr>

								</c:forEach>
							</tbody>
						</table>

					</div>
				</c:if>
			</div>



			<!-- start : div for borrow tab -->


			<div class="tab-pane" id="borrow">
				<br>
				<form role="form" action="borrow">
					<div class="row">
						<div class="form-group col-lg-5">
							<p class="text-primary">Enter borrower details</p>

							<c:if test="${not empty bookRecordVO.message}">
								<div class="alert alert-error" id="errorId">
									<i class="fa fa-info-circle">&nbsp;</i>${bookRecordVO.message}</div>
							</c:if>

							<input type="text" class="form-control input-sm" id="bookId"
								placeholder="Enter book Id" name="bookId"> <br> <input
								type="text" class="form-control input-sm" id="borrowerId"
								placeholder="Enter borrower Id" name="borrowerId"> <br>
							<input type="date" class="date_picker" id="issueDate"
								name="issueDate"> <input type="date" class="date_picker"
								id="returnDate" name="returnDate"> <br> <br> <input
								type="checkbox" id="restricted" name="typeCheck"
								style="width: auto"> <label class="text-primary">Restricted
								Book</label>
						</div>
					</div>
					<input type="hidden" id="userId" name="userId" value="${userId}">
					<button type="submit" class="btn btn-primary btn-xs"
						onClick='activateTab("borrow")'>Issue Book</button>
				</form>


				<c:if test="${not empty reserveDtlsList}">
					<div class="table-container">
						<h4 class="text-primary">Book Availability</h4>
						<br>
						<table class="table table-striped ">
							<thead>
								<tr>
									<th class="col-md-2">Request ID</th>
									<th class="col-md-2">Book Name</th>
									<th class="col-md-2">Reserved for</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="reserve" items="${reserveDtlsList}">
									<tr>
										<td>${reserve.reqId}</td>
										<td>${reserve.bookNm}</td>
										<td>${reserve.reservedFor}</td>
									</tr>

								</c:forEach>
							</tbody>
						</table>

					</div>
				</c:if>


			</div>








			<!-- start : div for return  tab -->

			<div class="tab-pane" id="return">
				<br>
				<form role="form" action="return">
					<div class="row">
						<div class="form-group col-lg-5">
							<p class="text-primary">Enter return details</p>
							<input type="text" class="form-control input-sm" id="bookId"
								placeholder="Enter book Id" name="bookId"> <br> <input
								type="text" class="form-control input-sm" id="borrowerId"
								placeholder="Enter borrower Id" name="borrowerId"> <br>
							<input type="date" class="date_picker" id="actualReturnDate"
								name="returnDate">
						</div>
					</div>

					<button type="submit" class="btn btn-primary btn-xs"
						onClick='activateTab("return")'>Return Book</button>

					<input type="hidden" id="userId" name="userId" value="${userId}">

				</form>

				<c:if test="${not empty returnMessage}">
					<div class="alert alert-error" id="errorId">
						<i class="fa fa-info-circle">&nbsp;</i>${returnMessage}</div>
				</c:if>

				<div>
					<c:if test="${not empty bookRecordVO.logId}">
						<table class="table table-striped ">
							<thead>
								<tr>
									<th class="col-md-2">Record ID</th>
									<th class="col-md-2">Book ID</th>
									<th class="col-md-2">Borrower ID</th>
									<th class="col-md-2">Date Of Issue</th>
									<th class="col-md-2">Fine</th>
									<th class="col-md-2">Book Status</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${bookRecordVO.logId}</td>
									<td>${bookRecordVO.bookId}</td>
									<td>${bookRecordVO.borrowerId}</td>
									<td>${bookRecordVO.dtOfIssue}</td>
									<td>${bookRecordVO.fine}</td>
									<td>${bookRecordVO.bookStts}</td>
								</tr>

							</tbody>
						</table>
					</c:if>

					<input type="hidden" name="recordID" value="${bookRecordVO.logId}">
					<input type="hidden" name="bookId" value="${bookRecordVO.bookId}">



				</div>


			</div>

			<!-- start : div for search tab -->

			<div class="tab-pane" id="reserve">
				<br>
				<form role="form" action="reserve">
					<div class="row">
						<div class="form-group col-lg-5">
							<p class="text-primary">Enter reservation details</p>

							<input type="text" class="form-control input-sm" id="borrowerId"
								placeholder="Enter borrower Id" name="borrowerId"> <br>

							<input type="text" class="form-control input-sm" id="bookNm"
								placeholder="Enter book Name" name=bookNm> <br>
								 <p class="text-primary">Reservation for</p>

							<input type="date" class="date_picker" id="reserveDate"
								value="${reserveVo.reservedFor}" name="reserveDate"> <input
								type="date" class="date_picker" id="reserveTill"
								name="reserveTill" value="${reserveVo.reservedTill}"
								style="display: none">


							<div>

								<br>
								<button type="submit" class="btn btn-link btn-xs"
									style="display: none" id="confirmId"
									onclick="activateTab('reserve');reserveBook('multiple');">Confirm / </button>
								<button type="button" class="btn btn-link btn-xs"
									style="display: none" id="cancelId"
									onclick="cancel();">Cancel</button>

						<button type="button" id="reserveId" class="btn btn-link btn-xs"
									onclick="multipleReserve();">Multiple Reservation /</button>
								<button type="submit" id="reserveNewId"
									class="btn btn-link btn-xs"
									onclick="activateTab('reserve');reserveBook('new');">New /
									</button>

								<button type="submit" id="reserveAlterId"
									class="btn btn-link btn-xs"
									onclick="activateTab('reserve');reserveBook('alter');">Alter
									Reservation</button>
							</div>
							</div>
							<input type="hidden" id="reservTyp" name="reservTyp">
							<input type="hidden" id="multiple" name="multiple" value="${multiple}">
						</div>
					
				</form>

		<form action="multipleReserve">
					
					<c:if test="${not empty restrictedbookArrayList}">
					<p class="text-primary">Select for multiple reservation </p>
						<div class="table-container">
							<br>
							<table class="table table-striped ">
								<thead>
									<tr>
										<th class="col-md-2">Book Master ID</th>
										<th class="col-md-2">Book Name</th>
										<th class="col-md-5">type</th>
										<th class="col-md-5">Available</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="book" items="${restrictedbookArrayList}">
										<tr>
											<td>${book.bookNameId}</td>
											<td>${book.bookName}</td>
											<td>${book.type}</td>
											<td>${book.count}</td>
												<td><button type="submit" class="btn btn-link"
													onclick="activateTab('reserve');multipleReserveConfirm('${book.bookNameId}')">confirm </button>
											</td>
										</tr>

									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
					<input type="hidden" id="brwrId" name="brwrId" value="${reserveVo.reserverId}">
					<input type="hidden" id="dateId" name="dateId" value="${reserveVo.reservedFor}">
					<input type="hidden" id="tillId" name="tillId" value="${reserveVo.reservedTill}">
					<input type="hidden" id="bookNameId" name="bookNameId" >

				</form>




				<form action="updateReserve">
					<c:if test="${not empty reserveMessage}">
						<div class="alert alert-error" id="errorId">
							<i class="fa fa-info-circle">&nbsp;</i>${reserveMessage}</div>
					</c:if>
					<c:if test="${not empty reserveDtlsList}">
					<p class="text-primary">Alter Reservation</p>
						<div class="table-container">
							<br>
							<table class="table table-striped ">
								<thead> 
									<tr>
										<th class="col-md-2">Request ID</th>
										<th class="col-md-2">Book Name</th>
										<th class="col-md-5">Reserved for</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="book" items="${reserveDtlsList}">
										<tr>
											<td>${book.reqId}</td>
											<td>${book.bookNm}</td>
											<td><input type="date" name="modifiedDt"
												class="date_picker" value="${book.reservedFor}"
												id="modifydt" name="modifydt"></td>
											<td><button type="submit" class="btn btn-link"
													onclick="activateTab('reserve');modify('update', '${book.reqId}')">update</button>
												/
												<button type="submit" class="btn btn-link"
													onclick="activateTab('reserve');modify('cancel','${book.reqId}')">cancel</button>
											</td>
										</tr>

									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>

					<input type="hidden" id="cancelReqId" name="cancelReqId"
						value="${book.reqId}"> <input type="hidden" id="modifyTyp"
						name="modifyTyp">
				</form>


				<form action="confirmReserve">

					<input type="hidden" id="bookHdnNmId" name="bookHdnNmId"> <input
						type="hidden" id="brwrHdnId" name="brwrHdnId"
						value="${reserveVo.reserverId}"> <input type="hidden"
						id="bookTyp" name="bookTyp"> <input type="hidden"
						id="rsrvHdnFor" name="rsrvHdnFor" value="${reserveVo.reservedFor}">

					<c:if test="${not empty bookAvailList}">
						<div class="table-container">
							<br>
							<table class="table table-striped ">
								<thead>
									<tr>
										<th class="col-md-2">Book Master ID</th>
										<th class="col-md-2">Book Name</th>
										<th class="col-md-2">Type</th>
										<th class="col-md-2">Available</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="book" items="${bookAvailList}">
										<tr>
											<td>${book.bookNameId}</td>
											<td>${book.bookName}</td>
											<td>${book.type}</td>
											<td>${book.count}</td>
											<td><button type="submit" class="btn btn-link"
													onclick="activateTab('reserve');passVal('${book.bookNameId}','${book.type}')">Confirm
													a Book</button></td>
										</tr>

									</c:forEach>
								</tbody>
							</table>

						</div>
					</c:if>
				</form>
			</div>



			<div class="tab-pane " id="dataEntry">
				<br>
				<form method="post" action="entry" enctype="multipart/form-data">
					<div class="row">
						<div class="form-group col-lg-5">
							<label class="text-primary" for="searchBy">Select </label> <select
								id="selectEntry" name="select"
								onchange="getEntryTyp(this.value);">
								<option selected value="book">Book</option>
								<option value="student">Student</option>
								<option value="faculty">Faculty</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-lg-5">
							<input type="text" class="form-control input-sm" id="entryOrdrId"
								placeholder="Enter order ID" name=entryOrdrId>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-lg-5 ">
							<input type="file" class="form-control" id="file" name="file">
						</div>
					</div>
					<input type="hidden" id="userId" name="userId" value="${userId}">
					<button type="submit" class="btn btn-primary btn-xs"
						onClick='activateTab("dataEntry")'>Upload</button>
					<c:if test="${not empty entryMessage}">
						<div class="alert alert-error" id="errorId">
							<i class="fa fa-info-circle">&nbsp;</i>${entryMessage}</div>
					</c:if>

				</form>



			</div>


			<div class="tab-pane " id="order">
				<br>
				<form role="form" action="order">
					<div class="row">
						<div class="form-group col-lg-5">
							<p class="text-primary">Enter Order details</p>
							<input type="text" class="form-control input-sm" id="ordrId"
								placeholder="Enter order ID" name=ordrId> <br> <input
								type="text" class="form-control input-sm" id="storeNm"
								placeholder="Acquired from" name="storeNm"> <br> <input
								type="text" id="copies" placeholder="Number of copies"
								class="form-control input-sm" name="copies">
						</div>

					</div>
					<div class="row">
						<div class="form-group col-lg-5 ">
							<label class="text-primary" for="acqTyp">Acquired Type </label> <select
								id="acqTyp" name="acqTyp"
								onchange="getToBeReturnedDt(this.value);">
								<option selected value="purchase">Purchase</option>
								<option value="donation">Donation</option>
								<option value="loan">Loan</option>
							</select>
						</div>
					</div>
					<div class="row" id="ordrDisplayDt" style="display: none">
						<div class="form-group col-lg-5 ">
							<label class="text-primary" for="ordrDt">To be Returned
								on </label> <input type="date" class="date_picker" id="ordrDt"
								name="ordrDt">
						</div>
					</div>
					<input type="hidden" id="userId" name="userId" value="${userId}">
					<button type="submit" class="btn btn-primary btn-xs"
						onClick='activateTab("order")'>Save</button>
					<c:if test="${not empty orderMessage}">
						<div class="alert alert-error" id="errorId">
							<i class="fa fa-info-circle">&nbsp;</i>${orderMessage}</div>
					</c:if>

				</form>



			</div>


		</div>

	</div>
</body>
<script type="text/javascript">
	function getToBeReturnedDt(typVal) {
		if (typVal == "loan") {
			document.getElementById("ordrDisplayDt").style.display = "block";
		} else {
			document.getElementById("ordrDisplayDt").style.display = "none";
		}
	}

	function getEntryTyp(typVal) {
		if (typVal != "book") {
			document.getElementById("entryOrdrId").style.display = "none";
		} else {
			document.getElementById("entryOrdrId").style.display = "block";
		}
	}
	$(document).ready(function() {
		var temp = document.getElementById("restricted").checked;
		var today = moment().format('YYYY-MM-DD');
		document.getElementById("issueDate").value = today;
		document.getElementById("actualReturnDate").value = today;
		document.getElementById("reserveDate").value = today;
		var returnOn = moment(today, 'YYYY-MM-DD').add(14, 'days');
		var returnDate = returnOn.format('YYYY-MM-DD');
		document.getElementById("returnDate").value = returnDate;
		var activeTab = localStorage.getItem('activeTab');
		if (activeTab) {
			$('.nav-tabs li a[href="#' + activeTab + '"]').tab('show');
			localStorage.clear();
		}
		var val = document.getElementById("multiple").value ;
		if (val=='multiple') {
			document.getElementById('reserveTill').style.display = "inline";
			
		}
	})
	$("#restricted").change(
			function() {
				var today = moment().format('YYYY-MM-DD')
				if (this.checked) {
					document.getElementById("returnDate").value = today;
				} else {
					var returnOn = moment(today, 'YYYY-MM-DD').add(14, 'days');
					document.getElementById("returnDate").value = returnOn
							.format('YYYY-MM-DD');
				}

			});
	function activateTab(tabId) {
		localStorage.setItem('activeTab', tabId)
	};

	function passVal(bookNmId, type) {
		document.getElementById('bookHdnNmId').value = bookNmId;
		document.getElementById('bookTyp').value = type;

	}
	function reserveBook(val) {
		document.getElementById('reservTyp').value = val;
	}

	function multipleReserve(val) {
		document.getElementById('reserveTill').style.display = "inline";
		document.getElementById('reserveNewId').style.display = "none";
		document.getElementById('reserveAlterId').style.display = "none";
		document.getElementById('confirmId').style.display = "inline";
		document.getElementById('cancelId').style.display = "inline";
		document.getElementById('reserveId').style.display = "none";
	}
	function cancel() {
		document.getElementById('reserveTill').style.display = "none";
		document.getElementById('reserveNewId').style.display = "inline";
		document.getElementById('reserveAlterId').style.display = "inline";
		document.getElementById('confirmId').style.display = "none";
		document.getElementById('cancelId').style.display = "none";
		document.getElementById('reserveId').style.display = "inline";
	}

	function modify(val, reqId) {
		document.getElementById('modifyTyp').value = val;
		document.getElementById('cancelReqId').value = reqId;
	}
	function multipleReserveConfirm(val) {
		document.getElementById('bookNameId').value = val;

	}
</script>

</html>