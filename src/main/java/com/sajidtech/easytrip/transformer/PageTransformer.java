package com.sajidtech.easytrip.transformer;

import com.sajidtech.easytrip.dto.response.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class PageTransformer {

    public static <T, R> PageResponse<R> pageToPageResponse(Page<T> pageData, List<R> content){
        return PageResponse.<R>builder()
                .content(content)
                .pageNumber(pageData.getNumber())
                .pageSize(pageData.getSize())
                .totalElements(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
                .last(pageData.isLast())
                .build();
    }
}
