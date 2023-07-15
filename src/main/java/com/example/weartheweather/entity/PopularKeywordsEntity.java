package com.example.weartheweather.entity;

import com.example.weartheweather.dto.MemberDTO;
import com.example.weartheweather.dto.PopularKeywordsDTO;
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

        public static PopularKeywordsEntity toSaveEntity(PopularKeywordsDTO popularKeywordsDTO) {

                PopularKeywordsEntity popularKeywordsEntity = new PopularKeywordsEntity();
                popularKeywordsEntity.setKeyword(popularKeywordsDTO.getKeyword());
                return popularKeywordsEntity;
        }

}
