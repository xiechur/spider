package com.xiechur.spider.admin.controller;

import com.xiechur.spider.admin.vo.BaseView;
import com.xiechur.spider.util.JsonUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = MonitorController.DIR)
public class MonitorController {

    final static String DIR = "/monitor";

    @RequestMapping(value = "/monitor.do", method = RequestMethod.GET)
    public void monitor(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(200);
        BaseView<String> view = new BaseView<String>(200, "success", "normal");
        try {
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().append(JsonUtil.toJson(view));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

}
