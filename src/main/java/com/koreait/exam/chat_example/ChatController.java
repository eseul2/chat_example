package com.koreait.exam.chat_example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chat")
public class ChatController {

    // RsData에서 정하지 않은 타입이 여기서 정해졌다.
    ChatMessage message = new ChatMessage("김철수","안녕");

    @PostMapping("/writeMessage")
    @ResponseBody
    //그래서 <> 이렇게 안에 지정을 했음 제네릭 찾아봐라
    public RsData<ChatMessage> writeMessage() {
        return new RsData<>("S-1","메세지가 작성됨", message);
    }
}






