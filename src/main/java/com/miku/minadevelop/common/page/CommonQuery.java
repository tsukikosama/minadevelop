package com.miku.minadevelop.common.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long current;
    /**
     * 每页大小
     */
    private Long pageSize;
    /**
     * 总页数
     */
    private Integer total;

    @JsonIgnore
    public <T> IPage<T> getPage() {
        if (current == null) {
            current = 1L;
        }
        if (pageSize == null) {
            pageSize = 20L;
        }
        return new Page<>(current, pageSize);
    }
}
