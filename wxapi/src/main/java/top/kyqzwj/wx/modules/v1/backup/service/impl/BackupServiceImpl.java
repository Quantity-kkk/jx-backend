package top.kyqzwj.wx.modules.v1.backup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kyqzwj.wx.helper.UserHelper;
import top.kyqzwj.wx.modules.v1.backup.domain.KzOvertime;
import top.kyqzwj.wx.modules.v1.backup.dto.KzOvertimeDto;
import top.kyqzwj.wx.modules.v1.backup.repository.KzOvertimeRepository;
import top.kyqzwj.wx.modules.v1.backup.service.BackupService;
import top.kyqzwj.wx.util.DateUtil;
import top.kyqzwj.wx.util.ListUtil;
import top.kyqzwj.wx.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/14 14:16
 */
@Service
public class BackupServiceImpl implements BackupService {

    @Autowired
    KzOvertimeRepository overtimeRepository;

    @Override
    public void saveOvertimeRecord(KzOvertimeDto dto) {
        //多个加班人员
        String peopleName = dto.getPeopleName();
        //加班日期
        Date overDate = dto.getOverDate();
        String week = DateUtil.getWeekOfDate(overDate);
        if(StringUtil.isNotEmpty(peopleName, overDate)){
            peopleName = peopleName.replaceAll("，",",")
                    .replaceAll(" ","")
                    .replaceAll("\r\n","")
                    .replaceAll("\n","");

            List<KzOvertime> list = new ArrayList<>();

            for(String people : peopleName.split(",")){
                if(StringUtil.isNotEmpty(people)){
                    KzOvertime pojo = new KzOvertime();
                    pojo.setPeopleName(people);
                    pojo.setWeek(week);
                    pojo.setOverDate(overDate);
                    pojo.setCreateBy(UserHelper.getUserId());
                    list.add(pojo);
                }
            }
            if(ListUtil.isNotEmpty(list)){
                overtimeRepository.saveAll(list);

                //如果需要登记餐补
                if(dto.isCost()){
                    KzOvertime mainPojo = list.get(0);

                }
            }
        }
    }
}
