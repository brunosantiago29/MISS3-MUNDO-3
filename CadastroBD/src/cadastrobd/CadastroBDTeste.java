package cadastrobd;

import cadastrobd.model.Pessoa;
import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.util.PessoaFisicaDAO;
import cadastrobd.model.util.PessoaJuridicaDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CadastroBDTeste {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
            
            int opcao = -1;
            
            while (opcao != 0) {
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Incluir");
                System.out.println("2 - Alterar");
                System.out.println("3 - Excluir");
                System.out.println("4 - Exibir pelo ID");
                System.out.println("5 - Exibir todos");
                System.out.println("0 - Sair");
                
                opcao = scanner.nextInt();
                scanner.nextLine();  // Limpar o buffer
                
                switch (opcao) {
                    case 1 -> {
                        System.out.println("Escolha o tipo (F - Pessoa Física, J - Pessoa Jurídica):");
                        String tipo = scanner.nextLine().toUpperCase();
                        System.out.println("Digite o ID:");
                        int id = scanner.nextInt();
                        scanner.nextLine();  // Limpar o buffer
                        
                        System.out.println("Digite o nome:");
                        String nome = scanner.nextLine();
                        System.out.println("Digite o logradouro:");
                        String logradouro = scanner.nextLine();
                        System.out.println("Digite a cidade:");
                        String cidade = scanner.nextLine();
                        System.out.println("Digite o estado:");
                        String estado = scanner.nextLine();
                        System.out.println("Digite o telefone:");
                        String telefone = scanner.nextLine();
                        System.out.println("Digite o email:");
                        String email = scanner.nextLine();
                        
                        if ("F".equals(tipo)) {
                            System.out.println("Digite o CPF:");
                            String cpf = scanner.nextLine();
                            PessoaFisica pessoaFisica = new PessoaFisica(
                                    id, nome, logradouro, cidade, estado, telefone, email, cpf
                            );
                            
                            try {
                                pessoaFisicaDAO.incluir(pessoaFisica);
                                System.out.println("Pessoa Física incluída com sucesso!");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else if ("J".equals(tipo)) {
                            System.out.println("Digite o CNPJ:");
                            String cnpj = scanner.nextLine();
                            PessoaJuridica pessoaJuridica = new PessoaJuridica(
                                    id, nome, logradouro, cidade, estado, telefone, email, cnpj
                            );
                            
                            try {
                                pessoaJuridicaDAO.incluir(pessoaJuridica);
                                System.out.println("Pessoa Jurídica incluída com sucesso!");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Opção inválida.");
                        }
                    }
                    case 2 -> {
                        System.out.println("Escolha o tipo (F - Pessoa Física, J - Pessoa Jurídica):");
                        String tipo = scanner.nextLine().toUpperCase();
                        System.out.println("Digite o ID:");
                        int id = scanner.nextInt();
                        scanner.nextLine();  // Limpar o buffer
                        
                        switch (tipo) {
                            case "F" -> {
                                try {
                                    PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);
                                    if (pessoaFisica != null) {
                                        System.out.println("Dados atuais:");
                                        pessoaFisica.exibir();
                                        
                                        System.out.println("Digite o novo telefone:");
                                        String novoTelefone = scanner.nextLine();
                                        pessoaFisica.setTelefone(novoTelefone);
                                        
                                        pessoaFisicaDAO.alterar(pessoaFisica);
                                        System.out.println("Pessoa Física alterada com sucesso!");
                                    } else {
                                        System.out.println("Pessoa Física não encontrada.");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            case "J" -> {
                                try {
                                    PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
                                    if (pessoaJuridica != null) {
                                        System.out.println("Dados atuais:");
                                        pessoaJuridica.exibir();
                                        
                                        System.out.println("Digite o novo telefone:");
                                        String novoTelefone = scanner.nextLine();
                                        pessoaJuridica.setTelefone(novoTelefone);
                                        
                                        pessoaJuridicaDAO.alterar(pessoaJuridica);
                                        System.out.println("Pessoa Jurídica alterada com sucesso!");
                                    } else {
                                        System.out.println("Pessoa Jurídica não encontrada.");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            default -> System.out.println("Opção inválida.");
                        }
                    }
                    case 3 -> {
                        System.out.println("Escolha o tipo (F - Pessoa Física, J - Pessoa Jurídica):");
                        String tipo = scanner.nextLine().toUpperCase();
                        System.out.println("Digite o ID:");
                        int id = scanner.nextInt();
                        scanner.nextLine();  // Limpar o buffer
                        
                        switch (tipo) {
                            case "F" -> {
                                try {
                                    pessoaFisicaDAO.excluir(id);
                                    System.out.println("Pessoa Física excluída com sucesso!");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            case "J" -> {
                                try {
                                    pessoaJuridicaDAO.excluir(id);
                                    System.out.println("Pessoa Jurídica excluída com sucesso!");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            default -> System.out.println("Opção inválida.");
                        }
                    }
                    case 4 -> {
                        System.out.println("Escolha o tipo (F - Pessoa Física, J - Pessoa Jurídica):");
                        String tipo = scanner.nextLine().toUpperCase();
                        System.out.println("Digite o ID:");
                        int id = scanner.nextInt();
                        scanner.nextLine();  // Limpar o buffer
                        
                        switch (tipo) {
                            case "F" -> {
                                try {
                                    PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);
                                    if (pessoaFisica != null) {
                                        System.out.println("Dados da Pessoa Física:");
                                        pessoaFisica.exibir();
                                    } else {
                                        System.out.println("Pessoa Física não encontrada.");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            case "J" -> {
                                try {
                                    PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
                                    if (pessoaJuridica != null) {
                                        System.out.println("Dados da Pessoa Jurídica:");
                                        pessoaJuridica.exibir();
                                    } else {
                                        System.out.println("Pessoa Jurídica não encontrada.");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            default -> System.out.println("Opção inválida.");
                        }
                    }
                    case 5 -> {
                        System.out.println("Escolha o tipo (F - Pessoa Física, J - Pessoa Jurídica):");
                        String tipo = scanner.nextLine().toUpperCase();
                        
                        switch (tipo) {
                            case "F" -> {
                                try {
                                    List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
                                    if (!pessoasFisicas.isEmpty()) {
                                        System.out.println("Lista de Pessoas Físicas:");
                                        for (Pessoa pessoa : pessoasFisicas) {
                                            pessoa.exibir();
                                            System.out.println();
                                        }
                                    } else {
                                        System.out.println("Nenhuma Pessoa Física cadastrada.");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            case "J" -> {
                                try {
                                    List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
                                    if (!pessoasJuridicas.isEmpty()) {
                                        System.out.println("Lista de Pessoas Jurídicas:");
                                        for (Pessoa pessoa : pessoasJuridicas) {
                                            pessoa.exibir();
                                            System.out.println();
                                        }
                                    } else {
                                        System.out.println("Nenhuma Pessoa Jurídica cadastrada.");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            default -> System.out.println("Opção inválida.");
                        }
                    }
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida. Digite um número de 0 a 5.");
                }
            }
        }
    }
}
