package com.mall.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.seckill.entity.SeckillGoodsSkuEntity;
import com.mall.seckill.mapper.SeckillGoodsSkuMapper;
import com.mall.seckill.service.SeckillGoodsSkuService;
import com.mall.seckill.vo.GoodsSkuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 秒杀商品详细信息service实现
 * @Date 2022/1/6 10:06
 * @Version 1.0
 */
@Service("seckillGoodsDetailService")
public class SeckillGoodsSkuServiceImpl
        extends ServiceImpl<SeckillGoodsSkuMapper, SeckillGoodsSkuEntity> implements SeckillGoodsSkuService {

    @Autowired
    private SeckillGoodsSkuMapper seckillGoodsSkuMapper;

    @Override
    public List<SeckillGoodsSkuEntity> getSeckillGoodsSkuListByIds(List<Long> seckillGoodsSkuIds) {
        return seckillGoodsSkuMapper.getSeckillGoodsSkuListByIds(seckillGoodsSkuIds);
    }

    /**
     * 根据当前时间获取当前批次
     */
    @Override
    public String getBatchByCurrentTime(LocalDateTime localDateTime) {
        int hour = localDateTime.getHour(); //当前小时
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateTimeFormatter.format(localDateTime);
        String currentBatch = "";
        switch (hour) {
            case 0:
            case 1:
                currentBatch = date+" 00:00:00" + "," +date+" 02:00:00"; break;
            case 2:
            case 3:
                currentBatch = date+" 02:00:00" + "," +date+" 04:00:00"; break;
            case 4:
            case 5:
                currentBatch = date+" 04:00:00" + "," +date+" 06:00:00"; break;
            case 6:
            case 7:
                currentBatch = date+" 06:00:00" + "," +date+" 08:00:00"; break;
            case 8:
            case 9:
                currentBatch = date+" 08:00:00" + "," +date+" 10:00:00"; break;
            case 10:
            case 11:
                currentBatch = date+" 10:00:00" + "," +date+" 12:00:00"; break;
            case 12:
            case 13:
                currentBatch = date+" 12:00:00" + "," +date+" 14:00:00"; break;
            case 14:
            case 15:
                currentBatch = date+" 14:00:00" + "," +date+" 16:00:00"; break;
            case 16:
            case 17:
                currentBatch = date+" 16:00:00" + "," +date+" 18:00:00"; break;
            case 18:
            case 19:
                currentBatch = date+" 18:00:00" + "," +date+" 20:00:00"; break;
            case 20:
            case 21:
                currentBatch = date+" 20:00:00" + "," +date+" 22:00:00"; break;
            case 22:
            case 23:
                currentBatch = date+" 22:00:00" + "," +date+" 23:59:59"; break;
            default: currentBatch = null;
        }
        return currentBatch;
    }
}
