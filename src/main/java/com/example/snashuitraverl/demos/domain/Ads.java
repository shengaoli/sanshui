package com.example.snashuitraverl.demos.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 广告
 * @TableName ads
 */
@TableName(value ="ads")
@Data
public class Ads implements Serializable {
    /**
     * 广告序号
     */
    @TableId(type = IdType.AUTO)
    private Integer adsID;

    /**
     * 广告照片
     */
    private String adsAvatural;

    /**
     * 广告链接
     */
    private String adsLink;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}