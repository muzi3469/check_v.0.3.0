package com.web.check.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.check.DTO.ChatDTO;
import com.web.check.DTO.ChatUserDTO;
import com.web.check.constant.Constant;
import com.web.check.service.Service;
import com.web.check.service.board.BoardInitServiceImpl;
import com.web.check.service.chat.ChatInitServiceImpl;
import com.web.check.service.chat.ChatInsertServiceImpl;

@Controller
@ServerEndpoint(value="/connectWebSocket")
public class ChatController {

	private static final List<Session> sessionList=new ArrayList<Session>();;
	private static final HashMap<String, ChatUserDTO> userlist = new HashMap<String, ChatUserDTO>();
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	private Service service;
	
	public ChatController() {
		// TODO Auto-generated constructor stub
		System.out.println("웹소켓(서버) 객체생성");
		System.err.println("[Check] Chat Constructor, websocket ip 주소 확인할 것.");
		//ipconfig로
	}

	@RequestMapping(value = "board_chat")
	public String board_chat(Model model, HttpServletRequest request) {
		System.err.println("board_chat_requestMapping !!");
		System.out.println("[Check] board_chat model : " + model);
		
		return "check/chat_view";
	}
	
	
	@RequestMapping(value="/initChat")
	@ResponseBody
	public HashMap<Integer, ChatDTO> initChat(Model model, HttpServletRequest request) {
		System.err.println("[Check] initChat");

		model.addAttribute("request", request);

		service = new ChatInitServiceImpl();
		service.execute(model);

		Map<String, Object> map = model.asMap();
		HashMap<Integer, ChatDTO> result = (HashMap<Integer, ChatDTO>) map.get("result");

		return result;
	}
	
	/*----------------------------------------*/

	@OnOpen
	public void onOpen(Session session) {
		System.err.println("Open session id:"+session.getId());
		System.out.println("[Check] ChatController onOpen start now...");
		
		ChatUserDTO chatUser = new ChatUserDTO();
		chatUser.setUser(Constant.loginUser); chatUser.setBid(Constant.bid);
		chatUser.setSession(session);
		
		try {
			final Basic basic = session.getBasicRemote();
			basic.sendText("Connecting Established:::server:::"+chatUser.getUser());
		} catch(Exception e) { 
			e.printStackTrace();
		}
		session.setMaxIdleTimeout(1000*60*30);
		sessionList.add(session);
		userlist.put(session.getId(), chatUser);
	}
	/*
	 * 모든 사용자에게 메시지를 전달한다.
	 * @param self
	 * @param message
	 */
	private void sendAllSessionToMessage(Session self,String message) {
		System.out.println("[Check] ChatController sendAllSession executed...");
		
		String[] msg = message.split(":::");
		int sendbid = Integer.parseInt(msg[2]);
		
		try {
			for(Session session : ChatController.sessionList) {
				if(!self.getId().equals(session.getId())) { 
					if(userlist.get(session.getId()).getBid() == sendbid)
						session.getBasicRemote().sendText(message);
				}
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@OnMessage
	public void onMessage(String message,Session session,Model model) {
		
		System.out.println("[Check] ChatController onMessage started...");
		String[] splitMsg = message.split(":::"); 
		System.err.println("[Check] onMessage sender : " + splitMsg[1] + ", bid : " + splitMsg[2]);
		
		ChatDTO chat = new ChatDTO();
		chat.setC_message(splitMsg[0]);
		chat.setM_email(splitMsg[1]);
		chat.setB_bid(Integer.parseInt(splitMsg[2]));
		
		ChatInsertServiceImpl service = new ChatInsertServiceImpl();
		boolean insertResult = service.execute(chat);
		
		System.out.println("[Check] onMessage chat index : " + chat.getC_chatindex());

		if(splitMsg[1]!=null && splitMsg[2]!=null) {
			String msg = message;
			try {
				final Basic basic=session.getBasicRemote();	
				basic.sendText(splitMsg[0] + ":::me:::" + splitMsg[2]);		
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			sendAllSessionToMessage(session, msg);
		}
	}

	@OnError
	public void onError(Throwable e,Session session) {
		System.err.println("[Check] ChatController onError executed...");
		System.out.println(e.getStackTrace());
		System.out.println("onError - getMessage : "+e.getMessage());
		System.out.println("onError - getCause : " + e.getCause());
		e.printStackTrace();
	}
	@OnClose
	public void onClose(Session session) {
		logger.info("Session "+session.getId()+" has ended");
		userlist.remove(session.getId());
		sessionList.remove(session);
	}

}
