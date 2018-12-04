<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		
		
			<script type="text/javascript">
			function del(string ){ 
			if(!confirm("确认要"+string+"吗?")){ 
			window.event.returnValue = false; 
				} 
			} 
			</script>
		
		
		
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>
	<body>

			<%@include file="/jsp/head.jsp"%>
		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">
					<!-- bean.data为order的列表 未显示订单时间 -->
					  <c:forEach items="${bean.data }" var="o">
						<tbody>
							<tr class="success">
								<th colspan="2">订单编号:${o.oid }</th>
								<th colspan="1">
										<c:if test="${o.state == 0 }"><a href="${pageContext.request.contextPath }/order?method=getById&oid=${o.oid}">去付款</a></c:if>
										<c:if test="${o.state == 1 }"><a href="${pageContext.request.contextPath }/order?method=getById&oid=${o.oid}">已付款，查看订单</a></c:if>
										<c:if test="${o.state == 2 }"><a href="${pageContext.request.contextPath }/order?method=getById&oid=${o.oid}">已发货，查看订单</a></c:if>
										<c:if test="${o.state == 3 }"><a href="${pageContext.request.contextPath }/order?method=getById&oid=${o.oid}">已完成，查看订单</a></c:if>
								</th>
								<th colspan="1">小计:${o.total }元 </th>
								<th colspan="1"><a href="${pageContext.request.contextPath }/order?method=delete&oid=${o.oid}&pagenumber=${bean.pageNumber }&state=${o.state} " onclick="return del('删除订单')">删除订单</a></th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>金额</th>
							</tr>
							<!--order列表中的order对象的items属性为为订单项列表 -->
							<c:forEach items="${o.items }" var="oi">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${pageContext.request.contextPath}/${oi.product.pimage}" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank">${oi.product.pname }</a>
								</td>
								<td width="20%">
									￥${oi.product.shop_price }
								</td>
								<td width="10%">
									${oi.count }
								</td>
								<td width="15%">
									<span class="subtotal">￥${oi.sum }</span>
								</td>
							</tr>
							</c:forEach>
						</tbody>
						</c:forEach>
					</table>
				</div>
			</div>
			<%@include file="/jsp/page.jsp" %>
		</div>

		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		
	</body>

</html>