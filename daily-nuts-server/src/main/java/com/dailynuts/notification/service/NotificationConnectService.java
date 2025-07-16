package com.dailynuts.notification.service;

import com.dailynuts.notification.dto.NotificationResponseDto;
import com.dailynuts.notification.entity.Notification;
import com.dailynuts.notification.repository.NotificationRepository;
import com.dailynuts.notification.service.mapper.NotificationMapper;
import com.dailynuts.notification.service.message.MessageFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
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

        // 연결 종료
        sseEmitter.onCompletion(() -> {
            log.info("Complete sseEmitter for memberId: {}", memberId);
            clients.remove(memberId);
        });

        // 타임아웃 종료
        sseEmitter.onTimeout(() -> {
            log.info("Timeout sseEmitter for memberId: {}", memberId);
            clients.remove(memberId);
        });

        // 처음 연결 시 메시지 전송
        sendToClient(memberId, "Success to connection.");
        clients.put(memberId, sseEmitter);

        return sseEmitter;
    }

    public void sendToClient(Long memberId, String message) {
        SseEmitter sseEmitter = clients.get(memberId);
        if(sseEmitter == null) {
            log.info("Not exist sseEmitter for memberId: {}", memberId);
            return;
        }

        try {
            log.info("Sending message to client: {}", message);
            sseEmitter.send(
                    SseEmitter.event()
                            .name("message")
                            .data(message));
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Success to send message to memberId: {}", memberId);
    }

}
