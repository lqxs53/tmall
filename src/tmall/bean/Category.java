package tmall.bean;

import java.util.List;
/**
 * ∑÷¿‡¿‡
 * @author Administrator
 *
 */
public class Category {
	private int id;
	private String name;
	List<Product> products;
	List<List<Product>> productsByRow;
	
	public void setId(int id) {
		this.id=id;
	}
	public void setName(String name) {
		this.name=name;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Category [name="+name+"]";
	}
	public void setProducts(List<Product> products) {
		this.products=products;
	}
	public void setProductsByRow(List<List<Product>> productsByRow) {
		this.productsByRow=productsByRow;
	}
	public List<Product> getProducts(){
		return products;
	}
	public List<List<Product>> getProductsByRow(){
		return productsByRow;
	}
}
