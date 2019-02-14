package cn.chunhuitech.springframework.demo.service.impl;

import cn.chunhuitech.springframework.demo.service.IDemoService;
import cn.chunhuitech.springframework.framework.annotation.CHService;

@CHService
public class DemoService implements IDemoService{
    @Override
    public String get(String name) {
        return "My name is " + name;
    }
}
