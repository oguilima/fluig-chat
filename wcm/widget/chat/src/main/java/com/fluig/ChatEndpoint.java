package com.fluig;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

@ServerEndpoint(value = "/{userId}")
public class ChatEndpoint {

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        session.getUserProperties().put("userId", userId);
        SessionManager.addSession(userId, session);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        try (JsonReader reader = Json.createReader(new java.io.StringReader(message))) {
            JsonObject json = reader.readObject();
            String to = json.getString("to", null);
            String content = json.getString("content", "");
            String type = json.getString("type", "message");
            String from = (String) session.getUserProperties().get("userId");

            JsonObject outgoing = Json.createObjectBuilder()
                    .add("from", from == null ? "" : from)
                    .add("to", to == null ? "" : to)
                    .add("type", type)
                    .add("content", content)
                    .add("timestamp", System.currentTimeMillis())
                    .build();

            Session recipient = SessionManager.getSession(to);
            if (recipient != null && recipient.isOpen()) {
                recipient.getAsyncRemote().sendText(outgoing.toString());
            } else {
                JsonObject notice = Json.createObjectBuilder()
                        .add("type", "status")
                        .add("status", "offline")
                        .add("to", to == null ? "" : to)
                        .build();
                session.getAsyncRemote().sendText(notice.toString());
            }
        } catch (Exception e) {
            sendError(session, e.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        SessionManager.removeSession(userId);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        // Best-effort: close the session on error
        try {
            if (session != null && session.isOpen()) session.close();
        } catch (Exception ignore) {
        }
    }

    private void sendError(Session session, String error) {
        try {
            JsonObject obj = Json.createObjectBuilder()
                    .add("type", "error")
                    .add("message", error == null ? "" : error)
                    .build();
            session.getAsyncRemote().sendText(obj.toString());
        } catch (Exception ignore) {
        }
    }
}
