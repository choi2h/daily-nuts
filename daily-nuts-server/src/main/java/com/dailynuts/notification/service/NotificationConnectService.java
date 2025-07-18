package com.dailynuts.notification.service;

import com.dailynuts.notification.service.message.MessageFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConnectService {

    @Value(value = "${notification.timeout}")
    private Long MAX_CONNECTION_TIMEOUT_MS;
    private final Map<Long, SseEmitter> clients = new ConcurrentHashMap<>();

    public SseEmitter getSseEmitter(Long memberId) {
        // SSE 연결
        log.info("Connect sseEmitter for memberId: {}", memberId);
        SseEmitter sseEmitter = new SseEmitter(MAX_CONNECTION_TIMEOUT_MS);

        // 타임아웃 종료
        sseEmitter.onTimeout(() -> {
            log.info("Timeout sseEmitter for memberId: {}", memberId);
            clients.remove(memberId);
        });

        // 연결 종료
        sseEmitter.onCompletion(() -> {
            log.info("Complete sseEmitter for memberId: {}", memberId);
            clients.remove(memberId);
        });

        // 처음 연결 시 메시지 전송
        clients.put(memberId, sseEmitter);
        sendMessage(sseEmitter, MessageFormatter.CONNECT_MESSAGE);

        return sseEmitter;
    }

    public void sendMessageToClient(Long memberId, String message) {
        SseEmitter sseEmitter = clients.get(memberId);
        if(sseEmitter == null) {
            log.info("Not exist sseEmitter for memberId: {}", memberId);
            return;
        }

        sendMessage(sseEmitter, message);
        log.info("Success to send message to memberId: {}", memberId);
    }

    public void sendMessage(SseEmitter sseEmitter, String message) {
        try {
            log.info("Sending message to client: {}", message);
            sseEmitter.send(
                    SseEmitter.event()
                            .name("open")
                            .data(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
