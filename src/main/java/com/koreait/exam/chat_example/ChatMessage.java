package com.koreait.exam.chat_example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ChatMessage {
    private long id;
    private LocalDateTime createDate;
    private String authorName;
    private String content;

    public ChatMessage(String authorName, String content) {
        this(1, LocalDateTime.now(), authorName, content);
    }
}
