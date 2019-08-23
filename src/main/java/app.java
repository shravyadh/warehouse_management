import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Login;

public class app {
	public static void main(String args[]) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("WM");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Login login = new Login();
		login.setUsername("kapitan");
		login.setPassword("kapitan123");
		login.setRole("merchant");


//		Customer customer = new Customer();
//		customer.setCustomer_name("Akash");
//		customer.setPhone_number("9898675432");
//		customer.setAddress("hitech city");
//		customer.setLogin(login);
//		entityManager.persist(customer);
//		entityManager.getTransaction().commit();
//		Item i1=new Item();
//		i1.setItem_name("Cup");
//		i1.setItem_price(5);
//		i1.setItem_stock(500);
//		
//		Item  i2=new Item();
//		i2.setItem_name("Pens");
//		i2.setItem_price(40);
//		i2.setItem_stock(600);
//		Customer customer1=new Customer();
		entityManager.getTransaction().begin();
//		System.out.println(customer.getCustomer_name());
////		customer1=entityManager.find(Customer.class,customer.getCustomer_name());
//		System.out.println(customer1);
//		System.out.println(customer.getCustomer_name());
//		customer1.setCustomer_name("Asher");Eider">Eider</option>
		
//		customer1.setPhone_number("9099043432");
//		customer1.setAddress("hyderabad");
//		customer1.setLogin(login);
//		Item item=new Item();
//		item=entityManager.find(Item.class, 1);
//		
//		Item item2=new Item();
//		item2=entityManager.find(Item.class, 2);
//		
//		Merchant merchant=new Merchant();
//		merchant.setMerchant_name("Eider");
//		merchant.setItem(item);
//		
//		
//		
//	
//		
//		Merchant m4=new Merchant();
//		m4.setItem(item2);
//		m4.setMerchant_name("Kapitan");
//		entityManager.persist(merchant);
//	
//		entityManager.persist(m4);
		entityManager.persist(login);
		
		
//		entityManager.persist(customer1);
//		System.out.println(customer1);
		
		entityManager.getTransaction().commit();
	}
}
