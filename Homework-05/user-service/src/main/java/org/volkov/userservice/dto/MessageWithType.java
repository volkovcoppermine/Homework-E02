package org.volkov.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MessageWithType {
    private String type;
    private String content;
}
