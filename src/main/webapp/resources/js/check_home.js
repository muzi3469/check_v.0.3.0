/**
 *
 */
$(document).ready(function(){
  // $(".board-title-create-add").hide();

  console.log("check_home.js!!");

  initHome();



  //로그아웃
  $(".home-logout").click(function(){
    document.location.href = "/check/homeLogout";
  });

  //logo 이미지 클릭 -> 홈으로 가기
  $(".check-img").click(function(e){
    let tmp = e.target;
    if($(tmp).hasClass("check-img")){
      document.location.href = "/check/home";
    }
  });

  //새로운 보드 추가.
  $(".board-title-input").off("keydown").on("keydown",function(key){
    let tmp = key.target;
    let boardtitle = $(tmp).val();
    if(boardtitle=='') return;
    if(key.keyCode == 13){
      console.log("board-title-input enter, tmp is : ");
      console.log(tmp);
      let appendtmp = $(tmp).parents(".board-mod-add-item");
      let boardsection = $(tmp).parents(".boards-page-board-section");
      let teamid = boardsection.find(".board-page-team-id").text();
      let sendData = {
        b_title : boardtitle,
        t_tid : teamid
      };
      $.ajax({
        type:"POST",
        url:"makeboard",
        data:JSON.stringify(sendData),
        contentType:"application/json;",
        success:function(bid){
          console.log("make board success");
          contentMakeBoard(boardtitle, appendtmp, bid);
        },
        error:function(request,status,error){
          console.log("code:"+request.status);
          console.log("message:"+request.responseText);
          console.log("error:"+error);
        }
      });
    }
  });

  //home 화면에서 board 클릭 했을 때,
  $(".board-page-board-section-list-item").off("click").on("click", function(e){
    let tmp = e.target;
    console.log(" board click");
    console.log(tmp);
    if($(tmp).hasClass("board-title-details-name") ||
        $(tmp).hasClass("board-title-item") ){
      //해당 div 안에 span에 boardid를 찾아 페이지 이동한다.
      // https://hi-dev.tistory.com/21 를 참조
      let parenttmp = $(tmp).parents(".board-page-board-section-list-item");
      let boardid = parenttmp.find(".board-page-board-id").text();
      console.log("board click bid is : " + boardid);
      if(boardid=='') return;
      moveBoardPage(boardid);

    } else if($(tmp).hasClass("board-page-board-section-list-item")){
      let boardid = $(tmp).find(".board-page-board-id").text();
      console.log("board click bid is : " + boardid);
      if(boardid=='') return;
      moveBoardPage(boardid);

    } else if($(tmp).hasClass("board-section-list-item-container") ||
        $(tmp).hasClass("bold-item") || $(tmp).hasClass("bold")){
      let parenttmp = $(tmp).parents(".board-mod-add-item");
      parenttmp.find(".board-title-create-add").toggle(100);
      parenttmp.find(".board-title-mod-add").toggle(100);
    } else if($(tmp).hasClass("board-mod-add-item")){
      $(tmp).find(".board-title-create-add").toggle(100);
      $(tmp).find(".board-title-mod-add").toggle(100);
    }
  });


  //팀 추가 상황
  $(".create-team-input").off("keydown").on("keydown",function(key){
    let teamtitle = $(".create-team-input").val();
    if(teamtitle=='')
      return;
    console.log("create-team-input keydown event -> teamtitle : " + teamtitle);

    //엔터키 입력 상황
    if(key.keyCode == 13){
      /*
        ajax 연결 ㄱㄱ해야한다.
      */
      let teamdto = {
        t_title:teamtitle,
        t_type:"team"
      };
      $.ajax({
        type:"POST",
        url:"createTeamAjax",
        contentType:"application/json;",
        data:JSON.stringify(teamdto),
        success:function(result){
          console.log("team create ajax success result : " + result);
          sidenavMakeTeam(teamtitle);
          contentMakeTeam(teamtitle, result);
        },
        error:function(request,status,error){
          console.log("code:"+request.status);
          console.log("message:"+request.responseText);
          console.log("error:"+error);
        }
      });
      $(".create-team-input").val("");
    }
  });

});

/*---------------------initHome------------------------*/
function initHome(){
  //동기 ajax 진행. 초기 세팅
  $.ajax({
    url:"initHome",
    dataType:"json",
    async:false,
    timeout:5000,
    success:function(teams){
      console.log("initHome ajax success.");

      $.each(teams["teams"], function(key, team){
        console.log(key + " : " + team.t_title + " - " + team.t_tid);

        if(team.t_type != "personal"){
          sidenavMakeTeam(team.t_title);
          contentMakeTeam(team.t_title, team.t_tid);
        }else{
          $(".team-id-personal").text(team.t_tid);
        }

        $.each(teams["boards"], function(bkey, boardv){
          if(boardv.t_tid == team.t_tid){
            let parenttmp;
            if(team.t_type == "personal"){
              console.log("personal board tid : " + boardv.t_tid);
              parenttmp = $(".personal-section").find(".board-mod-add-item");
              contentMakeBoard(boardv.b_title, parenttmp, boardv.b_bid);
            }else{
              console.log("team board tid : " + boardv.t_tid);
              let teamidStr = ".team-id-"+boardv.t_tid;
              let teamtmp = $(teamidStr).parents(".boards-page-board-section");
              parenttmp = teamtmp.find(".board-mod-add-item");
              contentMakeBoard(boardv.b_title, parenttmp, boardv.b_bid);
            }
            delete teams["boards"][bkey];
          }
        });
      });
    },
    error:function(request,status,error){
      console.log("code:"+request.status);
      console.log("message:"+request.responseText);
      console.log("error:"+error);
    }
  });
}

function contentMakeBoard(boardtitle, parenttmp, boardid){
  let boardDiv = "<div class=\"board-page-board-section-list-item\"> "+
														"<span class=\"board-page-board-id board-id-"+boardid+"\" style=\"display: none;\">"+boardid+"</span> "+
														"<div class=\"board-section-list-item-container\"> "+
														"	<div class=\"board-title-item\"> "+
														"		<div class=\"board-title-details is-baged\"> "+
														"			<div class=\"board-title-details-name\">"+boardtitle+"</div> "+
														"		</div></div></div></div>";
  parenttmp.before(boardDiv);

  $(".board-page-board-section-list-item").off("click").on("click", function(e){
    let tmp = e.target;
    console.log(" board click");
    console.log(tmp);
    if($(tmp).hasClass("board-title-details-name") ||
        $(tmp).hasClass("board-title-item") ){
      //해당 div 안에 span에 boardid를 찾아 페이지 이동한다.
      // https://hi-dev.tistory.com/21 를 참조
      let parenttmp = $(tmp).parents(".board-page-board-section-list-item");
      let boardid = parenttmp.find(".board-page-board-id").text();
      console.log("board click bid is : " + boardid);
      if(boardid=='') return;
      moveBoardPage(boardid);

    } else if($(tmp).hasClass("board-page-board-section-list-item")){
      let boardid = $(tmp).find(".board-page-board-id").text();
      console.log("board click bid is : " + boardid);
      if(boardid=='') return;
      moveBoardPage(boardid);

    } else if($(tmp).hasClass("board-section-list-item-container") ||
        $(tmp).hasClass("bold-item") || $(tmp).hasClass("bold")){
      let parenttmp = $(tmp).parents(".board-mod-add-item");
      parenttmp.find(".board-title-create-add").toggle(100);
      parenttmp.find(".board-title-mod-add").toggle(100);
    } else if($(tmp).hasClass("board-mod-add-item")){
      $(tmp).find(".board-title-create-add").toggle(100);
      $(tmp).find(".board-title-mod-add").toggle(100);
    }
  });
}

function sidenavMakeTeam(teamtitle){
  //매개변수로 teamid 를 하나더 전달받아 세팅해준다.
  let aHtml = "<a href=\"#\" class=\"public-team-container\"><span class=\"public-team-id\" style=\"display: none;\"></span>"+teamtitle+"</a>";
  $(".sidenav-team").append(aHtml);
}

function contentMakeTeam(teamtitle, teamid){
  let teamDiv = "<div class=\"boards-page-board-section\">" +
												"<div class=\"boards-page-board-section-header\">" +
												"	<div class=\"boards-page-board-section-header-icon\"> " +
												"		<span class=\"icon-lg icon-member\"></span>  " +
												"	</div><div>  " +
                        "<div class=\"check-invite-container\"> "+
												"			<div class=\"check-invite-show\">"+
												"				<a class=\"button\">invite</a></div>"+
												"			<div class=\"check-invite-list-menu\" style=\"display: none;\" align=\"right\"> "+
												"				<table class=\"table-hover text-center check-invitetable\"> "+
												"					<thead style=\"text-align: center; background-color: #ea6153; font-size: 14px; line-height: 20px; color: #3b3b3b; -webkit-font-smoothing: antialiased; font-smoothing: antialiased; font-smoothing: antialiased; font-family: Helvetica;\">"+
												"						<tr><td width=\"35%\" height=\"35%\"><font color=\"white\">"+
												"									<input type=\"checkbox\" class=\"check-invite-all-btn\" /> "+
												"							</font></td>"+
												"							<td><font color=\"white\">NickName</font></td>"+
												"							<td><font color=\"white\">Email</font></td></tr>"+
												"					</thead>"+
												"					<tbody class=\"check-invitetable-tbody\" style=\"text-align: center; background-color: #e9e9e9;\">"+
												"						<tr><td><input type=\"checkbox\" name=\"user_Checkbox\" class=\"tr_check\"></td>"+
												"							<td></td><td></td></tr></tbody></table>"+
												"				<br><button type=\"button\" class=\"btn check-invite-btn\">초대하기</button>"+
												"			</div></div>"+
												"	<h3 class=\"boards-page-board-section-header-name\">" + teamtitle + "</h3></div>  " +
                        " <span class=\"board-page-team-id team-id-"+teamid+"\" style=\"display: none;\">"+teamid+"</span> "+
												"</div><!-- boards-page-board-section-header -->  " +
												"<div class=\"board-page-board-section-list\">  " +
												"	<div class=\"board-page-board-section-list-item board-mod-add-item\"> "+
												"		<div class=\"board-section-list-item-container\"> "+
												"			<div class=\"board-title-mod-add\"> "+
												"				<div class=\"bold\"> "+
												"					<div class=\"bold-item\">Create new Board...</div> "+
												"				</div> "+
												"			</div> "+
												"			<div class=\"board-title-create-add\" style=\"display: none;\"> "+
												"				<input type=\"text\" class=\"form-control board-title-input\" placeholder=\"Enter Board title..\"> "+
												"			</div> "+
												"		</div> "+
												"	</div>  " +
												"</div><!-- board-page-board-section-list -->  " +
											"</div><!-- boards-page-board-section -->";
  $(".personal-section").after(teamDiv);
  $(".board-page-board-section-list-item").off("click").on("click", function(e){
    let tmp = e.target;
    console.log(" board click");
    console.log(tmp);
    if($(tmp).hasClass("board-page-board-section-list-item") ||
        $(tmp).hasClass("board-title-details-name") ||
        $(tmp).hasClass("board-title-item") ){
      //해당 div 안에 span에 boardid를 찾아 페이지 이동한다.
      // https://hi-dev.tistory.com/21 를 참조
    } else if($(tmp).hasClass("board-section-list-item-container") ||
        $(tmp).hasClass("bold-item") || $(tmp).hasClass("bold")){
      let parenttmp = $(tmp).parents(".board-mod-add-item");
      parenttmp.find(".board-title-create-add").toggle(100);
      parenttmp.find(".board-title-mod-add").toggle(100);
    } else if($(tmp).hasClass("board-mod-add-item")){
      $(tmp).find(".board-title-create-add").toggle(100);
      $(tmp).find(".board-title-mod-add").toggle(100);
    }
  });
  $(".board-title-input").off("keydown").on("keydown",function(key){
    let tmp = key.target;
    let boardtitle = $(tmp).val();
    if(boardtitle=='') return;
    if(key.keyCode == 13){
      console.log("board-title-input enter, tmp is : ");
      console.log(tmp);
      let appendtmp = $(tmp).parents(".board-mod-add-item");
      let boardsection = $(tmp).parents(".boards-page-board-section");
      let teamid = boardsection.find(".board-page-team-id").text();
      let sendData = {
        b_title : boardtitle,
        t_tid : teamid
      };
      $.ajax({
        type:"POST",
        url:"makeboard",
        data:JSON.stringify(sendData),
        contentType:"application/json;",
        success:function(bid){
          console.log("make board success");
          contentMakeBoard(boardtitle, appendtmp, bid);
        },
        error:function(request,status,error){
          console.log("code:"+request.status);
          console.log("message:"+request.responseText);
          console.log("error:"+error);
        }
      });
    }
  });
  $(".check-invite-show").off("click").on("click", function(e){
    let tmp = e.target;
    let parenttmp = $(tmp).parents(".boards-page-board-section-header");
    let teamid = parenttmp.find(".board-page-team-id").text();
    let tbodytmp = parenttmp.find(".check-invitetable-tbody");
    let tbodylasttmp = parenttmp.find(".check-invitetable");
    let listmenutmp = parenttmp.find('.check-invite-list-menu').slideToggle("fast");
    $.ajax({
          url : "memberlist",
          dataType : "JSON",
          success : function(data) {
            tbodytmp.empty();
            $.each(data, function(key, value) {
              tbodylasttmp.append(
                "<tr><td><input type=\"checkbox\" class=\"check-email-value\" value=\""+value.m_email+"\"></td><td>"
                + value.m_nickname
                + "</td><td>"
                + value.m_email
                + "</td></tr>");
            });
          },
          error:function(request,status,error){
            console.log("code:"+request.status);  console.log("message:"+request.responseText);  console.log("error:"+error);
          }
    });
  });
  $(".check-invite-btn").off("click").on("click",function(e){
    let tmp = e.target;
    let boardSectionTmp = $(tmp).parents(".boards-page-board-section");
    let teamid = boardSectionTmp.find(".board-page-team-id").text();

    let teamMemberArr = [];
    boardSectionTmp.find(".check-email-value:checked").each(function(i){
      let teamMember = {
        t_tid : teamid,
        m_email:$(this).val()
      };
      teamMemberArr.push(teamMember);
    });
    console.log(teamMemberArr);
    $.ajax({
      method:"POST",
      url:"inviteTeamMember",
      data:JSON.stringify(teamMemberArr),
      contentType:"application/json;",
      success:function(result){
        if(result==true)
          console.log("invite success!!");
          boardSectionTmp.find('.check-invite-list-menu').slideToggle("fast");
      },
      error:function(request,status,error){
        console.log("code:"+request.status);  console.log("message:"+request.responseText);  console.log("error:"+error);
      }
    }); //멤버 초대 ajax.
  });//초대 버튼 클릭.
  $(".check-invite-all-btn").off("click").on("click",function(e){
    let tmp = e.target;
    let boardSectionTmp = $(tmp).parents(".boards-page-board-section");
    if($(tmp).prop("checked")){
      boardSectionTmp.find(".check-email-value").prop("checked",true);
    } else{
      boardSectionTmp.find(".check-email-value").prop("checked",false);
    }
  });//모두 선택 클릭.

}

/*
  board title 찾아서 넣어줘야함.
*/
function moveBoardPage(boardid){
  let boardform = document.createElement("form");
  let objs = document.createElement("input");
  objs.setAttribute("type", "hidden");
  objs.setAttribute("name", "bid");
  objs.setAttribute("value", boardid);
  boardform.appendChild(objs);
  // objs.setAttribute("type", "hidden");
  // objs.setAttribute("name", "btitle");
  // objs.setAttribute("value", "test");
  // boardform.appendChild(objs);
  boardform.setAttribute("method", "post");
  boardform.setAttribute("action", "board_main");
  document.body.appendChild(boardform);
  boardform.submit();
}

$(".check-invite-show").off("click").on("click", function(e){
  let tmp = e.target;
  let parenttmp = $(tmp).parents(".boards-page-board-section-header");
  let teamid = parenttmp.find(".board-page-team-id").text();
  let tbodytmp = parenttmp.find(".check-invitetable-tbody");
  let tbodylasttmp = parenttmp.find(".check-invitetable-tbody:last");
  console.log(parenttmp);
  console.log(teamid);
  console.log(tbodytmp);
  console.log(tbodylasttmp);
  $.ajax({
        url : "memberlist",
        dataType : "JSON",
        success : function(data) {
          console.log(data);
          tbodytmp.empty();
          $.each(data, function(key, value) {
            console.log('key:' + key + ' / '  + 'value:' + value);
            console.log(value.m_email);
            tbodylasttmp.append(
              "<tr><td><input type=\"checkbox\" name=\"check-"+value.m_email+"\" value=\"check-"+value.m_nickname+"\"></td><td>"
              + value.m_nickname
              + "</td><td>"
              + value.m_email
              + "</td></tr>");
          });
        },
        error:function(request,status,error){
          console.log("code:"+request.status);
          console.log("message:"+request.responseText);
          console.log("error:"+error);
        }
  });
});

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



















/*=====================================================================*/
