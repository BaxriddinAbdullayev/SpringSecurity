package dasturlash.uz.dto;

public class JwtDTO {

    private String username;
    private String role;

    public JwtDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
