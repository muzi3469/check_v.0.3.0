package com.web.check.service.calendar;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.web.check.DAO.CalendarDAO;
import com.web.check.service.Service;

public class EventModifyCalendarServiceImpl implements Service{

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map =model.asMap();
		Map<String, Object> passinmap =
				(Map<String, Object>)map.get("map");
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpSession session = request.getSession();
		
		int bid = Integer.parseInt(session.getAttribute("boardid").toString());
		String title = (String)passinmap.get("title");
		String start = (String)passinmap.get("start");
		String end = (String)passinmap.get("end");
		int cid = Integer.parseInt((String)passinmap.get("cid"));
		String description = (String)passinmap.get("description");
		CalendarDAO cd = new CalendarDAO();
		boolean saveresult = cd.saveModifyEvent(title,start,end,cid,description,bid);
		System.out.println("수저어어어엉디비저장결과는!!!!!!!!!!!"+saveresult);
	}
	
}
