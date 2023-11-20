package com.khu.bbangting.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.khu.bbangting.domain.user.model.User;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser_Follow is a Querydsl query type for Follow
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUser_Follow extends BeanPath<User.Follow> {

    private static final long serialVersionUID = 1639754854L;

    public static final QUser_Follow follow = new QUser_Follow("follow");

    public QUser_Follow(String variable) {
        super(User.Follow.class, forVariable(variable));
    }

    public QUser_Follow(Path<? extends User.Follow> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser_Follow(PathMetadata metadata) {
        super(User.Follow.class, metadata);
    }

}

