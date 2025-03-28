package com.nazran.springboot3firebseauth.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonDataHelper {

    public void getCommonData(Integer page, Integer size, Map<String, ?> searchResult, PaginatedResponse response, List<?> list) {

        Integer currentPage = (Integer) searchResult.get("currentPage");
        Integer nextPage = (Integer) searchResult.get("nextPage");
        Integer previousPage = (Integer) searchResult.get("previousPage");

        Map<String, Object> meta = new HashMap<>();
        meta.put("currentPage", currentPage);
        meta.put("nextPage", nextPage);
        meta.put("previousPage", previousPage);
        meta.put("totalPages", searchResult.get("totalPages"));
        meta.put("total", searchResult.get("total"));

        response.setList(list);
        response.setMeta(meta);
    }

    public void setPageSize(Integer page, Integer size) {

        if (page <= 0 || size < 0) {
            page = 1;
            size = 10;
        } else if (size > 50) {
            size = 50;
        }

    }
}

