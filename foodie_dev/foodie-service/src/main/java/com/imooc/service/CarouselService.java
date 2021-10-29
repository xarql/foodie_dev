package com.imooc.service;

import com.imooc.pojo.CarouselDO;

import java.util.List;

public interface CarouselService {

    /**
     * 查询所有轮播图列表
     * @param isShow
     * @return
     */
    public List<CarouselDO> querryAll(Integer isShow);
}
