$(".check-list").off("click").on("click",function(e){
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
    //리스트 타이틀 클릭
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

      $.each(cardsDiv, function(key, listCards){
        let tmpcid = $(listCards).find(".check-card-id").text();
        if(cardid!=tmpcid){
          $(listCards).find(".check-card-index").text(cardindex+"");
          let card ={
            c_cid : tmpcid,
            c_index:cardindex++,
            l_lid:listid
          }
          cardarr.push(card);
        } else{
          let card ={
            c_cid : tmpcid,
            c_index:-1,
            l_lid:listid
          }
          //lid 추가해야함 객체에
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

      $.ajax({
        method:"POST",
        url:"removeList",
        contentType:"application/json;",
        data:JSON.stringify(listarr),
        success:function(result){
          if(result==true){
            removetmp.remove();
            // html left 전부 이동.
            $.each(listdiv, function(key, boardlists){
              let movelid = $(boardlists).find(".check-list-id").text();
              let moveindex = $(boardlists).find(".check-list-index").text();
              if(moveindex!=-2){
                $(boardlists).css("left",15+248*moveindex);
              }
            });
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
      c_title : cardtitle,
      c_description : "hello",
      l_lid : lid
    }
    $.ajax({
      method:"post",
      url:"makecard",
      data:JSON.stringify(cardData),
      contentType:"application/json;",
      dataType:"json",
      success:function(result){
        console.log(result);
        contentMakeCard(cardtitle,parenttmp, result.cid, result.index);
      },
      error:function(request,status,error){
        console.log("code:"+request.status);
        console.log("message:"+request.responseText);
        console.log("error:"+error);
      }
    });

  }
});//check-list click

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
