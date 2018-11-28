package tmall.bean;

import java.util.Date;
/**
 * 产品展示类
 * @author Administrator
 *
 */
public class Review {
	private int id;
	private String content;
	private Date createdate;
	private User user;
	private Product product;
	
	public void setId(int id) {
		this.id=id;
	}
	public void setContent(String content) {
		this.content=content;
	}
	public void setCreatedate(Date createdate) {
		this.createdate=createdate;
	}
	public void setUser(User user) {
		this.user=user;
	}
	public void setProduct(Product product) {
		this.product=product;
	}
	
	public int getId() {
		return id;
	};
	public String getContent() {
		return content;
	};
	public Date getCreatedate() {
		return createdate;
	};
	public User getUser() {
		return user;
	};
	public Product getProduct() {
		return product;
	};
}
