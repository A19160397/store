package neau.domain;
		//订单项
public class CartItem {
	//商品，数量，购物项的小计
	private Product product;
	//小计
	private Double sum;
	//integer类型
	private Integer count;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	//得到小计
	public double getSum() {
		
		return product.getShop_price()*count;
	}
	/**
	 * 
	 * 方便封装成购物项
	 * @param product
	 * @param count
	 */
	//构造函数
	public CartItem(Product product,Integer count) {
		//super();
		this.count=count;
		this.product=product;
	}

}
