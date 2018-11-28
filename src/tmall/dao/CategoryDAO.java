package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.util.DBUtil;

public class CategoryDAO {
	/**
	 * 获取总数
	 * @return
	 */
	public int getTotal() {
		int total = 0;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			String sql = "select count(1) from Category";
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
	 * 增加属性
	 * @param category
	 */
	public void add(Category category) {
		String sql = "insert into category values (null,?)";
		try(Connection c=DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, category.getName());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				int id=rs.getInt(1);
				category.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改属性
	 * @param category
	 */
	public void update(Category category) {
		String sql="update category set name=? where id=?";
		try(Connection c= DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, category.getName());
			ps.setInt(2,category.getId());
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
			String sql="delete from category where id="+id;
			s.execute(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public Category get(int id) {
		Category category =null;
		try(Connection c=DBUtil.getConnection(); Statement s = c.createStatement();){
			String sql="select id,name from category where id = "+id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				category = new Category();
				String name = rs.getString(2);
				category.setName(name);
				category.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return category;
	}
	/**
	 * 分页查询
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Category> list(int start,int count){
		List<Category> categorys = new ArrayList<Category>();
		String sql = "select id,name from category order by id desc limit ?,?";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Category category = new Category();
				String name = rs.getString(2);
				int id = rs.getInt(1);
				category.setId(id);
				category.setName(name);
				categorys.add(category);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return categorys;
	}
}
