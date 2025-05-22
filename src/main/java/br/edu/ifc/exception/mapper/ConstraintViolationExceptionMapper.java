package br.edu.ifc.exception.mapper;

import br.edu.ifc.exception.Error;
import io.quarkus.hibernate.validator.runtime.jaxrs.ViolationReport;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Entidade para mapear as exceções de validação do Hibernate Validator.
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Response.Status status = Response.Status.BAD_REQUEST;

        List<ViolationReport.Violation> violations = exception.getConstraintViolations()
            .stream()
            .map(cv -> new ViolationReport.Violation(cv.getPropertyPath().toString(), cv.getMessage()))
            .collect(Collectors.toList());

        Error error = Error.builder()
            .status(status.getStatusCode())
            .message("Erro de validação")
            .violations(violations)
            .build();

        return Response.status(status)
            .entity(error)
            .header("content-type", "application/json")
            .build();
    }
}
