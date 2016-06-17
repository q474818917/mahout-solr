package com.dwarf.mahout.example;

import java.util.List;

import javax.sql.DataSource;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

public class MySQLTemplate {
	
	private static Logger logger = LoggerFactory.getLogger(MySQLTemplate.class);
	
	public static DataSource getDataSource(){
		DruidDataSource dataSource = new DruidDataSource();       
        dataSource.setUsername("dmdevelop");       
        dataSource.setPassword("develop@dm.com");       
        dataSource.setUrl("jdbc:mysql://192.168.100.182:3306/user_20160309?useUnicode=true&characterEncoding=utf-8");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver"); 
        dataSource.setInitialSize(10); 
        dataSource.setMinIdle(10); 
        dataSource.setMaxActive(20); 
        dataSource.setPoolPreparedStatements(false);
        
        return dataSource;
	}
	
	public static void main(String[] args) {
		try {
			long t1 = System.currentTimeMillis();
			DataModel model = new MySQLJDBCDataModel(getDataSource(), "taste_preferences", "user_id",
					"item_id", "preference", null);
			long t2 = System.currentTimeMillis();
			logger.info("加载数据使用时间, {}", (t2 - t1));
			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
			UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
			//给用户Param1推荐Param2个数的信息
			List<RecommendedItem> recommendations = recommender.recommend(2, 10);
			for (RecommendedItem recommendation : recommendations) {
			  System.out.println(recommendation);
			}
			long t3 = System.currentTimeMillis();
			logger.info("剔除加载数据，计算需要时间, {}", (t3 - t2));
		} catch (TasteException e) {
			e.printStackTrace();
		}
	}

}
