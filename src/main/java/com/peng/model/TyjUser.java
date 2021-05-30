package com.peng.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author pengxuanyu
 * @since 2021-05-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TyjUser extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 行业板块代码
     */
    private Integer userId;

    /**
     * 行业名称
     */
    private String nickName;

    /**
     * 作者
     */
    private String headImgUrl;

    /**
     * 当前体验金
     */
    private Integer gold;

    /**
     * 当前收益
     */
    private Integer income;

    private Integer phaseId;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;


}
