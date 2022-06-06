package com.lwf.demo.service;

import com.lwf.demo.dao.InformationsMapper;
import com.lwf.demo.pojo.Informations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class InformationsServiceImpl implements InformationsService {

    @Resource
    private InformationsMapper informationsMapper;


    public List<Informations> findInformation() {
        return informationsMapper.findInformation();
    }


    public Informations findInformationsById(Long id) {
        return informationsMapper.findInformationsById(id);
    }


    public int updateInfoViewCount(Long id) {
        return informationsMapper.updateInfoViewCount(id);
    }


    public int updateinforeplyCount(Long id, Date lastPostTime) {
        return informationsMapper.updateinforeplyCount(id, lastPostTime);
    }
}
