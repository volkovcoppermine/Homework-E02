package org.volkov.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MessageWithType {
    @JsonProperty
    private String type;
    @JsonProperty
    private String content;
}
