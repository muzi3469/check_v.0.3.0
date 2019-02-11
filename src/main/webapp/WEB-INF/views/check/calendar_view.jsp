<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check's Board</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/fullcalendar.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/check.css?v=<%=System.currentTimeMillis() %>">
<script
	src="${pageContext.request.contextPath }/resources/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath }/resources/js/check_calendar.js?v=<%=System.currentTimeMillis() %>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/fullcalendar.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/js/locale-all.js"></script>
<script
	src="${pageContext.request.contextPath }/resources/js/check.js?v=<%=System.currentTimeMillis() %>"></script>


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
					<div class="calendar-wrapper" align="center">
						<div class="calendar-container">
							<div class="check-calendar-other-content">
							</div>
							<div class="check-calendar-content">
								<p id="calendar"></p>
							</div>
						</div>
						<div class="calendar-view-container">

							<div class="check-calendar-content-view">
								<div class="check-calendar-content-view-name">
									<div class="check-calendar-content-view-name-box">
										<p class="check-calendar-content-view-smalltitle"></p>
										<input type="text" style="display:none" id="calendar-title" value="" class="calendar-input-title" readonly=""></input>
									</div>
								</div>
								<div class="check-calendar-other-view">
									
									<div>
										<p class="check-calendar-other-view-start">시작일</p>
									</div>
									<div>
										<p class="check-calendar-other-view-end">종료일</p>
									</div>
									
								</div>
								<div class="check-calendar-main-view">
									<div class="check-calendar-title-box">								
									</div>
									<div>
										<p id="calendar-start"></p>
									</div>
									<div>
										<p id="calendar-end"></p>
									</div>
									
									</div>
									<div class="check-caldendar-descrption-box">
										<p class="descrption-a"></p>		
										<textarea style="display:none" class="descrption-textarea form-control" rows="15" cols="9" id="calendar-description"></textarea>
									</div>
										<div class="check-calendar-button-box">
										<div class="check-calendar-button-create">
											<button class="check-calendar-button-create-btn form-control" id="create">Create</button>
										</div>
										<div class="check-calendar-button-delete">
											<button class="check-calendar-button-delete-btn form-control" id="delete" disabled="">Delete</button>
										</div>
										<div class="check-calendar-button-modify">
											<button class="check-calendar-button-modify-btn form-control" id="modify" disabled="">Modify</button>
										</div>
										<div class="check-calendar-button-ok">
											<button class="check-calendar-button-ok-btn form-control" id="ok">Okay</button>
										</div>
									</div>								
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