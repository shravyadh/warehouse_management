<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="javax.servlet.RequestDispatcher"
	import="javax.servlet.http.HttpServletResponse"
	import="javax.servlet.http.HttpServletRequest" import="entities.Item"
	import="java.util.*" import="entities.Customer"
	import="entities.Purchase"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 25%;
	background-color: #f1f1f1;
	height: 100%;
	position: fixed;
	overflow: auto;
}

li a {
	display: block;
	color: #000;
	padding: 8px 16px;
	text-decoration: none;
}

/* Change the link color on hover */
li a:hover {
	background-color: #555;
	color: white;
}

table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

input {
	margin-bottom: 10px;
	diplay: block;
}
</style>
<title>Admin operations</title>
</head>
<body>
	<h2>WAREHOUSE MANAGEMENT</h2>
	<h3>
		Welcome
		<%=session.getAttribute("sessionname")%></h3>
	<div>
		<ul>
			<li><a href="AdminOperations?operation=ViewItems">List Items</a></li>
			<li><a href="AdminOperations?operation=ViewCustomers">Display
					customer details</a></li>
			<li><a href="adminoperations.jsp?operation=add">Add Customer</a></li>
			<li><a href="adminoperations.jsp?operation=deletecustomer">Delete
					Customer</a></li>
			<li><a href="adminoperations.jsp?operation=stock">Add stock</a></li>
			<li><a href="AdminOperations?operation=ViewPurchases">Display
					Purchase Details</a></li>
			<li><a href="logout.jsp">Logout</a></li>
		</ul>
	</div>

	<%
		String operation = (String) request.getParameter("operation");
		if (operation != null) {
			if (operation.equals("add")) {
	%>

	<form action="AdminOperations?operation=add" method="post">
		<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
			Customer Code:<input type="text" name="code" required><br>
			Name:<input type="text" name="name" required><br>
			Contact details:<input type="text" name="phoneno" id="s" required><br>
			Address:<input type="text" name="address" required><br>
			<input type="submit" value="submit" onclick="phonenumber(document.getElementId(s))"><br> <input
				type="button" value="back" onclick="history.back()">
		</div>
	</form>

	<%
		}
			if (operation.equals("ViewItems")) {
	%>

	<form action="AdminOperations?operation=ViewItems" method="post">
	</form>
	<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
		<table style="width: 80%" id="a">
			<tr>
				<th>Item Name</th>
				<th>Item Price</th>
				<th>Order</th>
				<th>Wishlist</th>
			</tr>


			<%
				@SuppressWarnings("unchecked")
						List<Item> items = (List<Item>) session.getAttribute("items");
						for (Item item : items) {
			%>

			<tr>
				<td><%=item.getItem_name()%></td>
				<td><%=item.getItem_price()%></td>
				<td><a
					href="adminoperations.jsp?operation=order&id=<%=item.getItem_code()%>">Order
						Now</a></td>

			</tr>

			<%
				}
			%>

		</table>
	</div>

	<%
		}
			if (operation.equals("ViewCustomers")) {
	%>

	<form action="AdminOperations?operation=ViewCustomers" method="post">
	</form>
	<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
		<table style="width: 80%" id="a">
			<tr>
				<th>Customer Name</th>
				<th>Address</th>
				<th>Phone Number</th>
			</tr>

			<%
				@SuppressWarnings("unchecked")
						List<Customer> customers = (List<Customer>) session.getAttribute("customers");
						for (Customer c : customers) {
			%>
			<tr>
				<td><%=c.getCustomer_name()%></td>
				<td><%=c.getAddress()%></td>
				<td><%=c.getPhone_number()%></td>
			</tr>

			<%
				}
			%>
		</table>
	</div>
	<%
		}
			if (operation.equals("deletecustomer")) {
	%>

	<form action="AdminOperations?operation=deletecustomer" method="post">
		<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">

			Customer Name:<input type="text" name="customername	"><br>
			<input type="submit" value="submit">
		</div>
	</form>
	<%
		}

			if (operation.equals("stock")) {
	%>

	<form action="AdminOperations?operation=stock" method="post">

		<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
			Item Name:<input type="text" name="item"><br> Quantity:<input
				type="text" name="quantity"><br>
			<br> <input type="submit" value="submit">
		</div>
	</form>
	<%
		}
			if (operation.equals("order")) {
	%>
	<form
		action="AdminOperations?operation=order&id=<%=Integer.parseInt(request.getParameter("id"))%>"
		method="post">
		<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
			Customer Name:<input type="text" name="name"><br>
			Quantity:<input type="text" name="quantity"><br> <input
				type="submit" value="submit">
		</div>
	</form>
	<%
		}
			if (operation.equals("ViewPurchases")) {
	%>
	<form action="AdminOperations?operation=ViewPurchases" method="post">
	</form>
	<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
		<table style="width: 80%" id="a">
			<tr>
				<th>transaction_id</th>
				<th>date_Of_purchase</th>
				<th>quantity_purchased</th>
				<th>customer_name</th>
				<th>item_code</th>
				<th>total</th>
			</tr>

			<%
				@SuppressWarnings("unchecked")
						List<Purchase> purchases = (List<Purchase>) session.getAttribute("purchases");
						for (Purchase purchase : purchases) {
			%>
			<tr>
				<td><%=purchase.getTransaction_id()%></td>
				<td><%=purchase.getDate_Of_purchase()%></td>
				<td><%=purchase.getQuantity_purchased()%></td>
				<td><%=purchase.getCustomer().getCustomer_name()%></td>
				<td><%=purchase.getItem().getItem_name()%></td>
				<td><%=purchase.getTotal()%></td>
			</tr>

			<%
				}
			%>
		</table>
	</div>
	<%
		}}
	%>
	<script>
function phonenumber(inputtxt)
{
  var phoneno = /^\d{10}$/;
  var phone1=/^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
  var phone2= /^\+?([0-9]{2})\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})$/;
  if((inputtxt.value.match(phoneno))
        {
      return true;
        }
  else if((inputtxt.value.match(phone1))
		  {
	  return true;
	  }
  else if((inputtxt.value.match(phone2))
		  {
	  return true;
	  }
  
      else
        {
        alert("message");
        return false;
        }
}
</script>
</body>
</html>