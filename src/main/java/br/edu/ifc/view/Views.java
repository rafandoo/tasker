package br.edu.ifc.view;

/**
 * Definições de views para controle de serialização com Jackson.
 * <p>
 * Estas views podem ser usadas com {@link com.fasterxml.jackson.annotation.JsonView}
 * para filtrar dinamicamente os campos serializados em respostas JSON.
 */
public class Views {

    /**
     * Construtor privado para evitar instanciamento.
     */
    private Views() {
    }

    /**
     * View padrão para dados públicos.
     */
    public interface Public {
    }

    /**
     * Definição da view de detalhamento, onde dados mais profundos serão exibidos.
     */
    public interface Detailed extends Public {
    }

    /**
     * Definição da view privada, onde dados sensíveis não serão exibidos.
     */
    public interface Private extends Public {
    }
}

