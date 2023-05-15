package org.bluett.common.translation.impl;

import org.bluett.common.annotation.TranslationType;
import org.bluett.common.constant.TransConstant;
import org.bluett.common.core.service.DeptService;
import org.bluett.common.translation.TranslationInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 部门翻译实现
 *
 * @author Bluett Dream
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.DEPT_ID_TO_NAME)
public class DeptNameTranslationImpl implements TranslationInterface<String> {

    private final DeptService deptService;

    public String translation(Object key, String other) {
        return deptService.selectDeptNameByIds(key.toString());
    }
}
