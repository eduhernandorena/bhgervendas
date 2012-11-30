package br.com.servlet;

import br.com.ejb.bean.Pedido;
import br.com.ejb.bean.enumeration.FormaPagamento;
import br.com.ejb.bean.enumeration.StatusPedido;
import br.com.ejb.bean.enumeration.TipoEntidade;
import br.com.ejb.ejb.EntidadeDAORemote;
import br.com.ejb.ejb.PedidoDAORemote;
import br.com.ejb.ejb.ProdutoDAO;
import br.com.ejb.ejb.ProdutoDAORemote;
import br.com.ejb.ejb.UfDAORemote;
import br.com.ejb.ejb.UsuarioDAORemote;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Eduardo Hernandorena
 */
@WebServlet(name = "teste", urlPatterns = {"/teste"})
public class teste extends HttpServlet {

    @EJB
    UsuarioDAORemote dao;
    @EJB
    EntidadeDAORemote entDAO;
    @EJB
    UfDAORemote ufDAO;
    @EJB
    PedidoDAORemote pedDAO;
    @EJB
    ProdutoDAORemote prodDAO;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        Pedido ped = new Pedido();
        ped.setValor(23.);
        ped.setDataCompra(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        ped.setFormaPagamento(FormaPagamento.AVISTA);
        ped.setFornecedor(entDAO.find(TipoEntidade.Fornecedor, 2));
        ped.setLucro(0.);
        ped.setNroParcelas(1);
        ped.setObsPagamento("");
        ped.setProdutos(prodDAO.findAll());
        ped.setStatus(StatusPedido.PENDENTE);
        ped.setCliente(entDAO.find(TipoEntidade.Cliente, 1));
        pedDAO.create(ped);

//        List<Entidade> list = entDAO.findAllCliente();
//        if (!list.isEmpty()) {
//            System.out.println("Tem coisa!");
//            
//        } else {
//            Entidade ent = new Entidade();
//            ent.setCelular(81093531l);
//            ent.setNome("Luiz Eduardo");
//            ent.setEstadoCivil(EstadoCivil.NOIVO);
//            ent.setTipoEntidade(TipoEntidade.CLIENTE);
//            ent.setGenero(Genero.MASCULINO);
//            ent.setPedidos(null);
//            ent.setCpfCnpj("01735654094");
//            ent.setEmail("eduhernandorena@live.com");
//            Endereco end = new Endereco();
//            end.setBairro("Areal");
//            end.setCep("96080080");
//            Cidade cid = new Cidade();
//            cid.setCodIbge(7236984l);
//            cid.setDescricao("Pelotas");
//            UF uf = new UF();
//            uf.setCodIbge(98760987l);
//            uf.setDescricao("Rio Grande do Sul");
//            uf.setSigla("RS");
//            ufDAO.create(uf);
//            cid.setUf(uf);
//            cidadeDAO.create(cid);
//            end.setCidade(cid);
//            end.setComplemento(null);
//            end.setNumero(1559);
//            end.setRua("Claudio Manoel da Costa");
//            end.setTipoEndereco(TipoEndereco.RESIDENCIAL);
//            end = enderecoDAO.create(end);
//            ent.setEndereco(end);
//            ent = entDAO.create(ent);
//            System.out.println(ent.getId());
//        }

    }
}
