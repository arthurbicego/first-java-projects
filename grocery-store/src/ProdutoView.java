import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ProdutoView {
    Scanner scanner = new Scanner(System.in);

    public Boolean menuPrincipal() {
        Boolean saida;

        do {
            System.out.println("+-------------------------------+");
            System.out.println("+-            MENU             -+");
            System.out.println("+-------------------------------+");
            System.out.println("+    Escolha Uma das Op��es     +");
            System.out.println("+ 1. Cadastrar Produto          +");
            System.out.println("+ 2. Listar Produto             +");
            System.out.println("+ 3. Atualizar Produto por Id   +");
            System.out.println("+ 4. Remover Produto por Id     +");
            System.out.println("+ 5. Procurar Produto  por Id   +");
            System.out.println("+ 6. Procurar Produto por Nome  +");
            System.out.println("+ 7. Comprar Produto            +");
            System.out.println("+ 0. Sair                       +");
            System.out.println("+-------------------------------+");
            try {
                switch (Integer.valueOf(scanner.nextLine())) {
                    case 1:
                        menuCadastrarProduto();
                        saida = true;
                        break;
                    case 2:
                        menuListaProduto();
                        saida = true;
                        break;
                    case 3:
                        menuAtualizarProduto();
                        saida = true;
                        break;
                    case 4:
                        menuRemoverProduto();
                        saida = true;
                        break;
                    case 5:
                        menuProcurarProduto();
                        saida = true;
                        break;

                    case 6:
                        menuProcurarProdutoNome();
                        saida = true;
                        break;

                    case 7:
                        menuComprarProduto();
                        saida = true;
                        break;

                    case 0:
                        menuSair();
                        saida = false;
                        break;
                    default:
                        mensagemValorNaoEncontrado();
                        saida = true;
                        break;
                }

            } catch (Exception e) {
                System.out.println("Erro! Repita opera��o observando os valores digitados.");
                saida = true;
            }

        } while (saida);

        return false;

    }

    private void menuCadastrarProduto() throws Exception {
        Produto produto = new Produto();
        Locale.setDefault(new Locale("pt", "Brazil"));
        System.out.println("----------CADASTRAR------------");
        System.out.println("Digite o nome do Produto: ");
        String nome = scanner.nextLine().replaceAll("\\s{2,}", " ");;
        System.out.println("Digite o Quantidade do Produto: ");
        String quantidade = scanner.nextLine().trim().toUpperCase();
        ;
        System.out.println("Digite o Valor do Produto: ");
        String preco = scanner.nextLine().replaceAll(",", ".");
        Produto produtoValido = produto.validaProduto(nome, quantidade, preco);
        produto.cadastrarProduto(produtoValido);
        produto.listarProduto();
        System.out.println("Produto Cadastrado Com Sucesso!");
    }

    private void menuListaProduto() throws Exception {
        Produto produto = new Produto();
        System.out.println("----------LISTA------------");
        produto.listarProduto();
    }

    private void menuAtualizarProduto() throws Exception {
        Produto produto = new Produto();
        System.out.println("----------ATUALIZAR------------");
        produto.listarProduto();
        System.out.println("Digite o ID do Produto: ");
        String id = scanner.nextLine();
        Produto procuraProduto = produto.procuraProduto(id);
        if (procuraProduto.equals(null)) {
            System.out.println("Produto N�o Encontrado!");
        } else {
            System.out.println("Digite o novo nome do Produto: ");
            String nome = scanner.nextLine().replaceAll("\\s{2,}", " ");
            System.out.println("Digite a nova Quantidade do Produto: ");
            String quantidade = scanner.nextLine().trim().toUpperCase();
            ;
            System.out.println("Digite o novo Valor do Produto: ");
            String preco = scanner.nextLine().replaceAll(",", ".");

            produto.atualizarProduto(procuraProduto, nome, quantidade, preco);
        }

    }

    private void menuRemoverProduto() throws Exception {
        Produto produto = new Produto();
        System.out.println("----------REMOVER------------");
        produto.listarProduto();
        System.out.println("Digite o ID do Produto Para Remo��o: ");
        String id = scanner.nextLine();
        Produto procuraProduto = produto.procuraProduto(id);
        if (procuraProduto.equals(null)) {
            System.out.println("Produto N�o Encontrado!");
        } else {
            System.out.println("----------ATENÇÃO----------");
            System.out.println("O produto ID: " + procuraProduto.getId() + " Nome: " + procuraProduto.getNomeProduto());
            System.out.println("Será excluido do Nosso Sistema. Tem certeza dessa operação?");
            System.out.println("1.Sim");
            System.out.println("Qualquer outra tecla para cancelar!");
            switch (scanner.nextLine()) {
                case "1":
                    produto.removerProduto(procuraProduto);
                    System.out.println("Produto Excluido com Sucesso!");
                    break;
                default:
                    System.out.println("Operação Cancelada!");
                    break;
            }
        }
    }

    private void menuProcurarProdutoNome() throws IOException {
        Produto produto = new Produto();
        System.out.println("Digite o nome do Produto ou parte dele: ");
        String nome = scanner.nextLine();
        Produto procuraProduto = produto.procuraProdutoNome(nome);
        if (procuraProduto.equals(null)) {
            System.out.println("Produto não Localizado");
        }
    }

    private void menuProcurarProduto() throws IOException {
        Produto produto = new Produto();
        System.out.println("Digite o ID do Produto: ");
        String id = scanner.nextLine();
        Produto procuraProduto = produto.procuraProduto(id);
        if (procuraProduto.equals(null)) {
            System.out.println("Produto n�o Localizado!");
        }
    }

    private void menuComprarProduto() throws Exception {
        Produto produto = new Produto();
        produto.listarProduto();
        Boolean continua = true;
        List<Carrinho> listaPedido = new ArrayList<Carrinho>();
        do {
            System.out.println("Digite o ID do Produto:");
            Produto produtoSelecionado = produto.procuraProduto(scanner.nextLine());
            System.out.println("Digite a Quantidade.");
            String quantidadeCompra = scanner.nextLine();
            Carrinho pedido = new Carrinho().verificaEstoque(produtoSelecionado, quantidadeCompra);
            listaPedido.add(pedido);
            System.out.println("Deseja adicionar mais algum item?");
            System.out.println("1.Sim");
            System.out.println("Ou qualquer outra tecla para N�o!");
            switch (scanner.nextLine()) {
                case "1":
                    System.out.println("Adicione mais um Produto.");
                    break;
                default:
                    continua = false;
                    break;
            }
        } while (continua);

        new Carrinho().somaFinal(listaPedido);

    }

    private void menuSair() {
        System.out.println("Obrigado por utilizar nosso sistema.");
        System.out.println("Santander Coders 22. Colaboradores:\nAmanda Solsona\n"
                + "Ana Carolina Muratori\nArthur Bicego\nFelipe Soares\nRodrigo Rocha");
    }

    private void mensagemValorNaoEncontrado() {
        System.out.println("Valor n�o encontrado Repita a Opera��o");

    }
}