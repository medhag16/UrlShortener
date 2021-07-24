package com.urlShortener.URL_Shortener.repository;

import com.urlShortener.URL_Shortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//GET API: to query short link when generated

@Repository
public interface UrlRepository extends JpaRepository<Url,Long> {
    public Url findByShortLink(String shortLink);
}
