package com.study.studyes.simplequery;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.studycode.studyes.StudyESApplication;
import com.studycode.studyes.entity.Accountopeninfo;
import com.studycode.studyes.entity.BankTransactionFlow;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: lx
 * @Date: 2022/08/30
 * @Description:
 */
@SpringBootTest(classes = StudyESApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TestSimpleQuery {


    @Resource
    private RestHighLevelClient restHighLevelClient;


    /**
     * 无条件查询全部  相当于 select * from tldw_accountopeninfo;
     * 注意：如果不加size，默认时10条数据
     * GET /tldw_banktransactionflow/_search
     * {
     * "query": {
     * "match_all": {
     * <p>
     * }
     * }
     * }
     *
     * @throws IOException
     */
    @Test
    public void matchAllQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        searchRequest.source(searchSourceBuilder);
        searchRequest.indices("tldw_accountopeninfo");

        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            Accountopeninfo accountopeninfo = JSON.parseObject(hit.getSourceAsString(), Accountopeninfo.class);
            log.info(hit.getSourceAsString());
        }
    }


    /**
     * 条件查询，单个条件， 相当于  select *  where query_card = 6222621310025084423
     * <p>
     * GET /tldw_banktransactionflow/_search
     * {
     * "query": {
     * "term": {
     * "query_card": {
     * "value": "6222621310025084423"
     * }
     * }
     * }
     * }
     *
     * @throws IOException
     */
    @Test
    public void oneAndConditionQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.termQuery("query_card", "6222621310025084423"));

        searchRequest.source(searchSourceBuilder);
        searchRequest.indices("tldw_banktransactionflow");

        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            BankTransactionFlow bankTransactionFlow = JSON.parseObject(hit.getSourceAsString(), BankTransactionFlow.class);
            log.info(hit.getSourceAsString());
        }
    }

    /**
     * 多条件 and 查询
     * select * tldw_banktransactionflow where query_card = 6222621310025084423 and transaction_opposite_name = 朱武彪
     * <p>
     * GET /tldw_banktransactionflow/_search
     * {
     * "query": {
     * "bool": {
     * "must": [
     * {
     * "term": {
     * "query_card": {
     * "value": "6222621310025084423"
     * }
     * }
     * },
     * {
     * "term": {
     * "transaction_opposite_name": {
     * "value": "朱武彪"
     * }
     * }
     * }
     * ]
     * }
     * }
     * }
     */
    @Test
    public void andConditionsQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("query_card", "6222621310025084423"));
        boolQueryBuilder.must(QueryBuilders.termQuery("transaction_opposite_name", "朱武彪"));

        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        searchRequest.indices("tldw_banktransactionflow");

        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            BankTransactionFlow bankTransactionFlow = JSON.parseObject(hit.getSourceAsString(), BankTransactionFlow.class);
            log.info(hit.getSourceAsString());
        }
    }


    /***
     * 多条件 or 查询
     * select * from tldw_banktransactionflow where query_card = "6222621310025084423" or cash_flag = "其它"
     *
     *
     *
     * GET /tldw_banktransactionflow/_count
     * {
     *   "query": {
     *     "bool": {
     *       "should": [
     *          {
     *           "term": {
     *             "query_card": {
     *               "value": "6222621310025084423"
     *             }
     *           }
     *         },
     *         {
     *           "term": {
     *             "cash_flag": {
     *               "value": "其它"
     *             }
     *           }
     *         }
     *       ]
     *     }
     *   }
     * }
     *
     */
    @Test
    public void orConditionsQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        List<QueryBuilder> should = boolQueryBuilder.should();
        should.add(QueryBuilders.termQuery("query_card", "6222621310025084423"));
        should.add(QueryBuilders.termQuery("cash_flag", "其它"));

        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        searchRequest.indices("tldw_banktransactionflow");

        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            BankTransactionFlow bankTransactionFlow = JSON.parseObject(hit.getSourceAsString(), BankTransactionFlow.class);
            log.info(hit.getSourceAsString());
        }
    }


    /**
     * and or 联合查询
     * <p>
     * select * from tldw_banktransactionflow
     * where case_id = ”62c63c2056d42f6e35b10a16“ and ( loan_flag = "xxx" or cash_flag = "xxx")
     * <p>
     * 关于booleanQuery的使用注意：must语句都需要匹配，而所有的must_not语句都不能匹配，
     * 但是should语句需要匹配多少个呢？默认情况下，should语句一个都不要求匹配，只有一个特例：如果查询中没有must语句，那么至少要匹配一个should语句。
     */
    @Test
    public void andOrConditionsQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("case_id", "62c63c2056d42f6e35b10a16"));

        List<QueryBuilder> should = boolQueryBuilder.should();
        should.add(QueryBuilders.termQuery("loan_flag", "随便输入"));
        should.add(QueryBuilders.termQuery("cash_flag", "随便输入"));

        //关于booleanQuery的使用注意：must语句都需要匹配，而所有的must_not语句都不能匹配，
        // 但是should语句需要匹配多少个呢？默认情况下，should语句一个都不要求匹配，只有一个特例：如果查询中没有must语句，那么至少要匹配一个should语句。
        // 所以想要should至少一个满足 需要添加 minimumShouldMatch
        boolQueryBuilder.minimumShouldMatch(1);

        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        searchRequest.indices("tldw_banktransactionflow");

        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            BankTransactionFlow bankTransactionFlow = JSON.parseObject(hit.getSourceAsString(), BankTransactionFlow.class);
            log.info(hit.getSourceAsString());
        }
    }


    /**
     * 聚合，分组
     * <p>
     * select * from tldw_banktransactionflow group by resource_id
     */
    @Test
    public void aggregation() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        TermsAggregationBuilder test_group_resId = AggregationBuilders.terms("test_group_resId").field("resource_id");


        searchSourceBuilder.aggregation(test_group_resId);
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        searchRequest.indices("tldw_banktransactionflow");

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Terms responseAggregation = response.getAggregations().get("test_group_resId");
        Iterator iterator = responseAggregation.getBuckets().iterator();
        while (iterator.hasNext()) {
            Terms.Bucket bucket = (Terms.Bucket) iterator.next();
            String keyAsString = bucket.getKeyAsString();
            log.info(keyAsString);
        }
    }


    /**
     * 大于等于
     * <p>
     * {
     * "query": {
     * "bool": {
     * "filter": [
     * {
     * "range": {
     * "transaction_money": {
     * "gte": 10000000
     * }
     * }
     * }
     * ]
     * }
     * }
     * }
     *
     * @throws IOException
     */
    @Test
    public void range() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        List<QueryBuilder> should = boolQueryBuilder.should();
        should.add(QueryBuilders.termQuery("query_card", "6222621310025084423"));
        should.add(QueryBuilders.termQuery("cash_flag", "其它"));

        // 范围查询  大于等于
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("transaction_money").gte("100000"));

        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        searchRequest.indices("tldw_banktransactionflow");

        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            BankTransactionFlow bankTransactionFlow = JSON.parseObject(hit.getSourceAsString(), BankTransactionFlow.class);
            log.info(hit.getSourceAsString());
        }
    }


    @Test
    public void test() {
        System.out.println("===============between==============");
        String dateStr1 = "22:33:23";
        String dataStr2 = "20:00";
        String dataStr3 = "21:34";
        String datastr4 = "21:03";

        DateTime parse = DateUtil.parse(datastr4);
        Date current = new Date();

        System.out.println(DateUtil.hour(parse, true));
        System.out.println(DateUtil.minute(parse));


        String a = "00";
        System.out.println(Integer.parseInt(a));
    }


}
