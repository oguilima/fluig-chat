package com.fluig;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    public static void addSession(String userId, Session session) {
        if (userId != null && session != null) {
            sessions.put(userId, session);
        }
    }

    public static Session getSession(String userId) {
        if (userId == null) return null;
        return sessions.get(userId);
    }

    public static void removeSession(String userId) {
        if (userId != null) sessions.remove(userId);
    }
}
