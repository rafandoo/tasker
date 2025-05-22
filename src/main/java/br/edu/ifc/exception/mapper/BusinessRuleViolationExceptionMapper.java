package br.edu.ifc.exception.mapper;

import br.edu.ifc.exception.BusinessRuleViolationException;
import br.edu.ifc.exception.Error;
import io.vertx.core.http.HttpServerRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Classe para mapeamento de excessões de violação de regras de negócio.
 */
@Provider
public class BusinessRuleViolationExceptionMapper implements ExceptionMapper<BusinessRuleViolationException> {

    @Context
    HttpServerRequest request;

    @Override
    public Response toResponse(BusinessRuleViolationException exception) {
        Response.Status status = Response.Status.BAD_REQUEST;

        Error error = Error.builder()
            .status(status.getStatusCode())
            .message("Violação de regra de negócio")
            .error(exception.getMessage())
            .exception(exception.getClass().getName())
            .path(request.path())
            .method(request.method().name())
            .build();

        return Response.status(status)
            .entity(error)
            .header("content-type", "application/json")
            .build();
    }
}
