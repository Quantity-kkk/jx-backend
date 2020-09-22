package top.kyqzwj.wx.modules.v1.backup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kyqzwj.wx.helper.UserHelper;
import top.kyqzwj.wx.jpa.repository.NativeSql;
import top.kyqzwj.wx.modules.v1.backup.domain.KzActivityCost;
import top.kyqzwj.wx.modules.v1.backup.domain.KzOtSubsidy;
import top.kyqzwj.wx.modules.v1.backup.domain.KzOvertime;
import top.kyqzwj.wx.modules.v1.backup.dto.KzOtSubsidyDto;
import top.kyqzwj.wx.modules.v1.backup.dto.KzOvertimeDto;
import top.kyqzwj.wx.modules.v1.backup.dto.KzdActivityCostDto;
import top.kyqzwj.wx.modules.v1.backup.repository.KzActivityCostRepository;
import top.kyqzwj.wx.modules.v1.backup.repository.KzOtSubsidyRepository;
import top.kyqzwj.wx.modules.v1.backup.repository.KzOvertimeRepository;
import top.kyqzwj.wx.modules.v1.backup.service.BackupService;
import top.kyqzwj.wx.util.BeanUtil;
import top.kyqzwj.wx.util.DateUtil;
import top.kyqzwj.wx.util.ListUtil;
import top.kyqzwj.wx.util.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    KzOtSubsidyRepository otSubsidyRepository;

    @Autowired
    KzActivityCostRepository activityCostRepository;


    @Override
    public void saveOvertimeRecord(KzOvertimeDto dto) {
        //多个加班人员
        String peopleName = dto.getPeopleName();
        //加班日期
        Date overDate = dto.getOverDate();
        String week = DateUtil.getWeekOfDate(overDate);
        String overTimeDesc = dto.getOverTimeDesc();
        if(StringUtil.isNotEmpty(peopleName, overDate)){
            peopleName = peopleName.replaceAll("，",",")
                    .replaceAll("、",",")
                    .replaceAll(" ","")
                    .replaceAll("\r\n","")
                    .replaceAll("\n","");

            List<KzOvertime> list = new ArrayList<>();

            for(String people : peopleName.split(",")){
                if(StringUtil.isNotEmpty(people)){
                    KzOvertime pojo = new KzOvertime();
                    pojo.setPeopleName(people);
                    pojo.setWeek(week);
                    pojo.setOverTimeDesc(overTimeDesc);
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
                    KzOtSubsidy subsidyDto = new KzOtSubsidy();
                    subsidyDto.setCost(new BigDecimal("15"));
                    subsidyDto.setCreateBy(mainPojo.getCreateBy());
                    subsidyDto.setOverDate(overDate);
                    subsidyDto.setOverTimeDesc(overTimeDesc);
                    subsidyDto.setPeopleName(mainPojo.getPeopleName());
                    subsidyDto.setWeek(mainPojo.getWeek());

                    otSubsidyRepository.save(subsidyDto);
                }
            }
        }
    }

    @Override
    public void saveSubsidyRecord(KzOtSubsidyDto dto) {
        Date overDate = dto.getOverDate();
        String week = DateUtil.getWeekOfDate(overDate);

        KzOtSubsidy subsidy = new KzOtSubsidy();
        BeanUtil.beanCopy(dto, subsidy);

        subsidy.setCreateBy(UserHelper.getUserId());
        subsidy.setWeek(week);

        otSubsidyRepository.save(subsidy);
    }

    @Override
    public void saveActivityRecord(KzdActivityCostDto dto) {
        Date activityDate = dto.getActivityDate();
        String week = DateUtil.getWeekOfDate(activityDate);
        String quarter = DateUtil.getQuarterByDate(activityDate);

        String peopleName = dto.getPeopleName()
                .replaceAll("，",",")
                .replaceAll(" ","")
                .replaceAll("、",",")
                .replaceAll("\r\n","")
                .replaceAll("\n","");

        int peopleCount = peopleName.split(",").length;


        KzActivityCost activityCost = new KzActivityCost();
        BeanUtil.beanCopy(dto, activityCost);

        activityCost.setCreateBy(UserHelper.getUserId());
        activityCost.setWeek(week);
        activityCost.setPeopleCount(peopleCount);
        activityCost.setQuarter(quarter);

        activityCostRepository.save(activityCost);
    }

    @Override
    public List<Map> getOvertimeRecord(Map paramMap) {
        List params = new ArrayList();
        String sql = prepareOvertimeSql("select people_name, count(*) as over_time_count from kz_overtime where 1=1 ", paramMap, params);

        sql += " group by people_name";

        return NativeSql.findByNativeSQL(sql, params);
    }

    @Override
    public List<Map> getOvertimeRecordDetail(Map paramMap) {
        List params = new ArrayList();
        String sql = prepareOvertimeSql("select * from kz_overtime where 1=1 ", paramMap, params);

        return NativeSql.findByNativeSQL(sql, params);
    }


    private String prepareOvertimeSql(String sql, Map paramMap,List params){

        String start = (String) paramMap.get("start");
        String end = (String) paramMap.get("end");
        String peopleName = (String) paramMap.get("peopleName");

        sql = resolveCondition(sql, "create_by", UserHelper.getUserId(), "=", params);
        sql = resolveCondition(sql, "over_date", start, ">=", params);
        sql = resolveCondition(sql, "over_date", end, "<=", params);
        sql = resolveCondition(sql, "people_name", peopleName, "=", params);

        return sql;
    }



    private String resolveCondition(String sql, String field, Object value, String op, List params){
        if(StringUtil.isNotEmpty(value)){
            params.add(value);
            sql += " and " + field + op +"? ";
        }
        return sql;
    }


    @Override
    public List<Map> getSubsidyRecord(Map paramMap) {
        List params = new ArrayList();
        String sql = "select * from kz_ot_subsidy where 1=1 ";

        String start = (String) paramMap.get("start");
        String end = (String) paramMap.get("end");

        sql = resolveCondition(sql, "create_by", UserHelper.getUserId(), "=", params);
        sql = resolveCondition(sql, "over_date", start, ">=", params);
        sql = resolveCondition(sql, "over_date", end, "<=", params);

        return NativeSql.findByNativeSQL(sql, params);
    }

    @Override
    public List<Map> getActivityRecord(Map paramMap) {
        List params = new ArrayList();
        String sql = "select * from kz_activity_cost where 1=1 ";

        String start = (String) paramMap.get("start");
        String end = (String) paramMap.get("end");

        sql = resolveCondition(sql, "create_by", UserHelper.getUserId(), "=", params);
        sql = resolveCondition(sql, "activity_date", start, ">=", params);
        sql = resolveCondition(sql, "activity_date", end, "<=", params);

        return NativeSql.findByNativeSQL(sql, params);
    }

}
