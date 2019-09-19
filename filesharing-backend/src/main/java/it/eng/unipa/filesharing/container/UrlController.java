package it.eng.unipa.filesharing.container;

import it.eng.unipa.filesharing.dto.ResourceDTO;
import it.eng.unipa.filesharing.dto.UrlDTO;
import it.eng.unipa.filesharing.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/url")
public class UrlController {
    @Autowired
    UrlService urlService;
    public UrlController(@Autowired UrlService urlService){this.urlService = urlService;}

    @PostMapping("")
    public String generateUrl(@RequestBody UrlDTO urlDTO) {urlService.generateURL(urlDTO);
    return this.urlService.generateUrl(urlDTO);}



    @PostMapping("/download")
    public ResponseEntity<Resource> download(@RequestBody UrlDTO urlDTO) {
        ResourceDTO resourceDTO = urlService.getContent(urlDTO);
        return resourceDTO!=null ? getResponseEntityResource(resourceDTO.getName(), resourceDTO.getContent()) : new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Resource> getResponseEntityResource(String name, byte[] body) {
        return ResponseEntity.ok()
                //.contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"")
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition")
                .body(new InputStreamResource(new ByteArrayInputStream(body)));
    }

}


