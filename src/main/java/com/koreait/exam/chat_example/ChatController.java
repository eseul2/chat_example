package com.koreait.exam.chat_example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    //채팅 메시지를 저장하는 리스트 (메모리 내 저장소 역할 아직 db를 안만들어서..)
    private List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();

//    @AllArgsConstructor
//    @Getter
//    public static class  writeChatMessageRequest {
//        private final String authorName;
//        private final String content;
//    }

    // 클라이언트에서 채팅 메세지를 보낼 때 사용하는 요청 데이터 클래스
    public record   writeChatMessageRequest(String authorName, String content) {

    }

     /*채팅 메시지 작성 후, 클라이언트에게 응답을 보낼 때 사용할 record
     - record는 Java 14+ 에서 사용 가능하며, 불변(immutable)한 데이터 객체를 쉽게 만들 수 있음*/
    public record writeChatMessageResponse(long id) {

    }

// 이 방식도 사용이 가능하다.
//    @PostMapping("/writeMessage")
//    @ResponseBody
//    //그래서 <> 이렇게 안에 지정을 했음 제네릭 찾아봐라
//    public RsData<writeChatMessageResponse> writeMessage(writeChatMessageRequest req) {
//        // RsData에서 정하지 않은 타입이 여기서 정해졌다.
//        ChatMessage message = new ChatMessage(req.getAuthorName(),req.getContent());
//        chatMessages.add(message);
//        return new RsData<>(
//                "S-1",
//                "메세지가 작성됨",
//                new writeChatMessageResponse(message.getId())
//        );
//    }

    @PostMapping("/writeMessage")
    @ResponseBody // 응답을 JSON 형식으로 변환
    //그래서 <> 이렇게 안에 지정을 했음 제네릭 찾아봐라
    public RsData<writeChatMessageResponse> writeMessage(@RequestBody writeChatMessageRequest req) {
        // 새로운 채팅 메세지 생성
        ChatMessage message = new ChatMessage(req.authorName(), req.content());
        chatMessages.add(message); // 메세지 리스트에 저장

        return new RsData<>(
                "S-1",
                "메세지가 작성됨",
                new writeChatMessageResponse(message.getId()) // 생성된 메세지의 id 포함
        );
    }

    // 채팅 메세지 리스트를 반환하는 응답 데이터 클래스
    public record messagesResponse(List<ChatMessage> messages, long count) {

    }

    // 저장된 모든 채팅 메세지를 조회
    // 현재까지 저장된 메세지를 리스트 형태로 반환
    @GetMapping("/messages")
    @ResponseBody
    public RsData<messagesResponse> messages() {
        return new RsData<>(
                "S-1",
                "메세지 리스트",
                new messagesResponse(chatMessages, chatMessages.size()) // 메세지 목록과 개수 반환
        );
    }

}






