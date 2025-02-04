package com.koreait.exam.chat_example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/chat")
@Slf4j
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


    public record messagesRequest(Long fromId) {

    }

    // 채팅 메세지 리스트를 반환하는 응답 데이터 클래스
    public record messagesResponse(List<ChatMessage> messages, long count) {

    }

    // 저장된 모든 채팅 메세지를 조회
    // 현재까지 저장된 메세지를 리스트 형태로 반환
    @GetMapping("/messages")
    @ResponseBody
    public RsData<messagesResponse> messages(messagesRequest req) {

        // 상단에 전역변수를 이용해서 지역변수로 사용함
        List<ChatMessage> messages = chatMessages;

        log.debug("req : {}", req );  // 디버깅 할 수 있음. 상단에 어노테이션 붙여야됨 @Slf4j

        // 번호가 같이 입력되었다면?
        // 걍 리스트 내놔! 이게 아니라 조건에 맞춰서 내놔라~
        if(req.fromId != null) {
            int index = IntStream.range(0, messages.size())
                    .filter(i -> chatMessages.get(i).getId() == req.fromId) // 말그대로 필터임. 
                    .findFirst().orElse(-1); // 아예 없는 인덱스 번호인 -1

            if(index != -1) {
                messages = messages.subList(index + 1, messages.size());
            }

        }

        return new RsData<>(
                "S-1",
                "메세지 리스트",
                new messagesResponse(chatMessages, chatMessages.size()) // 메세지 목록과 개수 반환
        );
    }

}






