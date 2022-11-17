package com.lianxi.mapper;

import com.lianxi.pojo.Brand;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author fq
 * @create 2022-11-16 15:33
 */
public interface BrandMapper {
    /**
     * 查询所有
     * @return
     */
    @Select("select * from tb_brand")
    @ResultMap("brandResultMap")
    List<Brand> selectAll();

    @Insert("insert into tb_brand values (null,#{brandName},#{companyName},#{ordered},#{description},#{status});")
    void add(Brand brand);

    @Select("select * from tb_brand where id=#{id}")
    @ResultMap("brandResultMap")
    Brand selectById(int id);

    @Update("update tb_brand set brand_name = #{brandName},company_name = #{companyName},ordered = #{ordered},description = #{description},status = #{status} where id=#{id};")
    void update(Brand brand);

    @Delete("delete from tb_brand where id=#{id};")
    void delete(int id);
}