package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//최근검색어  DTO엔 작성일자 입력받는 칸 있어야함
@Entity
@Getter
@Setter
@Table(name = "recent_keywords_table")
public class



































RecentKeywordsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;


}
