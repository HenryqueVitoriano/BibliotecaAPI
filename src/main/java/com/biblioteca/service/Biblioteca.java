package com.biblioteca.service;

import com.biblioteca.dao.LivroDAO;

import com.biblioteca.dao.leitorDAO;
import com.biblioteca.model.Leitor;
import com.biblioteca.model.Livro;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Scanner;

public class Biblioteca {

   private final LivroDAO livroDAO = new LivroDAO();
   private final leitorDAO leitorDAO = new leitorDAO();

   private final Scanner scanner = new Scanner(System.in);

   Leitor leitor;

    public Biblioteca() {
    }


    //Principal
    public void rodar() throws Throwable {
        if (!this.login()){
            return;
        }

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

    public boolean login() throws Throwable {

        while (true){
            System.out.println("ENTRE OU CRIE UMA CONTA NA BIBLIOTECA: " +
                    "\n 1 - ENTRAR" +
                    "\n 2 - CADASTRAR" +
                    "\n 3 - SAIR");

            String option = scanner.nextLine();

            switch (option){
                case "1" -> {
                    System.out.println("DIGITE SEU ID: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();

                   this.leitor = leitorDAO.buscarLeitorID(id);

                    if (leitor != null){
                        System.out.println("USUARIO LOGADO COM SUCESSO: " + leitor.getNome());
                        return false;
                    }else {
                        System.out.println("FALHA AO LOGAR");
                    }

                }

                case "2" -> {
                    System.out.println("DIGITE SEU NOME PARA CADASTRO: ");
                    String nome = scanner.nextLine();

                    this.leitor = leitorDAO.novoLeitor(nome);

                    if (leitor != null){
                        System.out.println("CADASTRADO COM SUCESSO");
                        return false;
                    }else {
                        System.out.println("ERRO AO CADASTRAR");
                    }
                }

                case "3" -> {
                    return false;
                }

                default -> {
                    System.out.println("OPÇÃO INVÁLIDA");
                }

            }
        }
    }


    //Funções:
    public void emprestarLivro(){
        System.out.println("DIGITE O NOME DO LIVRO: ");
        String titulo = scanner.nextLine();
        Livro livro = livroDAO.buscarLivroTitulo(titulo);

        if (livro == null){
            System.out.println("LIVRO NÃO EXISTE");
            return;
        }

        if (!livroDAO.isLivroEmprestado(livro)){
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

        API_service apiService = new API_service();
        Livro livro =  apiService.convertJSON(httpResponse.body(), isbn);

        try{

            livroDAO.Insert(livro);

            System.out.println("TITULO: " + livro.getTitulo());
            System.out.println("AUTOR: " + livro.getAutor().getNome());
            System.out.println("EDITORA: " + livro.getEditora().getNome());

            System.out.println("LIVRO ADICIONADO COM SUCESSO");

        }catch (Exception e){
            throw new Exception("ERRO AO ADICIONAR LIVRO");
        }
        httpClient.close();
    }
}
