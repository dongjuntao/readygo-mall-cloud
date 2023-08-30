package com.mall.goods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.EvaluationEntity;
import com.mall.goods.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("save")
    public CommonResult save(@RequestBody EvaluationEntity evaluationEntity) {
        Long userId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        evaluationEntity.setMemberId(userId);
        return evaluationService.saveEvaluation(evaluationEntity) > 0 ? CommonResult.success()
                : CommonResult.fail();
    }

    /**
     * 分页查询评论列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("list")
    public CommonResult list(@RequestParam(value = "pageNum",required = false) Integer pageNum,
                             @RequestParam(value = "pageSize",required = false) Integer pageSize) {
        Long userId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        PageUtil pageResult = evaluationService.queryPage(pageNum, pageSize, userId);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), pageResult);
    }
}
