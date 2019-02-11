package com.web.check.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.server.ServerEndpoint;

import org.springframework.ui.Model;

import com.web.check.DTO.BoardUserDTO;
import com.web.check.constant.Constant;
import com.web.check.constant.ServerWebSocketType;

@ServerEndpoint(value="/connBoardWebsocket")
public class BoardWebSocket {
	private static final List<Session> boardSessionList = new ArrayList<Session>();
	private static final HashMap<String, BoardUserDTO> boardUserList = new HashMap<String, BoardUserDTO>();

	public BoardWebSocket() {
		// TODO Auto-generated constructor stub
		System.err.println("[Check] BoardWebSocket Constructor ...");
		System.out.println("Constant bid : " + Constant.bid);
		System.out.println("Constant email : " + Constant.loginUser);
	}

	@OnOpen
	public void onOpen(Session session) {
		System.err.println("Open session id:"+session.getId());
		System.out.println("[Check] BoardWebSocket onOpen start now...");
		System.out.println("Constant bid : " + Constant.bid);
		System.out.println("Constant email : " + Constant.loginUser);

		BoardUserDTO boardUser = new BoardUserDTO();
		boardUser.setEmail(Constant.loginUser); boardUser.setBid(Constant.bid);
		boardUser.setSession(session);

		session.setMaxIdleTimeout(1000*60*30);
		boardSessionList.add(session);
		boardUserList.put(session.getId(), boardUser);
	}
	/*
	 * 모든 사용자에게 메시지를 전달한다.
	 * @param self
	 * @param message
	 */
	private void sendAllSessionToMessage(Session self,String message) {
		System.out.println("[Check] BoardWebSocket sendAllSession executed... - " + message);

		String[] msg = message.split(":::");
		int sendbid = Integer.parseInt(msg[msg.length-1]);
		System.err.println("[Check] BoardWebSocket sendbid : " + sendbid);

		try {
			for(Session session : BoardWebSocket.boardSessionList) {
				if(!self.getId().equals(session.getId())) { 
					if(boardUserList.get(session.getId()).getBid() == sendbid)
						session.getBasicRemote().sendText(message);
				}
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@OnMessage
	public void onMessage(String message,Session session,Model model) {

		System.out.println("[Check] BoardWebSocket onMessage started...");
		/**
		 * message
		 * type ::: bid ::: 순서로..
		 */
		String[] splitMsg = message.split(":::"); 
		int bid = boardUserList.get(session.getId()).getBid();
		System.out.println("[Check] onMessage userlist my bid : " + bid);
		System.err.println("[Check] onMessage msg : " + message);

		int type = Integer.parseInt(splitMsg[0]);
		String sendMsg=null;

		sendMsg= message + ":::" + bid;
		
		if(sendMsg!=null)
			sendAllSessionToMessage(session, sendMsg);
	}

	@OnError
	public void onError(Throwable e,Session session) {
		System.err.println("[Check] BoardWebSocket onError executed...");
		System.out.println(e.getStackTrace());
		System.out.println("onError - getMessage : "+e.getMessage());
		System.out.println("onError - getCause : " + e.getCause());
		e.printStackTrace();
	}
	@OnClose
	public void onClose(Session session) {
		boardUserList.remove(session.getId());
		boardSessionList.remove(session);
	}

}





