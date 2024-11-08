package dasturlash.uz.repository;

import dasturlash.uz.entity.TaskEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<TaskEntity, String> {

    List<TaskEntity> findAllByProfileId(String profileId);

}
