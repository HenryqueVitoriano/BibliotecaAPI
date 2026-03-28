package com.biblioteca.service;

import com.biblioteca.dao.LivroDAO;
import com.biblioteca.dao.genericDAO;
import com.biblioteca.model.Autor;
import com.biblioteca.model.Editora;
import com.biblioteca.model.Livro;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class API_service {

    public API_service() {
    }

    public Livro convertJSON(String json, String isbn) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(json);

        String chave = jsonNode.fieldNames().next();
        JsonNode livroNode = jsonNode.get(chave);

        Livro livro = new Livro();

        livro.setTitulo(livroNode.get("title").asText());
        livro.setISBN(isbn);
        livro.setAutor(new Autor(livroNode.get("authors").get(0).get("name").asText()));
        livro.setEditora(new Editora(livroNode.get("publishers").get(0).get("name").asText()));

        return livro;
    }
}
