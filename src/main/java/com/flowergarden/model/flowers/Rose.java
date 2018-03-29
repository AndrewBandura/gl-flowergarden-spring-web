package com.flowergarden.model.flowers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rose extends GeneralFlower {

    private boolean spike;

    public Rose() {
        super.name = "rose";
    }
}
