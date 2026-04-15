package main.com.biblioteca.service;

import main.com.biblioteca.model.Autor;
import main.com.biblioteca.model.Editora;
import main.com.biblioteca.model.Livro;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiService {

    public ApiService() {
    }

    public Livro convertJSON(String json, String isbn) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(json);

        String chave = jsonNode.fieldNames().next();
        JsonNode livroNode = jsonNode.get(chave);

        Livro livro = new Livro();

        livro.setTitulo(livroNode.get("title").asText());
        livro.setISBN(isbn);
        livro.setNota(10.0);
        livro.setAutor(new Autor(livroNode.get("authors").get(0).get("name").asText()));
        livro.setEditora(new Editora(livroNode.get("publishers").get(0).get("name").asText()));

        return livro;
    }
}
