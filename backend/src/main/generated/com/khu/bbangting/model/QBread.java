package com.khu.bbangting.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.khu.bbangting.domain.bread.model.Bread;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBread is a Querydsl query type for Bread
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBread extends EntityPathBase<Bread> {

    private static final long serialVersionUID = 2147394442L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBread bread = new QBread("bread");

    public final StringPath breadName = createString("breadName");

    public final BooleanPath closed = createBoolean("closed");

    public final BooleanPath created = createBoolean("created");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> maxTingNum = createNumber("maxTingNum", Integer.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public final QStore store;

    public final ComparablePath<Character> tingStatus = createComparable("tingStatus", Character.class);

    public final DateTimePath<java.time.LocalDateTime> tingTime = createDateTime("tingTime", java.time.LocalDateTime.class);

    public QBread(String variable) {
        this(Bread.class, forVariable(variable), INITS);
    }

    public QBread(Path<? extends Bread> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBread(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBread(PathMetadata metadata, PathInits inits) {
        this(Bread.class, metadata, inits);
    }

    public QBread(Class<? extends Bread> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store"), inits.get("store")) : null;
    }

}

