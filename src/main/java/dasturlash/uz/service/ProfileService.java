package dasturlash.uz.service;

import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO registration(ProfileDTO dto){

        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if(optional.isPresent()){
            return null;
        }

        ProfileEntity entity=new ProfileEntity();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());

        profileRepository.save(entity);

        dto.setId(entity.getId());
        return dto;
    }
}
