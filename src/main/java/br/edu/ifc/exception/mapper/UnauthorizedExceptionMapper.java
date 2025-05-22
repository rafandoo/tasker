package br.edu.ifc.exception.mapper;

import br.edu.ifc.exception.Error;
import br.edu.ifc.exception.UnauthorizedException;
import io.vertx.core.http.HttpServerRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Classe para mapeamento de excessões de autorização.
 */
@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {

    @Context
    HttpServerRequest request;

    @Override
    public Response toResponse(UnauthorizedException exception) {
        Response.Status status = Response.Status.UNAUTHORIZED;

        Error error = Error.builder()
            .status(status.getStatusCode())
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
