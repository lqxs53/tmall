package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

public class ProdcutImageDAO {
	public static final String type_single="type_single";
	public static final String type_detail="type_detail";
	/**
	 * 查询图片数量
	 * @return
	 */
	public int getTotal() {
		int total=0;
		try(Connection c = DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="select count(1) from productimage";
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
	 * 增加图片
	 * @param productimage
	 */
	public void add(ProductImage productimage) {
		String sql = "insert into productimage values (null,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, productimage.getProduct().getId());
			ps.setString(2, productimage.getType());
			ps.execute();
			
			ResultSet rs=ps.getGeneratedKeys();
			if(rs.next()) {
				int id=rs.getInt(1);
				productimage.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除图片
	 * @param id
	 */
	public void delete(int id) {
		try(Connection c = DBUtil.getConnection();Statement s =c.createStatement();){
			String sql = "delete from productimage where id="+id;
			s.executeQuery(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新图片
	 * @param productimage
	 */
	public void update(ProductImage productimage) {
		
	}
	/**
	 * 获取图片信息
	 * @param id
	 * @return
	 */
	public ProductImage get(int id) {
		ProductImage productimage = new ProductImage();
		try(Connection c=DBUtil.getConnection();Statement s = c.createStatement();){
			String sql = "select * from productimage where id="+id;
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				int pid=rs.getInt("pid");
				String type=rs.getString("type");
				Product product = new ProductDAO().get(id);
				productimage.setId(pid);
				productimage.setType(type);
				productimage.setProduct(product);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return productimage;
	}
	/**
	 * 查询图片列表
	 * @param p
	 * @param type
	 * @return
	 */
	public List<ProductImage> list(Product p,String type){
		return list(p,type,0,Short.MAX_VALUE);
	}
	public List<ProductImage> list(Product p,String type,int start,int count){
		List<ProductImage> productimages = new ArrayList<ProductImage>();
		String sql="select * from productimage where pid=? and type=? order by id desc limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql)){
			ps.setInt(1, p.getId());
			ps.setString(2, type);
			ps.setInt(3, start);
			ps.setInt(4, count);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ProductImage pi = new ProductImage();
				int id = rs.getInt(1);
				pi.setId(id);
				pi.setProduct(p);
				pi.setType(type);
				productimages.add(pi);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return productimages;
	}
}
