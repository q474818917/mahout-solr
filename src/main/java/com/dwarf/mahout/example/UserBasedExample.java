package com.dwarf.mahout.example;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
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

/**
 * taste下是协同过滤推荐：
 * 	+ 用户协同
 *  + 内容协同
 * 
 * 主要的类：
 * + DataModel:抽象接口，用来读取用户喜好
 * 	+ FileDataModel
 *  + JDBCDataModel
 * + UserSimilarity、ItemSimilarity：相识度
 * + UserNeighborhood：基于UserSimilarity计算邻居User
 * + recommender：推荐的核心接口
 * 
 * SlopeOneRecommender:由于缺乏实际使用，在0.9.0被移除掉了
 * @author jiyu
 * 
 * 用户X：喜好A、B
 * 用户Y：喜好A、B、C
 * 根据喜好A、B的人同时也喜好C,将C推荐给用户X
 */
public class UserBasedExample {
	
	private static Logger logger = LoggerFactory.getLogger(UserBasedExample.class);

	public static void main(String[] args) {
		try {
			long t1 = System.currentTimeMillis();
			DataModel model = new FileDataModel(new File("src/main/resources/ratings.csv"));
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TasteException e) {
			e.printStackTrace();
		}
	}

}
