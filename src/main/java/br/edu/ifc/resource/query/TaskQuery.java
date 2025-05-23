package br.edu.ifc.resource.query;

import br.edu.ifc.enums.Status;
import jakarta.ws.rs.QueryParam;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class TaskQuery {

    @Parameter(description = "Filtrar pelo nome da tarefa")
    @QueryParam("description")
    private String description;

    @Parameter(description = "Filtrar pelo status da tarefa")
    @QueryParam("status")
    private Status status;

    @Parameter(description = "Filtrar pelo ID da categoria")
    @QueryParam("categoryId")
    private long categoryId;

    @Parameter(description = "Filtrar pela prioridade da tarefa")
    @QueryParam("priority")
    private int priority;

    @Parameter(description = "Filtrar pela data de vencimento da tarefa")
    @QueryParam("dueDate")
    private Date dueDate;
}
