package com.khu.bbangting.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.khu.bbangting.domain.user.model.Role;
import com.khu.bbangting.domain.user.model.Type;
import com.khu.bbangting.domain.user.model.User;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 901121785L;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> banCount = createNumber("banCount", Integer.class);

    public final DateTimePath<java.sql.Timestamp> createdDate = createDateTime("createdDate", java.sql.Timestamp.class);

    public final StringPath email = createString("email");

    // custom
    public final QUser_Follow follow = new QUser_Follow(forProperty("follow"));

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final EnumPath<Type> type = createEnum("type", Type.class);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

