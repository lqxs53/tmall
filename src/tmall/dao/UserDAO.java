package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;

public class UserDAO {
	/**
	 * 查询总数
	 * @return
	 */
	public int getTotal() {
		int total = 0;
		String  sql = "select count(1) from user";
		try(Connection c = DBUtil.getConnection();Statement s= c.createStatement();){
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				total = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	/**
	 * 增加用户
	 * @param user
	 */
	public void  add(User user) {
		String sql = "insert into user(id,name,password) values(null,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			while(rs.next()) {
				int id = rs.getInt(1);
				user.setId(id);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	/**
	 * 更新用户信息
	 * @param user
	 */
	public void update(User user) {
		String sql="update user set name=?,password=? where id=?";
		try(Connection c =DBUtil.getConnection(); PreparedStatement ps =c.prepareStatement(sql);){
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getId());
			
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除用户
	 * @param id
	 */
	public void delete(int id) {
		try(Connection c = DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="delete from user where id="+id;
			s.execute(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询单个用户信息
	 * @param id
	 * @return
	 */
	public User get(int id) {
		User user = null;
		try(Connection c = DBUtil.getConnection();Statement s=c.createStatement();){
			String sql = "select * from user where id="+id;
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				user = new User();
				String name=rs.getString("name");
				user.setName(name);
				String password=rs.getString("password");
				user.setPassword(password);
				user.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * 展示所有查询
	 * @return
	 */
	public List<User> list(){
		return list(0,Short.MAX_VALUE);
	}
	/**
	 *  分页查询
	 * @param start
	 * @param count
	 * @return
	 */
	public List<User> list(int start,int count){
		List<User> users = new ArrayList<User>();
		String sql="select * from user order by id desc limit ?,? ";
		try(Connection c = DBUtil.getConnection();PreparedStatement ps =c.prepareStatement(sql);){
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User user = new User();
				int id = rs.getInt(1);
				String name = rs.getString("name");
				user.setName(name);
				String password = rs.getString("password");
				user.setPassword(password);
				user.setId(id);
				
				users.add(user);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	/**
	 * 验证用户是否存在
	 * @param name
	 * @return
	 */
	public boolean isExist(String name) {
		User user = get(name);
		return user!=null;
	}
	/**
	 * 根据用户名查询用户信息
	 * @param name
	 * @return
	 */
	public User get(String name) {
		User user = null;
		String sql="select * from user where name=?";
		try(Connection c =DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				user=new User();
				int id = rs.getInt("id");
				user.setName(name);
				String password = rs.getString("password");
				user.setPassword(password);
				user.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	/**
	 * 验证用户登入密码
	 * @param name
	 * @param password
	 * @return
	 */
	public User get(String name,String password) {
		User user = null;
		String sql="select * from user where name=? and password =?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, name);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				user = new User();
				user.setName(name);
				user.setPassword(password);
				int id=rs.getInt("id");
				user.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
}
