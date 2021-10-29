package com.imooc.service.impl.center;

import com.github.pagehelper.PageInfo;
import com.imooc.utils.PagedGridResult;

import java.util.List;

public class BaseService {

    public PagedGridResult setterPagedGrid(List<?> list, Integer page){
        PageInfo<?> pagelist=new PageInfo<>(list);
        PagedGridResult grid=new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pagelist.getPages());
        grid.setRecords(pagelist.getTotal());
        return grid;
    }
}
