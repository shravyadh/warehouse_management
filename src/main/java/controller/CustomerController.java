package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import DAO.CustomerDAO;

import entities.Login;

/**
 * Servlet implementation class CustomerController
 */
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Client client = ClientBuilder.newClient(new ClientConfig());
	String apiURL = "http://localhost:8081/warehouse_management/webapi/myresource";
	WebTarget webTarget;
	Invocation.Builder invocationBuilder ;
	Login login=new Login();
	CustomerDAO customerDAO=new CustomerDAO();
	Response clientResponse;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher ;
		PrintWriter out=response.getWriter();
		String operation=request.getParameter("operation");
	
		String uname=request.getParameter("username");
		HttpSession session = request.getSession(true);
		session.setAttribute("sessionname",uname);

		if(operation.equals("login"))
		{
			
			String username=(String) session.getAttribute("sessionname");
			String password=request.getParameter("password");
			login.setUsername(username);
			System.out.println(login);
		    webTarget = client.target(apiURL).path("validate");
			invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			clientResponse = invocationBuilder.post(Entity.entity(login, MediaType.APPLICATION_JSON));
			login=clientResponse.readEntity(Login.class);
			System.out.println(login);
			String role=login.getRole();
			String value=login.getPassword();
			if(value.equals(password)&&role.equals("admin"))
			{
				
				requestDispatcher = request.getRequestDispatcher("adminoperations.jsp");
				requestDispatcher.forward(request, response);
			}
			else if(value.equals(password)&&role.equals("merchant"))
			{
				requestDispatcher = request.getRequestDispatcher("MerchantOperation?operation=list");
				requestDispatcher.forward(request, response);
			}
			else
			{
				out.println("Invalid uaername and password! Please try again");
				requestDispatcher = request.getRequestDispatcher("index.jsp");
				requestDispatcher.forward(request, response);
			}
			
		}
		
	
		
	 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
