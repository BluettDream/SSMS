package org.bluett.demo.mapper;

import org.bluett.common.annotation.DataColumn;
import org.bluett.common.annotation.DataPermission;
import org.bluett.common.core.mapper.BaseMapperPlus;
import org.bluett.demo.domain.TestTree;
import org.bluett.demo.domain.vo.TestTreeVo;

/**
 * 测试树表Mapper接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),
    @DataColumn(key = "userName", value = "user_id")
})
public interface TestTreeMapper extends BaseMapperPlus<TestTreeMapper, TestTree, TestTreeVo> {

}
