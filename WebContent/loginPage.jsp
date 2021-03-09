<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>Library Mgnt Sys</title>
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<link href="style.css" rel="stylesheet" type="text/css">
</head>

<body>
	<form action="login">
		<div class="container" id="loginContent">
			<h3 class='text-center text-primary bg-gradient-warning' style="text-shadow: 2px 2px white;">LIBRARY MANAGEMENT SYSTEM</h3>
			<br>
			<div class="form-group" >
			<h4>Enter credentials to login</h4>
			
			<c:if test="${not empty message}">
								<div class="alert alert-error text-danger" id="errorId">
									<i class="fa fa-info-circle">&nbsp;</i>${message}</div>
							</c:if>
			<input type="text" name="userId" placeholder="Username">
			<br><br>
			<input type="password" name="pswd" placeholder="Password">
			<br><br>
			<input type="submit" class="btn btn-primary" value="Login">
			</div>
			</div>
	</form>
</body>
</html>