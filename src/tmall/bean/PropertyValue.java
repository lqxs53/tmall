package tmall.bean;
/**
 *  Ù–‘÷µ¿‡
 * @author Administrator
 *
 */
public class PropertyValue {
	private int id;
	private String value;
	private Product product;
	private Property property;
	
	public void setId(int id) {
		this.id=id;
	}
	public void setValue(String value) {
		this.value=value;
	}
	public void setProduct(Product product) {
		this.product=product;
	}
	public void setProperty(Property property) {
		this.property=property;
	}
	public int getId() {
		return id;
	};
	public String getValue() {
		return value;
	};
	public Product getProduct() {
		return product;
	};
	public Property getProperty() {
		return property;
	};
}
