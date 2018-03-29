package com.flowergarden.model.flowers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chamomile extends GeneralFlower {

    private int petals;

    public Chamomile() {
        super.name = "chamomile";
    }

    public boolean getPetal() {
        if (petals <= 0) return false;
        petals = -1;
        return true;
    }


}
