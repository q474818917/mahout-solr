package com.dwarf.mahout.example;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

/**
 * 基于内容的协同推荐
 * @author jiyu
 * 
 * 用户X：喜好A、
 * 用户Y：喜好A、D
 * 根据A、D比较类似，将D推荐给用户X
 */
public class ItemBasedExample {
	
	public static void main(String[] args) {
		try {
			DataModel model = new FileDataModel(new File("src/main/resources/dataset.csv"));
			ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
			ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
			//给用户Param1推荐Param2个数的信息
			List<RecommendedItem> recommendations = recommender.recommend(2, 3);
			for (RecommendedItem recommendation : recommendations) {
			  System.out.println(recommendation);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TasteException e) {
			e.printStackTrace();
		}
	}
	
}
