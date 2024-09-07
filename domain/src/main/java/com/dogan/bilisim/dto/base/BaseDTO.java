package com.dogan.bilisim.dto.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class BaseDTO implements Serializable {
    private Long id;

    public BaseDTO(Long id) {
        this.id = id;
    }
}
