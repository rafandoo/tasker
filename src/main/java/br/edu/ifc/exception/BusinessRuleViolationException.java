package br.edu.ifc.exception;

import java.io.Serial;

/**
 * Exceção que indica uma violação de regra de negócio.
 * Pode ser usada para sinalizar condições que não representam erros técnicos,
 * mas sim falhas nas regras de domínio da aplicação.
 */
public class BusinessRuleViolationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessRuleViolationException(String message) {
        super(message);
    }

    public BusinessRuleViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessRuleViolationException(Throwable cause) {
        super(cause);
    }

    public BusinessRuleViolationException() {
        super("Regra de negócio violada.");
    }
}
