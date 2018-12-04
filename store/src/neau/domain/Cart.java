package neau.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//购物车
public class Cart {
	private Double total=0.00;
	private Map<String,CartItem> itemMap=new HashMap<String,CartItem>();
	
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Map<String, CartItem> getItemMap() {
		return itemMap;
	}
	public void setItemMap(Map<String, CartItem> itemMap) {
		this.itemMap = itemMap;
	}
	/**
	 * 
	 * 
	 * 增加商品得到封装好的购物项，放入购物车里
	 */
	public void add2cart(CartItem item) {
		/**
		 * 1.从购物车中查询，是否已有同商品购物项，如果有，合并成新的购物项，如果没有，直接放入map中
		 * 2.修改总金额
		 */
		String pid=item.getProduct().getPid();
		if(itemMap.containsKey(pid)) {
			CartItem oitem=itemMap.get(pid);
			oitem.setCount(item.getCount()+oitem.getCount());	
			//itemMap.put(pid,oitem);
		}
		else {
			itemMap.put(pid,item);
		}
		total+=item.getSum();
			
	}
	/**
	 * 删除商品
	 * 1.得到移除商品的id，移除
	 * 2.修改总金额
	 */
	public void deleteItem(String id) {
		CartItem item=itemMap.remove(id);
		total-=item.getSum();
	}
	/**
	 * 
	 * 清空购物车
	 * 清空map集合
	 * 修改金额为0.0
	 */
	public void clear() {
		itemMap.clear();
		total=0.0;
		
	}
	/**
	 * 
	 * 得到购物车中的所有购物项
	 * @return
	 */
	
	
	public Collection<CartItem> getCartItems(){
		
		return itemMap.values();
	}
}
