package com.web.check.service.chat;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.web.check.DAO.ChatDAO;
import com.web.check.DTO.ChatDTO;
import com.web.check.service.Service;

public class ChatInitServiceImpl implements Service {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpSession session = request.getSession();
		
		String email = (String) session.getAttribute("loginUser");
		int bid = Integer.parseInt(session.getAttribute("boardid").toString());
		
		ChatDTO chat = new ChatDTO();
		chat.setM_email(email); chat.setB_bid(bid);
		
		ChatDAO dao = new ChatDAO();
		HashMap<Integer, ChatDTO> result = dao.initChat(chat);
		
		if(result.size()!=0)
			model.addAttribute("result", result);
	}

}
