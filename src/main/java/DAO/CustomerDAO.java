package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Customer;
import entities.Item;
import entities.ItemOrder;
import entities.Login;
import entities.Merchant;
import entities.Purchase;

public class CustomerDAO {
	public CustomerDAO() {

	}

	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("WM");
	EntityManager entityManager = entityManagerFactory.createEntityManager();

	public Login validate(Login login) {
		System.out.println("validate");
		Login login1 = new Login();
		entityManager.getTransaction().begin();
		login1 = entityManager.find(Login.class, login.getUsername());
		System.out.println(login1);
		return login1;
	}

	public Login changePassword(Login login) {
		Login login1 = new Login();
		entityManager.getTransaction().begin();
		System.out.println(login.getUsername());
		login1 = entityManager.find(Login.class, login.getUsername());
		String password = login.getPassword();
		login1.setPassword(password);
		entityManager.persist(login1);
		entityManager.getTransaction().commit();
		return login1;
	}

	public Customer addCustomer(Customer customer) {

		customer.setLogin(entityManager.find(Login.class, customer.getLogin().getUsername()));
		entityManager.getTransaction().begin();
		entityManager.persist(customer);
		entityManager.getTransaction().commit();
		return customer;
	}

	public ArrayList<Item> listItems() {
		@SuppressWarnings("unchecked")
		ArrayList<Item> items = new ArrayList<Item>(
				entityManager.createQuery("select item from Item item").getResultList());
		return items;
	}

	public ArrayList<Customer> listCustomers() {
		@SuppressWarnings("unchecked")
		ArrayList<Customer> customers = new ArrayList<Customer>(
				entityManager.createQuery("select customer from Customer customer").getResultList());
		return customers;
	}

	public String deleteCustomer(String name) {

		Customer customer = new Customer();
		entityManager.getTransaction().begin();
		customer = entityManager.find(Customer.class, name);
		entityManager.remove(customer);
		entityManager.getTransaction().commit();
		return "true";
	}

	public ItemOrder addOrder(ItemOrder itemOrder) {
		entityManager.getTransaction().begin();
		entityManager.persist(itemOrder);
		entityManager.getTransaction().commit();
		return itemOrder;
	}

	public Purchase placeOrder(Purchase purchase) {
		
		int quantity=purchase.getQuantity_purchased();
		double totalprice=quantity*(purchase.getItem().getItem_price());
		entityManager.getTransaction().begin();
		purchase.setTotal(totalprice);
		entityManager.persist(purchase);
		entityManager.getTransaction().commit();
		return purchase;
	}

	public String getMerchant(String name)
	{
		ArrayList<Merchant> merchants = new ArrayList<Merchant>(
				entityManager.createQuery("select merchant from Merchant merchant where merchant.item.item_name= :name").setParameter("name",name).getResultList());
		for(Merchant merchant:merchants)
		{
			if(merchant.getItem().getItem_name().equals(name))
			{
				return merchant.getMerchant_name();
			}
		}
		return "okay";
	}

	public void updateStock(int id,int quantity)
	{
		entityManager.getTransaction().begin();
		Item item=new Item();	
		item=entityManager.find(Item.class, id);
		item.setItem_stock(item.getItem_stock()-(quantity));
		entityManager.persist(item);
		entityManager.getTransaction().commit();
	}
	
	public ArrayList<Purchase> listPurchases() {
		@SuppressWarnings("unchecked")
		ArrayList<Purchase> purchases = new ArrayList<Purchase>(
				entityManager.createQuery("select purchase from Purchase purchase").getResultList());
		return purchases;
	}
	
}
