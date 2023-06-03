package com.theater.domain.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;

//예매 정보
//TODO 결제일자 해당 클래스에 추가해야 하는지 고려
@Data
@AllArgsConstructor
public class Reservation {

    private Integer key; //예매번호
    //TODO payMethod를 enum 클래스를 써서 하는 방법도 고려
    private String payMethod; //결제방법
    private Boolean payStatus; //결제상태
    private Integer price; //가격
    private String id; //회원아이디

}
