package org.totoro.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.totoro.demo.javabean.vo.UserVO;
import org.totoro.demo.javabean.dto.UserReqDTO;
import org.totoro.demo.entity.UserEntity;
import org.totoro.demo.javabean.dto.UserPageReqDTO;
import org.totoro.common.javabean.vo.PageVO;

import java.util.List;

/**
 * 用户表Service接口
 *
 * @author ChangLF 2023/07/28
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 查询所有列表
     *
     * @param userReqDTO 查询参数
     * @return List
     */
    List<UserVO> queryAll(UserReqDTO userReqDTO);

    /**
     * 分页查询用户表
     *
     * @param userPageReqDTO 查询参数
     * @return ListVO
     */
    PageVO<UserVO> queryPage(UserPageReqDTO userPageReqDTO);

    /**
     * 获取详情用户表
     *
     * @param id 主键
     * @return 新增结果
     */
    UserVO info(String id);

    /**
     * 新增用户表
     *
     * @param userReqDTO 用户表
     * @return 新增结果
     */
    boolean add(UserReqDTO userReqDTO);

    /**
     * 根据主键更新用户表
     *
     * @param userReqDTO 用户表
     * @return 更新结果
     */
    boolean update(UserReqDTO userReqDTO);

    /**
     * 根据主键删除用户表
     *
     * @param id 主键
     * @return 删除结果
     */
    boolean delete(String id);
}
