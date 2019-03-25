package com.xwdz.site.mapper;

import com.xwdz.site.base.BaseMapper;
import com.xwdz.site.entity.Picture;
import org.apache.ibatis.annotations.Mapper;

/**
 * 将图片(Picture)插入数据库Mapper
 */
@Mapper
public interface PictureMapper extends BaseMapper<Picture> {


    int create(Picture picture);

    Picture findById(String id);

}
