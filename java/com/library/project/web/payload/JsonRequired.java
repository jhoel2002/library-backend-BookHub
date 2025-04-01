package com.library.project.web.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Data
@NoArgsConstructor
public class JsonRequired
{
    private Integer status;
    private Map<String, String> message;
    private String url;
    private String time;

    public JsonRequired(Integer status,Map<String, String> message, String url) {
        this.status = status;
        this.message = message;
        this.url = url.replace("uri=","");
        this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
