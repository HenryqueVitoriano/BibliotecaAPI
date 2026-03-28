package com.biblioteca.service;

import com.biblioteca.dao.LivroDAO;
import com.biblioteca.model.Autor;
import com.biblioteca.model.Editora;
import com.biblioteca.model.Livro;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Biblioteca {

   private final LivroDAO dao;
   private final Scanner scanner = new Scanner(System.in);

    public Biblioteca() {
       this.dao = new LivroDAO();
    }

    public void rodar() throws Throwable {
        while (true){
            String option;

            System.out.println(
                    "BEM VINDO A BIBLIOTECA! O QUE DESEJA FAZER: " +
                    "\n1 - Emprestar Livro" +
                    "\n2 - Adicionar Livro" +
                    "\n3 - sair...");


            option = scanner.nextLine();

            switch (option){
                case "1" -> this.emprestarLivro();
                case "2" -> this.adicionarLivroISBN();
                case "3" -> {
                    return;
                }
            }
        }



    }


    public void emprestarLivro(){
        System.out.println("DIGITE O NOME DO LIVRO: ");
        String titulo = scanner.nextLine();
        Livro livro = dao.buscarLivroTitulo(titulo);

        if (livro == null){
            System.out.println("LIVRO NÃO EXISTE");
            return;
        }

        if (!dao.isLivroEmprestado(livro)){
            System.out.println("LIVRO SENDO EMPRESTADO");
            livro.setEmprestado(true);
        }else {
            System.out.println("LIVRO JÁ ESTÁ EMPRESTADO");
        }
    }


    public void adicionarLivroISBN() throws Exception {
        System.out.println("DIGITE O ISBN DO LIVRO: ");
        String isbn = scanner.nextLine().trim();

        String url = "https://openlibrary.org/api/books?bibkeys=ISBN:"
                + isbn + "&format=json&jscmd=data";


        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        convertJSON(httpResponse.body(), isbn);
        httpClient.close();

    }

    private void convertJSON(String json, String isbn) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(json);

        String chave = jsonNode.fieldNames().next();
        JsonNode livroNode = jsonNode.get(chave);

        Livro livro = new Livro();

        livro.setTitulo(livroNode.get("title").asText());
        livro.setISBN(isbn);
        livro.setAutor(new Autor(livroNode.get("authors").get(0).get("name").asText()));
        livro.setEditora(new Editora(livroNode.get("publishers").get(0).get("name").asText()));

        try{
            dao.Insert(livro);

            System.out.println("TITULO: " + livro.getTitulo());
            System.out.println("AUTOR: " + livro.getAutor().getNome());
            System.out.println("EDITORA: " + livro.getEditora().getNome());
            System.out.println("LIVRO ADICIONADO COM SUCESSO");
        }catch (Exception e){
            throw new Exception("ERRO AO ADICIONAR LIVRO");
        }
    }
}
