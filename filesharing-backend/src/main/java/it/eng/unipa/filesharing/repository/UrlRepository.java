package it.eng.unipa.filesharing.repository;

import it.eng.unipa.filesharing.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UrlRepository extends JpaRepository<Url,Long> {

    @Query("select u from Url u where u.uuid=:uuid and u.token=:token")
     Optional<Url> myUrl(@Param("uuid") UUID uuid, @Param("token") String token);


}
