package com.koreait.exam.chat_example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// API 응답 데이터를 담는 클래스 (제네릭을 사용하여 다양한 데이터 타입을 지원)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RsData<T> {
    private String resultCode;
    private String msg;
    private T data; // 타입을 나중에 정하기 위해서 제네릭
}




