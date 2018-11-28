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
	 * ��ѯ����
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
	 * �����û�
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
	 * �����û���Ϣ
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
	 * ɾ���û�
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
	 * ��ѯ�����û���Ϣ
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
	 * չʾ���в�ѯ
	 * @return
	 */
	public List<User> list(){
		return list(0,Short.MAX_VALUE);
	}
	/**
	 *  ��ҳ��ѯ
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
	 * ��֤�û��Ƿ����
	 * @param name
	 * @return
	 */
	public boolean isExist(String name) {
		User user = get(name);
		return user!=null;
	}
	/**
	 * �����û�����ѯ�û���Ϣ
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
	 * ��֤�û���������
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
