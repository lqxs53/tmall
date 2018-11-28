package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ReviewDAO {
	/**
	 * 查询总数
	 * @return
	 */
	public int getTotal() {
		int total = 0;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="select count(1) from review";
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
	 * 根据条件查询总数
	 * @param pid
	 * @return
	 */
	public int getTotal(int pid) {
		int total=0;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="select count1) from review where pid=?";
			ResultSet rs= s.executeQuery(sql);
			while(rs.next()) {
				total=rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	/**
	 * 增加
	 * @param review
	 */
	public void add(Review review) {
		String sql="insert into review values (null,?,?,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, review.getContent());
			ps.setInt(2, review.getUser().getId());
			ps.setInt(3, review.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(review.getCreatedate()));
			ps.execute();
			ResultSet rs=ps.getGeneratedKeys();
			if(rs.next()) {
				int id=rs.getInt(1);
				review.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新
	 * @param review
	 */
	public void update(Review review) {
		String sql="update review set content=?,uid=?,pid=?,createdate=? where id=?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, review.getContent());
			ps.setInt(2, review.getUser().getId());
			ps.setInt(3, review.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(review.getCreatedate()));
			ps.setInt(5, review.getId());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除
	 * @param id
	 */
	public void delete(int id) {
		try(Connection c=DBUtil.getConnection();Statement s =c.createStatement();){
			String sql="delete from review where id="+id;
			s.execute(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取信息
	 * @param id
	 * @return
	 */
	public Review get(int id) {
		Review review = new Review();
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="select * from review where id="+id;
			ResultSet rs=s.executeQuery(sql);
			while(rs.next()) {
				String content = rs.getString("content");
				int uid = rs.getInt("uid");
				int pid = rs.getInt("pid");
				Date createDate=DateUtil.t2d(rs.getTimestamp("createdate"));
				Product product=new ProductDAO().get(pid);
				User user=new UserDAO().get(uid);
				
				review.setContent(content);
				review.setUser(user);
				review.setCreatedate(createDate);
				review.setProduct(product);
				review.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return review;
	}
	/**
	 *   查询清单
	 * @param pid
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Review> list(int pid,int start,int count){
		List<Review> reviews = new ArrayList<Review>();
		String sql="select * from review where pid=? order by id desc limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, pid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Review review = new Review();
				int id = rs.getInt("id");
				String content = rs.getString("content");
				int uid = rs.getInt("uid");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createdate"));
				
				Product product = new ProductDAO().get(pid);
				User user = new UserDAO().get(uid);
				
				review.setId(id);
				review.setContent(content);
				review.setProduct(product);
				review.setUser(user);
				review.setCreatedate(createDate);
				
				reviews.add(review);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return reviews;
	}
	
	public List<Review> list(int pid){
		return list(pid,0,Short.MAX_VALUE);
	}
	/**
	 * 查询数量
	 * @param pid
	 * @return
	 */
	public int getCount(int pid) {
		String sql="select count(*) from review where pid=?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, pid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 判断是否存在
	 * @param content
	 * @param pid
	 * @return
	 */
	public boolean isExists(String content,int pid) {
		String sql="select * from review where content=? and pid= ?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, content);
			ps.setInt(2, pid);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
