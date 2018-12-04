package neau.constant;

import org.apache.commons.dbutils.ResultSetHandler;

public interface Constant {
	/**
	 * 用户未激活
	 */
	int USER_IS_NOT_ACTIVE = 0;
	
	
	/**
	 * 用户已激活
	 */
	int USER_IS_ACTIVE = 1;
	
	
	/**
	 * 记住用户名
	 */
	String SAVE_NAME ="ok";

/**
 * 
 * 热门商品
 */
	int PRODUCT_IS_HOT = 1;
/**
 * 商品未下架
 */

	int PRODUCT_IS_UP = 0;
	/**
	 * 下架
	 */
	int PRODUCT_IS_DOWN = 1;
	/**
	 * 支付状态：未支付
	 * 
	 */
	int ORDER_WEIZHIFU=0;
	
	
	/**
	 * 支付状态：已支付||待发货
	 * 
	 */
	int ORDER_YIFUKUAN = 1;
	
	/**
	 * 订单状态：已发货
	 * 
	 */
	int ORDER_YIFAHUO=2;
	
	/**
	 * 订单状态：已完成
	 * 
	 */
	int ORDER_YIWANCHENG=3;



}
