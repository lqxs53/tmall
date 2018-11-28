package tmall.bean;
/**
 * ²úÆ·Í¼Æ¬Àà
 * @author Administrator
 *
 */
public class ProductImage {
	private int id;
	private String type;
	private Product product;
	
	public void setId(int id) {
		this.id=id;
	}
	public void setType(String type) {
		this.type=type;
	}
	public void setProduct(Product product) {
		this.product=product;
	}
	public int getId() {
		return id;
	};
	public String getType() {
		return type;
	};
	public Product getProduct() {
		return product;
	}; 
}
