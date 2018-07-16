package com.redian.chat.domain;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public class PageDTO<T> implements Serializable {
    private Integer page;
    private Integer size;
    private Long totalPages;
    private Long totalElements;
    private List<T> content;

    public PageDTO(Page<T> page) {
        this.size = page.getSize();
        this.page = page.getNumber();
        this.totalPages = page.getTotalElements();
        this.totalElements = page.getTotalElements();
        this.content = page.getContent();
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
