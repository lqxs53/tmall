package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.DBUtil;

public class PropertyDAO {
	/**
	 * 属性数量
	 * @param cid
	 * @return
	 */
	public int getTotal(int cid	) {
		int total = 0;
		try(Connection c=DBUtil.getConnection();Statement s = c.createStatement();){
			String sql="select count(1) from property where cid="+cid;
			ResultSet rs=s.executeQuery(sql);
			while (rs.next()) {
				total=rs.getInt(1)	;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	/**
	 * 增加属性
	 * @param property
	 */
	public void add(Property property) {
		String sql="insert into property values (null,?,?)";
		try(Connection c=DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
			ps.setInt(1, property.getCategory().getId());
			ps.setString(2, property.getName());
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			while(rs.next()) {
				int id = rs.getInt(1);
				property.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改属性
	 * @param property
	 */
	public void update(Property property) {
		String sql="update property set cid=?,name=? where id=?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, property.getCategory().getId());
			ps.setString(2, property.getName());
			ps.setInt(3, property.getId());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除属性
	 * @param id
	 */
	public void delete(int id) {
		try(Connection c=DBUtil.getConnection();Statement s = c.createStatement();){
			String sql="delete from property where id="+id;
			s.execute(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询单个属性
	 * @param id
	 * @return
	 */
	public Property get(int id) {
		Property property=new Property();
		try(Connection c=DBUtil.getConnection();Statement ps=c.createStatement();){
			String sql="select * from property where id = " +id;
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				String name = rs.getString("name");
				int cid = rs.getInt("cid");
				property.setName(name);
				Category category = new CategoryDAO().get(cid);
				property.setCategory(category);
				property.setId(cid);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return property;
	}
	public List<Property> list(int cid){
		return list(cid,0,Short.MAX_VALUE);
	}
	/**
	 * 返回批量
	 * @param cid
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Property> list(int cid,int start,int count){
		List<Property> propertys = new ArrayList<Property>();
		String sql ="select * from property where cid = ? order by id limit ?,?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(1, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Property property = new Property();
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Category category = new CategoryDAO().get(cid);
				property.setName(name);
				property.setId(id);
				property.setCategory(category);
				propertys.add(property);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return propertys;
	}
}
