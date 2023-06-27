package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String memberEmail;

    @Column(length = 40)
    private String memberPassword;

    @Column(length = 20, nullable = false, unique = true)
    private String memberNickName;

    @Column(length = 30, nullable = false)
    private String memberMobile;

    @Column(nullable = false)
    private String memberGender;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberGradeEntity> memberGradeEntityList = new ArrayList<>();

}
