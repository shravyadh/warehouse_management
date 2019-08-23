package com.warehouse.warehouse_management;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DAO.MerchantDAO;
import entities.ItemOrder;
@Path("merchList")
public class MerchantResource {
	
	MerchantDAO merchantDAO=new MerchantDAO();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ItemOrder> getOrders() {
		return merchantDAO.listOrders();
	}
}
