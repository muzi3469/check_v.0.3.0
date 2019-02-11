<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>invite</title>



<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/check.css?v=<%=System.currentTimeMillis() %>">

<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">

<script
	src="${pageContext.request.contextPath }/resources/js/a_suggest.js"></script>
<script
	src="${pageContext.request.contextPath }/resources/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath }/resources/js/check.js?v=<%=System.currentTimeMillis() %>"></script>

<script src="${pageContext.request.contextPath }/resources/js/invite.js"></script>
<!-- test 검색-->


<!-- jQuery  -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- bootstrap JS -->
<script
	src="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>



<script type="text/javascript"
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.9.0/jquery.js"></script>


<!-- bootstrap CSS -->
<link
	href="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
	rel="stylesheet">

<style type="text/css">
a.button {
	font-size: 14px;
	font-weight: 600;
	color: white;
	padding: 6px 25px 0px 20px;
	margin: 10px 8px 20px 0px;
	display: inline-block;
	float: center;
	text-decoration: none;
	width: 95px;
	height: 30px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	background-color: #6fa028;
	-webkit-box-shadow: 0 3px rgba(20, 0, 12, .09);
	-moz-box-shadow: 0 3px rgba(20, 0, 12, .09);
	box-shadow: 0 3px rgba(20, 0, 12, .09);
	transition: all 0.1s linear 0s;
	top: 0px;
	position: relative;
}

a.button:hover {
	top: 3px;
	background-color: #2f8b2e;
	-webkit-box-shadow: none;
	-moz-box-shadow: none;
	box-shadow: none;
}

.box {
	margin: 100px auto;
	width: 300px;
	height: 50px;
}

.container-3 {
	width: 300px;
	vertical-align: middle;
	white-space: nowrap;
	position: relative;
}

.container-3 input#search {
	width: 300px;
	height: 50px;
	background: #2b303b;
	border: none;
	font-size: 10pt;
	float: left;
	color: #262626;
	padding-left: 45px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	color: #fff;
}

.container-3 input#search::-webkit-input-placeholder {
	color: #65737e;
}

.container-3 input#search:-moz-placeholder { /* Firefox 18- */
	color: #65737e;
}

.container-3 input#search::-moz-placeholder { /* Firefox 19+ */
	color: #65737e;
}

.container-3 input#search:-ms-input-placeholder {
	color: #65737e;
}

.container-3 .icon {
	position: absolute;
	top: 50%;
	margin-left: 17px;
	margin-top: 17px;
	z-index: 1;
	color: #4f5b66;
	-webkit-transition: all .55s ease;
	-moz-transition: all .55s ease;
	-ms-transition: all .55s ease;
	-o-transition: all .55s ease;
	transition: all .55s ease;
}

.container-3 input#search:focus, .container-3 input#search:active {
	outline: none;
}

.container-3:hover .icon {
	margin-top: 16px;
	color: #93a2ad;
	-webkit-transform: scale(1.5); /* Safari and Chrome */
	-moz-transform: scale(1.5); /* Firefox */
	-ms-transform: scale(1.5); /* IE 9 */
	-o-transform: scale(1.5); /* Opera */
	transform: scale(1.5);
}
</style>


<!--  invite(초대 눌렀을 때 숨겨졌다가 나타남 -> db에 저장되어 있는 모든 값들을 불러냄  -->
<script type="text/javascript">
	function invitebtn() {
		let nickname = $("#nickname").val();
		let email = $("#email").val();
		let check = $("#check").val();
		$
				.ajax({

					url : "memberlist",
					dataType : "JSON",

					success : function(data) {
						console.log(data);
						$("#invitetable > tbody").empty();
						$
								.each(
										data,
										function(key, value) {
											console.log('key:' + key + ' / '
													+ 'value:' + value);
											console.log(value.m_email);

											$("#invitetable > tbody:last")
													.append(
															"<tr><td><input type=\"checkbox\" name=\"check-"+value.m_email+"\" value=\"check-"+value.m_nickname+"\"></td><td>"
																	+ value.m_nickname
																	+ "</td><td>"
																	+ value.m_email
																	+ "</td></tr>")
										});
					},
					error : function() {
						console.log("error");
					}
				});
	}

	//add 버튼 클릭시 체크된 row의 값을 가지고 온다. 
	$("#addbtn2").click(function() {
		console.log("addbtn실행");
		var checkbox = $('input[name="user_CheckBox"]:checked');

		console.log("checkbox" + checkbox);

		var rowData = new Array();
		var toArr = new Array();

		//체크된 체크바스의 값을 가지고온다.

		checkbox.each(function(i) {

			// checkbox.parent() : checkbox의 부모는 <td>이다.
			// checkbox.parent().parent() : <td>의 부모이므로 <tr>이다.
			var tr = checkbox.parent().parent().eq(i);
			var td = tr.children();

			// 체크된 row의 모든 값을 배열에 담는다.
			rowData.push(tr.text());

			// td.eq(0)은 체크박스 이므로  td.eq(1)의 값부터 가져온다.

			var name = td.eq(1).text() + ", ";
			var email = td.eq(2).text() + ", ";

			// 가져온 값을 배열에 담는다.

			tdArr.push(name);
			tdArr.push(email);

			console.log("name : " + name);
			console.log("email : " + email);
		});

	});

	$(function() { //전체선택 체크박스 클릭
		$("#allCheck").click(function() { //만약 전체 선택 체크박스가 체크된상태일경우 
			if ($("#allCheck").prop("checked")) { //해당화면에 전체 checkbox들을 체크해준다 
				$("input[type=checkbox]").prop("checked", true); // 전체선택 체크박스가 해제된 경우
			} else { //해당화면에 모든 checkbox들의 체크를해제시킨다. 
				$("input[type=checkbox]").prop("checked", false);
			}
		})
	})

	//체크된 값 넘어오나 
	$(function() {
		$("#addbtn").click(function() {
			$(".tr_check:checked").each(function(idx, row) {
				var record = $(row).parents("tr");
				console.log(record[0].innerText);
			});
		});
	});
</script>




</head>
<body>
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
	<form>


		<div align="right" id="show">
			<a class="button" onclick="invitebtn()">invite</a>
		</div>

		<div class="menu" style="display: none;" align="right">


			<table id="invitetable" class="table-hover text-center">
				<thead
					style="text-align: center; background-color: #ea6153; font-size: 14px; line-height: 20px; color: #3b3b3b; -webkit-font-smoothing: antialiased; font-smoothing: antialiased; font-smoothing: antialiased; font-family: Helvetica;">
					<tr>

						<td width="35%" height="35%"><font color="white"> <input
								type="checkbox" id="allCheck" /></font></td>
						<td><font color="white">NickName</font></td>
						<td><font color="white">Email</font></td>



					</tr>

				</thead>

				<tbody style="text-align: center; background-color: #e9e9e9;">
					<td><input type="checkbox" name="user_Checkbox" class="tr_check"></td>
					<td></td>
					<td></td>

				</tbody>

			</table>



			<br>


			<button type="button" class="btn" onclick="addbtn()">초대하기</button>

		</div>



	</form>


	<!-- 이름 검색 (자동 추천 기능) -->

 <!--  
	<form name="frm">
		<div class="box menu" style="display: none;">
			<div class="container-3">
				<span class="icon" onclick="searchbtn()"><i
					class="fa fa-search"></i></span> <input type="search" id="search"
					placeholder="이름 혹은 이메일을 입력하세요..." />
			</div>
		</div>
	</form>
-->


</body>
</html>