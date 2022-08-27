package com.spring.usermanagement.payload.request;

import lombok.Data;

@Data
public class UpdateActiveRequest {
    Long id;
    boolean activeStatus;

}
