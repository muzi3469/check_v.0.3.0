/**
 *
 */

 var ws;
 var messages = $("#messages");

 $(document).ready(function(){

   //logo 이미지 클릭 -> 홈으로 가기
   $(".check-img").click(function(e){
     let tmp = e.target;
     if($(tmp).hasClass("check-img")){
       document.location.href = "/check/home";
     }
   });

   //웹소켓 객체 만드는 코드, 테스트용
   // ws = new WebSocket("ws://localhost:8085/check/connectWebSocket");

   /*------------web socket-------------*/
   //공개용
   ws = new WebSocket("ws://192.168.0.43:8085/check/connectWebSocket");

   console.log("new Websocket created..")

   if (ws !== undefined && ws.readyState !== WebSocket.CLOSED) {
     console.log("채팅방이 연결되었습니다");
   }

   ws.onopen = function(event) {
     if (event.data === undefined) return;
     console.log("ws.onopen = " + event.data);
   };
   ws.onmessage = function(event) {
     writeResponse(event.data);
   };
   ws.onclose = function(event) {
     console.log("<<< 접속을 끊습니다 >>>");
   }
   /*------------web socket-------------*/

   initChat();

   $(".check-chat-sendbtn").off("click").on("click", function(){
     console.log(ws);
     let msg = $(".check-chat-message").val();
     let sender = $("#check-chat-sender").val();
     let sendbid = $("#check-chat-bid").val();
     console.log("sendbtn click msg : " + msg + ", " + sender);
     ws.send(msg+":::"+sender+":::"+sendbid);
     $(".check-chat-message").val("");
   });//채팅 보내기 버튼

 });//document.ready

function initChat(){
  $.ajax({
    url:"initChat",
    dataType:"json",
    async:false,
    success:function(result){
      $.each(result, function(k, chat){
        let msg = chat.c_message + ":::" + chat.m_email + ":::" + chat.b_bid;
        console.log("msg : " + chat.m_email);
        writeResponse(msg);
      });//each(result)
    },
    error:function(request,status,error){
      console.log("code:"+request.status);
      console.log("message:"+request.responseText);
      console.log("error:"+error);
    }
  });//initChat ajax.
}

function writeResponse(text){
  console.log("server response text : " + text);
  let msg = text.split(":::");
  console.log("writeResponse - msg : " + msg);
  if(msg[1]=="server") return;
  let htmlme = "<div class=\"talk-bubble-container\">"+
  "<div class=\"talk-bubble talk-right tri-right btm-left talktest\"><p>"
      + msg[0] + "</p></div></div>";

  let htmlother = "<div class=\"talk-bubble-container\">" +
  "<div class=\"talk-bubble talk-left tri-right btm-right talktest2\"><p>"
      + msg[0] + "</p></div></div>";
  if(msg[1] == "me") $("#messages").append(htmlme);
  else $("#messages").append(htmlother);
}

















 /*=====================================================================*/
