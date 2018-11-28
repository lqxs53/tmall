package tmall.bean;

import java.util.Date;
import java.util.List;
/**
 * ²úÆ·Àà
 * @author Administrator
 *
 */
public class Product {
	private int id;
	private String name;
	private String subtitle;
	private float orignalprice;
	private float promoteprice;
	private int stock;
	private Date createdate;
	private Category category;
	
	private ProductImage firstProductImage;
	private List<ProductImage> productImages;
	private List<ProductImage> productSingleImages;
	private List<ProductImage> productDetailImages;
	private int reviewCount;
	private int saleCount;
	
	public void setId(int id) {
		this.id=id;
	}
	public void setName(String name) {
		this.name=name;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle=subtitle;
	}
	public void setOrignalprice(float orignalprice) {
		this.orignalprice=orignalprice;
	}
	public void setPromoteprice(float promoteprice) {
		this.promoteprice=promoteprice;
	}
	public void setStock(int stock) {
		this.stock=stock;
	}
	public void setCreatedate(Date createdate) {
		this.createdate=createdate;
	}
	public void setCategory(Category category) {
		this.category=category;
	}
	public int getId() {
		return id;
	};
	public String getName(){
		return name;
	};
	public String getSubtitle() {
		return subtitle;
	};
	public float getOrignalprice() {
		return orignalprice;
	};
	public float getPromoteprice() {
		return promoteprice;
	};
	public int getStock() {
		return stock;
	};
	public Date getCreatedate() {
		return createdate;
	};
	public Category getCategory() {
		return category;
	}
	public ProductImage getFirstProductImage() {
		return firstProductImage;
	}
	public void setFirstProductImage(ProductImage firstProductImage) {
		this.firstProductImage = firstProductImage;
	}
	public List<ProductImage> getProductImages() {
		return productImages;
	}
	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}
	public List<ProductImage> getProductSingleImages() {
		return productSingleImages;
	}
	public void setProductSingleImages(List<ProductImage> productSingleImages) {
		this.productSingleImages = productSingleImages;
	}
	public List<ProductImage> getProductDetailImages() {
		return productDetailImages;
	}
	public void setProductDetailImages(List<ProductImage> productDetailImages) {
		this.productDetailImages = productDetailImages;
	}
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	public int getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	};
}
