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
<link href="http://netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.css"	rel="stylesheet">
<script
	src="${pageContext.request.contextPath }/resources/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath }/resources/js/jquery-ui.min.js"></script>
<script
	src="${pageContext.request.contextPath }/resources/js/check_board.js?v=<%=System.currentTimeMillis() %>"></script>

<style type="text/css">
</style>
<script type="text/javascript">
	$(document).ready(function(){
		console.log("html script boardid : "+"${sessionScope.boardid}");
	});
</script>

<c:if
	test="${sessionScope.loginUser=='' || sessionScope.loginUser==null }">
	<c:redirect url="login_view" />
</c:if>
<c:if test="${sessionScope.boardid=='' || sessionScope.boardid==null }">
	<c:redirect url="home" />
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
					<div class="board-wrapper">
						<div class="board-canvas">

							<!-- ---------------------------------------------- -->

							<!-- ---------------------------------------------- -->

							<!-- add list div -->
							<div class="check-list-wrapper check-add-list">
							<div class="list-wrapper mod-add is-idle" style="">
								<span class="check-list-id" style="display: none;">-2</span>
								<span class="check-list-index" style="display: none;">0</span>
								<!-- 바로 아래 div는 원래 form이었음 -->
								<div class="mod-add-children">
									<a class="open-add-list" href="#"> <span
										class="placeholder"> Add another list </span> <!-- placeholder -->
									</a>
									<!-- open-add-list -->
									<input class="list-name-input form-control" type="text"
										name="name" placeholder="Enter List title..."
										autocomplete="off" maxlength="512" style="display: none;">
									<div class="list-add-controls u-clearfix"
										style="display: none;">
										<input type="submit"
											class="btn btn-primary mod-list-add-button check-save-edit"
											value="Add List"> <a href="#"
											class="dark-hover check-cancel-edit"></a>
									</div>
									<!-- list-add-controls, u-clearfix -->
								</div>
								<!-- mod-add-children -->
							</div>
							<!-- check-add-list,list-wrapper,mod-add -->
							</div><!-- check-list-wrapper check-add-list -->
						</div>
					</div>
					<!-- board-wrapper -->
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