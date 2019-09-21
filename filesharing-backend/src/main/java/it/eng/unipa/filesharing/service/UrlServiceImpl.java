package it.eng.unipa.filesharing.service;

import it.eng.unipa.filesharing.dto.ResourceDTO;
import it.eng.unipa.filesharing.dto.UrlDTO;
import it.eng.unipa.filesharing.model.Url;
import it.eng.unipa.filesharing.repository.UrlRepository;
import it.eng.unipa.filesharing.resource.BucketResource;
import it.eng.unipa.filesharing.resource.ContentResource;
import it.eng.unipa.filesharing.resource.Resource;
import it.eng.unipa.filesharing.resource.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UrlServiceImpl implements UrlService {
    private static final long TEMPO_VALIDITA = 3600 * 1000 ;
    @Value("${sharedUrl}")
    String sharedUrl;
    @Autowired
    TeamService teamService;

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    ConversionService conversionService;

  @Override
    public UrlDTO generateURL(UrlDTO urlDTO) {
        ResourceDTO resourceDTO = teamService.getContent(urlDTO.getUuid(), urlDTO.getBucketName(), urlDTO.getUniqueId());

        if (resourceDTO != null) {
            Url url = new Url();
            url.setBucketName(urlDTO.getBucketName());
            url.setUuid(urlDTO.getUuid());
            url.setUniqueId(urlDTO.getUniqueId());
            url.setToken(UUID.randomUUID().toString());
            url.setDataScadenza(new Timestamp(System.currentTimeMillis() + TEMPO_VALIDITA));
            String urlString = this.sharedUrl + "/" + url.getToken();
            url.setUrl(urlString);

            urlRepository.save(url);

            urlDTO.setToken(url.getToken());
            urlDTO.setUrl(url.getUrl());

            return urlDTO;
        }
        return null;
    }


    @Override
    public ResourceDTO getContent(UrlDTO urlDTO) {
        Optional<Url> urlOpt = urlRepository.myUrl(urlDTO.getToken());
        if (urlOpt.isPresent()) {
            Url url = urlOpt.get();
            if (System.currentTimeMillis() < url.getDataScadenza().getTime()) {
                BucketResource bucketResource = resourceRepository.loadBucket(url.getUuid(), url.getBucketName());
                if (bucketResource != null) {
                    Resource contentResource = resourceRepository.read(bucketResource, url.getUniqueId());
                    if (contentResource != null) {
                        return (ResourceDTO) conversionService.convert(contentResource, TypeDescriptor.valueOf(ContentResource.class), TypeDescriptor.valueOf(ResourceDTO.class));
                    }
                }
            }
        }
        return null;
    }
}
