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

@Service
@Transactional
public class UrlService {
    private static final long TEMPO_VALIDITA= 3600;

    @Value("${sharedUrl}")
    String sharedUrl;

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    ResourceRepository resourceRepository;


    String url;
    private ConversionService conversionService;


    private TeamService teamService;

    public  ResourceDTO getContent(UrlDTO urlDTO) {
        Optional<Url> urlOpt = urlRepository.myUrl(urlDTO.getUuid(), urlDTO.getToken());
        if(urlOpt.isPresent()){
            Url url = urlOpt.get();
            if(System.currentTimeMillis()<url.getDataScadenza().getTime()){
                BucketResource bucketResource = resourceRepository.loadBucket(url.getUuid(), url.getBucketName());
                if(bucketResource!=null){
                    Resource contentResource = resourceRepository.read(bucketResource, url.getUniqueId());
                    if(contentResource!=null){
                        return (ResourceDTO) conversionService.convert(contentResource, TypeDescriptor.valueOf(ContentResource.class), TypeDescriptor.valueOf(ResourceDTO.class));
                    }
                }
            }
        }
        return null;
    }

    public String generateURL(UrlDTO urlDTO) {
        ResourceDTO resourceDTO = teamService.getContent(urlDTO.getUuid(), urlDTO.getBucketName(), urlDTO.getUniqueId());

        if (resourceDTO != null) {
            urlDTO.setDataScadenza(new Timestamp(System.currentTimeMillis()+TEMPO_VALIDITA));
            String url = this.sharedUrl + "/" + urlDTO.getToken();

            return url;
        }
        return null;
    }
}
