
package br.ufla.dcc.ppoo.gui;


import br.ufla.dcc.ppoo.i18n.I18N;
import br.ufla.dcc.ppoo.imagens.GerenciadorDeImagens;
import br.ufla.dcc.ppoo.modelo.Usuario;
import br.ufla.dcc.ppoo.servicos.GerenciadorUsuarios;
import br.ufla.dcc.ppoo.servicos.GerenciadorPontotur;
import br.ufla.dcc.ppoo.seguranca.SessaoUsuario;
import br.ufla.dcc.ppoo.util.Utilidades;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class TelaCadastroPonto {
    
    private final TelaPrincipal telaPrincipal;
    private final SessaoUsuario sessao;
    private JDialog janela;
    private GridBagLayout layout;
    private GridBagConstraints gbc;
    private JLabel lbNome;
    private JLabel lbDesc;
    private JLabel lbTags;
    private JTextField txtNome;
    private JTextField txtDesc;
    private JTextField txtTags;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private final GerenciadorPontotur gerenciador;
    private final GerenciadorUsuarios gerenciadorUsuario;

    
    public TelaCadastroPonto(TelaPrincipal telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
        sessao = SessaoUsuario.obterInstancia();
        gerenciador = new GerenciadorPontotur();
        gerenciadorUsuario = new GerenciadorUsuarios();
    }
    
    public void inicializar() {
        construirTela();
        configurarAcoesBotoes();
        exibirTela();
    }
    
        private void adicionarComponente(Component c,
            int anchor, int fill, int linha,
            int coluna, int largura, int altura) {
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.gridy = linha;
        gbc.gridx = coluna;
        gbc.gridwidth = largura;
        gbc.gridheight = altura;
        gbc.insets = new Insets(5, 5, 5, 5);
        layout.setConstraints(c, gbc);
        janela.add(c);
    }

    /**
     * Adiciona um componente à tela.
     */
    private void adicionarComponentes() {
        lbNome = new JLabel(I18N.obterRotuloPontoturNome());
        adicionarComponente(lbNome,
                GridBagConstraints.LINE_END,
                GridBagConstraints.NONE,
                0, 0, 1, 1);

        lbDesc = new JLabel(I18N.obterRotuloPontoturDesc());
        adicionarComponente(lbDesc,
                GridBagConstraints.LINE_END,
                GridBagConstraints.NONE,
                1, 0, 1, 1);

        lbTags = new JLabel(I18N.obterRotuloPontoturTags());
        adicionarComponente(lbTags,
                GridBagConstraints.LINE_END,
                GridBagConstraints.NONE,
                2, 0, 1, 1);

        txtNome = new JTextField(35);
        adicionarComponente(txtNome,
                GridBagConstraints.LINE_START,
                GridBagConstraints.NONE,
                0, 1, 1, 1);

        txtDesc = new JTextField(80);
        adicionarComponente(txtDesc,
                GridBagConstraints.LINE_START,
                GridBagConstraints.NONE,
                1, 1, 1, 1);

        txtTags = new JTextField(35);
        adicionarComponente(txtTags,
                GridBagConstraints.LINE_START,
                GridBagConstraints.NONE,
                2, 1, 1, 1);

        btnSalvar = new JButton(I18N.obterBotaoSalvar(),
                GerenciadorDeImagens.OK);

        btnCancelar = new JButton(I18N.obterBotaoCancelar(),
                GerenciadorDeImagens.CANCELAR);

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        adicionarComponente(painelBotoes,
                GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                4, 0, 2, 1);
    }
    
    /**
     * Limpa os componentes da tela
     */
    private void limparTela() {
        txtNome.setText("");
        txtDesc.setText("");
        txtTags.setText("");
        txtNome.requestFocus();
    }

    /**
     * Configura os eventos dos botões.
     */
    private void configurarAcoesBotoes() {
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                janela.dispose();
            }
        });

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String log = gerenciadorUsuario.retornarLogin();
                boolean veri = gerenciador.novoPontotur(txtNome.getText(), txtDesc.getText(), txtTags.getText(),log);
                if (veri == false){
                    JOptionPane.showMessageDialog(null,"Não foi possível cadastrar, ponto turistico já cadastrado");
                    limparTela();
                }
                else {
                    JOptionPane.showMessageDialog(null,"Ponto turístico cadastrado");
                    limparTela();
                }
            }
        });
    }

    

    /**
     * Constrói a janela tratando internacionalização, componentes e layout.
     */
    private void construirTela() {
        janela = new JDialog();
        janela.setTitle(I18N.obterTituloTelaCadastroPonto());
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        janela.setLayout(layout);
        adicionarComponentes();        
        janela.pack();
    }

    /**
     * Exibe a tela.
     */
    public void exibirTela() {
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setLocationRelativeTo(telaPrincipal.obterJanela());
        janela.setModal(true);
        janela.setVisible(true);
        janela.setResizable(false);
    }
    
}
