package com.urlShortener.URL_Shortener.service;

import com.google.common.hash.Hashing;
import com.urlShortener.URL_Shortener.model.Url;
import com.urlShortener.URL_Shortener.model.UrlDto;
import com.urlShortener.URL_Shortener.repository.UrlRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class UrlServiceImpl implements UrlService
{
    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url generateShortLink(UrlDto urlDto) {
        if(StringUtils.isNotEmpty(urlDto.getUrl()))
        {
            String encodedUrl=encodeUrl(urlDto.getUrl());
            Url urlToPersist=new Url();
            urlToPersist.setShortLink(encodedUrl);
            urlToPersist.setOriginalUrl(urlDto.getUrl());
            urlToPersist.setCreationDate(LocalDateTime.now());
            urlToPersist.setExpirationDate(getExpirationDate(urlDto.getExpirationDate(),urlToPersist.getCreationDate()));
            Url urlToRet=persistShortLink(urlToPersist);
            if(urlToRet!=null) return urlToRet;
            return null;
        }
        return null;
    }

    private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate) {
        if(StringUtils.isBlank(expirationDate))
        {
            return creationDate.plusSeconds(60);
        }
        LocalDateTime expirationDateToRet = LocalDateTime.parse(expirationDate);
        return expirationDateToRet;
    }

    //Encode the string in this method using Guava library
    private String encodeUrl(String url) {
        String encoded="";
        LocalDateTime time = LocalDateTime.now();
        encoded= Hashing.adler32().hashString(url.concat(time.toString()), StandardCharsets.UTF_8).toString();
        return encoded;
    }

    @Override
    public Url persistShortLink(Url url) {
        Url urlToRet=urlRepository.save(url);
        return urlToRet;
    }

    @Override
    public Url getEncodedUrl(String url) {
        Url urlToRet=urlRepository.findByShortLink(url);
        return urlToRet;
    }

    @Override
    public void deleteShortLink(Url url) {
        urlRepository.delete(url);
    }
}
