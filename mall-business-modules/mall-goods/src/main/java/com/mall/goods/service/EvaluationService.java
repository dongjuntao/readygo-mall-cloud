package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.EvaluationEntity;

public interface EvaluationService extends IService<EvaluationEntity> {

    int saveEvaluation(EvaluationEntity evaluation);

    PageUtil queryPage(Integer pageNum, Integer pageSize, Long memberId);
}



