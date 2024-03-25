package org.totoro.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.totoro.common.util.ObjectUtil;
import org.totoro.demo.mapper.UserMapper;
import org.totoro.demo.entity.UserEntity;
import org.totoro.demo.service.UserService;
import org.totoro.demo.javabean.vo.UserVO;
import org.totoro.demo.javabean.dto.UserReqDTO;
import org.totoro.demo.javabean.dto.UserPageReqDTO;
import org.totoro.common.javabean.vo.PageVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户表Service实现类
 *
 * @author ChangLF 2023/07/28
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<UserVO> queryAll(UserReqDTO reqDTO) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>(ObjectUtil.copy(reqDTO, UserEntity.class));
        List<UserEntity> list = this.list(queryWrapper);
        return ObjectUtil.copy(list, UserVO.class);
    }

    @Override
    public PageVO<UserVO> queryPage(UserPageReqDTO reqDTO) {
        Page<UserEntity> page = new Page<>(reqDTO.getPageIndex(), reqDTO.getPageSize());
        // 防止sql注入，若删除此代码需自行防止sql注入
        reqDTO.setSortColumn(ObjectUtil.objectContainColumn(reqDTO, reqDTO.getSortColumn()) ? reqDTO.getSortColumn() : null);
        List<UserEntity> list = userMapper.selectUserPage(page, reqDTO);
        return PageVO.constructPage(reqDTO, page.getTotal(), list, UserVO.class);
    }

    @Override
    public UserVO info(String id) {
        return ObjectUtil.copy(this.getById(id), UserVO.class);
    }

    @Override
    public boolean add(UserReqDTO reqDTO) {
        return this.save(ObjectUtil.copy(reqDTO, UserEntity.class));
    }

    @Override
    public boolean update(UserReqDTO reqDTO) {
        return this.updateById(ObjectUtil.copy(reqDTO, UserEntity.class));
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public void saveES(UserReqDTO reqDTO) {
        UserEntity copy = ObjectUtil.copy(reqDTO, UserEntity.class);
    }

}
