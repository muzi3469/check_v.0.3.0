<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check's Board</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/check.css?v=<%=System.currentTimeMillis() %>">
<script
	src="${pageContext.request.contextPath }/resources/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath }/resources/js/check_login.js?v=<%=System.currentTimeMillis() %>"></script>
<style type="text/css">
</style>

</head>
<body style="background-color: #339933;">

	<div id="classic-body">
		<div id="classic">
			<div id="surface">
				<div id="header" class="header" align="center">
					<div id="header-center" align="center">
						<div class="header-logo check-home-via-logo">
							<img alt="logo"
								src="${pageContext.request.contextPath }/resources/img/check logo_white.png"
								class="header-logo check-img">
						</div>
						<!-- header-logo, check-home-via-logo -->
					</div>
					<!-- header-center -->
				</div>
				<!-- menu -->
				<div id="login-content">
					<div class="login-wrapper">
						<div class="login-canvas">
							<div class="id-pwd-wrapper">
								<!--  style="background-image:url('${pageContext.request.contextPath }/resources/img/check login.jpg'); background-repeat:no-repeat;"  -->
								<div>
									<div class="login-ment">LOGIN IS NECESSARY</div>
									<div class="login-img"
										style="background-image:url('${pageContext.request.contextPath }/resources/img/check login.png'); background-repeat:no-repeat;">
									</div>
								</div>
								<form action="doLogin" method="post">
								<div class="login-id-input">
									<input type="text" name="email" class="login-id-input-text form-control" placeholder="E-mail adress" onkeyup="checkinput()"/>
								</div>
								<div class="login-pwd-input">
									<input type="password" name="pwd" class="login-pwd-input-password form-control" placeholder="password" onkeyup="checkinput()"/>
								</div>
								<!-- 
								<div class="login-checkbox">
									<input type="checkbox" name="isRememberEmail" class="isRememberEmail">remember your email</input>							
								</div>
								 -->
								<div class="login-login-div">
									<input type="submit" class="login-login-btn form-control" value="LOGIN" disabled="disabled"/>								
								</div>	
								</form>
								<div class="login-signup-href">
									If you are not our member,<br>
									Please <strong><a href="register_view">Sign up</a></strong> our page.<br>
									Then you can enjoy our service.
								</div>
							</div>
						</div>
					</div>
					<!-- login-wrapper -->
				</div>
				<!-- id:content -->
			</div>
			<!-- surface -->
		</div>
		<!-- classic -->
	</div>
	<!-- classic-body -->


</body>
</html>