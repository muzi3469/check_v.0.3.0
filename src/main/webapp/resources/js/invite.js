$(document).ready(function(){
	$('#show').click(function(e) {
		console.log(e.target);
		$('.check-invite-list-menu').slideToggle("fast");

	});
});

function invitebtn() {
		let nickname = $("#nickname").val();
		let email = $("#email").val();
		let check = $("#check").val();
		$	.ajax({
				url : "memberlist",
				dataType : "JSON",
				success : function(data) {
					console.log(data);
					$("#invitetable > tbody").empty();
					$.each(data,function(key, value) {
							console.log('key:' + key + ' / ' + 'value:' + value);
							console.log(value.m_email);
							$("#invitetable > tbody:last").append(
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
