package com.neighbourly.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Response<T> {
    private String uuid;
    private T data;

}
