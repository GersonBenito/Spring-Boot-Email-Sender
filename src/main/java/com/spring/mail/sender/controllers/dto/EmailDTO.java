package com.spring.mail.sender.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class EmailDTO {
    private String[] toUser;
    private String subject;
    private String message;
}
