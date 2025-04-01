package com.library.project.web.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class ApiResponse {

    private Integer status;
    private String message;
    private String time;
    private String url;

    public ApiResponse(Integer status,String message, String url) {
        this.status = status;
        this.message = message;
        this.url = url.replace("uri=","");
        this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
