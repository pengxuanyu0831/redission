package com.peng.mapper;

import com.peng.model.TyjUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author pengxuanyu
 * @since 2021-05-30
 */
public interface TyjUserMapper extends BaseMapper<TyjUser> {
    TyjUser selectIncoome();

    Integer updateIncome(Integer userId);

}
