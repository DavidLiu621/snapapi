package com.snap.restapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.URL;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.snap.restapi.exception.ResourceNotFoundException;
import com.snap.restapi.model.KeyUrl;
import com.snap.restapi.model.UrlEntity;
import com.snap.restapi.service.KeyUrlService;

@RestController
public class KeyUrlController {

	private KeyUrlService keyUrlService;

	public KeyUrlController(KeyUrlService keyUrlService) {
		super();
		this.keyUrlService = keyUrlService;
	}
	
	//welcome at the root
	@GetMapping("/")
	public String root() {
		return "Welcome!";
	}

	// save KeyUrl into DB
	@PostMapping("/keyurl")
	public ResponseEntity<KeyUrl> saveKeyUrl(@RequestBody() @Valid KeyUrl keyUrl) {
		return new ResponseEntity<KeyUrl>(keyUrlService.saveKeyUrl(keyUrl), HttpStatus.CREATED);

	}

	// get all keyUrl in DB
	@GetMapping("/getAll")
	public List<KeyUrl> getAllKeyUrl() {
		return keyUrlService.getAllKeyUrl(null);
	}

	// get keyUrl by Id
	@GetMapping("/getById/{id}")
	public ResponseEntity<KeyUrl> getKeyUrlById(@PathVariable("id") long id) {
		return new ResponseEntity<KeyUrl>(keyUrlService.getKeyUrlById(id), HttpStatus.OK);

	}

	// update keyUrl by Id
	@PutMapping("/updateKeyUrl/{id}")
	public ResponseEntity<KeyUrl> updateKeyUrlById(@PathVariable("id") long id, @RequestBody() KeyUrl keyUrl) {
		return new ResponseEntity<KeyUrl>(keyUrlService.updateKeyUrlById(keyUrl, id), HttpStatus.OK);
	}

	// get keyUrl by appkey
	@GetMapping("/getKeyUrlByAppkey/{appKey}")
	public KeyUrl getKeyUrlByAppkey(@PathVariable("appKey") String appKey) {
		return keyUrlService.getKeyUrlByAppKey(appKey);
	}

	// get keyUrl by appkey and url
	@GetMapping("/getByAppkeyAndUrl")
	public KeyUrl getByAppkeyAndUrl(@RequestHeader("x-app-key") String appkey, @Param("url") @Valid String url) {
		return keyUrlService.getKeyUrlByAppKeyAndUrl(appkey, url);
	}



	// getDestinationPathKey by appkey (Header) and url (parma)
	@PostMapping("/map")
	public ResponseEntity<UrlEntity> getPathKey(@RequestHeader("x-app-key") String appKey,
			@Param("url") @URL @Valid String url) {
		return new ResponseEntity<UrlEntity>(keyUrlService.getUrlByAppKeyAndUrl(appKey, url), HttpStatus.OK);
	}

	// get keyUrl.id by appkey and url
	@GetMapping("/getIdByAppkeyAndUrl")
	public long getIdByAppkeyAndUrl(@RequestHeader("x-app-key") String appKey, @Param("url") @Valid String url) {
		return keyUrlService.getKeyurlIdByAppKeyAndUrl(appKey, url);
	}

	// get keyUrl.appKey by appKey and url
	@GetMapping("/getAppkeyByAppkeyAndUrl")
	public String getAppkeyByAppkeyAndUrl(@RequestHeader("x-app-key") String appKey, @Param("url") @Valid String url) {
		return keyUrlService.getAppKeyByAppKeyAndUrl(appKey, url);
	}

//	get and redirection

	@GetMapping("/{pathId}")
	public ResponseEntity<Object> redirectToExternalUrl(@PathVariable("pathId") String pathId)
			throws URISyntaxException {
		String destinationUrl = keyUrlService.getDestinationUrlbyPathId(pathId);
		if (destinationUrl == null) {
			throw new ResourceNotFoundException("KeyUrl", "pathId", pathId);
		} else {
			URI uri = new URI(destinationUrl);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(uri);
			return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
		}

	}
}
