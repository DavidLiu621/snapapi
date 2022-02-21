package com.snap.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.snap.restapi.model.KeyUrl;
import com.snap.restapi.model.UrlEntity;

//@Repository
public interface KeyUrlRepository extends JpaRepository<KeyUrl, Long> {
	
	@Query("select k from KeyUrl k where k.appKey = ?1 And k.url =?2")
	String findMapKeyByAppKeyAndUrl(String appKey, String url);
	
	@Query("select k from KeyUrl k where k.appKey = ?1 And k.url =?2")
	KeyUrl findByAppKeyAndUrl(String appKey, String url);

	@Query("select k from KeyUrl k where k.appKey = ?1")
	KeyUrl findByAppKey(String appKey);
	
	@Query(value="select k.id from Key_Url k where k.app_Key = ?1 And k.url =?2", nativeQuery= true)
	long findIdByAppKeyAndUrl(String appKey, String url);
	
	@Query(value="select k.app_key from Key_Url k where k.app_Key = ?1 And k.url =?2", nativeQuery= true)
	String findAppkeyByAppKeyAndUrl(String appKey, String url);
	
	@Query(value="select k.path_id from Key_Url k where k.apath_id = ?1", nativeQuery= true)
	String  findByPath(String pathId);
	
	@Query(value="select k.destination_url from Key_Url k where k.path_id = ?1", nativeQuery= true)
	String  findDestinationByPath(String pathId);
	
	
}
