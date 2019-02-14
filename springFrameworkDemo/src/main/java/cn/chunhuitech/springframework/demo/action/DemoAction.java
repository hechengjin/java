package cn.chunhuitech.springframework.demo.action;

import cn.chunhuitech.springframework.demo.service.IDemoService;
import cn.chunhuitech.springframework.framework.annotation.CHAutowried;
import cn.chunhuitech.springframework.framework.annotation.CHController;
import cn.chunhuitech.springframework.framework.annotation.CHRequestMapping;
import cn.chunhuitech.springframework.framework.annotation.CHRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@CHController("/demo")
@CHController
@CHRequestMapping("/demo")
public class DemoAction {

    @CHAutowried
    private IDemoService demoService;

    @CHRequestMapping("/query.json")
    public void query(HttpServletRequest req, HttpServletResponse resp, @CHRequestParam("name") String name) {
        String result = demoService.get(name);
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @CHRequestMapping("/edit.json")
    public void edit(HttpServletRequest req, HttpServletResponse resp, @CHRequestParam("id") Integer id) {
        try {
            resp.getWriter().write(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
