package com.kei.messageservice.vo;

import com.kei.messageservice.constant.MQInfo;
import lombok.Data;

@Data
public class MessageReq {

    private MQInfo mqInfo;
    private String message;
}
