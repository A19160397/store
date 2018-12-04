 <%@ page language="java" pageEncoding="UTF-8"%>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
	</HEAD>
	
	<body>
		<!--  -->
		<form id="userAction_save_do" name="Form1" action="${pageContext.request.contextPath }/editProduct" method="post"  enctype="multipart/form-data">
			<!--mutipart不会识别方法后面的路径 <input type="hidden" name="method" value="edit"> -->
			&nbsp;
			<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
						height="26">
						<strong><STRONG>编辑商品</STRONG>
						</strong>
					</td>
				</tr>

				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品名称：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="pname" value="${pro.pname }" id="userAction_save_do_logonName" class="bg"/>
					
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						是否热门：
					</td>
					<td class="ta_01" bgColor="#ffffff">
					<!-- select js的id -->
						<input id="select" type="hidden" value="${pro.is_hot }" > 
						<select name="is_hot" id="is_hot" >
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						市场价格：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="market_price"  id="userAction_save_do_logonName" class="bg" value="${pro.market_price }"/>
					
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商城价格：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="shop_price" id="userAction_save_do_logonName" class="bg" value="${pro.shop_price }"/>
					
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品图片：
					</td>
					<td class="ta_01" bgColor="#ffffff" >
						<input type="file" name="upload"value="${pro.pimage }"/>
					
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品ID：
					</td>
					<td class="ta_01" bgColor="#ffffff" >
						${pro.pid } 
				<input type="hidden" name="pid"value="${pro.pid }"/>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						所属分类：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<input id="select_1" type="hidden" value="${pro.category.cid }" >
						<select name="cid" id="cid" >
						 <c:forEach items="${list }" var="c">
								<option value="${c.cid }">${c.cname }</option>
						</c:forEach>	
						</select>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品描述：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<textarea name="pdesc" rows="5" cols="30">${pro.pdesc }</textarea>
					</td>
				</tr>
			<tr>
					<td class="ta_01" style="WIDTH: 100%" align="center"
						bgColor="#f5fafe" colSpan="4">
						<button type="submit" id="userAction_save_do_submit" value="确定" class="button_ok">
							&#30830;&#23450;
						</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<button type="reset" value="重置" class="button_cancel">&#37325;&#32622;</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<INPUT class="button_ok" type="button" onclick="history.go(-1)" value="返回"/>
						<span id="Label1"></span>
					</td>
				</tr>
			</table>
		</form>
	</body>
	
	<script type="text/javascript">
	$(document).ready(function(){  
	     var key=$("#select").val();
	     //根据值让option选中 
	     $("#is_hot option[value='"+key+"']").attr("selected","selected"); 
	}); 
	$(document).ready(function(){  
	     var key=$("#select_1").val();
	     //根据值让option选中 
	     $("#cid option[value='"+key+"']").attr("selected","selected"); 
	}); 
		</script>

</HTML>