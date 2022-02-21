package com.snap.restapi.service;

import java.util.List;

import com.snap.restapi.model.KeyUrl;
import com.snap.restapi.model.UrlEntity;

public interface KeyUrlService {

	KeyUrl saveKeyUrl(KeyUrl keyUrl);

	List<KeyUrl> getAllKeyUrl(KeyUrl keyUrl);

	KeyUrl getKeyUrlById(long id);

	KeyUrl updateKeyUrlById(KeyUrl keyUrl, long id);

	KeyUrl getKeyUrlByAppKeyAndUrl(String appKey, String url);

	KeyUrl getKeyUrlByAppKey(String appKey);
	
	long getKeyurlIdByAppKeyAndUrl(String appKey, String url);

	String getMapKeyByAppKeyAndUrl(String appKey, String url);
	
	String getAppKeyByAppKeyAndUrl(String appKey, String url);
	
	
	UrlEntity getUrlByAppKeyAndUrl(String appKey, String url);
	
	String getDestinationUrlbyPathId(String pathId);
	
	String getDestinationUrlByPathId(String pathId);
	
	
}
