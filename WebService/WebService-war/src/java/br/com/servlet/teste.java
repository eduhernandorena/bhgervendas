package br.com.servlet;

import br.com.ejb.bean.Cidade;
import br.com.ejb.bean.Endereco;
import br.com.ejb.bean.Entidade;
import br.com.ejb.bean.UF;
import br.com.ejb.bean.Usuario;
import br.com.ejb.bean.enumeration.EstadoCivil;
import br.com.ejb.bean.enumeration.Genero;
import br.com.ejb.bean.enumeration.TipoEndereco;
import br.com.ejb.bean.enumeration.TipoEntidade;
import br.com.ejb.ejb.CidadeDAORemote;
import br.com.ejb.ejb.EnderecoDAORemote;
import br.com.ejb.ejb.EntidadeDAORemote;
import br.com.ejb.ejb.UfDAORemote;
import br.com.ejb.ejb.UsuarioDAORemote;
import java.io.IOException;
import java.util.List;
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
    CidadeDAORemote cidadeDAO;
    @EJB
    EnderecoDAORemote enderecoDAO;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        Usuario us = new Usuario();
        us.setNome("eduardo");
        us.setSenha("teste");
        us = dao.create(us);
        System.out.println(us.getId());


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
