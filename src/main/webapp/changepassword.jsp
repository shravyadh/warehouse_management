<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Change Password</title>
</head>
<body>
	<h2>WAREHOUSE MANAGEMENT</h2>
	<form action="CustomerController?operation=changepassword"
		method="post">
		<%
			String username = request.getParameter("username");
			session.setAttribute("sessionname",username);
			out.print("Welcome " + username);
		%>
		<table style="width: 100%">
		
			<tr>
				<th>New Password:<input type="password" name="newpassword" required></th>
			</tr>
			<tr>
				<th>Confirm Password:<input type="password"
					name="confirmpassword" required></th>
			</tr>
			<tr>
				<th><input type="submit" value="submit"><input
					type="button" value="back" onclick="history.back()"></th>
			</tr>
		</table>
	</form>
</body>
</html>