package com.koreait.exam.chat_example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RsData<T> {
    private String resultCode;
    private String msg;
    private T data; // 타입을 나중에 정하기 위해서 제네릭
}




