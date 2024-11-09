package dasturlash.uz.service;

import dasturlash.uz.config.CustomUserDetails;
import dasturlash.uz.dto.JwtDTO;
import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.dto.TokenDTO;
import dasturlash.uz.dto.auth.AuthDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.GeneralStatus;
import dasturlash.uz.exp.AppBadRequestException;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

    public ProfileDTO registration(ProfileDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if (optional.isPresent()) {
            return null;
        }

        ProfileEntity entity = new ProfileEntity();

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setRole(dto.getRole());

        profileRepository.save(entity);

        dto.setId(entity.getId());
        return dto;
    }

    public ProfileDTO authorization(AuthDTO authDTO) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getPhone(), authDTO.getPassword()));

            if (authenticate.isAuthenticated()) {
                CustomUserDetails profile = (CustomUserDetails) authenticate.getPrincipal();

                ProfileDTO response = new ProfileDTO();
                response.setName(profile.getName());
                response.setSurname(profile.getSurname());
                response.setPhone(profile.getUsername());
                response.setRole(profile.getRole());
                response.setAccessToken(JwtUtil.encode(profile.getUsername(), profile.getRole().name()));
                response.setRefreshToken(JwtUtil.generateRefreshToken(profile.getUsername(), profile.getRole().name()));
                return response;
            }
            throw new AppBadRequestException("Phone or password wrong");
        } catch (BadCredentialsException e) {
            throw new AppBadRequestException("Phone or password wrong");
        }
    }

//    public ProfileDTO authorization1(AuthDTO authDTO) {
//        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(authDTO.getPhone());
//        if (optional.isEmpty()) {
//            throw new AppBadRequestException("Phone or password wrong");
//        }
//
//        ProfileEntity profile = optional.get();
//        if (!bCryptPasswordEncoder.matches(authDTO.getPassword(), profile.getPassword())) {
//            throw new AppBadRequestException("Phone or password wrong");
//        }
//
//        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
//            throw new AppBadRequestException("Phone or password wrong");
//        }
//
//        ProfileDTO response = new ProfileDTO();
//        response.setName(profile.getName());
//        response.setSurname(profile.getSurname());
//        response.setPhone(profile.getPhone());
//        response.setRole(profile.getRole());
//        response.setJwt(JwtUtil.encode(profile.getPhone(), profile.getRole().name()));
//
//        return response;
//    }

    public TokenDTO getNewAccessToken(TokenDTO dto){
        try{
            if(JwtUtil.isValid(dto.getRefreshToken())){
                JwtDTO jwtDTO = JwtUtil.decode(dto.getRefreshToken());

                Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(jwtDTO.getUsername());

                if(optional.isPresent()){
                    ProfileEntity profile = optional.get();

                    if(profile.getStatus().equals(GeneralStatus.NOT_ACTIVE)){
                        throw new AppBadRequestException("Invalid token");
                    }

                    TokenDTO response =new TokenDTO();
                    response.setAccessToken(JwtUtil.encode(profile.getPhone(),profile.getRole().name()));
                    response.setRefreshToken(JwtUtil.generateRefreshToken(profile.getPhone(),profile.getRole().name()));
                    return response;
                }
            }
        }catch (JwtException e){

        }
        throw new AppBadRequestException("Invalid token");
    }

}
