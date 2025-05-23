package br.edu.ifc.entity;

import br.edu.ifc.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "tb_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa uma categoria das tarefas")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da categoria", examples = "1")
    @JsonView(Views.Public.class)
    private long id;

    @NotBlank(message = "Nome da categoria não pode ser nulo ou em branco!")
    @Column(unique = true)
    @Schema(description = "Nome da categoria", examples = "Estudos")
    @JsonView(Views.Detailed.class)
    private String name;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    @Schema(description = "Data de criação da categoria", examples = "2025-01-01T12:00:00.000+00:00")
    @JsonView(Views.Detailed.class)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Data de atualização da categoria", examples = "2025-01-01T12:00:00.000+00:00")
    @JsonView(Views.Detailed.class)
    private Date updatedAt;
}
