package com.web.check.service.calendar;

import java.util.Map;

import org.springframework.ui.Model;

import com.web.check.DAO.CalendarDAO;
import com.web.check.service.Service;


public class EventDeleteCalendarServiceImpl implements Service{

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map =model.asMap();
		Map<String, Object> passinmap =
				(Map<String, Object>)map.get("map");
		int cid = Integer.parseInt((String)passinmap.get("cid"));
		CalendarDAO cdao = new CalendarDAO();
		int result = cdao.deleteEvent(cid);
		model.addAttribute("deleteresult",result);
	}
	
}
