package com.urlShortener.URL_Shortener.service;

import com.urlShortener.URL_Shortener.model.Url;
import com.urlShortener.URL_Shortener.model.UrlDto;
import org.springframework.stereotype.Service;

@Service
public interface UrlService
{
    public Url generateShortLink(UrlDto urlDto);
    public Url persistShortLink(Url url);
    public Url getEncodedUrl(String url);
    public  void  deleteShortLink(Url url);
}
