package br.edu.ifc.entity;

import br.edu.ifc.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Locale;

@Entity
@UserDefinition
@Table(name = "tb_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade para representação de um usuário")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do usuário", examples = "1")
    @JsonView(Views.Public.class)
    private long id;

    @Username
    @NotBlank(groups = Password.class, message = "Nome de usuário não pode ser nulo ou em branco!")
    @Column(unique = true)
    @Schema(description = "Nome de usuário", examples = "admin")
    @JsonView(Views.Public.class)
    private String username;

    @Password
    @NotBlank(groups = Password.class, message = "Senha não pode ser nula ou em branco!")
    @Schema(description = "Senha de acesso do usuário")
    @JsonView(Views.Private.class)
    private String password;

    @Roles
    @NotBlank(groups = Password.class, message = "Função não pode ser nula ou em branco!")
    @Schema(description = "Função do usuário", examples = "USER")
    @JsonView(Views.Public.class)
    private String roles;

    public void setPassword(String password) {
        this.password = BcryptUtil.bcryptHash(password);
    }

    public void setRoles(String roles) {
        this.roles = roles.toUpperCase(Locale.ROOT);
    }
}
