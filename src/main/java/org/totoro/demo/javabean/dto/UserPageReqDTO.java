package org.totoro.demo.javabean.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import org.totoro.common.javabean.reqDto.PageReqDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 用户表请求入参类
 *
 * @author ChangLF 2023/07/28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserPageReqDTO extends PageReqDTO {

    /**
     * id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实名称
     */
    private String rearName;

    /**
     * 密码
     */
    private String password;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
