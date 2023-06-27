package com.example.weartheweather.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//인기검색어
@Entity
@Setter
@Getter
@Table(name = "popular_keywords_table")
public class PopularKeywordsEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(length = 50)
        private String keyword;

        @Column
        private Long count;

}
