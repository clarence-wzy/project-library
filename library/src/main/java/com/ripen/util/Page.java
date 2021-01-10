package com.ripen.util;

import lombok.Data;

/**
 * 分页实体类
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:39
 * @since 2021/01/09
 */
@Data
public class Page {

    /**
     * 当前页码
     */
    private int num;
    /**
     * 每页数量
     */
    private int size;

}