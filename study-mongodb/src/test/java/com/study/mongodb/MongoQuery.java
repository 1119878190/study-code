package com.study.mongodb;

import cn.hutool.core.date.DateUtil;
import com.study.mongodb.vo.AccountAndValueMapVO;
import com.study.mongodb.vo.UserActiveDateType;
import com.study.mongodb.vo.UserLoginStatusCount;
import com.study.mongodb.vo.UserUsageStatisticsRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @Author: lx
 * @Date: 2023/02/17
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoQuery {

    @Autowired
    private MongoTemplate mongoTemplate;



    @Test
    public void testDataImport() {
        // 以天为单位统计用户的登录状态数量（登录即统计，不重复统计同一用户多次登录行为)
        Criteria criteria = Criteria.where("delFlag").is(0).and("status").is("FINISH");
        criteria.and("successfulImportRows").ne(null).gt(0);

        //Criteria dateCri = userStatisticsService.appendBeginAndEndOfDay(request.getStartDate(), request.getEndDate(), "createTime");
        //if (Objects.nonNull(dateCri)) {
        //    criteria.andOperator(dateCri);
        //}
        //criteria.and("account").in(userAccounts);


        Aggregation aggregation = Aggregation.newAggregation(
                //// where条件
                Aggregation.match(criteria),
                // 显示哪几个字段
                Aggregation.project("account", "successfulImportRows", "status"),
                // 根据用户分组  累加成功导入的数据条数(去重后的)
                Aggregation.group("status").first("account").as("account").sum("successfulImportRows").as("value")
        );

        AggregationResults<AccountAndValueMapVO> dataGovResource = mongoTemplate.aggregate(aggregation, "DataGovResource", AccountAndValueMapVO.class);


        List<AccountAndValueMapVO> mappedResults = dataGovResource.getMappedResults();
        System.out.println(mappedResults);
    }


    private Criteria getCriteria(UserUsageStatisticsRequest request, Set<String> userAccounts) {
        Criteria criteria = Criteria.where("delFlag").is(0).and("status").is("FINISH").and("successfulImportRows").gt(0);
        if (CollectionUtils.isNotEmpty(userAccounts)) {
            criteria.and("account").in(userAccounts);
        }
        //Criteria dateCri = appendBeginAndEndOfDay(request.getStartDate(), request.getEndDate(), "createTime");
        //if (Objects.nonNull(dateCri)) {
        //    criteria.andOperator(dateCri);
        //}

        return criteria;
    }

    @Test
    public void findAllCount() {
        UserUsageStatisticsRequest request = new UserUsageStatisticsRequest();

        Date start = DateUtil.parse("2020-01-21");
        Date end = DateUtil.parse("2023-02-01");
        request.setStartDate(start);
        request.setEndDate(end);


        Criteria criteria = getCriteria(request, null);
        Criteria dateCri = null;
        if (Objects.nonNull(dateCri)) {
            criteria.andOperator(dateCri);
        }
        Aggregation aggregation = Aggregation.newAggregation(
                // where条件
                Aggregation.match(criteria),
                Aggregation.project("successfulImportRows"),
                // 显示哪几个字段
                Aggregation.group().sum("successfulImportRows").as("value")
        );
        AggregationResults<AccountAndValueMapVO> dataGovResource = mongoTemplate.aggregate(aggregation, "DataGovResource", AccountAndValueMapVO.class);

        AccountAndValueMapVO uniqueMappedResult = dataGovResource.getUniqueMappedResult();
        if (Objects.isNull(uniqueMappedResult)) {
            System.out.println(0);
        } else {
            System.out.println(uniqueMappedResult.getValue());
        }
    }




    @Test
    public void test55(){

        UserActiveDateType dateType = UserActiveDateType.lastMonth;
        Date startDate = new Date();

        String formatVo = "MM/dd";
        String queryFormat = "yyyy/MM/dd";
        String mongoFormat = "%Y/%m/%d";
        if (UserActiveDateType.lastYear == dateType) {
            formatVo = "yyyy/MM";
            queryFormat = "yyyy/MM";
            mongoFormat = "%Y/%m";
        }

        Criteria criteria = Criteria.where("logAction").is("Login")
                .andOperator(Criteria.where("logTime").gte(startDate));

        // 解决数据库相差8小时，查询出的时间加上 8小时 再转换成字符串
        ProjectionOperation add8h = Aggregation.project("logTime", "actor")
                .andExpression("add(logTime,8 * 3600000)").as("date8");

        // where条件
        MatchOperation match = Aggregation.match(criteria);
        ProjectionOperation project = Aggregation.project("actor", "date8").and(DateOperators.DateToString.dateOf("date8").toString(mongoFormat)).as("date");
        // 按日期和用户分组，去重用户，一个用户，一天只算一次
        GroupOperation group = Aggregation.group("date", "actor").first("date").as("date").first("actor").as("actor");
        // 按照日期分组，统计每天登录成功的总人数
        GroupOperation dateGroup = Aggregation.group("date").count().as("loginCount").first("date").as("date");
        SortOperation sort = Aggregation.sort(Sort.Direction.ASC, "date");


        Aggregation aggregation = Aggregation.newAggregation(match, add8h, project, group, dateGroup, sort);

        AggregationResults<UserLoginStatusCount> results = mongoTemplate.aggregate(aggregation, "UserLog", UserLoginStatusCount.class);


        Map<String, Integer> resultMap = new HashMap<>();
        // 补全没有登录的日期
        List<String> dates = new ArrayList<>();
        List<Integer> count = new ArrayList<>();


        for (UserLoginStatusCount result : results.getMappedResults()) {
            String key = DateUtil.format(DateUtil.parse(result.getDate(), queryFormat), formatVo);
            resultMap.put(key, result.getLoginCount());
        }




        System.out.println("");
    }



}
