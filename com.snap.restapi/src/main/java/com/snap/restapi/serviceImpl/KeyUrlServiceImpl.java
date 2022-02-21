package com.snap.restapi.serviceImpl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.snap.restapi.exception.BadRequestException;
import com.snap.restapi.exception.ForbiddenException;
import com.snap.restapi.exception.ResourceNotFoundException;
import com.snap.restapi.exception.UnauthorizedException;
import com.snap.restapi.model.KeyUrl;
import com.snap.restapi.model.UrlEntity;
import com.snap.restapi.repository.KeyUrlRepository;
import com.snap.restapi.service.KeyUrlService;
import com.snap.restapi.util.Utility;

@Service
public class KeyUrlServiceImpl implements KeyUrlService {

	private KeyUrlRepository keyUrlRepository;

	public KeyUrlServiceImpl(KeyUrlRepository keyUrlRepository) {
		super();
		this.keyUrlRepository = keyUrlRepository;
	}

	@Override
	public KeyUrl saveKeyUrl(KeyUrl keyUrl) {

		return keyUrlRepository.save(keyUrl);

	}

	@Override
	public List<KeyUrl> getAllKeyUrl(KeyUrl keyUrl) {
		return keyUrlRepository.findAll();
	}

	@Override
	public KeyUrl getKeyUrlById(long id) {
		return keyUrlRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("KeyUrl", "Id", id));
	}

	@Override
	public KeyUrl updateKeyUrlById(KeyUrl keyUrl, long id) {
		KeyUrl existKeyUrl = keyUrlRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("KeyUrl", "Id", id));
		existKeyUrl.setAppKey(keyUrl.getAppKey());
		existKeyUrl.setUrl(keyUrl.getUrl());
		existKeyUrl.setDestinationUrl(keyUrl.getDestinationUrl());
		keyUrlRepository.save(existKeyUrl);
		return existKeyUrl;
	}

	@Override
	public KeyUrl getKeyUrlByAppKeyAndUrl(String appKey, String url) {
		if (appKey.isEmpty()) {
			throw new ForbiddenException();
		} else {
			KeyUrl existKeyUrl = keyUrlRepository.findByAppKeyAndUrl(appKey, url);
			return existKeyUrl;
		}

	}

	@Override
	public KeyUrl getKeyUrlByAppKey(String appKey) {
		return keyUrlRepository.findByAppKey(appKey);
	}

	@Override
	public String getMapKeyByAppKeyAndUrl(String appKey, String url) {
		if (appKey.isEmpty()) {
			throw new ForbiddenException();

		} else {
			// verify url is valid

			if (!Utility.isValidUrl(url)) {
				throw new BadRequestException();

			} else {

				String existAppKey = keyUrlRepository.findAppkeyByAppKeyAndUrl(appKey, url);
				if (existAppKey == null) {
					throw new UnauthorizedException();

				} else {
					KeyUrl existKeyUrl = keyUrlRepository.findByAppKeyAndUrl(appKey, url);
					String sId = existKeyUrl.getId().toString();
					String letterStr = Utility.numberStrToLetterStr(sId);
					existKeyUrl.setPathId(letterStr);
					keyUrlRepository.save(existKeyUrl);
					return letterStr;
				}
			}
		}
	}

	@Override
	public long getKeyurlIdByAppKeyAndUrl(String appKey, String url) {
		return keyUrlRepository.findIdByAppKeyAndUrl(appKey, url);
	}

	@Override
	public String getAppKeyByAppKeyAndUrl(String appKey, String url) {
		return keyUrlRepository.findAppkeyByAppKeyAndUrl(appKey, url);
	}

	@Override
	public UrlEntity getUrlByAppKeyAndUrl(String appKey, String url) {

		UrlEntity theUrl = new UrlEntity();
		theUrl.setUrl("http://s-pay.ca/" + getMapKeyByAppKeyAndUrl(appKey, url));
		return theUrl;
	}

	@Override
	public String getDestinationUrlByPathId(String pathId) {
		String exisPathId = keyUrlRepository.findByPath(pathId);
		if (exisPathId == null) {
			throw new ResourceNotFoundException("KeyUrl", "pathId", pathId);

		} else {
			// get destination url
			return keyUrlRepository.findDestinationByPath(exisPathId);

		}

	}

	@Override
	public String getDestinationUrlbyPathId(String pathId) {
		return keyUrlRepository.findDestinationByPath(pathId);
	}

}
