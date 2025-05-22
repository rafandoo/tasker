package br.edu.ifc.exception.mapper;

import br.edu.ifc.exception.Error;
import io.smallrye.mutiny.CompositeException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Classe para mapeamento de excessões genéricas.
 */
@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        Throwable cause = this.unwrap(exception);

        int status = (exception instanceof WebApplicationException)
            ? ((WebApplicationException) exception).getResponse().getStatus()
            : Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();

        Error error = Error.builder()
            .status(status)
            .message("Falha ao processar a requisição.")
            .exception(cause.getClass().getName())
            .error(cause.getMessage())
            .build();

        return Response.status(status)
            .entity(error)
            .header("content-type", "application/json")
            .build();
    }

    private Throwable unwrap(Throwable throwable) {
        if (throwable instanceof CompositeException composite) {
            return composite.getCause();
        }
        return throwable;
    }
}
