package br.edu.ifc.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.hibernate.validator.runtime.jaxrs.ViolationReport;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * Entidade para representação de um erro.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    @Builder.Default
    private Instant timestamp = Instant.now();
    private int status;
    private String method;
    private String path;
    private String message;
    private String error;
    private String exception;
    private List<Object> details;
    private List<ViolationReport.Violation> violations;
}
