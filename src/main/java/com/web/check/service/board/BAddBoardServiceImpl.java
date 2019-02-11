package com.web.check.service.board;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.web.check.DAO.BoardDAO;
import com.web.check.DTO.BoardDTO;
import com.web.check.service.Service;

public class BAddBoardServiceImpl implements Service {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		HttpSession session = request.getSession();
		
		String adminMail = (String)session.getAttribute("loginUser");
		BoardDTO board = (BoardDTO)map.get("board");
		board.setB_admin(adminMail);
		
		BoardDAO dao = new BoardDAO();
		int bid = dao.makeBoard(board);
		
		model.addAttribute("bid", bid);
	}

}
