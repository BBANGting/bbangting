package com.khu.bbangting.domain.follow.model;

import com.khu.bbangting.domain.store.model.Store;
import com.khu.bbangting.domain.user.model.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "follows")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "followId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
