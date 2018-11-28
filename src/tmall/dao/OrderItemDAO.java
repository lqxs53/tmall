package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.User;
import tmall.util.DBUtil;

public class OrderItemDAO {
	/**
	 * 查询订单明细总量
	 * 
	 * @return
	 */
	public int getTotal() {
		int total = 0;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			String sql = "select count(1) from orderitem";
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * 增加订单项
	 * 
	 * @param orderItem
	 */
	public void add(OrderItem orderItem) {
		String sql = "insert into orderitem values (null,?,?,?,?)";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, orderItem.getProduct().getId());
			if (null == orderItem.getOrder())
				ps.setInt(2, -1);
			else
				ps.setInt(2, orderItem.getOrder().getId());

			ps.setInt(3, orderItem.getUser().getId());
			ps.setInt(4, orderItem.getNumber());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				orderItem.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改订单项
	 * 
	 * @param orderItem
	 */
	public void update(OrderItem orderItem) {
		String sql = "update orderitem set pid=?,oid=?,uid=?,number=? where id=?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, orderItem.getProduct().getId());
			if (null == orderItem.getOrder())
				ps.setInt(2, -1);
			else
				ps.setInt(2, orderItem.getOrder().getId());
			ps.setInt(3, orderItem.getUser().getId());
			ps.setInt(4, orderItem.getNumber());
			ps.setInt(5, orderItem.getId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除订单项信息
	 * 
	 * @param id
	 */
	public void delete(int id) {
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			String sql = "delete from orderitem where id=" + id;
			s.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询订单项信息
	 * 
	 * @param id
	 * @return
	 */
	public OrderItem get(int id) {
		OrderItem orderItem = new OrderItem();
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			String sql = "select * from orderitem where id=" + id;
			ResultSet rs = s.executeQuery(sql);
			if (rs.next()) {
				int pid = rs.getInt("pid");
				int oid = rs.getInt("oid");
				int uid = rs.getInt("uid");
				int number = rs.getInt("number");
				orderItem.setId(id);
				orderItem.setNumber(number);
				if (-1 != oid) {
					Order order = new OrderDAO().get(oid);
					orderItem.setOrder(order);
				}
				Product product = new ProductDAO().get(pid);
				orderItem.setProduct(product);
				User user = new UserDAO().get(uid);
				orderItem.setUser(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderItem;
	}
	/**
	 * 查询用户所有订单
	 * @param uid
	 * @return
	 */
	public List<OrderItem> listByUser(int uid){
		return listByUser(uid,0,Short.MAX_VALUE);
	}
	public List<OrderItem> listByUser(int uid, int start, int count) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		String sql = "select * from orderitem where uid=? order by id limit ?,?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, uid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderItem orderItem = new OrderItem();
				int id = rs.getInt("id");
				int pid = rs.getInt("pid");
				int oid = rs.getInt("oid");
				int number = rs.getInt("number");
				orderItem.setId(id);
				orderItem.setNumber(number);
				Product product = new ProductDAO().get(pid);
				orderItem.setProduct(product);
				if (-1 == oid) {
					Order order = new OrderDAO().get(oid);
					orderItem.setOrder(order);
				}
				User user = new UserDAO().get(uid);
				orderItem.setUser(user);
				orderItems.add(orderItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderItems;
	}
	/**
	 * 根据订单查询订单项
	 * @param oid
	 * @return
	 */
	public List<OrderItem> listByOrder(int oid){
		return listByOrder(oid,0,Short.MAX_VALUE);
	}
	public List<OrderItem> listByOrder(int oid,int start,int count){
		List<OrderItem> orderItems=new ArrayList<OrderItem>();
		String sql="select * from orderitem where oid=? order by id limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, oid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs=ps.executeQuery(sql);
			while(rs.next()) {
				OrderItem orderItem=new OrderItem();
				int id=rs.getInt("id");
				int number=rs.getInt("number");
				int pid=rs.getInt("pid");
				Product product=new ProductDAO().get(pid);
				int uid=rs.getInt("uid");
				User user=new UserDAO().get(uid);
				if(-1!=oid) {
				Order order=new OrderDAO().get(oid);
				orderItem.setOrder(order);
				}
				orderItem.setId(id);
				orderItem.setNumber(number);
				orderItem.setProduct(product);
				orderItem.setUser(user);
				orderItems.add(orderItem);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return orderItems;
	}
	/**
	 * 计算所有订单的订单项数量和金额
	 * @param os
	 */
	public void fill(List<Order> os) {
		for(Order o: os) {
			List<OrderItem> ois=listByOrder(o.getId());
			float total=0;
			int totalNumber=0;
			for(OrderItem oi: ois) {
				total+=oi.getNumber()*oi.getProduct().getPromoteprice();
				totalNumber+=oi.getNumber();
			}
			o.setTotal(total);
			o.setOrderItems(ois);
			o.setTotalNumber(totalNumber);
		}
	}
	public void fill(Order o) {
		List<OrderItem> ois=listByOrder(o.getId());
		float total=0;
		for(OrderItem oi:ois) {
			total+=oi.getNumber()*oi.getProduct().getPromoteprice();
		}
		o.setTotal(total);
		o.setOrderItems(ois);
	}
	/**
	 * 根据产品查询订单项
	 * @param pid
	 * @return
	 */
	public List<OrderItem> listByProduct(int pid){
		return listByProduct(pid,0,Short.MAX_VALUE);
	}
	public List<OrderItem> listByProduct(int pid,int start,int count){
		List<OrderItem> orderItems=new ArrayList<OrderItem>();
		String sql="select * from orderitem where pid=? order by id limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, pid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				OrderItem orderItem=new OrderItem();
				int id=rs.getInt("id");
				int number=rs.getInt("number");
				int oid=rs.getInt("oid");
				if(-1!=oid) {
					Order order=new OrderDAO().get(oid);
					orderItem.setOrder(order);
				}
				int uid=rs.getInt("uid");
				User user=new UserDAO().get(uid);
				orderItem.setUser(user);
				Product product=new ProductDAO().get(pid);
				orderItem.setProduct(product);
				orderItem.setId(id);
				orderItem.setNumber(number);
				orderItems.add(orderItem);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return orderItems;
	}
	/**
	 * 查询产品销量
	 * @param pid
	 * @return
	 */
	public int getSaleCount(int pid) {
		int total = 0;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="select sum(number) from orderitem where pid="+pid;
			ResultSet rs=s.executeQuery(sql);
			while(rs.next()) {
				total=rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
}
