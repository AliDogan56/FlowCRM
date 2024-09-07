package com.dogan.bilisim.domain.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@NoArgsConstructor
public class PageLink implements PageableRequest {

    @Min(value = 1, message = "Validation.invalid.page.size.min")
    private int pageSize = 16;
    private int page = 0;
    private SortType sortOrder;

    public PageLink(PageLink pageLink) {
        this.pageSize = pageLink.getPageSize();
        this.page = pageLink.getPage();
        this.sortOrder = pageLink.getSortType();
    }

    public PageLink(@Min(1) int pageSize) {
        this(pageSize, 0);
    }

    public PageLink(int pageSize, int page) {
        this(pageSize, page, null);
    }

    public PageLink(int pageSize, int page, SortType sortOrder) {
        this.pageSize = pageSize;
        this.page = page;
        this.sortOrder = sortOrder;
    }

    public PageLink(int pageSize, int page, String textSearch, SortOrder sortOrder) {
        this.pageSize = pageSize;
        this.page = page;
        this.sortOrder = sortOrder;
    }

    @JsonIgnore
    public PageLink nextPageLink() {
        return new PageLink(this.pageSize, this.page + 1, this.sortOrder);
    }

    @Override
    public SortType getSortType() {
        return sortOrder;
    }

    @Override
    public Integer getPage() {
        return page;
    }

    @Override
    public Integer getPageSize() {
        return pageSize;
    }
}
