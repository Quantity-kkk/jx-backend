package top.kyqzwj.wx.modules.v1.file.service;

import org.springframework.web.multipart.MultipartFile;
import top.kyqzwj.wx.modules.v1.file.domain.KzFile;

import java.util.Collection;
import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/11 11:06
 */
public interface FileService {

    Map<String, String> getFilePath(Collection collection);

    KzFile saveDailyFile(MultipartFile file, String fileType);

    KzFile saveMonthlyFile(MultipartFile file, String fileType);
}
