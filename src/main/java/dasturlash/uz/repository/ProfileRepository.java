package dasturlash.uz.repository;

import dasturlash.uz.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, String> {

    Optional<ProfileEntity> findByPhoneAndVisibleTrue(String phone);
}
