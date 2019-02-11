
checkinput = function() {
  // 이메일 검증 스크립트 작성
  var emailVal = $(".login-id-input-text").val();
    var pwd = $(".login-pwd-input-password").val();
  var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
  // 검증에 사용할 정규식 변수 regExp에 저장
  var loginbutton = $(".login-login-btn");

  if (emailVal.match(regExp) != null && pwd!='') {
    console.log('Good!');
    loginbutton.attr("disabled",false);
  }
  else{
    console.log('Error!');
    loginbutton.attr("disabled",true);
  }
};
