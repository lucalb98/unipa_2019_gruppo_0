package it.eng.unipa.filesharing.repository;

import it.eng.unipa.filesharing.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/*import org.springframework.data.mongodb.repository.MongoRepository;*/
@Repository
public interface TeamRepository extends JpaRepository<Team, UUID>/*extends MongoRepository<Team, UUID>*/{

	
	@Query("select t from Team t join t.members m where m.oid.email=:email")
	List<Team> myTeams(@Param("email")String email);
			

}
