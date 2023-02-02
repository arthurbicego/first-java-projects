import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class Produto {

    private Integer id;
    private String nomeProduto;
    private Integer quantidade;
    private BigDecimal preco;

    Path path = Paths.get("src/produtos.txt");

    public Produto(String nomeProduto, Integer quantidade, BigDecimal preco) {
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public Produto(Integer id, String nomeProduto, Integer quantidade, BigDecimal preco) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public Produto() {

    }

    public Produto validaProduto(String nomeProduto, String quantidade, String preco  ) {
        String nomeProdutoValidado = nomeProduto.toLowerCase().trim();
        Integer quantidadeProdutoValidado = Integer.valueOf(quantidade);
        BigDecimal  precoProdutoValidado = new BigDecimal(preco);
        return new Produto(nomeProdutoValidado, quantidadeProdutoValidado, precoProdutoValidado);
    }

    public Produto validaProduto(String id, String nomeProduto, String quantidade, String preco  ) {
        Integer idProdutoValidado = Integer.valueOf(id);
        String nomeProdutoValidado = nomeProduto.toLowerCase().trim();
        Integer quantidadeProdutoValidado = Integer.valueOf(quantidade);
        BigDecimal  precoProdutoValidado = new BigDecimal(preco);
        return new Produto(idProdutoValidado, nomeProdutoValidado, quantidadeProdutoValidado, precoProdutoValidado);
    }

    public String cadastrarProduto(Produto produto) throws Exception {
        if(!Files.exists(path)) {
            Files.createFile(path);
        }
        int quantidadeLinhas = Files.readAllLines(path).size();
        if(quantidadeLinhas == 0) {
            produto.setId(0);
        }else{
            List<String> valoresArquivo = Files.readAllLines(path);
            quantidadeLinhas--;
            String linha = valoresArquivo.get(quantidadeLinhas);
            String[] split = linha.split("\\|");
            Integer valueOf = Integer.valueOf(split[0]);
            valueOf++;
            produto.setId(valueOf);
        }
        Files.writeString(path, produto.toString(), StandardOpenOption.APPEND);

        return produto.toString();
    }

    public void listarProduto() throws Exception {
        List<String> listaProdutos = Files.readAllLines(path);

        for (String produto : listaProdutos) {
            String idString = produto.split("\\|")[0];
            String nomeProdutoString = produto.split("\\|")[1];
            String quantidadeProdutoString = produto.split("\\|")[2];
            String precoProdutoString = produto.split("\\|")[3];
            System.out.println("ID: " + idString + " Nome Produto: " + nomeProdutoString + " Quantidade: " + quantidadeProdutoString
                    + " Valor R$" + precoProdutoString);
        }
    }

    public Produto procuraProduto(String id) throws IOException {
        List<String> listaProdutos = Files.readAllLines(path);
        for(int i = 0; i < listaProdutos.size(); i++) {
            String produtoString = listaProdutos.get(i);
            String idString = produtoString.split("\\|")[0];
            if(id.equals(idString)) {
                String nomeString = produtoString.split("\\|")[1];
                String quantidadeString = produtoString.split("\\|")[2];
                String precoString = produtoString.split("\\|")[3];
                Produto validaProduto = new Produto()
                        .validaProduto(idString, nomeString, quantidadeString, precoString);
                String[] saidaFormatada = validaProduto.toString().split("\\|");
                System.out.println("ID:" + saidaFormatada[0] +":" + " Nome Produto: " + saidaFormatada[1] + " Quantidade: " + saidaFormatada[2]
                        + " Valor R$" + saidaFormatada[3]);
                return validaProduto;
            }
        }
        return null;
    }

    public Produto procuraProdutoNome(String nome) throws IOException {
        List<String> listaProdutos = Files.readAllLines(path);
        for(int i = 0; i < listaProdutos.size(); i++) {
            String produtoString = listaProdutos.get(i);
            String idString = produtoString.split("\\|")[0];
            String nomeString = produtoString.split("\\|")[1];
            if(nomeString.contains(nome)) {
                String quantidadeString = produtoString.split("\\|")[2];
                String precoString = produtoString.split("\\|")[3];
                Produto validaProduto = new Produto()
                        .validaProduto(idString, nomeString, quantidadeString, precoString);
                String[] saidaFormatada = validaProduto.toString().split("\\|");
                System.out.println("ID:" + saidaFormatada[0] +":" + " Nome Produto: " + saidaFormatada[1] + " Quantidade: " + saidaFormatada[2]
                        + " Valor R$" + saidaFormatada[3]);
                return validaProduto;
            }
        }
        return null;
    }


    public void atualizarProduto(Produto produto, String nomeProduto, String quantidadeProduto, String precoProduto) throws Exception {
        String id = String.valueOf(produto.getId());
        Produto produtoParaAtualizar = this.validaProduto(id, nomeProduto, quantidadeProduto, precoProduto);
        List<String> listaProdutos = Files.readAllLines(path);

        for(int i = 0; i < listaProdutos.size(); i++) {
            String linhaProduto = listaProdutos.get(i);
            String idString = linhaProduto.split("\\|")[0];
            if(idString.equals(String.valueOf(produto.getId()))) {
                String string = produtoParaAtualizar.toString();
                listaProdutos.set(i, string.trim());
                Files.write(path, listaProdutos);
            }
        }

        this.listarProduto();
    }

    public void removerProduto(Produto produto) throws IOException {
        List<String> listaProdutos = Files.readAllLines(path);
        for(int i = 0; i < listaProdutos.size(); i++) {
            String linhaProduto = listaProdutos.get(i);
            String idString = linhaProduto.split("\\|")[0];
            System.out.println(idString);
            if(idString.equals(String.valueOf(produto.getId()))) {
                listaProdutos.remove(i);
                Files.write(path, listaProdutos);
            }

        }

    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    public BigDecimal getPreco() {
        return preco;
    }
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }


    @Override
    public String toString() {
        return this.id + "|" + this.nomeProduto + "|"
                + this.quantidade +"|" + this.preco + "\n";
    }
}