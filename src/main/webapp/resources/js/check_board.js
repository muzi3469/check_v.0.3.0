/**
 *
 */

var wSocket;
const TYPE_LIST_MAKE = 0;
const TYPE_LIST_REMOVE = 1;
const TYPE_LIST_UPDATE_TITLE = 2;
const TYPE_CARD_MAKE = 3;
const TYPE_CARD_REMOVE = 4;
const TYPE_CARD_UPDATE_MOVE = 5;
const TYPE_CARD_UPDATE_TITLE = 6;
//메시지 보낼 때, 타입 ::: 메시지

 $(document).ready(function(){

   console.log("check_board.js!!");
   init();

   //logo 이미지 클릭 -> 홈으로 가기
   $(".check-img").click(function(e){
     let tmp = e.target;
     if($(tmp).hasClass("check-img")){
       document.location.href = "/check/home";
     }
   });

   //list 추가 버튼 클릭시 동작.
   $(".mod-add").off("click").on("click",function(e){
     let tmp = e.target;
     console.log(".mod-add click tmp is -> ");
     console.log(tmp);
     if($(tmp).hasClass("mod-add") || $(tmp).hasClass("mod-add-children")
         || $(tmp).hasClass("placeholder") || $(tmp).hasClass("list-add-controls")
         || $(tmp).hasClass("check-cancel-edit"))
       modAddToggle();
     else if($(tmp).hasClass("mod-list-add-button")){
       let listTitle = $(".list-name-input").val();
       if(listTitle=='' || listTitle==null){
         return;
       }else{
         ajaxMakeList(listTitle);
       }
     }
   });

   /*
     나중에 구현 ㄱㄱ
     $("body").click(function(e){
       let tmp = e.target;
       console.log("body click - target : " + tmp.tagName);
       if(!$(".mod-add").hasClass("is-idle")){
         if(tmp == $("body"))
           modAddToggle();
       }
     });
   */
     //List 추가 버튼 hover 기능
     $(".mod-add").hover(function(){
       if($(".mod-add").hasClass("is-idle"))
         $(".mod-add").css("background-color", "#003300");
     }, function(){
       if($(".mod-add").hasClass("is-idle")){
         $(".mod-add").css("background-color", "#006633");
       }
     });

     /*------------web socket-------------*/

     wSocket = new WebSocket("ws://192.168.0.43:8085/check/connBoardWebsocket");
     console.log("board webSocket created...");
     if (wSocket !== undefined && wSocket.readyState !== WebSocket.CLOSED)
       console.log("board web socket connected well...");

     wSocket.onopen = function(event){
       if(event.data===undefined) return;
       console.log("board websocket onopen = " + event.data);
     }
     wSocket.onmessage = function(event){
       console.log("board websocket onmessage");
       console.log(event.data);
       let msg = event.data.split(":::");
       let typeNum = parseInt(msg[0]);
       let listArr = $(".check-list-wrapper");
       switch (typeNum) {
         case TYPE_LIST_MAKE: //리스트 만듦.
         //type ::: title ::: lid ::: index ::: bid
           contentMakeList(msg[1], msg[2], msg[3]);
         break;
         case TYPE_LIST_REMOVE: // 리스트 삭제. 리스트
           let removeObj = msg[1].split(",");
           removeObj.pop(); // 마지막 배열은 빈 값이기 때문에 pop으로 빼낸다.
           $.each(listArr, function(key, value){
             let removeList = removeObj[key].split(":"); // [0] : lid, [1] : index
             let listid = $(value).find(".check-list-id").text();
             let listindex = $(value).find(".check-list-index").text(removeList[1]);
             if(removeList[1]!=-1){
               $(value).css("left",15+248*removeList[1]);
             }else{
               $(value).remove();
             }
           });
         break;
         case TYPE_LIST_UPDATE_TITLE: // 리스트 제목 변경, [type]:::[lid]:::[title]:::[bid]
           $.each(listArr, function(key, value){
             let listid = $(value).find(".check-list-id").text();
             if(listid==msg[1]){
               $(value).find(".check-list-name-assist").text(msg[2]);
             }
           });
         break;
         case TYPE_CARD_MAKE: //카드 만듦
           let tmpMsg = msg[1].split(":");  // lid, title, cid, index
           $.each(listArr, function(key, value){
             let listid = $(value).find(".check-list-id").text();
             if(listid==tmpMsg[0]){
               let parenttmp = $(value).find(".check-list-cards-container");
               contentMakeCard(tmpMsg[1], parenttmp, tmpMsg[2], tmpMsg[3]);
             }
           });
         break;
         case TYPE_CARD_REMOVE: //카드 삭제. [type] ::: [cid]:[index]:[lid],,, ::: [bid]
           let removeCard = msg[1].split(",");
           removeCard.pop();
           console.log(removeCard);
           let tmpCard = removeCard[0].split(":");
           $.each(listArr, function(key, value){
             let listid = $(value).find(".check-list-id").text();
             if(listid==tmpCard[2]){
               let cardArr = $(value).find(".list-card");
               console.log(cardArr);
               console.log(cardArr.length);
               $.each(cardArr, function(k, card){
                 if(k<cardArr.length-1){
                   console.log("[k] : " + k + ", [card] : " + removeCard[k]);
                   let cardSplit = removeCard[k].split(":");
                   let cardid = $(card).find(".check-card-id").text();
                   $(card).find(".check-card-index").text(cardSplit[1]);
                   if(cardSplit[1]==-1){
                     $(card).remove();
                   }
                 }
               });
             }
           });
         break;
         case TYPE_CARD_UPDATE_MOVE: // 카드 이동
           let moveCard = msg[1].split(",");
           if(moveCard.length>=2){
             moveCard.pop();
             console.log(moveCard);
             let tmpCard = moveCard[0].split(":");
             $.each(listArr, function(key, value){
               let listid = $(value).find(".check-list-id").text();
               if(listid==tmpCard[2]){
                 let cardContainer = $(value).find(".check-list-cards-container");
                 console.log(cardContainer);
                 cardContainer.empty();
                 $.each(moveCard, function(k, card){
                   let cardSplit = card.split(":");
                   contentMakeCard(cardSplit[3], cardContainer, cardSplit[0], cardSplit[1]);
                 });
                 //contentMakeCard(cardtitle,parenttmp, result.cid, result.index);
               }
             });
           }
         break;
         case TYPE_CARD_UPDATE_TITLE: //카드 이름 변경
         break;
       }
     }
     wSocket.onclose = function(event){
       console.log("board websocket onclose... disconnected...");
     }

     /*------------web socket-------------*/

 });//document ready

 function init(){
   var boardid;
   $.ajax({
     url:"initBoard",
     async:false,
     dataType : "json",
     success:function(result){
       //list -> l_lid, l_title, b_bid, l_index
       $.each(result["lists"], function(key, list){
         contentMakeList(list.l_title, list.l_lid, list.l_index);
         //card -> c_cid, c_title, c_description, c_index, l_lid
         $.each(result["cards"], function(k, card){
           if(card.l_lid == list.l_lid){
             let lidStr = ".list-id-"+card.l_lid;
             let contenttmp = $(lidStr).parents(".check-list-content");
             let parenttmp = contenttmp.find(".check-list-cards-container");

             contentMakeCard(card.c_title, parenttmp, card.c_cid, card.c_index);

             delete result["cards"][k];
           }
         });
       });
     },
     error:function(request,status,error){
       console.log("code:"+request.status);  console.log("message:"+request.responseText); console.log("error:"+error);
     }
   });
 }

 function ajaxMakeList(title){
   let listdata = {
     l_title : title
   }
   $.ajax({
     type:'POST',
     url:'makelist',
     contentType:"application/json;",
     data:JSON.stringify(listdata),
     dataType:"json",
     success:function(result){
       /*
        result : lid, index // t_title
       */
       let sendMsg = TYPE_LIST_MAKE + ":::" + title + ":::" + result.lid + ":::" + result.index;
       wSocket.send(sendMsg);
       contentMakeList(title, result.lid, result.index);
     }
   }); // 리스트 생성 ajax...
 }

 function modAddToggle(){
   $(".placeholder").toggle(100);
   $(".list-name-input").toggle(100);
   $(".list-add-controls").toggle(100);
   console.log("modAddToggle() hasClass(is-idle) ? : "+$(".mod-add").hasClass("is-idle"));
   if($(".mod-add").hasClass("is-idle")){
     $(".mod-add").css("background-color", "#009933");
     $(".mod-add").removeClass("is-idle");
   } else{
     $(".mod-add").css("background-color", "#006633");
     $(".mod-add").addClass("is-idle");
   }
 }

 function contentMakeList(title, lid, index){
   let addDiv = $(".check-add-list").offset();
   let divHtml = "<div class=\"check-list-wrapper\" style=\"left:"+addDiv.left+"px;\">"+
   "<div class=\"check-list list-wrapper\">"+
   "<div class=\"list check-list-content\">"+
   "<span class=\"check-list-id list-id-"+lid+"\" style=\"display: none;\">"+lid+"</span>"+
	 "<span class=\"check-list-index\" style=\"display: none;\">"+index+"</span>"+
   "<div class=\"list-header check-list-header u-clearfix is-menu-shown\">"+
 	"<div class=\"list-header-target check-editing-target\"></div>"+
  "<div class=\"check-list-header-name-container\">"+
  "<a class=\"list-header-name-assist check-list-name-assist\">"+title+"</a>"+
  "<i class=\"icon-remove check-icon\"></i></div>"+
 	"<textarea class=\"list-header-name mod-list-name check-list-name-input form-control\" style=\"resize: none; display:none;\"></textarea>"+
 	"</div><div class=\"check-list-cards-container\">"+
  "</div><div class=\"list-cards u-fancy-scrollbar u-clearfix check-list-cards check-list-sortable ui-sortable\" style=\"display:none;\">"+
 	"<div class=\"card-composer\">"+
 	"<div class=\"list-card check-composer\">"+
 	"<textarea class=\"list-card-composer-textarea check-card-title form-control\""+
 	" placeholder=\"Enter a title for this card...\" style=\"resize: none;\"></textarea>"+
 	"</div><div class=\"cc-controls u-clearfix\">"+
 	"<div class=\"cc-controls-section\">"+
 	"<input "+
 	" class=\"btn btn-primary confirm mod-compact check-add-card\""+
 	" type=\"submit\" value=\"Add Card\"> <a href=\"#\" "+
 	" class=\"dark-hover check-card-cancel\"></a>"+
 	"</div></div></div></div>"+
   "<div class=\"check-open-card-composer-container\"  style=\"width: 100%; height: 30px; padding-top: 5px;\">"+
 	"<a class=\"open-card-composer check-open-card-composer\""+
 	" href=\"#\"> <span class=\"check-add-another-card\">	Add a card </span> "+
 	"</a></div>"+
   "</div></div></div>"

   $(".check-add-list").before(divHtml);
   // $(".check-list-name-input").hide();
   // $(".check-list-cards").hide();
   $(".check-add-list").css("left",addDiv.left+233);
   $(".list-name-input").val("");
   //클릭 이벤트 연결
   $(".check-list").off("click").on("click", function(e){
     let tmp = e.target;
     console.log(tmp);
     if($(tmp).hasClass("check-open-card-composer-container") ||
         $(tmp).hasClass("check-add-another-card") ||
         $(tmp).hasClass("cc-controls-section")){
       /*
         부모 check-list-content를 찾은 후,
         그를 바탕으로 자식 노드인 check-list-cards와 check-open-card-composer-container
         를 토글한다.
       */
       let ptmp = $(tmp).parents(".check-list-content");
       ptmp.children(".check-list-cards").toggle(100);
       ptmp.children(".check-open-card-composer-container").toggle(100);
       // $(".check-list-cards"poser-container").toggle(100);
     } else if($(tmp).hasClass("check-list-header-name-container") ||
                 $(tmp).hasClass("list-header-name-assist")){
       let parenttmp = $(tmp).parents(".check-list-header");
       parenttmp.find(".check-list-header-name-container").toggle(100);
       parenttmp.find(".check-list-name-input").toggle(100);
     } else if($(tmp).hasClass("check-card-title") ||
                 $(tmp).hasClass("check-card-details")){
       //카드 클릭
       let parenttmp;
     }else if($(tmp).hasClass("icon-remove")){
       if($(tmp).hasClass("list-card-operation")){ //카드 지울때.

         let parenttmp = $(tmp).parents(".list-card");
         let cardid = parenttmp.find(".check-card-id").text();
         parenttmp.find(".check-card-index").text("-1");

         let listid = parenttmp.parents(".check-list-content").find(".check-list-id").text();
         let listcontainertmp = parenttmp.parents(".check-list-cards-container");
         let cardsDiv = listcontainertmp.find(".list-card");
         let cardarr = [];
         let cardindex=0;
         let sendMsg = TYPE_CARD_REMOVE + ":::";

         $.each(cardsDiv, function(key, listCards){
           let tmpcid = $(listCards).find(".check-card-id").text();
           if(cardid!=tmpcid){
             $(listCards).find(".check-card-index").text(cardindex+"");
             let card ={
               c_cid : tmpcid,
               c_index:cardindex++,
               l_lid:listid
             }
             sendMsg += card.c_cid + ":" + card.c_index + ":" + card.l_lid + ",";
             cardarr.push(card);
           } else{
             let card ={
               c_cid : tmpcid,
               c_index:-1,
               l_lid:listid
             }
             //lid 추가해야함 객체에
             sendMsg += card.c_cid + ":" + card.c_index + ":" + card.l_lid + ",";
             cardarr.push(card);
           }
         });//cardsDiv for문

         $.ajax({
           method:"POST",
           url:"removeCard",
           contentType:"application/json;",
           data:JSON.stringify(cardarr),
           success:function(result){
             if(result==true){
               wSocket.send(sendMsg);
               parenttmp.remove();
             }
           }, error:function(request,status,error){
             console.log("code:"+request.status);
             console.log("message:"+request.responseText);
             console.log("error:"+error);
           }
         });//카드 삭제 ajax

       }else{//리스트 지울때
         let parenttmp = $(tmp).parents(".check-list");
         let removetmp = parenttmp.parent();
         let listid = parenttmp.find(".check-list-id").text();
         parenttmp.find(".check-list-index").text("-1");

         let listdiv = $(".board-canvas").find(".check-list-wrapper");

         let listarr = [];
         let listindex=0;
         $.each(listdiv, function(key, boardlists){
           let tmplid = $(boardlists).find(".check-list-id").text();
           if(tmplid!=listid){
             $(boardlists).find(".check-list-index").text(listindex+"");
             let list = {
               l_lid:tmplid,
               l_index:listindex++
             };
             listarr.push(list);
           }else{
             let list = {
               l_lid:tmplid,
               l_index:-1
             };
             listarr.push(list);
           }
         });//listarr 초기화 진행

         console.log(listarr);
         $.ajax({
           method:"POST",
           url:"removeList",
           contentType:"application/json;",
           data:JSON.stringify(listarr),
           success:function(result){
             if(result==true){
               removetmp.remove();
               // html left 전부 이동.
               let sendMsg=TYPE_LIST_REMOVE + ":::";
               $.each(listdiv, function(key, boardlists){
                 let movelid = $(boardlists).find(".check-list-id").text();
                 let moveindex = $(boardlists).find(".check-list-index").text();
                 sendMsg += movelid + ":" + moveindex + ","
                 if(moveindex!=-1){
                   $(boardlists).css("left",15+248*moveindex);
                 }
               });
               wSocket.send(sendMsg);
             }
           }, error:function(request,status,error){
             console.log("code:"+request.status);
             console.log("message:"+request.responseText);
             console.log("error:"+error);
           }
         });//removeList ajax.
       }
     }else if($(tmp).hasClass("check-add-card")){
       //카드 추가 버튼 클릭
       let topparent = $(tmp).parents(".check-list-content")
       let parenttmp = topparent.find(".check-list-cards-container");
       let lid = topparent.find(".check-list-id").text();
       let childTextArea = topparent.find(".list-card-composer-textarea");
       let cardtitle = childTextArea.val();
       if(cardtitle=='') return;

       console.log("cardtitle : " + cardtitle);
       console.log("card lid : " + lid);

       let cardData = {
         c_title:cardtitle,
         l_lid:lid
       }
       $.ajax({
         method:"post",
         url:"makecard",
         data:JSON.stringify(cardData),
         contentType:"application/json;",
         dataType:"json",
         success:function(result){
           let sendMsg = TYPE_CARD_MAKE + ":::" + lid + ":" + cardtitle + ":" + result.cid + ":" + result.index;
           wSocket.send(sendMsg);
           contentMakeCard(cardtitle,parenttmp, result.cid, result.index);
         },
         error:function(request,status,error){
           console.log("code:"+request.status);
           console.log("message:"+request.responseText);
           console.log("error:"+error);
         }
       });

     }
   });
   console.log($(".check-add-list").offset().left);
   $(".check-list-name-input").off("keydown").on("keydown", function(key){
     let tmp = key.target;
     let listtitle = $(tmp).val();
     if(key.keyCode == 13){
       console.log(listtitle);
       let parenttmp = $(tmp).parents(".check-list-header");
       parenttmp.find(".check-list-header-name-container").toggle(100);
       parenttmp.find(".check-list-name-input").toggle(100);

       let listContentTmp = parenttmp.parents(".check-list-content");
       let listid = listContentTmp.find(".check-list-id").text();
       if(listtitle!=''){
         $(tmp).val("");
         let listObj = {
           l_lid : listid,
           l_title:listtitle
         }
         $.ajax({
           method:"POST",
           url:"modifyListTitle",
           data:JSON.stringify(listObj),
           contentType:"application/json;",
           success:function(result){
             if(result==true){
               let sendMsg = TYPE_LIST_UPDATE_TITLE + ":::" + listid + ":::" + listtitle;
               wSocket.send(sendMsg);
               parenttmp.find(".check-list-name-assist").text(listtitle);
             }
           },
           error:function(request,status,error){
             console.log("code:"+request.status);
             console.log("message:"+request.responseText);
             console.log("error:"+error);
           }
         });//change list title ajax
       }
     }
   });//.check-list-name-input 엔터 이벤트.

 }

//check-list-cards-contaier
 function contentMakeCard(cardtitle,parenttmp, cid, index){
   let cardDiv = "<div class=\"list-card check-member-droppable ui-droppable\"> " +
 		"<div class=\"list-card-cover check-card-cover\"></div>" +
    "	<span class=\"check-card-id\" style=\"display: none;\">"+cid+"</span> " +
 		"	<span class=\"check-card-index\" style=\"display: none;\">"+index+"</span> " +
 		"<span " +
 		"	class=\"check-icon icon-remove list-card-operation dark-hove check-open-quick-card-editor check-card-menur\"></span> " +
 		"<div class=\"list-card-details check-card-details\">" +
 		"	<div class=\"list-card-labels check-card-labels\"></div> " +
 		"	<span class=\"list-card-title check-card-title\"> " +	cardtitle + "	</span> " +
 		"	<div class=\"list-card-members check-list-card-members\"></div> " +
 		"</div> " +
 		"<p class=\"list-card-dropzone\" " +
 		"	style=\"height: 32px; line-height: 32px; display: none;\">Drop " +
 		"	files to upload.</p> " +
 	  "</div>";
    parenttmp.append(cardDiv);

 }

 $(function(){
   $(".check-list-cards-container").sortable({
     connectWith:".check-list-cards-container",
     change:function(event, ui){
       //dom 이 변하기 전.
     },
     update:function(event, ui){
       //dom 업데이트 완료 후.
       let topparent = $(this).parents(".check-list-content");
       let lid = topparent.find(".check-list-id").text();
       let cardlist = topparent.find(".check-list-cards-container").children(".list-card");
       let cardarr = [];
       let cardindex=0;
       let sendMsg = TYPE_CARD_UPDATE_MOVE + ":::";
       $.each(cardlist, function(key, carddiv){
         $(carddiv).find(".check-card-index").text(""+cardindex);
         let cid = $(carddiv).find(".check-card-id").text();
         let ctitle = $(carddiv).find(".check-card-title").text();
         let card={
           c_cid: cid,
           c_index : cardindex++,
           l_lid : lid,
           c_title : ctitle
         };
         cardarr.push(card);
         sendMsg += card.c_cid + ":" + card.c_index + ":" + card.l_lid + ":" + card.c_title + ",";
       });//each문
       console.log(cardarr);
       $.ajax({
         method:"POST",
         url:"updateListCard",
         data:JSON.stringify(cardarr),
         contentType:"application/json;",
         success:function(result){
           wSocket.send(sendMsg);
         },
         error:function(request,status,error){
           console.log("code:"+request.status);
           console.log("message:"+request.responseText);
           console.log("error:"+error);
         }
       });//move card ajax
     }
   });
 });
















/*=====================================================================*/
