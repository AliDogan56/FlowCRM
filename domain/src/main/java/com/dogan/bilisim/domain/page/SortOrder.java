package com.dogan.bilisim.domain.page;

import lombok.Data;

@Data
public class SortOrder implements SortType {

    private final String property;
    private final Direction direction;

    public SortOrder(String property) {
        this(property, Direction.ASC);
    }

    public SortOrder(String property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    @Override
    public String getSearchField() {
        return property;
    }

    public enum Direction {
        ASC, DESC
    }

}
