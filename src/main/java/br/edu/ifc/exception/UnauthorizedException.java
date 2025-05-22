package br.edu.ifc.exception;

import java.io.Serial;

/**
 * Exceção que indica falha na autenticação ou autorização do usuário.
 * Pode ser utilizada para sinalizar acessos não permitidos ou tokens inválidos.
 */
public class UnauthorizedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UnauthorizedException() {
        super("Usuário não autorizado.");
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }
}
