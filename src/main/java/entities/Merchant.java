package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Merchant 
{
@Id
private String merchant_name;
@ManyToOne
private Item item;
public String getMerchant_name() {
	return merchant_name;
}
public Item getItem() {
	return item;
}
public void setMerchant_name(String merchant_name) {
	this.merchant_name = merchant_name;
}
public void setItem(Item item) {
	this.item = item;
}
}
