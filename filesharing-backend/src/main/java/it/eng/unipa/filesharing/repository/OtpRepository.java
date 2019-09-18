package it.eng.unipa.filesharing.repository;

import it.eng.unipa.filesharing.model.Otp;
import it.eng.unipa.filesharing.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*import org.springframework.data.mongodb.repository.MongoRepository;*/

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long>/*extends MongoRepository<Team, UUID>*/{


	@Query("select o from Otp o where o.email=:email and o.token=:token")
	Optional<Otp> myOtp(@Param("email") String email, @Param("token")String token);

	@Query("select o from Otp o where o.email=:email and o.token=:token and o.otp=:otp")
	Optional<Otp> myOtp(@Param("email") String email, @Param("token")String token,@Param("otp")Integer otp);
}
