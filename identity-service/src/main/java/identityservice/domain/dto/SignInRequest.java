package identityservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Authentication Request")
public class SignInRequest {

    @Schema(description = "Username", example = "Jon")
    @Size(min = 4, max = 50, message = "Username must contain between 5 and 50 characters")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(min = 4, max = 255, message = "Password length must be between 8 and 255 characters")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
