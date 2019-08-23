package com.warehouse.warehouse_management;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DAO.CustomerDAO;
import entities.Customer;
import entities.Item;
import entities.ItemOrder;
import entities.Login;
import entities.Merchant;
import entities.Purchase;

@Path("myresource")
public class MyResource {
	CustomerDAO customerDAO = new CustomerDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Customer> getCustomers() {
		return customerDAO.listCustomers();
	}
	
	@GET
	@Path("merchants/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMerchant(@PathParam("name") String name)
	{
		System.out.println(name);
		return customerDAO.getMerchant(name);
	}

	@GET
	@Path("/getList")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Item> getItems() {
		return customerDAO.listItems();
	}
	

	
	@Path("/purchases")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Purchase> getPurchases() {
		return customerDAO.listPurchases();
	}

	@Path("/validate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Login validateUser(Login login) {
		System.out.println(login);
		return customerDAO.validate(login);
	}

	@Path("/changepassword")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Login changePassword(Login login) {
		return customerDAO.changePassword(login);
	}

	@Path("/addcustomer")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Customer addCustomer(Customer customer) {
		return customerDAO.addCustomer(customer);
	}
	
	@Path("/addorder")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ItemOrder addOrder(ItemOrder itemOrder) {
		return customerDAO.addOrder(itemOrder);
	}
	
	@Path("/placeorder")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Purchase placeOrder(Purchase purchase) {
		return customerDAO.placeOrder(purchase);
	}
	
	
	
	@DELETE
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteCustomer(@PathParam("name") String name)
	{
		System.out.println(name);
		return customerDAO.deleteCustomer(name);
	}
}
