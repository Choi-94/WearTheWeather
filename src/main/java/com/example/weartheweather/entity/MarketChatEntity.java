package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "chat_table")
public class MarketChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 메세지 방번호

    @Column(length = 20, nullable = false)
    private String send_nick; // 메세지 보내는사람 닉네임

    @Column(length = 20, nullable = false)
    private String read_nick; // 메세지 받는사람 닉네임

    @Column(length = 1000, nullable = false)
    private String contents;   // 메세지 내용

    @Column(nullable = false)
    private int read_check; // 받는사람이 메세지 읽었는지 체크 읽으면 0, 안읽으면 1

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private MarketProductEntity product;

}