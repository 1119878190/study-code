package com.study.seata.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-08-24 23:05:21
 */
@Data
@TableName("tab_storage")
public class TabStorageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 产品id
	 */
	private Long productId;
	/**
	 * 总库存
	 */
	private Integer total;
	/**
	 * 已用库存
	 */
	private Integer used;

}
