<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/check.css?v=<%=System.currentTimeMillis() %>">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/check_register.css?v=<%=System.currentTimeMillis() %>">
<script
	src="${pageContext.request.contextPath }/resources/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath }/resources/js/check.js?v=<%=System.currentTimeMillis() %>"></script>

<!--  추가 코드  -->

<script src="${pageContext.request.contextPath }/resources/js/email.js"></script>


<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600'
	rel='stylesheet' type='text/css'>
<link
	href="http://netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.css"
	rel="stylesheet">


<script type="text/javascript">
	//회원가입 버튼
	function registerbtn() {
		document.getElementById("registerfrom").submit();
	}
</script>

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
				<!-- board-main-content,board-canvas -->
			</div>
			<!-- board-wrapper -->
		</div>
		<!-- id:content -->
	</div>
	<!-- surface -->



	<!--  
    회원 테이블
    email : 이메일
    pwd : 비밀번호
    nickname : 닉네임
    school :학교
    field :분야(관심 있는 분야)
    
  
-->
	<div class="testbox">
		<h1>Registration</h1>
		<form action="register" method="post" id="registerfrom">
			<hr>
			<div>
				<label id="icon" for="name"><i class="icon-envelope "></i></label> <input
					type="text" name="m_email" id="m_email" placeholder="Email"
					maxlength="30" onkeyup="verifyEmail()" requsired />
				<div id="b">
					<a onclick="confirmbtn()" class="button2" name="emailCheck"
						id="emailCheck">중복확인 </a>
				</div>
				<div>
					<a onclick="numberbtn1()" class="button3">확인</a>
				</div>

			</div>
			<div>
				<label id="icon" for="name"><i class="icon-envelope "></i></label> <input
					type="text" name="email_check" id="email_check"
					placeholder="인증 번호를 입력하세요" maxlength="6" required />


			</div>

			<div>
				<label id="icon" for="name"><i class="icon-shield"></i></label> <input
					type="password" name="m_pwd" id="m_pwd" placeholder="Password"
					maxlength="10" required />
			</div>
			<div>
				<label id="icon" for="name"><i class="icon-user"></i></label> <input
					type="text" name="m_nickname" id="m_nickname" placeholder="Name"
					maxlength="10" required />
			</div>

			<label id="icon" for="name"><i class="icon-home"></i></label> <input
				type="text" name="m_school" id="m_school" placeholder="School"
				maxlength="10" required />


			<div style="margin-bottom: 25px">
				<label id="icon" for="name"><i class="icon-search"></i></label> <select
					name="m_field">
					<option value="인문계열">인문계열</option>
					<option value="사회계열">사회계열</option>
					<option value="교육계열">교육계열</option>
					<option value="공학계열">공학계열</option>
					<option value="자연계열">자연계열</option>
					<option value="의학계열">의학계열</option>
					<option value="예체능계열">예체능계열</option>
					<option value="기타">기타...</option>
				</select>

			</div>
			<p></p>
			<a onclick="registerbtn()" class="button">Register</a>
		</form>
	</div>
</body>
</html>