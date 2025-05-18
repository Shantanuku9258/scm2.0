package com.scm.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    
    private String content;
    //this class is to control the colour of message type
    @Builder.Default
    private MessageType type= MessageType.blue;

}
