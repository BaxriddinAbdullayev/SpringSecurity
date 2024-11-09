package dasturlash.uz.controller;

import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.dto.TokenDTO;
import dasturlash.uz.dto.auth.AuthDTO;
import dasturlash.uz.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("restration")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.registration(dto));
    }

    @PostMapping("authorization")
    public ResponseEntity<ProfileDTO> authorization(@RequestBody AuthDTO dto) {
        ProfileDTO result = profileService.authorization(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenDTO dto){
        TokenDTO result = profileService.getNewAccessToken(dto);
        return ResponseEntity.ok(result);
    }
}
