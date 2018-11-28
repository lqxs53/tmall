package tmall.bean;
/**
 *  Ù–‘¿‡
 * @author Administrator
 *
 */
public class Property {
	private int id;
	private String name;
	private Category category;
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
	public void setCategory(Category category) {
		this.category=category;
	}
	public Category getCategory() {
		return category;
	}
}
