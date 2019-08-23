<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="entities.ItemOrder,java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
table {
  border-collapse: collapse;
  width: 100%;
}

th, td {
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {background-color: #f2f2f2;}
</style>
<title>View Orders</title>
</head>
<body>
<h2>WAREHOUSE MANAGEMENT</h2>
<form action="MerchantOperation?operation=list" method="post">

	<div style="margin-left: 25%; padding: 1px 16px; height: 1000px;">
		<table style="width: 80%" id="a">
			<tr>
				<th>order_id</th>
				<th>quantity_purchased</th>
				<th>Date</th>
				<th>Merchant_Name</th>
				
			</tr>

			<%
						List<ItemOrder> orders = (List<ItemOrder>) session.getAttribute("orders");
						for (ItemOrder itemOrder : orders) {
			%>
			<tr>
				<td><%=itemOrder.getOrderid()%></td>
				<td><%=itemOrder.getQuantity()%></td>
				<td><%=itemOrder.getDate()%></td>
				<td><%=itemOrder.getMerchant_name()%></td>
				
				<td><a href="MerchantOperation?operation=accept&id=<%=itemOrder.getOrderid()%>">Accept</a></td>
				<td><a href="MerchantOperation?operation=cancel&id=<%=itemOrder.getOrderid()%>">Cancel</a></td>
			</tr>
          
			<%
				}
			%>
		  </table>
	        </div>
	
		</form>
	
</body>
</html>