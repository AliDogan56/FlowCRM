package com.dogan.bilisim.domain.page;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class AdvancePageData<T, E> extends PageData<T> {

    private final E metaData;

    public AdvancePageData() {
        this(Collections.emptyList(), null, 0, 0, false);
    }

    @JsonCreator
    public AdvancePageData(@JsonProperty("data") List<T> data,
                           @JsonProperty("metaData") E metaData,
                           @JsonProperty("totalPages") int totalPages,
                           @JsonProperty("totalElements") long totalElements,
                           @JsonProperty("hasNext") boolean hasNext) {
        super(data, totalPages, totalElements, hasNext);
        this.metaData = metaData;
    }

    public E getMetaData() {
        return metaData;
    }
}
