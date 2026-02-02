package com.sajidtech.easytrip.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({
        "content",
        "pageNumber",
        "pageSize",
        "totalElements",
        "totalPages",
        "last"
})
@Builder
public class PageResponse<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

}

