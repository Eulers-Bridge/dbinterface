package com.eulersbridge.iEngage.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eulersbridge.iEngage.core.services.NewsService;

@RestController
@RequestMapping("/api")
public class NewsController 
{
    @Autowired NewsService newsService;

}
