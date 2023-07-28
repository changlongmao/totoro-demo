package org.totoro.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.totoro.demo.entity.UserEntity;
import org.totoro.demo.javabean.dto.UserPageReqDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表Mapper
 *
 * @author ChangLF 2023/07/28
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param reqDTO 查询参数
     * @return List
     */
    List<UserEntity> selectUserPage(Page<UserEntity> page, @Param("params") UserPageReqDTO reqDTO);
}
