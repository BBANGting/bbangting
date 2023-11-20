package com.khu.bbangting.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.khu.bbangting.domain.order.model.Order;
import com.khu.bbangting.domain.order.model.OrderStatus;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -2135567904L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final QBread bread;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final EnumPath<OrderStatus> orderStatus = createEnum("orderStatus", OrderStatus.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final QUser user;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bread = inits.isInitialized("bread") ? new QBread(forProperty("bread"), inits.get("bread")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

