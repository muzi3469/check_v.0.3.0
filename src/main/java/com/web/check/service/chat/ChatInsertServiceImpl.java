package com.web.check.service.chat;

import com.web.check.DAO.ChatDAO;
import com.web.check.DTO.ChatDTO;

public class ChatInsertServiceImpl{

	public boolean execute(ChatDTO chat) {
		// TODO Auto-generated method stub
		boolean insertResult = false;
		
		ChatDAO dao = new ChatDAO();
		insertResult = dao.insertChat(chat);
		
		System.out.println("[Check] ChatInsertServ chat nickname : " + chat.getM_nickname());
		
		
		return insertResult;
	}

}
