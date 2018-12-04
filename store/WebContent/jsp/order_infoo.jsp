<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

	
		<%@include file="/jsp/head.jsp" %>

		<div class="container">
			<div class="row">

				<div style="margin:0 auto;margin-top:10px;width:950px;">
					<strong>订单详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th colspan="2">订单编号:${order.oid }</th>
								<th colspan="1">
									<c:if test="${order.state == 0 }">去付款</c:if>
									<c:if test="${order.state == 1 }">已付款</c:if>
									<c:if test="${order.state == 2 }"><a href="${pageContext.request.contextPath }/order?method=update&oid=${order.oid}" onclick="return del('收货')">已发货,确认收货</a></c:if>
									<c:if test="${order.state == 3 }">已完成</c:if>
								</th>
								<th colspan="2">时间:<fmt:formatDate value="${order.ordertime }" pattern="yyyy-MM-dd HH:mm:ss"/>
								 </th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							
							<c:forEach items="${order.items }" var="oi">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${pageContext.request.contextPath}/${oi.product.pimage}" width="70" height="60">
								</td>
								<td width="30%">
									
									<a  href="${pageContext.request.contextPath }/product?method=getById&pid=${oi.product.pid}">${oi.product.pname}</a>
								</td>
								<td width="20%">
								￥${oi.product.shop_price}
								</td>
								<td width="10%">
									${oi.count}
								</td>
								<td width="15%">
									<span class="subtotal">￥${oi.sum}</span>
								</td>
							</tr>
						</c:forEach>
						<tr class="active">
						<td colspan="1">地址:</td><td colspan="4">${order.address}</td></tr>
						<tr  class="active">
						<td colspan="1">收货人:</td><td colspan="4">${order.name}</td></tr>
						</tr>
						<tr  class="active">
						<td colspan="1">电话:</td><td colspan="4">${order.telephone}</td></tr>
						</tr>
					
						</tbody>
					</table>
				</div>

				<div style="text-align:right;margin-right:120px;">
					商品金额: <strong style="color:#ff6600;">￥${order.total }元</strong>
				</div>

			</div>
		</div>
		
		
	</body>
</html>