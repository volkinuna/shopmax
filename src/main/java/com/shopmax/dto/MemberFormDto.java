package com.shopmax.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {

    private String name; //타입과 이름은 entity와 맞춰야 한다.

    private String email;

    private String password;

    private String address;
}
