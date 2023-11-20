package com.khu.bbangting.domain.bread.event;

import com.khu.bbangting.domain.bread.model.Bread;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TingUpdatedEvent {

    private final Bread bread;
    private final String message;

}
