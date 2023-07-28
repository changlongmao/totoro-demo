package org.totoro.demo.javabean.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户表展示类
 *
 * @author ChangLF 2023/07/28
 */
@Data
@Accessors(chain = true)
public class UserVO {

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
