package com.xwdz.site.mapper;

import com.xwdz.site.base.BaseMapper;
import com.xwdz.site.entity.ApiChannel;
import org.apache.ibatis.annotations.Mapper;

/**
 * 检查客户端key合法性
 * 1. 比对数据库通过Api传输的key
 */
@Mapper
public interface ApiChannelMapper extends BaseMapper<ApiChannel> {


    ApiChannel findByKey(String key);

}
