import java.math.BigDecimal;
import java.util.List;

public class Carrinho extends FormataValor {
    private Produto produto;
    private Integer quantidadeCompra;
    private BigDecimal valorTotal;

    public Carrinho verificaEstoque (Produto produto, String quantidadeCompra) throws Exception {
        Integer quantidade = Integer.valueOf(quantidadeCompra);
        if(quantidade > produto.getQuantidade()) {
            System.out.println("Quantidade em estoque: "+ produto.getQuantidade());
            System.out.println("Necessario diminur a quantidade do pedido.");
            return null;
        }

        Carrinho carrinho = new Carrinho();
        carrinho.setProduto(produto);
        carrinho.setQuantidadeCompra(quantidade);
        BigDecimal quantidadeDecimal = new BigDecimal(quantidade);
        carrinho.setValorTotal(produto.getPreco().multiply(quantidadeDecimal));
        produto.setQuantidade(produto.getQuantidade()-quantidade);
        produto.atualizarProduto(produto, produto.getNomeProduto(), String.valueOf(produto.getQuantidade()), String.valueOf(produto.getPreco()));

        return carrinho;
    }

    public void somaFinal(List<Carrinho> carrinho) {
        BigDecimal somaValores = new BigDecimal(0);
        for(Carrinho c : carrinho) {
            somaValores = somaValores.add(c.getValorTotal());
        }

        System.out.println("PEDIDO");
        System.out.println("Produtos: "+carrinho.toString()+" Valor Total: R$"+ super.valorDecimal().format(somaValores));
    }


    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidadeCompra() {
        return quantidadeCompra;
    }

    public void setQuantidadeCompra(Integer quantidadeCompra) {
        this.quantidadeCompra = quantidadeCompra;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "Produto: "+this.produto+" Quantidade: "+this.quantidadeCompra+" Valor do Pedido: R$"+this.valorTotal;
    }

}
