package org.bluett.generator.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.bluett.common.core.mapper.BaseMapperPlus;
import org.bluett.generator.domain.GenTableColumn;

import java.util.List;

/**
 * 业务字段 数据层
 *
 * @author Bluett Dream
 */
@InterceptorIgnore(dataPermission = "true")
public interface GenTableColumnMapper extends BaseMapperPlus<GenTableColumnMapper, GenTableColumn, GenTableColumn> {
    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    List<GenTableColumn> selectDbTableColumnsByName(String tableName);

}
