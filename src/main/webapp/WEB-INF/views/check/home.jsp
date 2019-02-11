<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check's Home</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/check_home.css?v=2">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/check.css?v=<%=System.currentTimeMillis() %>">
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
<!-- bootstrap CSS -->
<link
	href="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
	rel="stylesheet">

<script type="text/javascript"
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.9.0/jquery.js"></script>
<script
	src="${pageContext.request.contextPath }/resources/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath }/resources/js/check_home.js?v=<%=System.currentTimeMillis() %>"></script>
<!-- bootstrap JS -->
<script
	src="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>

<style type="text/css">
</style>

<c:if
	test="${sessionScope.loginUser=='' || sessionScope.loginUser==null }">
	<c:redirect url="login_view" />
</c:if>

</head>
<body style="background-color: #f5fff2;">
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
				<div id="content">
					<div class="member-boards-view">
						<div class="check-boards-page">
							<div>
								<div class="home-container" style="height: 100%;">
									<div class="sticky-home-menu">
										<nav class="home-left-container">
											<div class="home-left-menu sidenav">
												<div class="sidenav-personal">
													<a href="#" class="sidenav-home">Boards...</a> <a href="#"
														class="personal-team"> <span class="personal-team-id"
														style="display: none;">-1</span> Personal
													</a>
												</div>
												<hr class="sidenav-hr-line">
												<div class="sidenav-team">
													<a class="divide-team-line">team</a>
												</div>
												<div class="sidenav-add-team">
													<div class="create-team-input-container">
														<input type="text" class="form-control create-team-input"
															placeholder="Enter team title...">
													</div>
													<div class=""></div>
													<div>
														<a class="home-logout">logout</a>
													</div>
												</div>
											</div>
											<!-- home-left-menu, sidenav -->
										</nav>
										<!-- home-left-container -->
									</div>
									<div class="all-boards">
										<div class="content-all-boards">


											<div class="boards-page-board-section personal-section">
												<div class="boards-page-board-section-header">
													<div class="boards-page-board-section-header-icon">
														<span class="icon-lg icon-member"></span>
													</div>
													<div class="boards-page-board-section-header-container">
														<!-- 
														<div class="check-invite-container">
															<div class="check-invite-show">
																<a class="button">invite</a>
															</div>
															<div class="check-invite-list-menu"
																style="display: none;" align="right">
																<table class="table-hover text-center check-invitetable">
																	<thead
																		style="text-align: center; background-color: #ea6153; font-size: 14px; line-height: 20px; color: #3b3b3b; -webkit-font-smoothing: antialiased; font-smoothing: antialiased; font-smoothing: antialiased; font-family: Helvetica;">
																		<tr>
																			<td width="35%" height="35%"><font color="white">
																					<input type="checkbox" id="allCheck" />
																			</font></td>
																			<td><font color="white">NickName</font></td>
																			<td><font color="white">Email</font></td>
																		</tr>
																	</thead>
																	<tbody class="check-invitetable-tbody"
																		style="text-align: center; background-color: #e9e9e9;">
																		<tr>
																			<td><input type="checkbox" name="user_Checkbox"
																				class="tr_check"></td>
																			<td></td>
																			<td></td>
																		</tr>
																	</tbody>
																</table>
																<br>
																<button type="button" class="btn" onclick="addbtn()">초대하기</button>
															</div>
														</div>
														 -->
														<h3 class="boards-page-board-section-header-name">Personal
															Boards</h3>
													</div>
													<span class="board-page-team-id team-id-personal"
														style="display: none;"></span>
												</div>
												<!-- boards-page-board-section-header -->
												<div class="board-page-board-section-list">

													<div class="board-page-board-section-list-item">
														<!-- 해당 보드 아이디와 팀 아이디 -->
														<span class="board-page-board-id" style="display: none;"></span>
														<!-- ----------------- -->
														<div class="board-section-list-item-container">
															<div class="board-title-item">
																<div class="board-title-details is-baged">
																	<div class="board-title-details-name">how to use
																		check</div>
																</div>
															</div>
														</div>
													</div>
													<div
														class="board-page-board-section-list-item board-mod-add-item">
														<div class="board-section-list-item-container">
															<div class="board-title-mod-add">
																<div class="bold">
																	<div class="bold-item">Create new Board...</div>
																</div>
															</div>
															<div class="board-title-create-add"
																style="display: none;">
																<input type="text"
																	class="form-control board-title-input"
																	placeholder="Enter Board title..">
															</div>
														</div>
													</div>
												</div>
												<!-- board-page-board-section-list -->
											</div>
											<!-- boards-page-board-section -->


										</div>
										<!-- content-all-boards -->
									</div>
									<!-- all-boards -->
								</div>
								<!-- home-container -->
							</div>
						</div>
						<!-- check-boards-page -->
					</div>
					<!-- member-boards-view -->
				</div>
				<!-- id:content -->
			</div>
			<!-- surface -->

			<!-- 
			<div class="window-overlay" align="center">
				<div class="window">
					<div class="window-wrapper">
						<div>
							<form class="create-board-form">
								<div class="create-board-title">
									<button type="button" class="hide-dialog-trigger unstyled-button">
										<span class="icon-sm icon-close"></span>
									</button>
									<div>
										<input placeholder="Add board title" class="subtitle-input">
									</div>
									<div>
										<button type="button" class="subtitle-chooser-trigger unstyled-button">
											<span>No team</span>
										</button>
									</div>
								</div>
								<button class="btn btn-primary" type="button">Create Board</button>
							</form>
						</div>
					</div>
				</div>
			</div>
			 -->
		</div>
		<!-- classic -->
	</div>
	<!-- classic-body -->


</body>
</html>