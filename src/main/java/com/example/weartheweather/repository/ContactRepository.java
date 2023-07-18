package com.example.weartheweather.repository;

import com.example.weartheweather.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    // findBy컬럼이름(컬럼 타입); => 정의가능
    Contact findTop1ByEmailOrderByWdateDesc(String email);
}
