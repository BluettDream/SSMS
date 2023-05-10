package org.bluett.common.convert;


import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.bluett.common.annotation.ExcelDictFormat;

import java.lang.reflect.Field;

/**
 * name: MengHao Tian
 * date: 2023/5/10 19:42
 */
public class ExcelLongListConvert implements Converter<Object> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return Object.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public Long[] convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        ExcelDictFormat annotation = getAnnotation(contentProperty.getField());
        String separator = annotation.separator();
        String stringValue = cellData.getStringValue();
        String[] strings = stringValue.split(separator);
        Long[] longs = new Long[strings.length];
        for (int i = 0; i < strings.length; i++) {
            longs[i] = Long.valueOf(strings[i]);
        }
        return longs;
    }

    @Override
    public WriteCellData<String> convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (ObjectUtil.isNull(value)) {
            return new WriteCellData<>("");
        }
        ExcelDictFormat annotation = getAnnotation(contentProperty.getField());
        String separator = annotation.separator();
        Long[] longs = (Long[]) value;
        StringBuilder stringBuilder = new StringBuilder();
        for (Long aLong : longs) {
            stringBuilder.append(aLong).append(separator);
        }
        String s = stringBuilder.toString();
        return new WriteCellData<>(s.substring(0, s.length() - 1));
    }

    private ExcelDictFormat getAnnotation(Field field) {
        return AnnotationUtil.getAnnotation(field, ExcelDictFormat.class);
    }
}
