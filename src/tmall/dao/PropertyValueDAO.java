package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

public class PropertyValueDAO {
	/**
	 * 查询属性值数量
	 * @return
	 */
	public int getTotal() {
		int total = 0;
		try(Connection c = DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="select count(1) from propertyvalue";
			ResultSet rs= s.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	/**
	 *增加属性值
	 * @param pv
	 */
	public void add(PropertyValue pv) {
		String sql="insert into propertyvalue values (null,?,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, pv.getProduct().getId());
			ps.setInt(2, pv.getProperty().getId());
			ps.setString(3,pv.getValue());
			ps.execute();
			ResultSet rs=ps.getGeneratedKeys();
			if(rs.next()) {
				int id=rs.getInt(1);
				pv.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 设置属性值
	 * @param pv
	 */
	public void update(PropertyValue pv) {
		String sql="update propertyvalue set pid=?,ptid=?,value=? where id=?	";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, pv.getProduct().getId());
			ps.setInt(2, pv.getProperty().getId());
			ps.setString(3, pv.getValue());
			ps.setInt(4,pv.getId());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除属性值
	 * @param id
	 */
	public void delete(int id) {
		try(Connection c = DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="delete from propertyvalue where id="+id;
			s.executeQuery(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 取对应ID的属性值
	 * @param id
	 * @return
	 */
	public PropertyValue get(int id) {
		PropertyValue pv = new PropertyValue();
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="select * from propertyvalue where id="+id;
			ResultSet rs=s.executeQuery(sql);
			if(rs.next()) {
				int pid = rs.getInt("pid");
				int ptid = rs.getInt("ptid");
				String value = rs.getString("value");
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				pv.setId(id);
				pv.setProduct(product);
				pv.setProperty(property);
				pv.setValue(value);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return pv;
	}
	/**
	 * 根据ptid和pid条件查询
	 * @param ptid
	 * @param pid
	 * @return
	 */
	public PropertyValue get(int ptid,int pid) {
		PropertyValue pv = new PropertyValue();
		try(Connection c=DBUtil.getConnection();Statement s =c.createStatement();){
			String sql ="select * from propertyvalue where ptid="+ptid+" and pid="+pid;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				int id = rs.getInt("id"	);
				String value = rs.getString("value");
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				pv.setId(id);
				pv.setProduct(product);
				pv.setProperty(property);
				pv.setValue(value);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return pv;
	}
	public List<PropertyValue> list(){
		return list(0,Short.MAX_VALUE);
	}
	public List<PropertyValue> list(int start,int count){
		List<PropertyValue> propertyvalues = new ArrayList<PropertyValue>();
		String sql="select * from propertyvalue order by id desc limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps =c.prepareStatement(sql);){
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				PropertyValue propertyvalue=new PropertyValue();
				int id = rs.getInt(1);
				int pid=rs.getInt("pid");
				int ptid=rs.getInt("ptid");
				String value = rs.getString("value");
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				propertyvalue.setId(id);
				propertyvalue.setProduct(product);
				propertyvalue.setProperty(property);
				propertyvalue.setValue(value);
				propertyvalues.add(propertyvalue);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return propertyvalues;
	}
	/**
	 * 
	 * @param p
	 */
	public void init(Product p) {
		List<Property> pts=new PropertyDAO().list(p.getCategory().getId());
		for(Property pt:pts) {
			PropertyValue pv=get(pt.getId(),p.getId());
			if(null==pv) {
				pv = new PropertyValue();
				pv.setProduct(p);
				pv.setProperty(pt);
				this.add(pv);
			}
		}
	}
	/**
	 * 根据Pid查询数据
	 * @param pid
	 * @return
	 */
	public List<PropertyValue> list(int pid){
		List<PropertyValue> propertyvalues = new ArrayList<PropertyValue>();
		String sql="select * from properyvalue where pid=? order by ptid desc";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, pid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PropertyValue propertyvalue = new PropertyValue();
				int id = rs.getInt(1);
				int ptid=rs.getInt("ptid");
				String value = rs.getString("value");
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				propertyvalue.setProduct(product);
				propertyvalue.setProperty(property);
				propertyvalue.setValue(value);
				propertyvalue.setId(id);
				propertyvalues.add(propertyvalue);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return propertyvalues;
	}
}
