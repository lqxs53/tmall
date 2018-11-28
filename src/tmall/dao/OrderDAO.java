package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tmall.bean.Order;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class OrderDAO {
	public static final String waitPay="waitPay";
	public static final String waitDelivery="waitDelivery";
	public static final String waitConfirm="waitConfirm";
	public static final String waitReview="waitReview";
	public static final String finish="finish";
	public static final String delete="delete";
	/**
	 * 获取所有订单总数
	 * @return
	 */
	public int getTotal() {
		int total=0;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="select count(1) from order_";
			ResultSet rs=s.executeQuery(sql);
			while(rs.next()) {
				total=rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	/**
	 * 新增订单
	 * @param order
	 */
	public void add(Order order) {
		String sql="insert into order_ values (null,?,?,?,?,?,?,?,?,?,?,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, order.getOrdercode());
			ps.setString(2, order.getAddress());
			ps.setString(3, order.getPost());
			ps.setString(4, order.getReceiver());
			ps.setString(5, order.getMobile());
			ps.setString(6, order.getUsermessage());
			ps.setTimestamp(7, DateUtil.d2t(order.getCreatedate()));
			ps.setTimestamp(8, DateUtil.d2t(order.getPaydate()));
			ps.setTimestamp(9, DateUtil.d2t(order.getDeliverydate()));
			ps.setTimestamp(10, DateUtil.d2t(order.getConfirmdate()));
			ps.setInt(11, order.getUser().getId());
			ps.setString(12, order.getStatus());
			
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			while(rs.next()) {
				int id = rs.getInt(1);
				order.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新订单信息
	 * @param order
	 */
	public void update(Order order) {
		String sql="update order_ set ordercode=?,address=?,post=?,receiver=?,mobile=?,usermessage=?,createdate=?,paydate=?,deliverydate=?,confirmdate=?,uid=?,status=? where id=?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, order.getOrdercode());
			ps.setString(2, order.getAddress());
			ps.setString(3, order.getPost());
			ps.setString(4, order.getReceiver());
			ps.setString(5, order.getMobile());
			ps.setString(6, order.getUsermessage());
			ps.setTimestamp(7, DateUtil.d2t(order.getCreatedate()));
			ps.setTimestamp(8, DateUtil.d2t(order.getPaydate()));
			ps.setTimestamp(9, DateUtil.d2t(order.getDeliverydate()));
			ps.setTimestamp(10, DateUtil.d2t(order.getConfirmdate()));
			ps.setInt(11, order.getUser().getId());
			ps.setString(12, order.getStatus());
			ps.setInt(13, order.getId());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除订单
	 * @param id
	 */
	public void delete(int id) {
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="delete from order_ where id="+id;
			s.execute(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询订单信息
	 * @param id
	 * @return
	 */
	public Order get(int id) {
		Order order=new Order();
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="select * from order_ where id="+id;
			ResultSet rs=s.executeQuery(sql);
			while(rs.next()) {
				String orderCode=rs.getString("orderCode");
				String address=rs.getString("address");
				String post=rs.getString("post");
				String receiver=rs.getString("receiver");
				String mobile=rs.getString("mobile");
				String userMessage=rs.getString("userMessage");
				int uid=rs.getInt("uid");
				String status=rs.getString("status");
				Date createDate=DateUtil.t2d(rs.getTimestamp("createDate"));
				Date payDate=DateUtil.t2d(rs.getTimestamp("payDate"));
				Date deliveryDate=DateUtil.t2d(rs.getTimestamp("deliverDate"));
				Date confirmDate=DateUtil.t2d(rs.getTimestamp("confirmDate"));
				order.setAddress(address);
				order.setConfirmdate(confirmDate);
				order.setCreatedate(createDate);
				order.setDeliverydate(deliveryDate);
				order.setId(id);
				order.setMobile(mobile);
				order.setOrdercode(orderCode);
				order.setPaydate(payDate);
				order.setPost(post);
				order.setReceiver(receiver);
				order.setStatus(status);
				order.setUsermessage(userMessage);
				User user=new UserDAO().get(uid);
				order.setUser(user);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return order;
	}
	/**
	 * 查询所有订单信息
	 * @return
	 */
	public List<Order> list(){
		return list(0,Short.MAX_VALUE);
	}
	public List<Order> list(int start,int count){
		List<Order> orders = new ArrayList<Order>();
		String sql="select * from order_ order by id limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs=ps.executeQuery(sql);
			while(rs.next()) {
				Order order=new Order();
				String orderCode=rs.getString("orderCode");
				String address=rs.getString("address");
				String post=rs.getString("post");
				String receiver=rs.getString("receiver");
				String mobile=rs.getString("mobile");
				String userMessage=rs.getString("userMessage");
				String status=rs.getString("status");
				Date createDate=DateUtil.t2d(rs.getTimestamp("createDate"));
				Date payDate=DateUtil.t2d(rs.getTimestamp("payDate"));
				Date deliveryDate=DateUtil.t2d(rs.getTimestamp("deliverDate"));
				Date confirmDate=DateUtil.t2d(rs.getTimestamp("confirmDate"));
				int uid=rs.getInt("uid");
				int id=rs.getInt("id");
				
				order.setAddress(address);
				order.setConfirmdate(confirmDate);
				order.setCreatedate(createDate);
				order.setDeliverydate(deliveryDate);
				order.setId(id);
				order.setMobile(mobile);
				order.setOrdercode(orderCode);
				order.setPaydate(payDate);
				order.setPost(post);
				order.setReceiver(receiver);
				order.setStatus(status);
				User user=new UserDAO().get(uid);
				order.setUser(user);
				order.setId(id);
				order.setUsermessage(userMessage);
				orders.add(order);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}
	/**
	 * 查询符合用户和状态的所有订单
	 * @param uid
	 * @param excludedStatus
	 * @return
	 */
	public List<Order> list(int uid,String excludedStatus){
		return list(uid,excludedStatus,0,Short.MAX_VALUE);
	}
	public List<Order> list(int uid,String excludedStatus,int start,int count){
		List<Order> orders =new ArrayList<Order>();
		String sql="select * from order_ where uid=? and status !=? order by id limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, uid);
			ps.setString(2, excludedStatus);
			ps.setInt(3, start);
			ps.setInt(4, count);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Order order=new Order();
				String orderCode=rs.getString("orderCode");
				String address=rs.getString("address");
				String post=rs.getString("post");
				String receiver=rs.getString("receiver");
				String mobile=rs.getString("mobile");
				String userMessage=rs.getString("userMessage");
				String status=rs.getString("status");
				Date createDate=DateUtil.t2d(rs.getTimestamp("createDate"));
				Date payDate=DateUtil.t2d(rs.getTimestamp("payDate"));
				Date deliveryDate=DateUtil.t2d(rs.getTimestamp("deliverDate"));
				Date confirmDate=DateUtil.t2d(rs.getTimestamp("confirmDate"));
				int id=rs.getInt("id");
				
				order.setAddress(address);
				order.setConfirmdate(confirmDate);
				order.setCreatedate(createDate);
				order.setDeliverydate(deliveryDate);
				order.setId(id);
				order.setMobile(mobile);
				order.setOrdercode(orderCode);
				order.setPaydate(payDate);
				order.setPost(post);
				order.setReceiver(receiver);
				order.setStatus(status);
				order.setUsermessage(userMessage);
				User user=new UserDAO().get(uid);
				order.setUser(user);
				order.setId(id);
				orders.add(order);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}
}
