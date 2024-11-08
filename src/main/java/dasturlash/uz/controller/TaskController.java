package dasturlash.uz.controller;

import dasturlash.uz.config.CustomUserDetails;
import dasturlash.uz.dto.TaskDTO;
import dasturlash.uz.service.TaskService;
import dasturlash.uz.util.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("")
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO dto,
                                          Principal principal,
                                          HttpServletRequest request,
                                          Authentication authentication,
                                          @AuthenticationPrincipal final CustomUserDetails customUserDetails) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getStatus());


        Principal userPrincipal = request.getUserPrincipal();
        System.out.println(userPrincipal.getName());


        System.out.println(customUserDetails.getUsername());
        System.out.println(customUserDetails.getStatus());

        System.out.println(principal.getName());
        CustomUserDetails user = (CustomUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        TaskDTO result = taskService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping({"","/"})
    public ResponseEntity<List<TaskDTO>> getAll() {
        List<TaskDTO> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/active/all")
    public ResponseEntity<List<TaskDTO>> active() {
        List<TaskDTO> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/finished/all")
    public ResponseEntity<List<TaskDTO>> getFenished() {
        List<TaskDTO> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/my/all")
    public ResponseEntity<List<TaskDTO>> getMyTaskList() {
        List<TaskDTO> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable String id) {
        TaskDTO result = taskService.getById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@RequestBody TaskDTO student,
                                          @PathVariable("id") String id) {
        Boolean result = taskService.update(student, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}
