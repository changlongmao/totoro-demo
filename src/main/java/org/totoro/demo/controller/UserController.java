package org.totoro.demo.controller;

import org.totoro.demo.javabean.vo.UserVO;
import org.totoro.demo.javabean.dto.UserReqDTO;
import org.totoro.demo.javabean.dto.UserPageReqDTO;
import org.totoro.demo.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.totoro.common.javabean.reqDto.IdStringReqDTO;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.totoro.common.javabean.vo.PageVO;
import org.totoro.common.javabean.dto.BaseRespDTO;

import java.util.List;

/**
 * 用户表Controller层
 *
 * @author ChangLF 2023/07/28
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 查看所有列表
     *
     * @param userReqDTO 查询参数
     * @return BaseRespDTO
     */
    @PostMapping("/queryAll")
    public BaseRespDTO<List<UserVO>> queryAll(@Valid @RequestBody UserReqDTO userReqDTO) {
        return BaseRespDTO.success(userService.queryAll(userReqDTO));
    }

    /**
     * 分页查询用户表
     *
     * @param userPageReqDTO 查询参数
     * @return BaseRespDTO
     */
    @PostMapping("/queryPage")
    public BaseRespDTO<PageVO<UserVO>> queryPage(@Valid @RequestBody UserPageReqDTO userPageReqDTO) {
        return BaseRespDTO.success(userService.queryPage(userPageReqDTO));
    }

    /**
     * 根据主键查询详情
     *
     * @param idReqDTO id
     * @return BaseRespDTO
     */
    @GetMapping("/info")
    public BaseRespDTO<UserVO> info(@Valid @RequestBody IdStringReqDTO idReqDTO) {
        return BaseRespDTO.success(userService.info(idReqDTO.getId()));
    }

    /**
     * 新增用户表
     *
     * @param userReqDTO userReqDTO
     * @return BaseRespDTO
     */
    @PostMapping("/add")
    public BaseRespDTO<Void> add(@Valid @RequestBody UserReqDTO userReqDTO) {
        userService.add(userReqDTO);
        return BaseRespDTO.success();
    }

    /**
     * 修改用户表
     *
     * @param userReqDTO userReqDTO
     * @return BaseRespDTO
     */
    @PostMapping("/update")
    public BaseRespDTO<Void> update(@Valid @RequestBody UserReqDTO userReqDTO) {
        userService.update(userReqDTO);
        return BaseRespDTO.success();
    }

    /**
     * 根据主键删除用户表
     *
     * @param idReqDTO id
     * @return BaseRespDTO
     */
    @PostMapping("/delete")
    public BaseRespDTO<Void> delete(@Valid @RequestBody IdStringReqDTO idReqDTO) {
        userService.delete(idReqDTO.getId());
        return BaseRespDTO.success();
    }
}
