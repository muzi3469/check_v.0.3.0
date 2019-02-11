<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check's Board</title>
<link rel="icon" href="/favicon.ico?v=2" type="image/x-icon">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/check.css?v=<%=System.currentTimeMillis() %>">
<script
	src="${pageContext.request.contextPath }/resources/js/jquery.min.js"></script>
	
	
<script
	src="${pageContext.request.contextPath }/resources/js/check.js?v=<%=System.currentTimeMillis() %>"></script>
<script
	src="${pageContext.request.contextPath }/resources/js/check_chat.js?v=<%=System.currentTimeMillis() %>"></script>


	<c:if test="${sessionScope.loginUser=='' || sessionScope.loginUser==null }">
		<c:redirect url="login_view"></c:redirect>
	</c:if>
	<c:if test="${sessionScope.boardid=='' || sessionScope.boardid==null }">
		<c:redirect url="home"></c:redirect>
	</c:if>

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
				<!-- header -->
				<div id="menu">
					<div class="menu board-menu-title">
						<ul>
							<li><a href="#">About Us</a></li>
							<li><a
								href="${pageContext.request.contextPath }/board_calendar">CheckCalendar</a></li>
							<li><a href="${pageContext.request.contextPath }/board_chat">CheckTalk</a></li>
							<li><a href="${pageContext.request.contextPath }/board_main">CheckBoard</a></li>
							<li style="float: left;"><a class="board-title">Untitled
									Board.</a></li>
						</ul>
					</div>
					<!-- menu, board-menu-title -->
				</div>
				<!-- menu -->
				<div id="content">
					<div class="chat-wrapper" align="center">
						<div class="chat-content">
							<div style="width: 600px;" class="chat-contaier">
								<div id="messages" align="left"></div>
								<input type="text" id="check-chat-sender" value="${sessionScope.loginUser }" style="display: none;">
								<input type="text" id="check-chat-bid" value="${sessionScope.boardid }" style="display: none;">
								<div class="input-group">
									<input type="text" id="messageinput" name="messageinput" class="form-control check-chat-message">
									<div class="input-group-btn">
										<button type="button" class="btn btn-default check-chat-sendbtn">보내기</button>
									</div>
								</div>
							</div>
							<div class="drop-container" style="display: none;">
								<div id="dropzone" style="height: 100px" align="center">Drag & Drop Files Here</div>
							</div>
						</div>
					</div>
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