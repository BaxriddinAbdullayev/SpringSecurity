package dasturlash.uz.service;

import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ProfileDTO registration(ProfileDTO dto){

        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if(optional.isPresent()){
            return null;
        }

        ProfileEntity entity=new ProfileEntity();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setRole(dto.getRole());

        profileRepository.save(entity);

        dto.setId(entity.getId());
        return dto;
    }
}
