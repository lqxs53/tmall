package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ProductDAO {
	/**
	 * 查询该属性总产品数
	 * @param cid
	 * @return
	 */
	public int getTotal(int cid) {
		int total=0;
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="select count(1) from product where cid="+cid;
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
	 * 增加产品
	 * @param product
	 */
	public void add(Product product) {
		String sql="insert into product values (null,?,?,?,?,?,?,?)";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, product.getName());
			ps.setString(2, product.getSubtitle());
			ps.setFloat(3, product.getOrignalprice());
			ps.setFloat(4, product.getPromoteprice());
			ps.setInt(5, product.getStock());
			ps.setInt(6, product.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(product.getCreatedate()));
			ps.execute();
			ResultSet rs=ps.getGeneratedKeys();
			while(rs.next()) {
				int id=rs.getInt(1);
				product.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改产品信息
	 * @param product
	 */
	public void update(Product product) {
		String sql="update product set name=?,subTile=?,orignalPrice=?,promotePrice=?,stock=?,cid=?,createDate=? where id=?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, product.getName());
			ps.setString(2, product.getSubtitle());
			ps.setFloat(3, product.getOrignalprice());
			ps.setFloat(4, product.getPromoteprice());
			ps.setInt(5, product.getStock());
			ps.setInt(6, product.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(product.getCreatedate()));
			ps.setInt(8, product.getId());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除产品信息
	 * @param id
	 */
	public void delete(int id) {
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="delete from product where id="+id;
			s.execute(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取产品信息
	 * @param id
	 * @return
	 */
	public Product get(int id) {
		Product product=new Product();
		try(Connection c=DBUtil.getConnection();Statement s=c.createStatement();){
			String sql="select * from product where id="+id;
			ResultSet rs=s.executeQuery(sql);
			if(rs.next()) {
				String name=rs.getString("name");
				String subTitle=rs.getString("subTitle");
				float orignalPrice=rs.getFloat("orignalPrice");
				float promotePrice=rs.getFloat("promotePrice");
				int stock=rs.getInt("stock");
				int cid=rs.getInt("cid");
				Date createDate=DateUtil.t2d(rs.getTimestamp("createDate"));
				product.setId(id);
				product.setName(name);
				product.setSubtitle(subTitle);
				product.setOrignalprice(orignalPrice);
				product.setPromoteprice(promotePrice);
				product.setStock(stock);
				product.setCategory(new CategoryDAO().get(cid));
				product.setCreatedate(createDate);
				setFirstProductImage(product);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return product;
	}
	/**
	 * 查询分类中所有产品
	 * @param cid
	 * @return
	 */
	public List<Product> list(int cid){
		return list(cid,0,Short.MAX_VALUE);
	}
	public List<Product> list(int cid,int start,int count){
		List<Product> products=new ArrayList<Product>();
		String sql="select * from product where cid=? order by id limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Product product=new Product();
				int id=rs.getInt("id");
				String name=rs.getString("name");
				String subTitle=rs.getString("subTitle");
				float orignalPrice=rs.getFloat("orignalPrice");
				float promotePrice=rs.getFloat("promotePrice");
				int stock=rs.getInt("stock");
				Category category=new CategoryDAO().get(cid);
				Date createDate=DateUtil.t2d(rs.getTimestamp("createDate"));
				product.setId(id);
				product.setName(name);
				product.setSubtitle(subTitle);
				product.setOrignalprice(orignalPrice);
				product.setPromoteprice(promotePrice);
				product.setStock(stock);
				product.setCategory(category);
				product.setCreatedate(createDate);
				setFirstProductImage(product);
				products.add(product);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	public List<Product> list(){
		return list(0,Short.MAX_VALUE);
	}
	public List<Product> list(int start,int count){
		List<Product> products=new ArrayList<Product>();
		String sql="select * from product limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Product product =new Product();
				int id=rs.getInt("id");
				String name=rs.getString("name");
				String subTitle=rs.getString("subTitle");
				float orignalPrice=rs.getFloat("orignalPrice");
				float promotePrice=rs.getFloat("promotePrice");
				int stock=rs.getInt("stock");
				int cid=rs.getInt("cid");
				Category category=new CategoryDAO().get(cid);
				Date createDate=DateUtil.t2d(rs.getTimestamp("createDate"));
				product.setId(id);
				product.setName(name);
				product.setSubtitle(subTitle);
				product.setOrignalprice(orignalPrice);
				product.setPromoteprice(promotePrice);
				product.setStock(stock);
				product.setCategory(category);
				product.setCreatedate(createDate);
				setFirstProductImage(product);
				products.add(product);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	/**
	 * 分类和产品对应关系
	 * @param cs
	 */
	public void fill(List<Category> cs) {
		for(Category c:cs) {
			fill(c);
		}
	}
	public void fill(Category c) {
		List<Product> ps=this.list(c.getId());
		c.setProducts(ps);
	}
	public void fillByRow(List<Category> cs) {
		int productNumberEachRow=8;
		for(Category c:cs) {
			List<Product> products=c.getProducts();
			List<List<Product>> productByRow=new ArrayList<>();
			for(int i=0;i<products.size();i+=productNumberEachRow) {
				int size=i+productNumberEachRow;
				size=size>products.size()?products.size():size;
				List<Product> productOfEachRow=products.subList(i,size);
				productByRow.add(productOfEachRow);
			}
			c.setProductsByRow(productByRow);
		}
	}
	public void setFirstProductImage(Product p) {
		List<ProductImage> pis=new ProdcutImageDAO().list(	p, ProdcutImageDAO.type_single);
		if(!pis.isEmpty()) {
			p.setFirstProductImage(pis.get(0));
		}
	}
	public void setSaleAndReviewNumber(Product p) {
		int saleCount=new OrderItemDAO().getSaleCount(p.getId());
		p.setSaleCount(saleCount);
		int reviewCount=new ReviewDAO().getCount(p.getId());
		p.setReviewCount(reviewCount);
	}
	public void setSaleAndReviewNumber(List<Product> ps) {
		for(Product p:ps) {
			setSaleAndReviewNumber(p);
		}
	}
	public List<Product> search(String keyword,int start,int count){
		List<Product> products=new ArrayList<Product>();
		if(null==keyword||0==keyword.trim().length()) 
			return products;
		String sql="select * from product where name like ? limit ?,?";
		try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql);){
			ps.setString(1, "%"+keyword+"%");
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Product product=new Product();
				int id=rs.getInt("id");
				String name=rs.getString("name");
				String subTitle=rs.getString("subTitle");
				float orignalPrice=rs.getFloat("orignalPrice");
				float promotePrice=rs.getFloat("promotePrice");
				int stock=rs.getInt("stock");
				int cid=rs.getInt("cid");
				Date createDate=DateUtil.t2d(rs.getTimestamp("createDate"));
				product.setId(id);
				product.setName(name);
				product.setSubtitle(subTitle);
				product.setOrignalprice(orignalPrice);
				product.setPromoteprice(promotePrice);
				product.setStock(stock);
				product.setCreatedate(createDate);
				Category category=new CategoryDAO().get(cid);
				product.setCategory(category);
				setFirstProductImage(product);
				products.add(product);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
}
