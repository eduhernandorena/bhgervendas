package br.com.nfe.view;

import br.com.nfe.DAO.DuplicataDAO;
import br.com.nfe.DAO.EmpresaDAO;
import br.com.nfe.DAO.InfEvtDAO;
import br.com.nfe.DAO.InfNFeDAO;
import br.com.nfe.DAO.NFE001DAO;
import br.com.nfe.DAO.NFeDAO;
import br.com.nfe.bean.BancoDados;
import br.com.nfe.bean.Duplicata;
import br.com.nfe.bean.Empresa;
import br.com.nfe.bean.InfNFe;
import br.com.nfe.bean.NFe;
import br.com.nfe.bean.NotaReferenciada;
import br.com.nfe.bean.NotaReferente;
import br.com.nfe.enumeration.Ambiente;
import br.com.nfe.enumeration.Banco;
import br.com.nfe.enumeration.TipoServico;
import br.com.nfe.fill.InfEvtFill;
import br.com.nfe.fill.InfNfeFill;
import br.com.nfe.servico.EnviarEmail;
import br.com.nfe.servico.EnvioNFe;
import br.com.nfe.servico.EventoNFe;
import br.com.nfe.servico.NFeConsultaNFe;
import br.com.nfe.servico.NFeConsultaStatus;
import br.com.nfe.servico.NFeContingencia;
import br.com.nfe.servico.NFeEventoCancelamento;
import br.com.nfe.servico.NFeRecepcao;
import br.com.nfe.servico.NFeRetRecepcao;
import br.com.nfe.util.AllKeyIntercept;
import br.com.nfe.util.Configuracao;
import br.com.nfe.util.DBConection;
import br.com.nfe.util.DataLi;
import br.com.nfe.util.NFeTableModel;
import br.com.nfe.util.Navegador;
import br.com.nfe.util.Select;
import br.com.nfe.util.Service;
import br.com.nfe.util.ValidadeCertificadoDigital;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.cert.CertificateException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author eduardo
 */
@SuppressWarnings("serial")
public final class NotaFiscalEletronica extends javax.swing.JFrame implements KeyListener {

    private static PrintStream ps;
    private final String maskData = "/  /";
    private final int ERRO = 0, INFO = 1;
    private Boolean licenca = false, certificado = false, conting = false;
    private NFeTableModel model;
    private Empresa emp;
    private String dir;
    private String dtInicial, dtFinal;
    private String certErro;
    public static String cacerts;
    public NotaFiscalEletronica thisNFe = this;
    public ConfiguracaoInicial conf = null;
    public Inutilizacao inut = null;
    public EmpresaCad empCad = null;
    public ExportNFe exp = null;
    public InfoCert info = null;
    public ConsCad cad = null;
    public ImportNotaEntrada impNFe = null;
    public Compatibilidade comp = null;
    public ConsEntrada consEntrada = null;
    private static final Logger LOG = Logger.getLogger(NotaFiscalEletronica.class.getName());

    public NotaFiscalEletronica() {
        initComponents();
        getRootPane().setDefaultButton(btEnviar);
        logger();
        validaParamInicial();
        preencheCmbEmpresa();
        inicializaTable();
        definicoes();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new AllKeyIntercept(this));
    }

    /**
     * Método usado para bloquear o uso dos botões de "Enviar", "Atualizar" e
     * "Inutilizar". Lançando na tela o aviso passado por parametro.
     *
     * @param msg Mensagem a ser enviada na janela.
     * @param title Titulo da janela de mensagem e conteudo do campo de aviso.
     */
    private void block(String title) {
        txtLicenca.setText(title);
        btEnviar.setEnabled(false);
        btAtt.setEnabled(false);
        mnInut.setEnabled(false);
        mnConsCad.setEnabled(false);
        mnConsCert.setEnabled(false);
        mnImpNfe.setEnabled(false);
    }

    /**
     * Método que cria o log do sistema. O método ainda testa se o arquivo é
     * maior que 10Mb e o apaga em caso positivo.
     *
     */
    private void logger() {
        try {
            File file = new File("logNFe.txt");
            if (file.exists()) {
                BigDecimal mbTam;
                BigDecimal tamanho = new BigDecimal(file.length());
                System.out.println(tamanho);
                mbTam = tamanho.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_EVEN);
                if (mbTam.longValue() > 10) {
                    file.delete();
                } else {

                    System.out.println(MessageFormat.format("{0}Mb", mbTam.toPlainString()));
                }
            }
            FileOutputStream arquivoLog = new FileOutputStream("logNFe.txt", true);
            ps = new PrintStream(arquivoLog);
            System.setOut(ps);
            System.setErr(ps);
            System.out.println(MessageFormat.format("\nSO: {0} -- Java: {1} -- Data: {2}", System.getProperty("os.name"), System.getProperty("java.runtime.version"), new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())));
        } catch (FileNotFoundException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Método usado para desbloquear os botoes de "Enviar", "Atualizar" e
     * "Inutilizar", assim como remover a mensagem do campo de aviso.
     */
    private void unlock() {
        if (licenca) {
            txtLicenca.setText("");
            btEnviar.setEnabled(true);
            btAtt.setEnabled(true);
            mnInut.setEnabled(true);
            mnConsCad.setEnabled(true);
            mnConsCert.setEnabled(true);
            mnImpNfe.setEnabled(true);
        }
    }

    /**
     * Método que valida o certificado digital, comparando a data de validade
     * com a data atual, e retornando false no caso de certificado expirado, ou
     * lançando a mensagem na tela em caso de certificado próximo de expiração.
     *
     * @return retorna um boolean informando se o certificado se encontra
     * expirado(false) ou não(true).
     */
    private boolean validaCert() {
        Date venc;
        long differ;
        if (emp != null) {
            try {
                venc = ValidadeCertificadoDigital.validade(emp);
                differ = venc.getTime() - System.currentTimeMillis();
            } catch (NullPointerException e) {
                if (emp.getTipoCert().isA1()) {
                    Service.message("Certificado Não Encontrado", MessageFormat.format("O certificado n\u00e3o foi encontrado, verifique se o mesmo encontra-se em {0} ou se a senha est\u00e1 correta.", emp.getPathCert()), 0);
                    certErro = "CERTIFICADO NÃO ENCONTRADO";
                } else {
                    Service.message("Certificado Desconectado", "O certificado n\u00e3o foi encontrado, verifique se o mesmo encontra-se conectado ou se a senha est\u00e1 correta.", 0);
                    certErro = "CERTIFICADO DESCONECTADO";
                }
                return false;
            } catch (CertificateException ex) {
                System.out.println(MessageFormat.format("Certificado Expirado: {0}", ex.getMessage()));
                certErro = "CERTIFICADO EXPIRADO";
                return false;
            }
            if (differ > 0 && differ < 864000000) {
                Service.message("Certificado Expirado", MessageFormat.format("Faltam {0} dias para expirar o certificado digital.", Math.round(differ / 86400000)), 1);
            }
        }
        return true;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDtFinal() {
        return dtFinal;
    }

    public void setDtFinal(String dtFinal) {
        this.dtFinal = dtFinal;
    }

    public String getDtInicial() {
        return dtInicial;
    }

    public void setDtInicial(String dtInicial) {
        this.dtInicial = dtInicial;
    }

    public static List<NFe> filtroNFe(List<NFe> nfeList, boolean export) {
        List<NFe> listaCancel = new ArrayList<>();
        List<NFe> listaEmit = new ArrayList<>();
        List<NFe> listaResto = new ArrayList<>();

        for (NFe nfe : nfeList) {
            if (nfe.getSituacao().equalsIgnoreCase("pendentecancel") || nfe.getSituacao().equalsIgnoreCase("cancelado")) {
                listaCancel.add(nfe);
            } else if (nfe.getSituacao().equalsIgnoreCase("enviado")) {
                listaEmit.add(nfe);
            } else {
                listaResto.add(nfe);
            }
        }

        for (NFe nfeC : listaCancel) {
            if (contem(listaResto, nfeC)) {
                nfeList.remove(nfeC);
            }
        }

        for (NFe nfeE : listaEmit) {
            if (contem(listaCancel, nfeE)) {
                nfeList.remove(nfeE);
            }
        }

        if (export) {
            for (NFe nfe : listaResto) {
                nfeList.remove(nfe);
            }
            return nfeList;
        }
        return nfeList;

    }

    private static boolean contem(List<NFe> lista, NFe nfe) {
        for (NFe n : lista) {
            if (n.getNrNota().equals(nfe.getNrNota())) {
                return true;
            }
        }
        return false;
    }

    private void definicoes() {
        isContingencia(model.getListNFe());
        if (cmbEmpresa.getSelectedItem() != "Todas") {
            emp = (Empresa) cmbEmpresa.getSelectedItem();
        }
        cmbEmpresa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmbEmpresa.getSelectedItem() != "Todas") {
                    emp = (Empresa) cmbEmpresa.getSelectedItem();
                } else {
                    emp = null;
                }
            }
        });

        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
        String tooltip = MessageFormat.format("<html> <center>Legenda:</center><img  src=\"{0}\"/> - Nota Emitida com Sucesso.<br><img  src=\"{1}\"/> - Nota Pendente.<br><img  src=\"{2}\"/> - Nota Rejeitada ou Inutilizada.<br><img  src=\"{3}\"/> - Nota Cancelada.<br><img  src=\"{4}\"/> - Nota Em Conting\u00eancia.<br></html>", getClass().getResource("img/ok.png"), getClass().getResource("img/default.png"), getClass().getResource("img/erro.gif"), getClass().getResource("img/cancel.png"), getClass().getResource("img/load.gif"));
        btHelp.setToolTipText(tooltip);
    }

    @SuppressWarnings("unchecked")
    private void inicializaTable() {
        //busca a lista com as NFe para inserir na tabela
        List<NFe> lista = new NFeDAO().retrieveInicial(emp);
        List<NFe> listaNFe = new ArrayList<>();
        List<Empresa> listaEmp = new ArrayList<>();

        if (cmbEmpresa.getSelectedItem() != "Todas") {
            listaEmp.add(((Empresa) cmbEmpresa.getSelectedItem()));
        } else {
            listaEmp = new EmpresaDAO().listaTodos();
        }

        if (listaEmp.isEmpty()) {
            listaEmp = new EmpresaDAO().listaTodos();
        }
        for (NFe nfe : lista) {
            for (Empresa empresa : listaEmp) {
                if (empresa != null && nfe.getCodEmpresa() == empresa.getCod().intValue()) {
                    if (nfe.getSerie().equalsIgnoreCase(empresa.getSerie())) {
                        listaNFe.add(nfe);
                    }
                }
            }
        }

        listaNFe = filtroNFe(listaNFe, false);

        Collections.sort(listaNFe, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                NFe nota1 = (NFe) o1;
                NFe nota2 = (NFe) o2;
                return nota2.compareTo(nota1);
            }
        });

        //cria o modelo de NFe da tabela
        model = new NFeTableModel(listaNFe);
        //atribui o modelo à tabela
        tbNotas.setModel(model);
        tbNotas.setAutoCreateRowSorter(true);
        ajustarColumns();
        txtTotalNFe.setValue(model.retNumTotal());
        //txtTotalValNFe.setValue(model.retValorTotal());
    }

    private void isContingencia(List<NFe> list) {
        boolean cont = false;
        for (NFe nfe : list) {
            if (nfe.getStatus() != null && nfe.getStatus().equalsIgnoreCase("contingencia")) {
                cont = true;
            }
        }
        if (cont) {
            Service.message("Notas em Contingência", "Existem notas em contingencia pendentes de envio. Favor envia-las o quanto antes para evitar perda de prazo!", 1);
        }
    }

    private void ajustarColumns() {
        TableColumnModel colModel = tbNotas.getColumnModel();

        //checkbox
        colModel.getColumn(0).setPreferredWidth(22);
        colModel.getColumn(0).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(0).setCellRenderer(tbNotas.getDefaultRenderer(Boolean.class));
        colModel.getColumn(0).setResizable(false);
        colModel.getColumn(0).setCellEditor(tbNotas.getDefaultEditor(Boolean.class));

        //icone
        colModel.getColumn(1).setPreferredWidth(20);
        colModel.getColumn(1).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(1).setResizable(false);

        //codigo empresa
        colModel.getColumn(2).setPreferredWidth(50);
        colModel.getColumn(2).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(2).setResizable(false);

        //numero da nota
        colModel.getColumn(3).setPreferredWidth(50);
        colModel.getColumn(3).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(3).setResizable(false);

        //série da nota
        colModel.getColumn(4).setPreferredWidth(50);
        colModel.getColumn(4).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(4).setResizable(false);

        //data de inclusão
        colModel.getColumn(5).setPreferredWidth(95);
        colModel.getColumn(5).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(5).setResizable(false);

        //data de processamento
        colModel.getColumn(6).setPreferredWidth(95);
        colModel.getColumn(6).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(6).setResizable(false);

        //CPF/CNPJ
        colModel.getColumn(7).setPreferredWidth(120);
        colModel.getColumn(7).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(7).setResizable(false);

        //Inscrição Estadual
        colModel.getColumn(8).setPreferredWidth(100);
        colModel.getColumn(8).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(8).setResizable(false);

        //Razão Social
        colModel.getColumn(9).setPreferredWidth(325);
        colModel.getColumn(9).setCellRenderer(alinhar(Align.LEFT));
        colModel.getColumn(9).setResizable(false);

        //Municipio
        colModel.getColumn(10).setPreferredWidth(150);
        colModel.getColumn(10).setCellRenderer(alinhar(Align.LEFT));
        colModel.getColumn(10).setResizable(false);

        //Mensagem Retorno
        colModel.getColumn(11).setPreferredWidth(250);
        colModel.getColumn(11).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(11).setResizable(false);

        //Valor
        colModel.getColumn(12).setPreferredWidth(90);
        colModel.getColumn(12).setCellRenderer(alinhar(Align.RIGHT));
        colModel.getColumn(12).setResizable(false);
    }

    private DefaultTableCellRenderer alinhar(Align align) {
        DefaultTableCellRenderer alinha = new DefaultTableCellRenderer();
        if (Align.LEFT == align) {
            alinha.setHorizontalAlignment(SwingConstants.LEFT);
        } else if (Align.CENTER == align) {
            alinha.setHorizontalAlignment(SwingConstants.CENTER);
        } else if (Align.RIGHT == align) {
            alinha.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        return alinha;
    }

    private List<NFe> listarSelected() {
        List<NFe> list = new ArrayList<>();

        for (NFe nfe : listaNFeTable()) {
            if (nfe.isCheck()) {
                list.add(nfe);
            }
        }
        return list;
    }

    public void atualizaTab() {
        limpaCampos();
        inicializaTable();
    }

    @SuppressWarnings("unchecked")
    public void preencheCmbEmpresa() {
        List<Empresa> listaEmpresa = new EmpresaDAO().listaTodos();
        cmbEmpresa.removeAllItems();
        cmbEmpresa.addItem("Todas");
        for (Empresa empresa : listaEmpresa) {
            cmbEmpresa.addItem(empresa);
            cmbEmpresa.setSelectedItem(empresa);
            emp = empresa;
        }
    }

    public boolean valida() {
        if (isNull(txtNFeInicial) && !isNull(txtNFeFinal)) {
            Service.message("Campo não preenchido.", "Para fazer uma consulta por intervalo de nota, favor preencher tamb\u00e9m o <br>campo de numero final.", ERRO);
            txtNFeFinal.requestFocus();
            return false;
        }

        if (!isNull(txtDtInicial) && !Service.isDate(txtDtInicial.getText())) {
            Service.message("Campo preenchido incorretamente", "O campo de data deve estar no formato dd/mm/aaaa.", ERRO);
            txtDtInicial.requestFocus();
            return false;
        }
        if (!isNull(txtDtFinal) && !Service.isDate(txtDtFinal.getText())) {
            Service.message("Campo preenchido incorretamente", "O campo de data deve estar no formato dd/mm/aaaa.", ERRO);
            txtDtFinal.requestFocus();
            return false;
        }

        if (isNull(txtDtInicial)) {
            if (!isNull(txtDtFinal)) {
                Service.message("Campo não preenchido", "Para fazer uma consulta por intevalo de emiss\u00e3o de nota, favor preencher o <br>campo de data inicial ou data inicial e final.", ERRO);
                txtDtInicial.requestFocus();
                return false;
            }
        }
        return true;
    }

    /**
     * Método que testa se o campo passado por parametro é vazio ou nulo,
     * retornando true para vazio ou null, e false para o contrario.
     *
     * @param campo
     * @return
     */
    public boolean isNull(JFormattedTextField campo) {
        if (campo == null) {
            return true;
        }
        if (campo.getText().trim().equals(maskData)) {
            return true;
        }
        return campo.getText().trim().isEmpty();
    }

    public List<NFe> listaNFeTable() {
        List<NFe> list = new ArrayList<>();
        for (int i = 0; i < tbNotas.getRowCount(); i++) {
            list.add(model.getNFe(i));
        }
        return list;
    }

    private void validaExit() {
        List<NFe> emit = new ArrayList<>();
        List<NFe> cancel = new ArrayList<>();
        for (NFe nfe : filtroNFe(new NFeDAO().retrieveInicial(emp), false)) {
            if (nfe.getSituacao().equalsIgnoreCase("pendenteenvio")) {
                emit.add(nfe);
            } else if (nfe.getSituacao().equalsIgnoreCase("pendentecancel")) {
                cancel.add(nfe);
            }
        }

        if (!emit.isEmpty() || !cancel.isEmpty()) {
            if (!emit.isEmpty() && !cancel.isEmpty()) {
                exit(MessageFormat.format("H\u00e1 {0} nota(s) a emitir.\nH\u00e1 {1} nota(s) a cancelar.\n\n", emit.size(), cancel.size()));
            } else if (!emit.isEmpty()) {
                exit(MessageFormat.format("H\u00e1 {0} nota(s) a emitir.\n\n", emit.size()));
            } else {
                exit(MessageFormat.format("H\u00e1 {0} nota(s) a cancelar.\n\n", cancel.size()));
            }
        } else {
            exit("");
        }
    }

    private void exit(String msg) {
        Object[] options = {"   Sair   ", "  Voltar  "};
        if (JOptionPane.showOptionDialog(null, MessageFormat.format("{0}Tem certeza que deseja sair da aplica\u00e7\u00e3o?", msg), "Sair", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]) == 0) {
            ps.close();
            System.exit(0);
        }
    }

    public void inicializaTelaPrincipal() {
        validaParamInicial();
        inicializaTable();
        definicoes();
    }

    @SuppressWarnings("unchecked")
    private void imprimeDanfe() {
        if (new File("logo.jpg").exists()) {
            Danfe dnf = new Danfe(this, null, true);
            dnf.setVisible(true);
            for (NFe nfe : this.listarSelected()) {
                if (nfe.getSituacao().equalsIgnoreCase("enviado") || nfe.getStatus().equalsIgnoreCase("Contingencia")) {
                    try (Connection cnx = DBConection.getConnection()) {
                        Map param = new HashMap();
                        DuplicataDAO duplDAO = new DuplicataDAO();
                        List<Duplicata> lista = duplDAO.retrieve(nfe.getNrNota(), nfe.getCodEmpresa().longValue(), nfe.getSerie());
                        int i = 0;
                        for (Duplicata duplicata : lista) {
                            i++;
                            param.put(MessageFormat.format("dupl0{0}", i), duplicata.getNrDup());
                            param.put(MessageFormat.format("venc0{0}", i), duplicata.getDtVenc());
                            param.put(MessageFormat.format("valor0{0}", i), duplicata.getVlrDup());
                        }
                        if (lista == null || lista.isEmpty()) {
                            param.put("dupl01", "A VISTA");
                        }
                        InputStream inSub;
                        InputStream in;
                        if (emp.isPaisagem()) {
                            if (Select.getTipo(nfe) == 2) {
                                in = NotaFiscalEletronica.class.getResourceAsStream("/br/com/nfe/danfe/layoutDanfeLand.jasper");
                                inSub = NotaFiscalEletronica.class.getResourceAsStream("/br/com/nfe/danfe/layoutDanfeLandSubMed.jasper");
                            } else {
                                in = NotaFiscalEletronica.class.getResourceAsStream("/br/com/nfe/danfe/layoutDanfeLand.jasper");
                                inSub = NotaFiscalEletronica.class.getResourceAsStream("/br/com/nfe/danfe/layoutDanfeLandSub.jasper");
                            }
                        } else {
                            if (Select.getTipo(nfe) == 2) {
                                in = NotaFiscalEletronica.class.getResourceAsStream("/br/com/nfe/danfe/layoutDanfe.jasper");
                                inSub = NotaFiscalEletronica.class.getResourceAsStream("/br/com/nfe/danfe/layoutDanfeSubMed.jasper");
                            } else {
                                in = NotaFiscalEletronica.class.getResourceAsStream("/br/com/nfe/danfe/layoutDanfe.jasper");
                                inSub = NotaFiscalEletronica.class.getResourceAsStream("/br/com/nfe/danfe/layoutDanfeSub.jasper");
                            }
                        }
                        param.put("NNF", nfe.getNrNota());
                        param.put("CODEMP", nfe.getCodEmpresa().longValue());
                        param.put("chave", new NFE001DAO().retrieve(nfe.getNrNota(), nfe.getCodEmpresa().longValue(), nfe.getSerie()).getChAcesso());
                        param.put("SRNOTA", nfe.getSerie());
                        param.put("SUBREPORT_DIR", inSub);
                        param.put("DIR_LOGO", new File("logo.jpg").getPath());
                        if (nfe.getStatus().equalsIgnoreCase("Contingencia")) {
                            param.put("MSG", "DANFE em Conting\u00eancia\nImpresso em Decorr\u00eancia de Problemas T\u00e9cnicos");
                        }

                        JasperPrint jasperPrint = JasperFillManager.fillReport(in, param, cnx);
                        if (getDir().equalsIgnoreCase("view")) {
                            //java.awt.Desktop.getDesktop().open(new File("arquivo"));
                            JasperViewer.viewReport(jasperPrint, false);
                        } else if (getDir().equalsIgnoreCase("print")) {
                            JasperPrintManager.printReport(jasperPrint, true);
                        } else {
                            String outFileName;
                            if (getDir().endsWith(File.separator)) {
                                new File(MessageFormat.format("{0}danfe {1}", getDir(), new SimpleDateFormat("dd-MM-yyyy").format(new Date()))).mkdir();
                                outFileName = MessageFormat.format("{0}danfe {1}{2}.pdf", getDir(), new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + File.separator, nfe.getChaveAcesso());
                            } else {
                                new File(MessageFormat.format("{0}danfe {1}", getDir() + File.separator, new SimpleDateFormat("dd-MM-yyyy").format(new Date()))).mkdir();
                                outFileName = MessageFormat.format("{0}danfe {1}{2}.pdf", getDir() + File.separator, new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + File.separator, nfe.getChaveAcesso());
                            }
                            // Cria um PDF exporter
                            JRExporter exporter = new JRPdfExporter();

                            // Configura o arquivo de saída e o arquivo JasperPrint
                            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
                            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

                            // Exporta o PDF
                            exporter.exportReport();
                        }

                    } catch (SQLException | JRException ex) {
                        LOG.log(Level.SEVERE, ex.getMessage(), ex);
                    }
                } else {
                    if (getDir().equalsIgnoreCase("view")) {
                        Service.message("Nota não enviada!", "Para emitir a DANFE é necessário emitir a nota previamente.", INFO);
                    } else if (getDir().equalsIgnoreCase("print")) {
                        Service.message("Nota não impressa", "Verifique se a impressora está ligada e conectada ao computador.", INFO);
                    }
                }
            }
            if (getDir().equalsIgnoreCase("print")) {
                Service.message("Arquivos enviados.", "Os arquivos foram enviados para a impressora.", 1);
            } else if (!getDir().equalsIgnoreCase("view")) {
                Service.message("Arquivos salvos.", "Os arquivos foram salvos com sucesso.", 1);
            }
        } else {
            Service.message("ERRO", "Logo não encontrado!", 0);
        }
    }

    @SuppressWarnings("unchecked")
    private void actConsultar() {
        if (valida()) {
            Map listConsulta = new HashMap();
            listConsulta.put("nfeInicial", txtNFeInicial.getText());
            listConsulta.put("nfeFinal", txtNFeFinal.getText());
            listConsulta.put("dataInicial", txtDtInicial.getText());
            listConsulta.put("dataFinal", txtDtFinal.getText());
            listConsulta.put("empresa", (cmbEmpresa.getSelectedItem() != "Todas" ? ((Empresa) cmbEmpresa.getSelectedItem()).getCod() : "Todas"));
            listConsulta.put("serie", (cmbEmpresa.getSelectedItem() != "Todas" ? ((Empresa) cmbEmpresa.getSelectedItem()).getSerie() : "Todas"));
            listConsulta.put("situacao", cmbSituacao.getSelectedItem());
            listConsulta.put("tipo", "saida");

            List<NFe> lista = new NFeDAO().retrieveConsulta(listConsulta);
            List<NFe> listaNFe = new ArrayList<>();
            List<Empresa> listaEmp = new ArrayList<>();

            if (cmbEmpresa.getSelectedItem() != "Todas") {
                listaEmp.add(((Empresa) cmbEmpresa.getSelectedItem()));
            } else {
                listaEmp = new EmpresaDAO().listaTodos();
            }

            for (NFe nfe : lista) {
                for (Empresa empresa : listaEmp) {
                    if (nfe.getCodEmpresa() == empresa.getCod().intValue()) {
                        listaNFe.add(nfe);
                    }
                }
            }

            listaNFe = filtroNFe(listaNFe, false);
            lista.clear();
            if (cmbSituacao.getSelectedItem().toString().equalsIgnoreCase("emitida")) {
                for (NFe nfe : listaNFe) {
                    if (!nfe.getAcao().equalsIgnoreCase("C")) {
                        lista.add(nfe);
                    }
                }
            } else {
                lista.addAll(listaNFe);
            }
            Collections.sort(lista, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    NFe nota1 = (NFe) o1;
                    NFe nota2 = (NFe) o2;
                    return nota2.compareTo(nota1);
                }
            });

            //cria o modelo de NFe da tabela
            model = new NFeTableModel(lista);

            //atribui o modelo à tabela
            tbNotas.setModel(model);
            tbNotas.setAutoCreateRowSorter(true);
            ajustarColumns();
        }

        txtTotalNFe.setValue(model.retNumTotal());
        //txtTotalValNFe.setValue(model.retValorTotal());
    }

    public void enviaConting(List<NFe> env, List<NFe> canc) {
        for (NFe nfe : env) {
            if (!nfe.getStatus().equalsIgnoreCase("Contingencia")) {
                InfNFe infNfe = null;
                InfNFeDAO dao = new InfNFeDAO();
                dao.gerarInfNFe(nfe.getNrNota(), infNfe, emp.getCod(), emp.getSerie());
                NFeContingencia.conting(nfe, emp);
            }
        }
        for (NFe nfe : canc) {
            if (!nfe.getStatus().equalsIgnoreCase("Contingencia")) {
                NFeContingencia.conting(nfe, emp);
            }
        }
    }

    public void consultaNfe(List<NFe> notas) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
        for (NFe nfe : notas) {
            try {
                //Salvar xml assinado e retorno;
                NFeRetRecepcao.procNFe(nfe, emp);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void consultaEvento(List<NFe> notas) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
        for (NFe nfe : notas) {
            try {
                //Salvar xml assinado e retorno;
                NFeConsultaNFe.consultaChave(emp, nfe, false);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void cancelaNfe(List<NFe> notas, List<NFe> listaP) {
        for (NFe nfe : notas) {
            try {
                //Salvar xml assinado e retorno;
                System.out.println(MessageFormat.format("Chave: {0}", nfe.getChaveAcesso()));
                InfEvtDAO dao = new InfEvtDAO();
                InfEvtFill fill = new InfEvtFill();
                EventoNFe evt = new EventoNFe();
                String xml = evt.eventoNFe(fill.fillEvtNfe(dao.retrieve(nfe, emp, "Canc", "")), emp, nfe);
                NFeEventoCancelamento.cancela(xml, nfe, emp);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void enviaNfe(List<NFe> notas, List<NFe> notasProcessamento) {
        try {
            InfNfeFill fill = new InfNfeFill();
            InfNFe infNfe = null;
            InfNFeDAO dao = new InfNFeDAO();
            for (NFe nfe : notas) {
                EnvioNFe envio = new EnvioNFe();
                infNfe = dao.gerarInfNFe(nfe.getNrNota(), infNfe, emp.getCod(), emp.getSerie());
                if (!infNfe.getDet().isEmpty()) {
                    if (!infNfe.getIde().getNotaRef().isEmpty()) {
                        if (Service.isNumeric(infNfe.getInfAdic().getInfAdFisco() != null ? infNfe.getInfAdic().getInfAdFisco().substring(0, 6) : "A")) {
                            Long nrNota = Long.valueOf(infNfe.getInfAdic().getInfAdFisco().substring(0, 6));
                            NFe n = model.getNFe(nrNota);
                            NotaReferente nRef = new NotaReferente();
                            nRef.setRefNFe(n.getChaveAcesso());

                            NotaReferenciada notaRef = new NotaReferenciada();
                            notaRef.setAamm(new SimpleDateFormat("yyMM").format(n.getDtProc()));
                            notaRef.setCnpj(infNfe.getEmit().getCnpj());
                            notaRef.setCodUFEmi(emp.getCodUF());
                            notaRef.setModDoc(infNfe.getIde().getCodMod());
                            notaRef.setNrNf(nrNota.toString());
                            notaRef.setSerieDoc(emp.getSerie());
                            nRef.setRefNF(notaRef);
                            infNfe.getIde().getNotaRef().add(nRef);
                        }
                    }
                    String xml = envio.enviarNFe(fill.fillInfeNfe(infNfe), emp);
                    //Salvar xml assinado e retorno;
                    NFeRecepcao.envia(xml, nfe, notasProcessamento, emp);
                } else {
                    Service.message("NFe Sem Produto", "Para emitir uma NFe, a mesma deve conter ao menos um produto!", 0);
                }
            }
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void validaParamInicial() {
        Boolean param = false;
        BancoDados banco = Configuracao.getConfig();
        if (banco.getNomeBanco() != null) {
            if (banco.getNomeBanco().equals(Banco.POSTGRES.name())) {
                param = banco.getSenha() != null && banco.getUsuario() != null && banco.getNomeBase() != null;
            } else if (banco.getNomeBanco().equals(Banco.SQLSERVER.name())) {
                param = banco.getSenha() != null && banco.getUsuario() != null && banco.getIpBanco() != null && banco.getPortaBanco() != null;
            } else {
                Service.message("PARÂMETRO INVÁLIDO", MessageFormat.format("Nome do banco inv\u00e1lido!\n{0}", banco.getNomeBanco()), ERRO);
                System.exit(0);
            }
            if (param) {
                try {
                    Connection cnx = DBConection.getConnection();
                    if (cnx != null) {
                        cnx.close();
                    } else {
                        Service.message("FALHA DE CONEXÃO", "N\u00e3o foi poss\u00edvel conectar ao banco de dados, verifique o arquivo de configura\u00e7\u00e3o e tente novamente!",
                                ERRO);
                        System.exit(0);
                    }
                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, ex.getMessage(), ex);
                }
            } else {
                Service.message("PARAMETROS BANCO DE DADOS", "Você deve preencher os parâmetros iniciais antes de iniciar o sistema!", ERRO);
                ConfiguracaoInicial config = new ConfiguracaoInicial(this, true, false);
                config.setVisible(true);
                Service.mkDirs();
            }
        } else {
            Service.message("PARAMETROS BANCO DE DADOS", "Você deve preencher os parâmetros iniciais antes de iniciar o sistema!", ERRO);
            ConfiguracaoInicial config = new ConfiguracaoInicial(this, true, false);
            config.setVisible(true);
            Service.mkDirs();
        }

        licenca = DataLi.dataLi();
        certificado = validaCert();
        if (!licenca) {
            block("LICENÇA EXPIRADA");
            Service.message("AVISO", "Licença expirada, entre em contato com suporte!", 1);
        } else if (!certificado) {
            block(certErro);
            mnImpNfe.setEnabled(true);
        } else {
            unlock();
        }
    }

    private void limpaCampos() {
        txtNFeInicial.setText("");
        txtNFeFinal.setText("");
        txtDtInicial.setText("");
        txtDtFinal.setText("");
        cbTodos.setSelected(false);
    }

    private void blockAll() {
        btClean.setEnabled(false);
        btConsultar.setEnabled(false);
        btDanfe.setEnabled(false);
        btEnviar.setEnabled(false);
        btAtt.setEnabled(false);
        btEnviaEmail.setEnabled(false);

        txtDtFinal.setEnabled(false);
        txtDtInicial.setEnabled(false);
        txtLicenca.setEnabled(false);
        txtNFeFinal.setEnabled(false);
        txtNFeInicial.setEnabled(false);
        txtTotalNFe.setEnabled(false);
        //txtTotalValNFe.setEnabled(false);

        mnCad.setEnabled(false);
        mnProc.setEnabled(false);
        mnExit.setEnabled(false);

        tbNotas.setEnabled(false);
    }

    private void enableAll() {
        btClean.setEnabled(true);
        btConsultar.setEnabled(true);
        btDanfe.setEnabled(true);
        btEnviar.setEnabled(true);
        btAtt.setEnabled(true);
        btEnviaEmail.setEnabled(true);

        txtDtFinal.setEnabled(true);
        txtDtInicial.setEnabled(true);
        txtLicenca.setEnabled(true);
        txtNFeFinal.setEnabled(true);
        txtNFeInicial.setEnabled(true);
        txtTotalNFe.setEnabled(true);
        //txtTotalValNFe.setEnabled(true);

        mnCad.setEnabled(true);
        mnProc.setEnabled(true);
        mnExit.setEnabled(true);

        tbNotas.setEnabled(true);
    }

    public enum Align {

        LEFT, CENTER, RIGHT;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        spGrid = new javax.swing.JScrollPane();
        tbNotas = new javax.swing.JTable();
        lbRodape = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btEnviar = new javax.swing.JButton();
        btDanfe = new javax.swing.JButton();
        btResumo = new javax.swing.JButton();
        btEnviaEmail = new javax.swing.JButton();
        btDownloadXML = new javax.swing.JButton();
        btIcone = new javax.swing.JButton();
        lbConsulta = new javax.swing.JPanel();
        lbDtIncl = new javax.swing.JPanel();
        lbData = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDtInicial = new javax.swing.JFormattedTextField();
        txtDtFinal = new javax.swing.JFormattedTextField();
        lbCmbEmp = new javax.swing.JPanel();
        lbEmpresa = new javax.swing.JLabel();
        cmbEmpresa = new javax.swing.JComboBox();
        btConsultar = new javax.swing.JButton();
        btClean = new javax.swing.JButton();
        lbNFe = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbNfe = new javax.swing.JLabel();
        txtNFeInicial = new javax.swing.JFormattedTextField();
        txtNFeFinal = new javax.swing.JFormattedTextField();
        lbCombo = new javax.swing.JPanel();
        cmbSituacao = new javax.swing.JComboBox();
        lbSituacao = new javax.swing.JLabel();
        cbTodos = new javax.swing.JCheckBox();
        btHelp = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtTotalNFe = new javax.swing.JFormattedTextField();
        btAtt = new javax.swing.JButton();
        txtLicenca = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        mbMenus = new javax.swing.JMenuBar();
        mnCad = new javax.swing.JMenu();
        mnEmp = new javax.swing.JMenuItem();
        mnParam = new javax.swing.JMenuItem();
        mnProc = new javax.swing.JMenu();
        mnConsCad = new javax.swing.JMenuItem();
        mnInut = new javax.swing.JMenuItem();
        mnConsCert = new javax.swing.JMenuItem();
        mnExport = new javax.swing.JMenuItem();
        mnImpNfe = new javax.swing.JMenuItem();
        mnConsEntrada = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        cbConting = new javax.swing.JCheckBoxMenuItem();
        mnCompat = new javax.swing.JMenu();
        mnExit = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");
        jMenuItem1.setName("jMenuItem1"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Emissor de Nota Fiscal Eletrônica");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1043, 614));
        setPreferredSize(new java.awt.Dimension(1043, 614));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        spGrid.setName("spGrid"); // NOI18N

        tbNotas.setAutoCreateRowSorter(true);
        tbNotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbNotas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbNotas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbNotas.setMaximumSize(new java.awt.Dimension(700, 64));
        tbNotas.setName("tbNotas"); // NOI18N
        tbNotas.getTableHeader().setReorderingAllowed(false);
        tbNotas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNotasMouseClicked(evt);
            }
        });
        spGrid.setViewportView(tbNotas);

        lbRodape.setName("lbRodape"); // NOI18N

        jPanel3.setName("jPanel3"); // NOI18N

        btEnviar.setText("Enviar");
        btEnviar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btEnviar.setName("btEnviar"); // NOI18N
        btEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEnviarActionPerformed(evt);
            }
        });

        btDanfe.setText("Emitir DANFE");
        btDanfe.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btDanfe.setName("btDanfe"); // NOI18N
        btDanfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDanfeActionPerformed(evt);
            }
        });

        btResumo.setText("Emitir Resumo");
        btResumo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btResumo.setName("btResumo"); // NOI18N
        btResumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btResumoActionPerformed(evt);
            }
        });

        btEnviaEmail.setText("Enviar E-mail");
        btEnviaEmail.setName("btEnviaEmail"); // NOI18N
        btEnviaEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEnviaEmailActionPerformed(evt);
            }
        });

        btDownloadXML.setText("Download XML");
        btDownloadXML.setName("btDownloadXML"); // NOI18N
        btDownloadXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDownloadXMLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btEnviar)
                .addGap(18, 18, 18)
                .addComponent(btDanfe)
                .addGap(18, 18, 18)
                .addComponent(btResumo)
                .addGap(18, 18, 18)
                .addComponent(btEnviaEmail)
                .addGap(18, 18, 18)
                .addComponent(btDownloadXML)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btDanfe, btEnviaEmail, btEnviar, btResumo});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btDanfe)
                    .addComponent(btResumo)
                    .addComponent(btEnviar)
                    .addComponent(btEnviaEmail)
                    .addComponent(btDownloadXML))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout lbRodapeLayout = new javax.swing.GroupLayout(lbRodape);
        lbRodape.setLayout(lbRodapeLayout);
        lbRodapeLayout.setHorizontalGroup(
            lbRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbRodapeLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        lbRodapeLayout.setVerticalGroup(
            lbRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btIcone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/nfe/view/img/logo.jpg"))); // NOI18N
        btIcone.setToolTipText("<html><center>\nAPH Consultoria & Sistemas Ltda.<br>\nSite: www.aphnet.com.br<br>\nTelefone: (53) 3027-6001\n</center></html>");
        btIcone.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btIcone.setContentAreaFilled(false);
        btIcone.setFocusable(false);
        btIcone.setName("btIcon"); // NOI18N
        btIcone.setSelected(true);

        lbConsulta.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbConsulta.setName("lbConsulta"); // NOI18N

        lbDtIncl.setName("lbDtIncl"); // NOI18N

        lbData.setText("Data de Inclusão:");
        lbData.setName("lbData"); // NOI18N

        jLabel6.setText("a");
        jLabel6.setName("jLabel6"); // NOI18N

        try {
            txtDtInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDtInicial.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDtInicial.setToolTipText("Data Inicial de Inclusão");
        txtDtInicial.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txtDtInicial.setName("txtDtInicial"); // NOI18N

        try {
            txtDtFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDtFinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDtFinal.setToolTipText("Data Final de Inclusão");
        txtDtFinal.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txtDtFinal.setName("txtDtFinal"); // NOI18N

        javax.swing.GroupLayout lbDtInclLayout = new javax.swing.GroupLayout(lbDtIncl);
        lbDtIncl.setLayout(lbDtInclLayout);
        lbDtInclLayout.setHorizontalGroup(
            lbDtInclLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbDtInclLayout.createSequentialGroup()
                .addGroup(lbDtInclLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lbDtInclLayout.createSequentialGroup()
                        .addComponent(txtDtInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDtFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
                    .addComponent(lbData))
                .addContainerGap())
        );
        lbDtInclLayout.setVerticalGroup(
            lbDtInclLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbDtInclLayout.createSequentialGroup()
                .addComponent(lbData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lbDtInclLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDtInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtDtFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        lbCmbEmp.setName("lbCmbEmp"); // NOI18N

        lbEmpresa.setText("Empresa:");
        lbEmpresa.setName("lbEmpresa"); // NOI18N

        cmbEmpresa.setMaximumSize(new java.awt.Dimension(23, 18));
        cmbEmpresa.setName("cmbEmpresa"); // NOI18N
        cmbEmpresa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbEmpresaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout lbCmbEmpLayout = new javax.swing.GroupLayout(lbCmbEmp);
        lbCmbEmp.setLayout(lbCmbEmpLayout);
        lbCmbEmpLayout.setHorizontalGroup(
            lbCmbEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbCmbEmpLayout.createSequentialGroup()
                .addGroup(lbCmbEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbEmpresa)
                    .addComponent(cmbEmpresa, 0, 208, Short.MAX_VALUE))
                .addContainerGap())
        );
        lbCmbEmpLayout.setVerticalGroup(
            lbCmbEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbCmbEmpLayout.createSequentialGroup()
                .addComponent(lbEmpresa)
                .addGap(5, 5, 5)
                .addComponent(cmbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        btConsultar.setText("Consultar NF-e");
        btConsultar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btConsultar.setName("btConsultar"); // NOI18N
        btConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConsultarActionPerformed(evt);
            }
        });

        btClean.setText("Limpar");
        btClean.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btClean.setName("btClean"); // NOI18N
        btClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCleanActionPerformed(evt);
            }
        });

        lbNFe.setName("lbNFe"); // NOI18N

        jLabel5.setText("a");
        jLabel5.setName("jLabel5"); // NOI18N

        lbNfe.setText("NF-e:");
        lbNfe.setName("lbNfe"); // NOI18N

        txtNFeInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtNFeInicial.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNFeInicial.setToolTipText("Numero Inicial de NFe");
        txtNFeInicial.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txtNFeInicial.setName("txtNFeInicial"); // NOI18N
        txtNFeInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNFeInicialKeyTyped(evt);
            }
        });

        txtNFeFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtNFeFinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNFeFinal.setToolTipText("Numero Final de NFe");
        txtNFeFinal.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txtNFeFinal.setName("txtNFeFinal"); // NOI18N
        txtNFeFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNFeFinalKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout lbNFeLayout = new javax.swing.GroupLayout(lbNFe);
        lbNFe.setLayout(lbNFeLayout);
        lbNFeLayout.setHorizontalGroup(
            lbNFeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbNFeLayout.createSequentialGroup()
                .addComponent(txtNFeInicial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNFeFinal))
            .addGroup(lbNFeLayout.createSequentialGroup()
                .addComponent(lbNfe, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 130, Short.MAX_VALUE))
        );
        lbNFeLayout.setVerticalGroup(
            lbNFeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbNFeLayout.createSequentialGroup()
                .addComponent(lbNfe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lbNFeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNFeInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNFeFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)))
        );

        lbCombo.setName("lbCombo"); // NOI18N

        cmbSituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todas", "Cancelada", "Emitida", "Pendente", "Rejeitada/Inutilizada" }));
        cmbSituacao.setName("cmbSituacao"); // NOI18N

        lbSituacao.setText("Situação:");
        lbSituacao.setName("lbSituacao"); // NOI18N

        javax.swing.GroupLayout lbComboLayout = new javax.swing.GroupLayout(lbCombo);
        lbCombo.setLayout(lbComboLayout);
        lbComboLayout.setHorizontalGroup(
            lbComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbComboLayout.createSequentialGroup()
                .addGroup(lbComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lbComboLayout.createSequentialGroup()
                        .addComponent(lbSituacao)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cmbSituacao, javax.swing.GroupLayout.Alignment.TRAILING, 0, 191, Short.MAX_VALUE))
                .addContainerGap())
        );
        lbComboLayout.setVerticalGroup(
            lbComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbComboLayout.createSequentialGroup()
                .addComponent(lbSituacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout lbConsultaLayout = new javax.swing.GroupLayout(lbConsulta);
        lbConsulta.setLayout(lbConsultaLayout);
        lbConsultaLayout.setHorizontalGroup(
            lbConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbConsultaLayout.createSequentialGroup()
                .addGroup(lbConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lbConsultaLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lbCmbEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(lbCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(lbDtIncl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lbNFe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lbConsultaLayout.createSequentialGroup()
                        .addGap(275, 275, 275)
                        .addComponent(btConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btClean, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        lbConsultaLayout.setVerticalGroup(
            lbConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbConsultaLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(lbConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbCmbEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDtIncl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbNFe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(lbConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btConsultar)
                    .addComponent(btClean))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        cbTodos.setText(" Marcar/Desmarcar todos.");
        cbTodos.setName("cbTodos"); // NOI18N
        cbTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTodosActionPerformed(evt);
            }
        });

        btHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/nfe/view/img/help.png"))); // NOI18N
        btHelp.setToolTipText("");
        btHelp.setBorderPainted(false);
        btHelp.setContentAreaFilled(false);
        btHelp.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/nfe/view/img/help.png"))); // NOI18N
        btHelp.setEnabled(false);
        btHelp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btHelp.setName("btHelp"); // NOI18N

        jLabel1.setText("Total de Notas:");
        jLabel1.setName("jLabel1"); // NOI18N

        txtTotalNFe.setEditable(false);
        txtTotalNFe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtTotalNFe.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalNFe.setToolTipText("Número todal de NFe na grid.");
        txtTotalNFe.setName("txtTotalNFe"); // NOI18N

        btAtt.setText("Atualizar");
        btAtt.setName("btAtt"); // NOI18N
        btAtt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAttActionPerformed(evt);
            }
        });

        txtLicenca.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtLicenca.setForeground(new java.awt.Color(204, 51, 0));
        txtLicenca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtLicenca.setToolTipText("");
        txtLicenca.setName("txtLicenca"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N

        mbMenus.setName("mbMenus"); // NOI18N

        mnCad.setText("Cadastros");
        mnCad.setName("mnCad"); // NOI18N

        mnEmp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, java.awt.event.InputEvent.CTRL_MASK));
        mnEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/nfe/view/img/emp.png"))); // NOI18N
        mnEmp.setText("Empresa");
        mnEmp.setName("mnEmp"); // NOI18N
        mnEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mnEmpMousePressed(evt);
            }
        });
        mnCad.add(mnEmp);

        mnParam.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.CTRL_MASK));
        mnParam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/nfe/view/img/param.png"))); // NOI18N
        mnParam.setText("Parametros Iniciais");
        mnParam.setName("mnParam"); // NOI18N
        mnParam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mnParamMousePressed(evt);
            }
        });
        mnCad.add(mnParam);

        mbMenus.add(mnCad);

        mnProc.setText("Processos");
        mnProc.setName("mnProc"); // NOI18N

        mnConsCad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, java.awt.event.InputEvent.CTRL_MASK));
        mnConsCad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/nfe/view/img/emp.png"))); // NOI18N
        mnConsCad.setText("Consulta Contribuinte Sefaz");
        mnConsCad.setName("mnConsCad"); // NOI18N
        mnConsCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnConsCadActionPerformed(evt);
            }
        });
        mnProc.add(mnConsCad);

        mnInut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, java.awt.event.InputEvent.CTRL_MASK));
        mnInut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/nfe/view/img/erro.gif"))); // NOI18N
        mnInut.setText("Inutilização");
        mnInut.setName("mnInut"); // NOI18N
        mnInut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                clickItemInut(evt);
            }
        });
        mnProc.add(mnInut);

        mnConsCert.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, java.awt.event.InputEvent.CTRL_MASK));
        mnConsCert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/nfe/view/img/cert.png"))); // NOI18N
        mnConsCert.setText("Consulta Certificado");
        mnConsCert.setName("mnConsCert"); // NOI18N
        mnConsCert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnConsCertActionPerformed(evt);
            }
        });
        mnProc.add(mnConsCert);

        mnExport.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, java.awt.event.InputEvent.CTRL_MASK));
        mnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/nfe/view/img/6058_16x16.png"))); // NOI18N
        mnExport.setText("Exportação de NFe");
        mnExport.setName("mnExport"); // NOI18N
        mnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnExportActionPerformed(evt);
            }
        });
        mnProc.add(mnExport);

        mnImpNfe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, java.awt.event.InputEvent.CTRL_MASK));
        mnImpNfe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/nfe/view/img/6131_16x16.png"))); // NOI18N
        mnImpNfe.setText("Importar NFe de entrada");
        mnImpNfe.setToolTipText("");
        mnImpNfe.setName("mnImpNfe"); // NOI18N
        mnImpNfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnImpNfeActionPerformed(evt);
            }
        });
        mnProc.add(mnImpNfe);

        mnConsEntrada.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, java.awt.event.InputEvent.CTRL_MASK));
        mnConsEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/nfe/view/img/emp.png"))); // NOI18N
        mnConsEntrada.setText("Consulta NFe de entrada");
        mnConsEntrada.setName("mnConsEntrada"); // NOI18N
        mnConsEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnConsEntradaActionPerformed(evt);
            }
        });
        mnProc.add(mnConsEntrada);

        mbMenus.add(mnProc);

        jMenu2.setText("Configurações");
        jMenu2.setName("jMenu2"); // NOI18N

        cbConting.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, java.awt.event.InputEvent.CTRL_MASK));
        cbConting.setText("Contingência");
        cbConting.setToolTipText("Entrar em modo de Contingência");
        cbConting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/nfe/view/img/offline.png"))); // NOI18N
        cbConting.setName("cbConting"); // NOI18N
        cbConting.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbContingItemStateChanged(evt);
            }
        });
        jMenu2.add(cbConting);

        mbMenus.add(jMenu2);

        mnCompat.setText("Compatibilidade");
        mnCompat.setName("mnCompat"); // NOI18N
        mnCompat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mnCompatMousePressed(evt);
            }
        });
        mbMenus.add(mnCompat);

        mnExit.setText("Sair");
        mnExit.setName("mnExit"); // NOI18N
        mnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mnExitMousePressed(evt);
            }
        });
        mbMenus.add(mnExit);

        setJMenuBar(mbMenus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spGrid)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cbTodos)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btAtt))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(334, 334, 334)
                                        .addComponent(txtLicenca, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbRodape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTotalNFe, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(btIcone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(lbConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btIcone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbTodos)
                        .addComponent(btAtt))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btHelp, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtLicenca, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spGrid, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtTotalNFe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbRodape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleDescription("");

        setSize(new java.awt.Dimension(1053, 644));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void cbTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTodosActionPerformed
        model.alteraCheckBox();
    }//GEN-LAST:event_cbTodosActionPerformed
    private void btConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConsultarActionPerformed
        actConsultar();
    }//GEN-LAST:event_btConsultarActionPerformed
    private void btEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEnviarActionPerformed
        if (emp != null) {
            cacerts = MessageFormat.format("nfe_cacerts_{0}", emp.getCod());
            blockAll();
            txtLicenca.setText("Processando, aguarde...");
            java.awt.EventQueue.invokeLater(new Runnable() {
                List<NFe> listaEnvio = model.listaPendenteEnvio();
                List<NFe> listaCancel = model.listaPendenteCancela();
                List<NFe> listaProc = model.listaProcessamento();
                List<NFe> listaEvt = model.listaEvento();

                @Override
                public void run() {
                    if (!listaEnvio.isEmpty() || !listaCancel.isEmpty() || !listaProc.isEmpty() || !listaEvt.isEmpty()) {
                        if (conting) {
                            enviaConting(listaEnvio, listaCancel);
                        } else {
                            if (NFeConsultaStatus.verificaStatus(emp) && certificado) {
                                //Processa Envio
                                if (!listaEnvio.isEmpty()) {
                                    enviaNfe(listaEnvio, listaProc);
                                }

                                if (!listaCancel.isEmpty()) {
                                    cancelaNfe(listaCancel, listaProc);
                                }

                                if (!listaProc.isEmpty()) {
                                    consultaNfe(listaProc);
                                }

                                if (!listaEvt.isEmpty()) {
                                    consultaEvento(listaEvt);
                                }
                            } else {
                                Service.message("Serviço Indisponível", "Servidor indispon\u00edvel ou certificado inv\u00e1lido.\n Para enviar favor ativar a conting\u00eancia. (Cadastros -> Conting\u00eancia)", INFO);
                            }
                        }
                    }
                    Service.message("Lote Processado", "Processamento de Notas Concluído.", INFO);
                    txtLicenca.setText("");
                    atualizaTab();
                    enableAll();
                }
            });

        } else {
            Service.message("Selecione uma empresa", "Para realizar o processo deve ser selecionada uma empresa.", INFO);
            cmbEmpresa.requestFocus();
        }
    }//GEN-LAST:event_btEnviarActionPerformed
    private void btCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCleanActionPerformed
        limpaCampos();
    }//GEN-LAST:event_btCleanActionPerformed
    private void btDanfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDanfeActionPerformed
        imprimeDanfe();
    }//GEN-LAST:event_btDanfeActionPerformed
    private void clickItemInut(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clickItemInut
        if (mnInut.isEnabled() && inut == null) {
            inut = new Inutilizacao(this, true);
            inut.setVisible(true);
            inut = null;
            inicializaTelaPrincipal();
        }
    }//GEN-LAST:event_clickItemInut
    private void mnEmpMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnEmpMousePressed
        if (empCad == null) {
            empCad = new br.com.nfe.view.EmpresaCad(this, true);
            empCad.setVisible(true);
            empCad = null;
            inicializaTelaPrincipal();
        }
    }//GEN-LAST:event_mnEmpMousePressed
    private void mnParamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnParamMousePressed
        if (conf == null) {
            conf = new ConfiguracaoInicial(this, true, true);
            conf.setVisible(true);
            conf = null;
            inicializaTelaPrincipal();
        }
    }//GEN-LAST:event_mnParamMousePressed
    private void mnExitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnExitMousePressed
        validaExit();
    }//GEN-LAST:event_mnExitMousePressed
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        validaExit();
    }//GEN-LAST:event_formWindowClosing
    private void tbNotasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNotasMouseClicked
        try {
            if (evt.getClickCount() == 2) {
                int selectedRow = (tbNotas.convertRowIndexToModel(tbNotas.getSelectedRow()));
                NFe nfe = model.getNFe(selectedRow);
                if (!nfe.getAcao().equalsIgnoreCase("X")) {
                    InfNFeDAO dao = new InfNFeDAO();
                    InfNFe infNfe = null;
                    infNfe = dao.gerarInfNFe(nfe.getNrNota(), infNfe, emp.getCod(), emp.getSerie());
                    Detalhe det = new Detalhe(this, true, infNfe, emp, nfe, this, true);
                    det.setVisible(true);
                } else {
                    Service.message("Inutilização", "NFe referente a inutilização, sem detalhes adicionais!", 1);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }//GEN-LAST:event_tbNotasMouseClicked
    private void mnConsCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnConsCadActionPerformed
        if (cad == null) {
            cad = new ConsCad(this, true, emp);
            cad.setVisible(true);
            cad = null;
        }
    }//GEN-LAST:event_mnConsCadActionPerformed
    private void mnConsCertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnConsCertActionPerformed
        if (info == null) {
            info = new InfoCert(this, true, emp);
            info.setVisible(true);
            info = null;
        }
    }//GEN-LAST:event_mnConsCertActionPerformed
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.requestFocus();
    }//GEN-LAST:event_formWindowOpened
    private void mnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnExportActionPerformed
        if (exp == null) {
            exp = new ExportNFe(this, true, emp);
            exp.setVisible(true);
            exp = null;
            inicializaTelaPrincipal();
        }
    }//GEN-LAST:event_mnExportActionPerformed

    private void btResumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btResumoActionPerformed
        emiteResumo();
    }//GEN-LAST:event_btResumoActionPerformed

    private void btAttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAttActionPerformed
        atualizaTab();
    }//GEN-LAST:event_btAttActionPerformed

    private void txtNFeInicialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNFeInicialKeyTyped
    }//GEN-LAST:event_txtNFeInicialKeyTyped

    private void txtNFeFinalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNFeFinalKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            actConsultar();
        }
    }//GEN-LAST:event_txtNFeFinalKeyTyped

    private void btEnviaEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEnviaEmailActionPerformed
        enviarEmailsPendentes();
    }//GEN-LAST:event_btEnviaEmailActionPerformed

    public void enviarEmailsPendentes() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            int enviadas = 0;

            @Override
            public void run() {
                boolean enviado = false;
                for (NFe nfe : thisNFe.listarSelected()) {
                    if (nfe.getSituacao().equalsIgnoreCase("enviado")) {
                        enviado = EnviarEmail.send(null, nfe, null, null, null, TipoServico.EMISSAO, nfe.getEmailDest());
                        enviadas++;
                    } else if (nfe.getSituacao().equalsIgnoreCase("cancelado")) {
                        enviado = EnviarEmail.send(null, nfe, null, null, null, TipoServico.CANCELAMENTO, nfe.getEmailDest());
                        enviadas++;
                    }
                }
                if (enviado) {
                    Service.message("Email Enviado", MessageFormat.format("{0} email enviados.", enviadas), INFO);
                }
                txtLicenca.setText("");
                atualizaTab();
                enableAll();
            }
        });
    }

    private void cbContingItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbContingItemStateChanged
        conting = cbConting.getState();
        System.out.println(conting);
        if (conting) {
            btEnviar.setEnabled(true);
        } else {
            if (!certificado) {
                btEnviar.setEnabled(false);
            }
        }
    }//GEN-LAST:event_cbContingItemStateChanged

    private void mnConsEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnConsEntradaActionPerformed
        if (consEntrada == null) {
            consEntrada = new ConsEntrada(this, true);
            consEntrada.setVisible(true);
            consEntrada = null;
            inicializaTelaPrincipal();
        }
    }//GEN-LAST:event_mnConsEntradaActionPerformed

    private void mnImpNfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnImpNfeActionPerformed
        if (impNFe == null) {
            impNFe = new ImportNotaEntrada(this, true, emp);
            impNFe.setVisible(true);
            impNFe = null;
            inicializaTelaPrincipal();
        }
    }//GEN-LAST:event_mnImpNfeActionPerformed

    private void cmbEmpresaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEmpresaItemStateChanged
        inicializaTelaPrincipal();
    }//GEN-LAST:event_cmbEmpresaItemStateChanged

    private void btDownloadXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDownloadXMLActionPerformed
        Service.message("Aviso", "Na janela a seguir, digite a chave da nota e o c\u00f3digo de verifica\u00e7\u00e3o.\n Assim que a nota for aberta, desca at\u00e9 o final da p\u00e1gina, clique em \nDownload do Documento e insira a senha do certificado.", 1);
        if (emp.getAmbiente().equals(Ambiente.PRODUCAO)) {
            Navegador n = new Navegador("http://www.nfe.fazenda.gov.br/portal/consulta.aspx?tipoConsulta=completa&tipoConteudo=XbSeqxE8pl8=");
        } else {
            Navegador n = new Navegador("http://hom.nfe.fazenda.gov.br/portal/consulta.aspx?tipoConsulta=completa&tipoConteudo=XbSeqxE8pl8=");
        }
    }//GEN-LAST:event_btDownloadXMLActionPerformed

    private void mnCompatMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnCompatMousePressed
        if (comp == null) {
            comp = new Compatibilidade(this, true);
            comp.setVisible(true);
            comp = null;
            inicializaTelaPrincipal();
        }
    }//GEN-LAST:event_mnCompatMousePressed

    @SuppressWarnings("unchecked")
    private void emiteResumo() {
        Resumo r = new Resumo(this, this, true);
        r.setVisible(true);
        Map param = new HashMap();
        int emit = 0, canc = 0;
        BigDecimal emitV = new BigDecimal(BigInteger.ZERO), cancV = new BigDecimal(BigInteger.ZERO);
        param.put("dataInicial", getDtInicial());
        param.put("dataFinal", getDtFinal());
        param.put("nfeInicial", "");
        param.put("nfeFinal", "");
        param.put("situacao", "rel");
        param.put("empresa", (cmbEmpresa.getSelectedItem() != "Todas" ? ((Empresa) cmbEmpresa.getSelectedItem()).getCod() : "Todas"));
        param.put("serie", (cmbEmpresa.getSelectedItem() != "Todas" ? ((Empresa) cmbEmpresa.getSelectedItem()).getSerie() : "Todas"));
        List<NFe> listaNF = new NFeDAO().retrieveConsulta(param);
        for (NFe nfe : listaNF) {
            if (nfe.getSituacao().equalsIgnoreCase("enviado")) {
                emit++;
                emitV = emitV.add(nfe.getValor());
            } else if (nfe.getSituacao().equalsIgnoreCase("cancelado")) {
                canc++;
                cancV = cancV.add(nfe.getValor());
            }
        }
        param.put("ttEmit", emit);
        param.put("ttCanc", canc);
        param.put("ttEmitV", emitV);
        param.put("ttCancV", cancV);

        InputStream in = NotaFiscalEletronica.class.getResourceAsStream("/br/com/nfe/relatorio/relNFe.jasper");
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(in, param);
        } catch (JRException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }


        JasperViewer.viewReport(jasperPrint, false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (txtNFeFinal.isFocusOwner() || txtNFeInicial.isFocusOwner()) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                actConsultar();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            mnCompatMousePressed(null);
        } else if (e.getKeyCode() == KeyEvent.VK_F3) {
            mnEmpMousePressed(null);
        } else if (e.getKeyCode() == KeyEvent.VK_F4) {
            mnParamMousePressed(null);
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            atualizaTab();
        } else if (e.getKeyCode() == KeyEvent.VK_F6) {
            mnConsCadActionPerformed(null);
        } else if (e.getKeyCode() == KeyEvent.VK_F7) {
            clickItemInut(null);
        } else if (e.getKeyCode() == KeyEvent.VK_F8) {
            mnConsCertActionPerformed(null);
        } else if (e.getKeyCode() == KeyEvent.VK_F9) {
            mnExportActionPerformed(null);
        } else if (e.getKeyCode() == KeyEvent.VK_F10) {
            mnImpNfeActionPerformed(null);
        } else if (e.getKeyCode() == KeyEvent.VK_F12) {
            conting = !conting;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            mnExitMousePressed(null);
        }
    }

    public static void main(String args[]) {

//        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//            if ("Metal".equalsIgnoreCase(info.getName())) {
//                try {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//                    LOG.log(Level.SEVERE, null, ex);
//                }
//            }
//        }
        try {
            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                NotaFiscalEletronica p = new NotaFiscalEletronica();
                p.setLocationRelativeTo(null);
                p.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAtt;
    private javax.swing.JButton btClean;
    private javax.swing.JButton btConsultar;
    private javax.swing.JButton btDanfe;
    private javax.swing.JButton btDownloadXML;
    private javax.swing.JButton btEnviaEmail;
    private javax.swing.JButton btEnviar;
    private javax.swing.JButton btHelp;
    private javax.swing.JButton btIcone;
    private javax.swing.JButton btResumo;
    private javax.swing.JCheckBoxMenuItem cbConting;
    private javax.swing.JCheckBox cbTodos;
    private javax.swing.JComboBox cmbEmpresa;
    private javax.swing.JComboBox cmbSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel lbCmbEmp;
    private javax.swing.JPanel lbCombo;
    private javax.swing.JPanel lbConsulta;
    private javax.swing.JLabel lbData;
    private javax.swing.JPanel lbDtIncl;
    private javax.swing.JLabel lbEmpresa;
    private javax.swing.JPanel lbNFe;
    private javax.swing.JLabel lbNfe;
    private javax.swing.JPanel lbRodape;
    private javax.swing.JLabel lbSituacao;
    private javax.swing.JMenuBar mbMenus;
    private javax.swing.JMenu mnCad;
    private javax.swing.JMenu mnCompat;
    public javax.swing.JMenuItem mnConsCad;
    private javax.swing.JMenuItem mnConsCert;
    private javax.swing.JMenuItem mnConsEntrada;
    private javax.swing.JMenuItem mnEmp;
    private javax.swing.JMenu mnExit;
    private javax.swing.JMenuItem mnExport;
    private javax.swing.JMenuItem mnImpNfe;
    private javax.swing.JMenuItem mnInut;
    private javax.swing.JMenuItem mnParam;
    private javax.swing.JMenu mnProc;
    private javax.swing.JScrollPane spGrid;
    private javax.swing.JTable tbNotas;
    private javax.swing.JFormattedTextField txtDtFinal;
    private javax.swing.JFormattedTextField txtDtInicial;
    private javax.swing.JLabel txtLicenca;
    private javax.swing.JFormattedTextField txtNFeFinal;
    private javax.swing.JFormattedTextField txtNFeInicial;
    private javax.swing.JFormattedTextField txtTotalNFe;
    // End of variables declaration//GEN-END:variables
}
