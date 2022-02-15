package com.southwind.mmall002.service.impl;

import com.alibaba.fastjson.JSON;
import com.southwind.mmall002.entity.Product;
import com.southwind.mmall002.mapper.ProductMapper;
import com.southwind.mmall002.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 建强
 * @since 2021-01-21
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ProductMapper productMapper;
    public List<Product> findByTypeAndId(String type, Integer id){
        Map<String,Object> map = new HashMap<>();
        map.put("categorylevel"+type+"_id",id);
        return productMapper.selectByMap(map);

    }

    @Override
    public List<Product> searchFromEs(String keyword) {
        List<Product> result = new ArrayList<>();

        SearchRequest request = new SearchRequest("lucasy");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("name",keyword))
                .highlighter(new HighlightBuilder().field("*").requireFieldMatch(false).preTags("<span style='color:red;font-weight:500'>").postTags("</span>"));
        request.source(searchSourceBuilder);

        SearchResponse response = null;

        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }

        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> map = hit.getSourceAsMap();
            Product product = new Product();
            product.setId((Integer) map.get("id"));
            product.setFileName(map.get("fileName").toString());
            product.setName(map.get("name").toString());
            result.add(product);
        }
        return result;
    }

    public void initEs() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("lucasy");
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        List<Product> list = productMapper.selectList(null);
        if (!exists){
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("lucasy");
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            System.out.println(createIndexResponse.isAcknowledged());
        }
        BulkRequest request = new BulkRequest();
        for (int i = 0; i < list.size(); i++) {
            request.add(
                    new IndexRequest("lucasy")
                            .id(""+(i+1))
                            .source(JSON.toJSONString(list.get(i)), XContentType.JSON)
            );
        }
        BulkResponse response = restHighLevelClient.bulk(request,RequestOptions.DEFAULT);
        System.out.println(response.status());
        restHighLevelClient.close();
    }


}
