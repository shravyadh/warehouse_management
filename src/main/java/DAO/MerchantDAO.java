package DAO;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.ItemOrder;

public class MerchantDAO {
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("WM");
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	
	public ArrayList<ItemOrder> listOrders() {
		@SuppressWarnings("unchecked")
		ArrayList<ItemOrder> orders = new ArrayList<ItemOrder>(
				entityManager.createQuery("select itemOrder from ItemOrder itemOrder").getResultList());
		return orders;
	}
	public void acceptItemOrder(int id)
	{
		ItemOrder itemOrder=new ItemOrder();
		entityManager.getTransaction().begin();
		itemOrder=entityManager.find(ItemOrder.class, id);
		int quantity=itemOrder.getQuantity();
		System.out.println(itemOrder.getQuantity());
		int stock=itemOrder.getItem().getItem_stock();
		System.out.println(itemOrder.getItem().getItem_stock());
		int total=stock+quantity;
		itemOrder.getItem().setItem_stock(total);
		entityManager.remove(itemOrder);
		entityManager.getTransaction().commit();
	}
	public void cancelItemOrder(int id)
	{
		ItemOrder itemOrder=new ItemOrder();
		entityManager.getTransaction().begin();
		itemOrder=entityManager.find(ItemOrder.class, id);
		System.out.println(itemOrder);
		entityManager.remove(itemOrder);
		entityManager.getTransaction().commit();
	}
}
