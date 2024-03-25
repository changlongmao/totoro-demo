package org.totoro.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.util.Date;

/**
 * 用户表实体类
 * <p>请不要手动修改该类</p>
 *
 * @author ChangLF 2023/07/28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class UserEntity extends Model<UserEntity> {

    /**
     * id
     */
    @TableId
    private String id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 真实名称
     */
    @TableField("rear_name")
    private String rearName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 日期
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField("`is_delete`")
    private Boolean deleteFlag;

    /**
     * 更新时间
     */
    @TableField("`update_time`")
    private Date updateTime;

}
