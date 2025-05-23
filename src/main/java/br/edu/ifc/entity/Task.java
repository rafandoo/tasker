package br.edu.ifc.entity;

import br.edu.ifc.enums.Status;
import br.edu.ifc.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "tb_task")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidade para representação de uma tarefa")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da tarefa", examples = "1")
    private long id;

    @NotBlank(message = "Descrição da tarefa não pode ser nula ou em branco!")
    @Schema(description = "Descrição da tarefa", examples = "Estudar Java")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Status da tarefa", examples = "DOING")
    private Status status = Status.TODO;

    @ManyToOne
    @Schema(description = "ID da categoria relacionada à tarefa", examples = "{\"id\": 1, \"name\": \"Estudos\"}")
    private Category category;

    @Min(message = "Prioridade deve ser maior ou igual a 1", value = 1)
    @Max(message = "Prioridade deve ser menor ou igual a 5", value = 5)
    @Column(nullable = false)
    @ColumnDefault("3")
    @Schema(description = "Prioridade da tarefa", examples = "3")
    private int priority;

    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Data de vencimento da tarefa", examples = "2025-01-01T12:00:00.000+00:00")
    private Date dueDate;

    @ManyToOne
    @Schema(hidden = true)
    @JsonView(Views.Private.class)
    private User user;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    @Schema(description = "Data de criação da tarefa", examples = "2025-01-01T12:00:00.000+00:00")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Data de atualização da tarefa", examples = "2025-01-01T12:00:00.000+00:00")
    private Date updatedAt;
}
