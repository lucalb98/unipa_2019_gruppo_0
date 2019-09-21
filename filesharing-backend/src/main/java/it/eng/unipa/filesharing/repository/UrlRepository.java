package it.eng.unipa.filesharing.repository;

import it.eng.unipa.filesharing.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UrlRepository extends JpaRepository<Url,UUID> {


    @Query("select u from Url u where u.token=:token")
    Optional<Url> myUrl( @Param("token") String token);


}
