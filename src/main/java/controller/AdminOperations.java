package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import DAO.CustomerDAO;
import entities.Customer;
import entities.Item;
import entities.ItemOrder;
import entities.Login;
import entities.Merchant;
import entities.Purchase;

public class AdminOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Client client = ClientBuilder.newClient(new ClientConfig());
	String apiURL = "http://localhost:8081/warehouse_management/webapi/myresource";
	WebTarget webTarget;
	Invocation.Builder invocationBuilder;
	CustomerDAO customerDAO = new CustomerDAO();
	Response clientResponse;
	RequestDispatcher requestDispatcher;

	public AdminOperations() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMerchants(String name) {
		client = ClientBuilder.newBuilder().build();
		webTarget = client.target(apiURL).path("merchants"+"/"+name);
		clientResponse = webTarget.request().get();
		String merchants = clientResponse.readEntity(String.class);
		return merchants;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String operation = request.getParameter("operation");
		// adding a customer
		if (operation.equals("add")) {
			String username = (String) session.getAttribute("sessionname");

			Login login = new Login();
			login.setUsername(username);

			Customer customer = new Customer();
			Integer code = Integer.parseInt(request.getParameter("code"));
			String name = request.getParameter("name");
			String phoneno = request.getParameter("phoneno");
			String address = request.getParameter("address");
			customer.setCustomer_code(code);
			customer.setCustomer_name(name);
			customer.setPhone_number(phoneno);
			customer.setAddress(address);
			customer.setLogin(login);
			System.out.println(customer);

			webTarget = client.target(apiURL).path("addcustomer");
			invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			clientResponse = invocationBuilder.post(Entity.entity(customer, MediaType.APPLICATION_JSON));
			customer = clientResponse.readEntity(Customer.class);

			if (customer != null) {
				out.println("Successfully added");
			} else {
				out.println("Please try again");
			}
		}
		// viewing items
		if (operation.equals("ViewItems")) {
			client = ClientBuilder.newBuilder().build();
			webTarget = client.target(apiURL).path("getList");
			clientResponse = webTarget.request().get();

			GenericType<List<Item>> genericItems = new GenericType<List<Item>>() {
			};
			List<Item> items = clientResponse.readEntity(genericItems);

			session.setAttribute("items", items);

			requestDispatcher = request.getRequestDispatcher("adminoperations.jsp?operation=ViewItems");
			requestDispatcher.forward(request, response);
		}
		// view customers
		if (operation.equals("ViewCustomers")) {
			client = ClientBuilder.newBuilder().build();
			webTarget = client.target(apiURL);
			clientResponse = webTarget.request().get();
			GenericType<List<Customer>> genericCustomers = new GenericType<List<Customer>>() {
			};
			List<Customer> customers = clientResponse.readEntity(genericCustomers);
			session.setAttribute("customers", customers);
			requestDispatcher = request.getRequestDispatcher("adminoperations.jsp?operation=ViewCustomers");
			requestDispatcher.forward(request, response);
		}
		// deleting customer
		if (operation.equals("deletecustomer")) {
			String name = request.getParameter("customername	");
			client = ClientBuilder.newBuilder().build();
			webTarget = client.target(apiURL + "/" + name);
			invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			clientResponse = invocationBuilder.delete();
			String result = clientResponse.readEntity(String.class);
			if (result.equals("true")) {
				out.println("Deleted Successfully");
			} else {
				out.println("Try Again!");
			}
		}
		// adding stock of an item
		if (operation.equals("stock")) {
			Item item = new Item();
			ItemOrder itemOrder = new ItemOrder();
			double total;
			String item_name = request.getParameter("item");
			Integer quantity = Integer.parseInt(request.getParameter("quantity"));
			//String merchant_name = request.getParameter("merchant");
			LocalDate date = LocalDate.now();
			
			client = ClientBuilder.newBuilder().build();
			webTarget = client.target(apiURL).path("getList");
			clientResponse = webTarget.request().get();
			GenericType<List<Item>> genericItems = new GenericType<List<Item>>() {
			};
			List<Item> items = clientResponse.readEntity(genericItems);

			item.setItem_name(item_name);

			for (Item singleItem : items) {
				if (singleItem.getItem_name().equals(item_name)) {
					item.setItem_code(singleItem.getItem_code());
					total = item.getItem_price() * quantity;
				}
			}

			itemOrder.setMerchant_name(getMerchants(item_name));
			itemOrder.setDate(date);
			itemOrder.setQuantity(quantity);
			itemOrder.setItem(item);

			webTarget = client.target(apiURL).path("addorder");
			invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			clientResponse = invocationBuilder.post(Entity.entity(itemOrder, MediaType.APPLICATION_JSON));
			itemOrder = clientResponse.readEntity(ItemOrder.class);
			if (itemOrder != null) {
				out.println("Order placed at merchant");
			} else {
				out.println("Try Again!");
			}
		}
		// placing an order
		if (operation.equals("order")) {
			Purchase purchase = new Purchase();

			double price = 0;
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			LocalDate date = LocalDate.now();

			System.out.println(id);

			// getting list of items
			client = ClientBuilder.newBuilder().build();
			webTarget = client.target(apiURL).path("getList");
			clientResponse = webTarget.request().get();
			GenericType<List<Item>> genericItems = new GenericType<List<Item>>() {
			};
			List<Item> items = clientResponse.readEntity(genericItems);
			for (Item i : items) {
				if (i.getItem_code() == id) {
					if (quantity < i.getItem_stock()) {
						purchase.setItem(i);
						purchase.setQuantity_purchased(quantity);

						// getting list of customers
						client = ClientBuilder.newBuilder().build();
						webTarget = client.target(apiURL);
						clientResponse = webTarget.request().get();
						GenericType<List<Customer>> genericCustomers = new GenericType<List<Customer>>() {
						};
						List<Customer> customers = clientResponse.readEntity(genericCustomers);
						for (Customer c : customers) {
							if (c.getCustomer_name().equals(name)) {
								purchase.setCustomer(c);
							}
						}

						purchase.setDate_Of_purchase(date);
						purchase.setTotal(price);

						webTarget = client.target(apiURL).path("placeorder");
						invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
						clientResponse = invocationBuilder.post(Entity.entity(purchase, MediaType.APPLICATION_JSON));
						purchase = clientResponse.readEntity(Purchase.class);
						out.println("Your total is " + purchase.getTotal());
						purchase.getItem().getItem_stock();
						customerDAO.updateStock(id, quantity);
					} else {
						out.print("Stock is less!Add to cart for now");
					}
				}
			}

		}

		// list purchases
		if (operation.equals("ViewPurchases")) {
			client = ClientBuilder.newBuilder().build();
			webTarget = client.target(apiURL).path("purchases");
			clientResponse = webTarget.request().get();

			GenericType<List<Purchase>> genericPurchases = new GenericType<List<Purchase>>() {
			};
			List<Purchase> purchases = clientResponse.readEntity(genericPurchases);

			session.setAttribute("purchases", purchases);

			requestDispatcher = request.getRequestDispatcher("adminoperations.jsp?operation=ViewPurchases");
			requestDispatcher.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
