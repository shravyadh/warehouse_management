package entities;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Item{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int item_code;

	@Column(length=20,nullable=false)
	private String item_name;

	@Column(nullable=false)
	private int item_stock;

	@Column(nullable=false)
	private double item_price;



	public Item()
	{

	}

	public int getItem_code() {
		return item_code;
	}
	public void setItem_code(int item_code) {
		this.item_code = item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public int getItem_stock() {
		return item_stock;
	}
	public void setItem_stock(int item_stock) {
		this.item_stock = item_stock;
	}
	public double getItem_price() {
		return item_price;
	}
	public void setItem_price(double item_price) {
		this.item_price = item_price;
	}
}