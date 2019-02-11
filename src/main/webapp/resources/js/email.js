$(document).ready(function(){
	var number;
	$(".check-img").click(function(e){
	     let tmp = e.target;
	     if($(tmp).hasClass("check-img")){
	       document.location.href = "/check/home";
	     }
	});
});

 

verifyEmail = function() {
	// 이메일 검증 스크립트 작성
	var emailVal = $("#m_email").val();

	var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	// 검증에 사용할 정규식 변수 regExp에 저장

	if (emailVal.match(regExp) != null) {
		console.log("올바른 이메일 형식입니다");
	}
	else {
		console.log("잘 못 된 이메일 형식입니다");
	}
};

//중복확인 하는 버튼

function confirmbtn() {

	let emaildata ={
			email:$("#m_email").val()
	}
	console.log(emaildata);
	$.ajax({
		type:'POST',
		url:'sendMail/auth2',
		contentType:"application/json;",
		async:false,
		data:$("#m_email").val(),
		success:function(result){
			console.log(result);
			number=result;
		}
	});
	alert('이메일 전송');
}

//확인하는 버튼
function numberbtn1() {

	let	numbercheck=$("#email_check").val();
	if(numbercheck == number){
		alert('인증 완료');
	}
	else {
		alert('틀린 인증번호입니다. 인증번호를 다시 입력해주세요 ');
	}

}
