package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import DAO.CustomerDAO;
import DAO.MerchantDAO;
import entities.ItemOrder;

public class MerchantOperation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Client client = ClientBuilder.newClient(new ClientConfig());
	String apiURL = "http://localhost:8081/warehouse_management/webapi/myresource";
	WebTarget webTarget;
	Invocation.Builder invocationBuilder;
	CustomerDAO customerDAO = new CustomerDAO();
	Response clientResponse;
	MerchantDAO merchantDAO=new MerchantDAO();
	public MerchantOperation() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		RequestDispatcher requestDispatcher;
		String operation = request.getParameter("operation");
		if (operation.equals("list")) {
			client = ClientBuilder.newBuilder().build();
			apiURL = "http://localhost:8081/warehouse_management/webapi/";
			webTarget = client.target(apiURL).path("merchList");
			clientResponse = webTarget.request().get();
			GenericType<List<ItemOrder>> genericOrders = new GenericType<List<ItemOrder>>() {
			};
			List<ItemOrder> orders = clientResponse.readEntity(genericOrders);
			session.setAttribute("orders", orders);
			requestDispatcher = request.getRequestDispatcher("merchant.jsp?operation=ViewOrders");
			requestDispatcher.forward(request, response);
		}
		if (operation.equals("accept")) {
			int id=Integer.parseInt(request.getParameter("id"));
			merchantDAO.acceptItemOrder(id);
			out.println("Order Accepted");
		
			
		}
		if(operation.equals("cancel"))
		{
			int id=Integer.parseInt(request.getParameter("id"));
			merchantDAO.cancelItemOrder(id);
			out.println("Order Cancelled");
		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
