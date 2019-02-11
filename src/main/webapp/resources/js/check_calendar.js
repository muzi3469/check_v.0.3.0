$(document).ready(function(){
	console.log("check_calendar.js ready!");
	// when delete and modify, event id intilaize
	var getId;
	//check-calendar-content-view-smalltitle
	//check-calendar-content-view-smalltitle
	$(".check-img").click(function(e){
	      let tmp = e.target;
	      if($(tmp).hasClass("check-img")){
	        document.location.href = "/check/home";
	      }
	    });

	//jsp button disabled set
	$button_delete_true = $("#delete").attr('disabled',true);
	$button_modify_true = $("#modify").attr('disabled',true);

	$button_ok_true = $("#ok").attr('disabled',true);
	$input_true = $("#calendar-title").attr('readonly',true);

	var passcid;
	var firstend;
	var modievent;

	var selectevent;
	var selectstart;
	var selectend;

	$("#calendar").fullCalendar({
		locale:'ko',
		height:500,
		width:550,
		contentHeight:450,
		defaultView:'month',
		displayEventTime:false,
		showNonCurrentDates:false,//달력안에 다른달 날짜들 색칠해줌
		selectable:true,
		selectHelper: true,
		allDay:true,
		unselect:false,
		events:function(start, end, timezone,callback) {

			$.ajax({
				url: "initCalendar",
				type: "POST",
				dataType: 'JSON',
				contentType: "application/json",
				success: function(data) {
					console.log("select 기능ajax 성공!");
					var events = [];
					var eventformat;
					$.each(data["events"],function(key,event){

						var formattest = moment(event.calend).format("YYYY-MM-DD");
						var yester = moment(formattest).add(1,'days');
						var final = moment(yester).format("YYYY-MM-DD");
						event.calend = final;
						//key 를출력하면 dao에서 저장한 events가 나온다.
						console.log("["+key+"]"+":"+event.caldescription);
						eventformat={
								"title":event.caltitle,
								"start":event.calstart,
								"end":event.calend,
								"id":event.calid,
								"description":event.caldescription
						};
						console.log("["+key+"]"+":"+event.calid);
						events.push(eventformat);

					});

					callback(events);


				},
			});
		},

		header:{
			left:'prevYear,prev,today',
			center:'title',
			right :'next,nextYear',
//			month,basicDay,
		},
		defaultDate : new Date(),
		editable : true,

		//navLinks: true,
		eventLimit : true,
		eventTextColor:'black',
		eventBackgroundColor : 'green',
		eventMouseover:function(){
			$(this).css('color','black');
			$(this).css('background','#FFF612');
			$(this).css('background',"+calEvent.color+");

		},
		eventMouseout(){
			$(this).css('color','black');
			$(this).css('background','green');
			$(this).css('background',"+calEvent.color+");
		},
		eventClick(event,end,element){
			if($('.calendar-input-title').css('display')!="none"){
				$('.descrption-a').toggle(100);
				$('.descrption-textarea').toggle(100);
				$('.check-calendar-content-view-smalltitle').toggle(100);
				$('.calendar-input-title').toggle(100);
			}
			$button_modify_false = $("#modify").attr('disabled',false);
			$button_delete_false = $("#delete").attr('disabled',false);

			//change date format method


			var changeStart = $.fullCalendar.formatDate(event.start, "YYYY-MM-DD");
			var formattest = moment(event.end).format("YYYY-MM-DD");
			var yester = moment(formattest).add(-1,'days');
			var final = moment(yester).format("YYYY-MM-DD");
			$('.check-calendar-content-view-smalltitle').text(event.title);
			$('#calendar-start').text(changeStart);
			$('#calendar-end').text(final);

			console.log("test chekck"+event.description);
			$('.descrption-a').text(event.description);


			console.log("click dsd : "+event.description);

			modievent=event;
			getId=event.id

		},
		eventDrop(event){
			var changeEnd = $.fullCalendar.formatDate(event.end, "YYYY-MM-DD");
			var formattest = moment(event.end).format("YYYY-MM-DD");
			var yester = moment(formattest).add(-1,'days');
			var end_date = moment(yester).format("YYYY-MM-DD");
			var start_date = $.fullCalendar.formatDate(event.start, "YYYY-MM-DD");

			var title = event.title;
			var description = event.description;
			console.log("drop cid:"+passcid);
			var resources =
			{
					"title":title+"",
					"start":start_date+"",
					"end":end_date+"",
					"cid":event.id+"",
					"description":description+""
			};
			$.ajax({
				type: "POST",
				url: "eventDropdata",
				contentType: 'application/json',
				data: JSON.stringify(resources),
				success: function (response) {

				},
				error: function (xhr) {
					debugger;
					alert('fail'+xhr);
				}
			});
		},
		eventResize(event){
			var changeEnd = $.fullCalendar.formatDate(event.end, "YYYY-MM-DD");
			var formattest = moment(event.end).format("YYYY-MM-DD");
			var yester = moment(formattest).add(-1,'days');
			var end_date = moment(yester).format("YYYY-MM-DD");
			var start_date = $.fullCalendar.formatDate(event.start, "YYYY-MM-DD");
			console.log("resize cid:"+passcid);
			var title = event.title;
			var resources =
			{
					"title":title+"",
					"start":start_date+"",
					"end":end_date+"",
					"cid":event.id+""
			};

			$.ajax({
				type: "POST",
				url: "eventResizedata",
				contentType: 'application/json',
				data: JSON.stringify(resources),
				success: function (response) {

				},
				error: function (xhr) {
					debugger;
					alert('fail'+xhr);
				}
			});
		},
		select: function(start, end,event){

			$('.check-calendar-content-view-smalltitle').text('');
			$('#calendar-description').val("");
			$input_false = $("#calendar-title").attr('readonly',false);
			$('.descrption-a').toggle(100);
			$('.descrption-textarea').toggle(100);
			$('.check-calendar-content-view-smalltitle').toggle(100);
			$('.calendar-input-title').toggle(100);
			$('#calendar-start').text('');
			$('#calendar-end').text('');
			$('.descrption-a').text('');

			this.eventData;
			firstend = moment(end).format("YYYY-MM-DD");
			var yester = moment(firstend).add(-1,'days');
			selectend = moment(yester).format("YYYY-MM-DD");

			selectstart = moment(start).format("YYYY-MM-DD");
			//ajax를 가지고 와서 success시에 return 되는 테이블의 시퀀스 값으로  event에 id를 부여한 후에 eventData를 rendering 해야한다
			console.log("select start:"+selectstart);
			console.log("select end:"+selectend);

		}
	});
	$("#delete").click(function(){
		$('#calendar').fullCalendar('removeEvents',getId);
		$('#calendar-title').text('');
		$('#calendar-start').text('');
		$('#calendar-end').text('');
		$('#calendar-description').text('');
		var resources =
		{
				"cid":getId+""
		};
		console.log(getId);
		$.ajax({
			type: "POST",
			url: "eventDeleteData",
			contentType: 'application/json',
			data: JSON.stringify(resources),
			success: function (response) {
				$('.descrption-a').text("");
				$('.check-calendar-content-view-smalltitle').text("");
			},
			error: function (xhr) {
				$('.descrption-a').text("");
				$('.check-calendar-content-view-smalltitle').text("");
				debugger;
				alert('fail'+xhr);
			}
		});

		$('#calendar-title').val("");
		$('#calendar-description').val("");
		$button_delete_true = $("#delete").attr('disabled',true);
		$button_modify_true = $("#modify").attr('disabled',true);
	});

	$("#modify").click(function(){

		$button_create_true = $("#create").attr('disabled',true);
		$button_ok_false = $("#ok").attr('disabled',false);
		$input_false = $("#calendar-title").attr('readonly',false);
		$('.descrption-a').toggle(100);
		$('.descrption-textarea').toggle(100);
		$('.check-calendar-content-view-smalltitle').toggle(100);
		$('.calendar-input-title').toggle(100);



	});
	//데이터를 저장하는역할 controller에 직접 연결된다.
	$('#create').click(function(){
		var selecttitle=$('#calendar-title').val();
		var selectdescription=$('#calendar-description').val();
		this.title= selecttitle;
		this.description=selectdescription;
		var formtitle = this.title;
		var formdescription=this.description;
		if(this.title){
			var resources =
			{
					"title":this.title+"",
					"start":selectstart+"",
					"end":selectend+"",
					"description":this.description+""
			};

			$.ajax(
					{
						type: "POST",
						url: "eventSelectdata",
						contentType: 'application/json;',
						data: JSON.stringify(resources),
						success: function (cid) {
							passcid = cid;

							this.eventData = {
									title: selecttitle,
									start: selectstart,
									end: firstend,
									id: passcid,
									description:selectdescription
							};
							$('#calendar').fullCalendar('renderEvent', this.eventData, true);
							$('.descrption-a').toggle(100);
							$('.descrption-textarea').toggle(100);
							$('.check-calendar-content-view-smalltitle').toggle(100);
							$('.calendar-input-title').toggle(100);

							$('.check-calendar-content-view-smalltitle').text('');
							$('.descrption-a').text("");
							$button_ok_true = $("#ok").attr('disabled',true);
							$('#calendar-start').text('');
							$('#calendar-end').text('');
						},
						error: function (xhr) {
							$('.descrption-a').toggle(100);
							$('.descrption-textarea').toggle(100);
							$('.check-calendar-content-view-smalltitle').toggle(100);
							$('.calendar-input-title').toggle(100);

							$('.check-calendar-content-view-smalltitle').text('');
							$('.descrption-a').text("");
							$button_ok_true = $("#ok").attr('disabled',true);
							$('#calendar-start').text('');
							$('#calendar-end').text('');
						}

					});

		}

	});
	$("#ok").click(function(){
		var modiObject = JSON.stringify($('#calendar').fullCalendar('clientEvents',getId).map(function(e){
			return {
				start: e.start,
				end: e.end,
				title:e.title
			};
		}));
		var modijson = JSON.parse(modiObject);

		var input_title = document.getElementById("calendar-title").value;


		var input_description = document.getElementById("calendar-description").value;

		this.title = input_title;
		this.description = input_description;
		var tstart = modijson[0].start;
		var tend = modijson[0].end;

		var formattest = moment(tend).format("YYYY-MM-DD");
		var yester = moment(formattest).add(-1,'days');
		var end_date = moment(yester).format("YYYY-MM-DD");
		console.log("end_date : "+end_date);
		if (this.title) {

			modievent.title = this.title;
			modievent.description = this.description;
			modievent.start = tstart;
			modievent.end = tend;

			$('#calendar-title').val(this.title);
			$('#calendar-description').val(this.description);
			$('#calendar').fullCalendar('updateEvent', modievent);
			console.log("pass cid:"+passcid);
			var resources =
			{
					"title":this.title+"",
					"start":tstart+"",
					"end":end_date+"",
					"description":this.description+"",
					"cid":getId+""
			};

			$.ajax(
					{
						type: "POST",
						url: "eventModifydata",
						contentType: 'application/json',
						data: JSON.stringify(resources),
						success: function (response) {
							$('.descrption-a').toggle(100);
							$('.descrption-textarea').toggle(100);

							$('.check-calendar-content-view-smalltitle').toggle(100);
							$('.calendar-input-title').toggle(100);

							$('.check-calendar-content-view-smalltitle').text('');
							$('.descrption-a').text("");
							$input_false = $("#calendar-title").attr('readonly',true);
							$button_ok_true = $("#ok").attr('disabled',true);
							$button_create_false = $("#create").attr('disabled',false);
							$('#calendar-start').text("");
							$('#calendar-end').text("");
						},
						error: function (xhr) {
							$('.descrption-a').toggle(100);
							$('.descrption-textarea').toggle(100);
							$('.check-calendar-content-view-smalltitle').toggle(100);
							$('.calendar-input-title').toggle(100);

							$('.check-calendar-content-view-smalltitle').text('');
							$('.descrption-a').text("");
							$input_false = $("#calendar-title").attr('readonly',true);
							$button_ok_true = $("#ok").attr('disabled',true);
							$button_create_false = $("#create").attr('disabled',false);
							$('#calendar-start').text("");
							$('#calendar-end').text("");
							debugger;
							alert('fail'+xhr);
						}
					});

		}
	});

});
