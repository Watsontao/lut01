package com.vincent.greenhouse.service.impl;

import com.vincent.enums.ResultCodeEnum;
import com.vincent.exception.CustomException;
import com.vincent.greenhouse.mapper.GreenhouseMapper;
import com.vincent.greenhouse.pojo.Greenhouse;
import com.vincent.greenhouse.service.GreenhouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class GreenhouseServiceImpl implements GreenhouseService {

    @Resource
    private GreenhouseMapper mapper;

    @Override
    public List<Greenhouse> getAll() {
        return mapper.selectAll();
    }

    @Override
    public Greenhouse getById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public void saveOrUpdate(Greenhouse g) {
        if (g.getGreenhouseId() == null) {          // 新增
            mapper.insert(g);
        } else {                                    // 更新
            mapper.updateById(g);
        }
    }

    @Override
    public void delete(Integer id) {
        mapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) { return; }
        mapper.deleteBatch(ids);
    }

    /** 灌溉 / 通风 / 灯光 统一切换 */
    @Override
    public void toggleStatus(Integer id, String type) {
        // 兼容 JDK8 —— 不用 List.of
        List<String> allow = Arrays.asList("irrigation", "ventilation", "lighting");
        if (!allow.contains(type)) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        mapper.toggleStatus(id, type);
    }


    //用于harmonyOS
    @Override
    public List<Greenhouse> getByUserId(Integer userId) {
        System.out.println("执行到了GreenhouseServiceImpl");
        return mapper.selectByUserId(userId);
    }


}
