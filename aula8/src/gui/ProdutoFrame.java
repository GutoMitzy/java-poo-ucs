package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Produto;

public class ProdutoFrame extends JFrame implements ActionListener{

	private JPanel fundo, campos, botoes;
	private JButton bSalva, bBusca, bLimpa;
	private JTextField tCodigo, tNome, tPreco;
	private JLabel lCodigo, lNome, lPreco;
	
	private Produto[] produtos;
	private static final int MAX_PRODUTOS =10;
	private int ultimoProduto;
	
	public ProdutoFrame() {
		super("Cadastro de Produtos");
		this.setSize(600, 400);
			
		this.fundo = new JPanel(new BorderLayout());
		this.campos = new JPanel(new GridLayout(3, 3));
		this.botoes = new JPanel(new FlowLayout());
		
		this.bSalva = new JButton("Salva");
		this.bSalva.addActionListener(this);
		
		this.bBusca = new JButton("Busca");
		this.bBusca.addActionListener(this);
		
		this.bLimpa = new JButton("Limpa");
		this.bLimpa.addActionListener(this);
		
		this.tCodigo = new JTextField(20);
		this.tNome = new JTextField(20);
		this.tPreco = new JTextField(20);
		
		this.lCodigo = new JLabel("Código");
		this.lNome = new JLabel("Nome");
		this.lPreco = new JLabel("Preço");
		
		this.campos.add(lCodigo);
		this.campos.add(tCodigo);
		this.campos.add(lNome);
		this.campos.add(tNome);
		this.campos.add(lPreco);
		this.campos.add(tPreco);
		this.fundo.add(this.campos, BorderLayout.CENTER);
		
		this.botoes.add(bSalva);
		this.botoes.add(bBusca);
		this.botoes.add(bLimpa);
	
		this.fundo.add(this.botoes, BorderLayout.SOUTH);
		
		this.getContentPane().add(this.fundo);
		
		
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		ProdutoFrame l = new ProdutoFrame();
		
		
	}
	
	private void produtoParaTela(Produto p) {
		this.tCodigo.setText(p.getCodigo()+"");
		this.tPreco.setText(p.getPreco()+"");
		this.tNome.setText(p.getNome());
	}
	
	public Produto telaParaProduto() {
		Produto p = new Produto();
		
		try {
			p.setCodigo(Integer.parseInt(tCodigo.getText()));
			p.setPreco(Float.parseFloat(tPreco.getText()));
			p.setNome(tNome.getText());			
		} catch(NumberFormatException nfe) {
			p = null;
		}
		
		return p;
	}

	private void salvar() {
		Produto p = this.telaParaProduto();
		
		if(p!=null && this.ultimoProduto<MAX_PRODUTOS) {
			this.produtos[this.ultimoProduto] = p;
			this.ultimoProduto++;
		}
		this.mostraProdutos();
		
	}
	
	private void mostraProdutos() {
		System.out.println("Produtos Cadastrados:");
		for(Produto p : this.produtos) {
			System.out.println(p);
		}
	}
	
	private void limpar() {
		this.tCodigo.setText("");
		this.tNome.setText("");
		this.tPreco.setText("");
	}
	
	private void buscar() {
		int codigo = Integer.parseInt(this.tCodigo.getText());
		
		Produto p = null;
		for(int i=0; i<this.ultimoProduto; i++) {
			if(this.produtos[i].getCodigo() == codigo) {
				p = this.produtos[i];
			}
		}
		
		if(p!=null) {
			this.produtoParaTela(p);			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.bSalva) {
			this.salvar();
		} else if (e.getSource()==this.bBusca){
			this.buscar();
		} else if(e.getSource()==this.bLimpa) {
			this.limpar();
		}
	}
	
}
