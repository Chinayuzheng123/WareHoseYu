
package com.lwf.demo.controller;


import com.alibaba.fastjson.JSON;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwf.demo.pojo.Informations;
import com.lwf.demo.pojo.Replies;
import com.lwf.demo.service.InformationsService;
import com.lwf.demo.service.RepliesMapperService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class InformationsController {

    @Resource
    private InformationsService informationsService;

    @Resource
    private RepliesMapperService repliesMapperService;

    @RequestMapping(value = "/index.action", method = RequestMethod.POST)
    @ResponseBody
    public Object toInformations(Integer pageNum) {
        if (pageNum == null) {
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, 2, true);
        List<Informations> infoList = informationsService.findInformation();
        PageInfo<Informations> pageInfo = new PageInfo<>(infoList);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("pageInfo", pageInfo);
        String json = JSON.toJSONStringWithDateFormat(resultMap, "yyyy-MM-dd HH:mm-ss");
        return json;
    }

    @RequestMapping(value = "/addReplies.action", method = RequestMethod.POST)
    @ResponseBody
    public Object addReplies(Replies replies) {
        Map<String, Object> resultMap = new HashMap<>();
        replies.setReplyTime(new Date());
        int num = repliesMapperService.insertReplies(replies);
        if (num > 0) {
            informationsService.updateinforeplyCount(replies.getInfoId(), new Date());
            resultMap.put("flag", "success");
            resultMap.put("replies", replies);
        } else {
            resultMap.put("flag", "fail");
        }
        String json = JSON.toJSONStringWithDateFormat(resultMap, "yyyy-MM-dd HH:mm-ss");
        return json;
    }

    @RequestMapping(value = "/detail.action", method = RequestMethod.POST)
    @ResponseBody
    public Object toInfoandReplies(Long id) {
        Informations info = informationsService.findInformationsById(id);
        List<Replies> rlist = repliesMapperService.finReplisByInfoId(id);
        int num = informationsService.updateInfoViewCount(id);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("info", info);
        resultMap.put("rlist", rlist);
        String json = JSON.toJSONStringWithDateFormat(resultMap, "yyyy-MM-dd HH:mm-ss");
        return json;
    }

}

