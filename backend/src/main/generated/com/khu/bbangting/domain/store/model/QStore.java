package com.khu.bbangting.domain.store.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = 241569070L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStore store = new QStore("store");

    public final StringPath description = createString("description");

    public final SetPath<com.khu.bbangting.domain.user.model.User, com.khu.bbangting.domain.user.model.QUser> followerList = this.<com.khu.bbangting.domain.user.model.User, com.khu.bbangting.domain.user.model.QUser>createSet("followerList", com.khu.bbangting.domain.user.model.User.class, com.khu.bbangting.domain.user.model.QUser.class, PathInits.DIRECT2);

    public final NumberPath<Integer> followerNum = createNumber("followerNum", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath location = createString("location");

    public final NumberPath<Double> rating = createNumber("rating", Double.class);

    public final StringPath storeName = createString("storeName");

    public final com.khu.bbangting.domain.user.model.QUser user;

    public QStore(String variable) {
        this(Store.class, forVariable(variable), INITS);
    }

    public QStore(Path<? extends Store> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStore(PathMetadata metadata, PathInits inits) {
        this(Store.class, metadata, inits);
    }

    public QStore(Class<? extends Store> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.khu.bbangting.domain.user.model.QUser(forProperty("user")) : null;
    }

}

