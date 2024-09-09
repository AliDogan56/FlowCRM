package com.dogan.bilisim.domain.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public enum PageUtil {
    INSTANCE;

    public  <T> PageData<T> toPageData(Page<T> page) {
        List<T> data = page.getContent();
        return new PageData<>(data, page.getTotalPages(), page.getTotalElements(), page.hasNext());
    }

    public  <T> PageData<T> pageToPageData(Page<T> page) {
        return new PageData<>(page.getContent(), page.getTotalPages(), page.getTotalElements(), page.hasNext());
    }

    public  Pageable toPageable(PageableRequest pageLink) {
        return toPageable(pageLink, Collections.emptyMap());
    }

    public  Pageable toPageable(PageableRequest pageLink, Map<String,String> columnMap) {
        return PageRequest.of(pageLink.getPage(), pageLink.getPageSize(), toSort(pageLink.getSortType(), columnMap));
    }

    public Sort toSort(SortOrder sortOrder) {
        return toSort(sortOrder, Collections.emptyMap());
    }

    public Sort toSort(SortType sortOrder, Map<String,String> columnMap) {
        if (sortOrder == null) {
            return Sort.unsorted();
        } else {
            String property = sortOrder.getSearchField();
            if (columnMap.containsKey(property)) {
                property = columnMap.get(property);
            }
            return Sort.by(Sort.Direction.fromString(sortOrder.getDirection().name()), property);
        }
    }

    public static PageRequest getPageRequest(int page, int pageSize, String orderProperty, String orderType) {
        SortOrder sortOrder = new SortOrder(orderProperty, orderType.equals(SortOrder.Direction.ASC.toString()) ? SortOrder.Direction.ASC : SortOrder.Direction.DESC);
        return PageRequest.of(page, pageSize, PageUtil.INSTANCE.toSort(sortOrder));
    }
}
