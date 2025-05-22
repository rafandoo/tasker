package br.edu.ifc.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Classe utilitária para mapeamento de objetos Java em {@link JsonObject}.
 */
@ApplicationScoped
public class Mapper {

    @Inject
    ObjectMapper objectMapper;

    /**
     * Serializa um objeto como {@link JsonObject}, incluindo todos os campos.
     *
     * @param object objeto a ser convertido
     * @return objeto JSON resultante
     * @throws JsonProcessingException em caso de falha na serialização
     */
    public JsonObject toJson(Object object) throws JsonProcessingException {
        return new JsonObject(objectMapper.writeValueAsString(object));
    }

    /**
     * Serializa um objeto como {@link JsonObject}, aplicando uma view.
     *
     * @param object objeto a ser convertido
     * @param view   view a ser aplicada (ex: {@code Views.Public.class})
     * @return objeto JSON resultante
     * @throws JsonProcessingException em caso de falha na serialização
     */
    public JsonObject toJson(Object object, Class<? extends Views.Public> view) throws JsonProcessingException {
        return new JsonObject(objectMapper.writerWithView(view).writeValueAsString(object));
    }
}
