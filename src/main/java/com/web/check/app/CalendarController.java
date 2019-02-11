package com.web.check.app;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.web.check.service.Service;
import com.web.check.service.calendar.EventDeleteCalendarServiceImpl;
import com.web.check.service.calendar.EventDragCalednarServiceImpl;
import com.web.check.service.calendar.EventResizeCalendarServiceImpl;
import com.web.check.service.calendar.EventSelectCalendarServiceImpl;
import com.web.check.service.calendar.InitEventsCalendarService;

@Controller
public class CalendarController {

	private Service chkService;
	@RequestMapping(value = "board_calendar")
	public String board_calendar(Model model,HttpServletResponse response) {
		System.err.println("board_calendar_requestMappint !!");

		return "check/calendar_view";
	}
	@RequestMapping(value = "/eventDropdata" , method=RequestMethod.POST, produces = "application/json; charset=UTF-8",consumes="application/json")
	@ResponseBody
	public void eventDropdata(@RequestBody  Map map,Model model, HttpServletRequest request){//HashMap<Object, Object> param 원래는 파람 값으로 해쉬맵 형태로 넘어 오지만, js 쪽에서 배열로 넘겼기 때문에 array로 받�f나
      
		model.addAttribute("map",map);
		model.addAttribute("request",request);
		chkService = new EventDragCalednarServiceImpl();
		chkService.execute(model);
	}
	
	@RequestMapping(value = "/eventSelectdata" , method=RequestMethod.POST, produces = "application/json; charset=UTF-8",consumes="application/json")
	@ResponseBody
	public int eventSelectdata(@RequestBody  Map map,Model model, HttpServletRequest request){//HashMap<Object, Object> param 원래는 파람 값으로 해쉬맵 형태로 넘어 오지만, js 쪽에서 배열로 넘겼기 때문에 array로 받�f나
		
		model.addAttribute("map",map);
		model.addAttribute("request",request);
		chkService = new EventSelectCalendarServiceImpl();
		chkService.execute(model);
		Map<String,Object> m = model.asMap();
		int cid = (Integer)m.get("cid");
		
		
		return cid;
	}
	
	@RequestMapping(value = "/eventResizedata" , method=RequestMethod.POST, produces = "application/json; charset=UTF-8",consumes="application/json")
	@ResponseBody
	public void eventResizedata(@RequestBody  Map map,Model model, HttpServletRequest request){//HashMap<Object, Object> param 원래는 파람 값으로 해쉬맵 형태로 넘어 오지만, js 쪽에서 배열로 넘겼기 때문에 array로 받�f나   
		model.addAttribute("map",map);
		model.addAttribute("request",request);
		chkService = new EventResizeCalendarServiceImpl();
		chkService.execute(model);
	}
	
	@RequestMapping(value = "/eventModifydata" , method=RequestMethod.POST, produces = "application/json; charset=UTF-8",consumes="application/json")
	@ResponseBody
	public void eventModifydata(@RequestBody  Map map,Model model, HttpServletRequest request){//HashMap<Object, Object> param 원래는 파람 값으로 해쉬맵 형태로 넘어 오지만, js 쪽에서 배열로 넘겼기 때문에 array로 받�f나
      
		model.addAttribute("map",map);
		model.addAttribute("request",request);
		chkService = new EventResizeCalendarServiceImpl();
		chkService.execute(model);
	}
	@RequestMapping(value = "/initCalendar" , method=RequestMethod.POST, produces = "application/json; charset=UTF-8",consumes="application/json")
	@ResponseBody

	public HashMap<Integer,HashMap> initCalendar(Model model, HttpServletResponse response, HttpServletRequest request){//HashMap<Object, Object> param 원래는 파람 값으로 해쉬맵 형태로 넘어 오지만, js 쪽에서 배열로 넘겼기 때문에 array로 받�f나
		
		// 해쉬맵으로 돌려보자		
		HashMap<Integer, HashMap> result = new HashMap<Integer, HashMap>();
		model.addAttribute("request",request);
		chkService = new InitEventsCalendarService();
		chkService.execute(model);
		Map<String,Object> m = model.asMap();
		result = (HashMap<Integer, HashMap>) m.get("result");
	
		response.setContentType("application/json"); 
		response.setCharacterEncoding("UTF-8");
		
		
		return result;
	}
	
	@RequestMapping(value = "/eventDeleteData" , method=RequestMethod.POST, produces = "application/json; charset=UTF-8",consumes="application/json")
	@ResponseBody
	public void eventDeleteData(@RequestBody Map map,Model model, HttpServletRequest request){//HashMap<Object, Object> param 원래는 파람 값으로 해쉬맵 형태로 넘어 오지만, js 쪽에서 배열로 넘겼기 때문에 array로 받�f나
		System.out.println("딜리트 잘들어왔구연");
		model.addAttribute("map",map);
		model.addAttribute("request",request);
		chkService = new EventDeleteCalendarServiceImpl();
		chkService.execute(model);	
	}
}
