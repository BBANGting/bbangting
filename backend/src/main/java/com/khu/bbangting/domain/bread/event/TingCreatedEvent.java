package com.khu.bbangting.domain.bread.event;

import com.khu.bbangting.domain.bread.model.Bread;
import com.khu.bbangting.domain.store.model.Store;
import lombok.Getter;

@Getter
public class TingCreatedEvent {

    private final Bread bread;

    public TingCreatedEvent(Bread bread) {
        this.bread= bread;
    }
}
