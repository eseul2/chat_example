package com.koreait.exam.chat_example;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;


/* 채팅 메시지를 저장하는 클래스
 - 각 메시지는 ID, 생성 시간, 작성자, 내용 정보를 가짐*/

@AllArgsConstructor // 모든 필드를 포함하는 생성자를 자동 생성
@Data // 롬복  사용해서 게터 세터 toString 자동생성
public class ChatMessage {
    private long id;
    //JSON 날짜 형식을 지정하는 어노테이션
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createDate;
    private String authorName;
    private String content;

    // 메세지를 생성할 때 id와 생성 시간을 자동으로 할당하는 생성자
    public ChatMessage(String authorName, String content) {
        this(ChatMessageIdGenerator.getNextId(), LocalDateTime.now(), authorName, content);
    }
}



/* ChatMessage의 고유 ID를 관리하는 클래스
 - 모든 메시지가 **유일한 ID** 를 갖도록 하기 위해 사용*/

class ChatMessageIdGenerator{

    // static변수는 id에 대한 것을 얘만 관리하기 위해서
    private static long id = 0;

    public static long getNextId() {
        return id++; // 1씩 증가
    }
}