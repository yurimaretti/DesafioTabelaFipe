package br.com.alura.TabelaFipe.main;

import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.model.Veiculo;
import br.com.alura.TabelaFipe.service.ConsumoApi;
import br.com.alura.TabelaFipe.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

//    public void exibeMenu() {
//        var menu = """
//               *** OPÇÕES ***
//               Carro
//               Moto
//               Caminhão
//
//               Digite uma das opções para consulta:
//               """;
//
//        System.out.println(menu);
//        var opcao = leitura.nextLine();
//        String endereco;
//
//        if (opcao.toLowerCase().contains("carr")) {
//            endereco = URL_BASE + "carros/marcas";
//        } else if (opcao.toLowerCase().contains("mot")) {
//            endereco = URL_BASE + "motos/marcas";
//        } else {
//            endereco = URL_BASE + "caminhoes/marcas";
//        }
//
//        var json = consumo.obterDados(endereco);
//        System.out.println(json);
//        var marcas = conversor.obterLista(json, Dados.class);
//        marcas.stream()
//                .sorted(Comparator.comparing(Dados::codigo))
//                .forEach(System.out::println);
//
//        System.out.println("Informe o código da marca para consulta: ");
//        var codigoMarca = leitura.nextLine();
//
//        endereco = endereco + "/" + codigoMarca + "/modelos";
//        json = consumo.obterDados(endereco);
//        var modeloLista = conversor.obterDados(json, Modelos.class);
//
//        System.out.println("\nModelos dessa marca: ");
//        modeloLista.modelos().stream()
//                .sorted(Comparator.comparing(Dados::codigo))
//                .forEach(System.out::println);
//
//        System.out.println("\nDigite um trecho do nome do carro a ser buscado");
//        var nomeVeiculo = leitura.nextLine();
//
//        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
//                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
//                .collect(Collectors.toList());
//
//        System.out.println("\nModelos filtrados");
//        modelosFiltrados.forEach(System.out::println);
//
//        System.out.println("Digite por favor o código do modelo para buscar os valores de avaliação: ");
//        var codigoModelo = leitura.nextLine();
//
//        endereco = endereco + "/" + codigoModelo + "/anos";
//        json = consumo.obterDados(endereco);
//        List<Dados> anos = conversor.obterLista(json, Dados.class);
//        List<Veiculo> veiculos = new ArrayList<>();
//
//        for (int i = 0; i < anos.size(); i++) {
//            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
//            json = consumo.obterDados(enderecoAnos);
//            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
//            veiculos.add(veiculo);
//        }
//
//        System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
//        veiculos.forEach(System.out::println);
//
//    }

    public void exibeMenu() {
        var menu = """
                *** OPÇÕES ***

                Carro
                Moto
                Caminhão

                Digite uma das opções para consultar:
                """;

        System.out.println(menu);
        var opcao = leitura.nextLine();
        String endereco = "";

        if (opcao.toLowerCase().contains("carr")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        } else if (opcao.toLowerCase().contains("cami")) {
            endereco = URL_BASE + "caminhoes/marcas";
        } else {
            System.out.println("Opção inexistente.");
        }

        try {
            var json = consumo.obterDados(endereco);
            var marcas = conversor.obterLista(json, Dados.class);

            marcas.stream()
                    .sorted(Comparator.comparing(Dados::codigo))
                    .forEach(System.out::println);

            System.out.println("Informe o código da marca para consulta: ");
            var codigoMarca = leitura.nextLine();

            endereco = endereco + "/" + codigoMarca + "/modelos";
            json = consumo.obterDados(endereco);
            var modeloLista = conversor.obterDados(json, Modelos.class);
            System.out.println("\nModelos dessa marca: ");

            modeloLista.modelos().stream()
                    .sorted(Comparator.comparing(Dados::codigo))
                    .forEach(System.out::println);

            System.out.println("\nDigite um trecho do nome do veículo desejado: ");
            var nomeVeiculo = leitura.nextLine();

            List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                    .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                    .collect(Collectors.toList());

            System.out.println("\nModelos filtrados: ");
            modelosFiltrados.forEach(System.out::println);

            System.out.println("\nDigite o código do modelo: ");
            var codigoModelo = leitura.nextLine();

            endereco = endereco + "/" + codigoModelo + "/anos";
            json = consumo.obterDados(endereco);
            List<Dados> anos = conversor.obterLista(json, Dados.class);
            List<Veiculo> veiculos = new ArrayList<>();

            for (int i = 0; i < anos.size(); i++) {
                var enderecoAnos = endereco + "/" + anos.get(i).codigo();
                json = consumo.obterDados(enderecoAnos);
                Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
                veiculos.add(veiculo);
            }

            System.out.println("\nVeículos encontrados: ");

            veiculos.forEach(System.out::println);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
