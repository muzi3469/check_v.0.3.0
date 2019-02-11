package com.web.check.service.calendar;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.web.check.DAO.CalendarDAO;
import com.web.check.service.Service;

public class InitEventsCalendarService implements Service{


	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpSession session = request.getSession();
		
		int bid = Integer.parseInt(session.getAttribute("boardid").toString());
		
		CalendarDAO cdao = new CalendarDAO();
		HashMap<String,HashMap> result = cdao.initCalendar(bid);
		System.out.println("요기는 컨트롤러"+result);
		model.addAttribute("result", result);
		
	}
}
