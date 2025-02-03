package com.koreait.exam.chat_example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @GetMapping("/writeMessage")
    @ResponseBody
    public String writeMessage() {
        return "메세지 작성됨";
    }
}




