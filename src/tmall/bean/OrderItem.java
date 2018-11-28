package tmall.bean;
/**
 * ¶©µ¥ÏêÇéÀà
 * @author Administrator
 *
 */
public class OrderItem {
	private int id;
	private Product product;
	private Order order;
	private User user;
	private int number;
	
	public void setId(int id) {
		this.id=id;
	}
	public void setProduct(Product product) {
		this.product=product;
	}
	public void setOrder(Order order) {
		this.order=order;
	}
	public void setUser(User user) {
		this.user=user;
	}
	public void setNumber(int number) {
		this.number=number;
	}
	
	public int getId() {
		return id;
	};
	public Product getProduct() {
		return product;
	};
	public Order getOrder() {
		return order;
	};
	public User getUser() {
		return user;
	};
	public int getNumber() {
		return number;
	};
}
