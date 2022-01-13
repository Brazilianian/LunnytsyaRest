package lunnytsya.com.dto;

public class AuthenticationResponseDto {

    private String jwtToken;

    public AuthenticationResponseDto() {
    }

    public AuthenticationResponseDto(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
