package tmall.bean;

import java.util.Date;
import java.util.List;
/**
 * 订单类
 * @author Administrator
 *
 */
public class Order {
	private int id;
	private String ordercode;
	private String address;
	private String post;
	private String receiver;
	private String mobile;
	private String usermessage;
	private Date createdate;
	private Date paydate;
	private Date deliverydate;
	private Date confirmdate;
	private User user;
	private String status;
	
	private List<OrderItem> orderItems;
	private int totalNumber;
	private float total;
	
	public String getStatusDesc() {
		String desc="未知";
		switch(status) {
//		case OrderDAO.waitPay:
//			 desc="待付款";
//			 break;
//		case OrderDAO.waitPay:
//			 desc="待付款";
//			 break;
//		case OrderDAO.waitPay:
//			 desc="待付款";
//			 break;
//		case OrderDAO.waitPay:
//			 desc="待付款";
//			 break;
//		case OrderDAO.waitPay:
//			 desc="待付款";
//			 break;
//		case OrderDAO.waitPay:
//			 desc="待付款";
//			 break;
//		default:
//			 desc="未知";
		}
		return desc;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode=ordercode;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	public void setPost(String post) {
		this.post=post;
	}
	public void setReceiver(String receiver) {
		this.receiver=receiver;
	}
	public void setMobile(String mobile) {
		this.mobile=mobile;
	}
	public void setUsermessage(String usermessage) {
		this.usermessage=usermessage;
	}
	public void setCreatedate(Date createdate) {
		this.createdate=createdate;
	}
	public void setPaydate(Date paydate) {
		this.paydate=paydate;
	}
	public void setDeliverydate(Date deliverydate) {
		this.deliverydate=deliverydate;
	}
	public void setConfirmdate(Date confirmdate) {
		this.confirmdate=confirmdate;
	}
	public void setUser(User user) {
		this.user=user;
	}
	public void setStatus(String status) {
		this.status=status;
	}
	
	public int getId() {
		return id;
	};
	public String getOrdercode() {
		return ordercode;
	};
	public String getAddress() {
		return address;
	};
	public String getPost() {
		return post;
	};
	public String getReceiver() {
		return receiver;
	};
	public String getMobile() {
		return mobile;
	};
	public String getUsermessage() {
		return usermessage;
	};
	public Date getCreatedate() {
		return createdate;
	};
	public Date getPaydate() {
		return paydate;
	};
	public Date getDeliverydate() {
		return deliverydate;
	};
	public Date getConfirmdate() {
		return confirmdate;
	};
	public User getUser() {
		return user;
	};
	public String getStatus() {
		return status;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	};
}
