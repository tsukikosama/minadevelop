package com.miku.minadevelop.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonQuery {

    /**
     * 当前页数
     */
    private Integer pageNum;
    /**
     * 每页数据
     */
    private Integer pageSize;
    /**
     * 总数
     */
    private Integer totalCount;
    /**
     * 总页数
     */
    private Integer totalPages;

}
