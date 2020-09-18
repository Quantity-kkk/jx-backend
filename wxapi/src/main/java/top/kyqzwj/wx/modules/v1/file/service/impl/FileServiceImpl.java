package top.kyqzwj.wx.modules.v1.file.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.kyqzwj.wx.jpa.repository.NativeSql;
import top.kyqzwj.wx.modules.v1.file.service.FileService;
import top.kyqzwj.wx.modules.v1.file.domain.KzFile;
import top.kyqzwj.wx.modules.v1.file.repository.KzFileRepository;
import top.kyqzwj.wx.util.FileUtil;
import top.kyqzwj.wx.util.ListUtil;
import top.kyqzwj.wx.util.StringUtil;

import java.util.*;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/11 11:07
 */
@Service
public class FileServiceImpl implements FileService {

    @Value("${weChat.basedir}")
    private String baseFileDir;

    @Autowired
    KzFileRepository fileRepository;

    @Override
    public Map<String, String> getFilePath(Collection collection) {
        Map<String, String> ret = new HashMap<>(16);
        if(collection.size()>0){
            String queryImgSql = "select id, path from kz_file where id in ("+ StringUtil.joinForSqlIn(collection,",") +")";
            List<Map> imgList = NativeSql.findByNativeSQL(queryImgSql, null);
            ret = ListUtil.convertList2Map(imgList,"id", "path");
        }
        return ret;
    }

    @Override
    public KzFile saveDailyFile(MultipartFile file, String fileType){
        String fileDestPath = "/"+fileType+"/" + FileUtil.getyyyyMMddPath(new Date());
        return saveFile(file, fileDestPath);
    }

    @Override
    public KzFile saveMonthlyFile(MultipartFile file, String fileType){
        String fileDestPath = "/"+fileType+"/" + FileUtil.getyyyyMMPath(new Date());
        return saveFile(file, fileDestPath);
    }

    private KzFile saveFile(MultipartFile file, String fileDestPath){
        String fileName = file.getOriginalFilename();

        FileUtil.saveFile(file, baseFileDir + fileDestPath);
        KzFile kzFile = new KzFile();
        kzFile.setFileSize(file.getSize());

        kzFile.setPath(fileDestPath+"/"+fileName);
        kzFile.setFileType(fileName.substring(fileName.lastIndexOf(".")));
        kzFile.setOriginalName(fileName);

        return fileRepository.save(kzFile);
    }
}
