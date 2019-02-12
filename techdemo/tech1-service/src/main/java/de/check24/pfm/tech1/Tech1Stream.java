package de.check24.pfm.tech1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import io.nats.client.Message;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

import javax.annotation.Resource;

/**
 * @author jan.kopcsek
 */
public class Tech1Stream extends TextWebSocketHandler  //implements WebSocketHandler {
{
    @Resource
    private Connection nats;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String path = session.getUri().getPath();
        String subject = path.substring("/stream/subject/".length());
        nats.createDispatcher(msg -> sendMessage(session, msg))
            .subscribe(subject);
    }

    private void sendMessage(WebSocketSession session, Message msg) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(MessageTO.fromNutsMessage(msg))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public Mono<Void> handle(WebSocketSession session) {
//        String path = session.getHandshakeInfo().getUri().getPath();
//        return session.send((subscriber) ->
//            nats.createDispatcher(msg -> subscriber.onNext(session.textMessage(new String(msg.getData()))))
//                .subscribe(path));
//    }
}
