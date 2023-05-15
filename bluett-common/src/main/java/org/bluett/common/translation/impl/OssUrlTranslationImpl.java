package org.bluett.common.translation.impl;

import org.bluett.common.annotation.TranslationType;
import org.bluett.common.constant.TransConstant;
import org.bluett.common.core.service.OssService;
import org.bluett.common.translation.TranslationInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * OSS翻译实现
 *
 * @author Bluett Dream
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.OSS_ID_TO_URL)
public class OssUrlTranslationImpl implements TranslationInterface<String> {

    private final OssService ossService;

    public String translation(Object key, String other) {
        return ossService.selectUrlByIds(key.toString());
    }
}
