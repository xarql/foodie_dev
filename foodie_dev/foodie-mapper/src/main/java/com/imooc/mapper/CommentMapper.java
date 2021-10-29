package com.imooc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface CommentMapper {
   public Integer getCommentCounts(@Param("itemId") String itemId
          ,@Param("level") Integer level);

}
