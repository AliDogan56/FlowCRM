package com.dogan.bilisim.domain.page;

public interface PageableRequest {

    SortType getSortType();

    Integer getPage();

    Integer getPageSize();
}
