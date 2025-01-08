package com.threeline.AccountService.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultApiResponse {
    private String status;
    private String message;;
    private Object data;
}
