package top.kyqzwj.wx.util;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/8 19:24
 */
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static Path saveFile(MultipartFile file, String destDir){
        Path fileName;

        Path path = Paths.get(destDir);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                logger.error("创建目录失败[{}]", e.toString(), e);
                return null;
            }
        }

        fileName = Paths.get(destDir, file.getOriginalFilename());

        try{
            //对图片进行了压缩再存储
            Thumbnails.of(file.getInputStream())
                    .scale(1f)
                    .outputQuality(0.2f)
                    .toFile(fileName.toFile());
        } catch (Exception e) {
            logger.error("创建文件失败[{}]", e.toString(), e);
            return null;
        }
        return fileName;
    }

    /**
     * 获取版本的yyyy/MM/dd的路径
     *
     * @param date
     * @return
     */
    public static String getyyyyMMddPath(Date date) {
        return DateUtil.formatDate(date, DateUtil.format_yyyy_MM_dd_path);
    }

    /**
     * 获取版本的yyyy/MM的路径
     *
     * @param date
     * @return
     */
    public static String getyyyyMMPath(Date date) {
        return DateUtil.formatDate(date, DateUtil.format_yyyy_MM_path);
    }
}
