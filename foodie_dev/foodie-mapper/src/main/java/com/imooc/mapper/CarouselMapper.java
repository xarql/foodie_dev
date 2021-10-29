package com.imooc.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.pojo.CarouselDO;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarouselMapper extends BaseMapper<CarouselDO> {

    public List<CarouselDO> bannerAll(Integer  isShow);
}
