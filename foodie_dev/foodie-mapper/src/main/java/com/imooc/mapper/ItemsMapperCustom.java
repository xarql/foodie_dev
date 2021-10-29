package com.imooc.mapper;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
@Mapper
public interface ItemsMapperCustom {
    public List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String,Object> map);
    public List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String,Object> map);
    public List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String,Object> map);
    public int  decreapItemSpecStock(@Param("specId") String specId,@Param("pendingCounts") int pendingCounts);
}
