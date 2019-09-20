package it.eng.unipa.filesharing.service;

import it.eng.unipa.filesharing.dto.ResourceDTO;
import it.eng.unipa.filesharing.dto.UrlDTO;

public interface UrlService {
    ResourceDTO getContent(UrlDTO urlDTO);

    UrlDTO generateURL(UrlDTO urlDTO);
}
