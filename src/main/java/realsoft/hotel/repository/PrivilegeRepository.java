package realsoft.hotel.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import realsoft.hotel.entity.Privilege;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege,Long> {

}
