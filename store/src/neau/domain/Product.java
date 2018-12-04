package neau.domain;

import java.util.Date;

public class Product {
	/*
	 * `pid` varchar(32) NOT NULL,
		  `pname` varchar(50) DEFAULT NULL,
		  `market_price` double DEFAULT NULL,
		  
		  `shop_price` double DEFAULT NULL,
		  `pimage` varchar(200) DEFAULT NULL,
		  `pdate` date DEFAULT NULL,
		  
		  `is_hot` int(11) DEFAULT NULL,
		  `pdesc` varchar(255) DEFAULT NULL,
		  `pflag` int(11) DEFAULT NULL,
		  
		  `cid` varchar(32) DEFAULT NULL,
	 */
	private String pid;
	private String pname;
	private double market_price;
	
	private double shop_price;
	private String pimage;
	private Date pdate;
	
	private Integer is_hot;
	private String pdesc;
	private Integer pflag;
	//多对一关系表，分类属性
	private Category category;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public double getMarket_price() {
		return market_price;
	}
	public void setMarket_price(double market_price) {
		this.market_price = market_price;
	}
	public double getShop_price() {
		return shop_price;
	}
	public void setShop_price(double shop_price) {
		this.shop_price = shop_price;
	}
	public String getPimage() {
		return pimage;
	}
	public void setPimage(String pimage) {
		this.pimage = pimage;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
	public Integer getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(Integer is_hot) {
		this.is_hot = is_hot;
	}
	public String getPdesc() {
		return pdesc;
	}
	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}
	public Integer getPflag() {
		return pflag;
	}
	public void setPflag(Integer pflag) {
		this.pflag = pflag;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

}
