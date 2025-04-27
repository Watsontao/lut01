package com.vincent.greenhouse.service;

import com.vincent.greenhouse.pojo.Greenhouse;

import java.util.List;

public interface GreenhouseService {
    List<Greenhouse> getAll();
    Greenhouse getById(Integer id);
    void saveOrUpdate(Greenhouse greenhouse);
    void delete(Integer id);
    void deleteBatch(List<Integer> ids);
    void toggleStatus(Integer id, String type);


    //用于harmonyOS
    List<Greenhouse> getByUserId(Integer userId);

}
