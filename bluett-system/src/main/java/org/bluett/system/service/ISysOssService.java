package org.bluett.system.service;

import org.bluett.common.core.domain.PageQuery;
import org.bluett.common.core.page.TableDataInfo;
import org.bluett.system.domain.bo.SysOssBo;
import org.bluett.system.domain.vo.SysOssVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 文件上传 服务层
 *
 * @author Bluett Dream
 */
public interface ISysOssService {

    TableDataInfo<SysOssVo> queryPageList(SysOssBo sysOss, PageQuery pageQuery);

    List<SysOssVo> listByIds(Collection<Long> ossIds);

    SysOssVo getById(Long ossId);

    SysOssVo upload(MultipartFile file);

    void download(Long ossId, HttpServletResponse response) throws IOException;

    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
