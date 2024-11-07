package dasturlash.uz.controller;

import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.dto.TaskDTO;
import dasturlash.uz.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("restration")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.registration(dto));
    }
}
