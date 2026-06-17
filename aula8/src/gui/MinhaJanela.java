package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MinhaJanela extends JFrame implements ActionListener{
	
	private JPanel fundo;
	private JButton botao1, botao2, botao3;
	private JTextField entrada1, entrada2, saida;
	
	public MinhaJanela() {
		super("Minha primeira Janela");
		this.setSize(800, 600);
		
		this.fundo = new JPanel(new FlowLayout());
		this.botao1 = new JButton("+");
		this.botao1.addActionListener(this);
		
		this.botao2 = new JButton("C");
		this.botao2.addActionListener(this);
		
		this.botao3 = new JButton("-");
		this.botao3.addActionListener(this);
		
		this.entrada1 = new JTextField(20);
		this.entrada2 = new JTextField(20);
		
		this.saida = new JTextField(20);
		
		this.fundo.add(this.entrada1);
		this.fundo.add(this.entrada2);
		this.fundo.add(this.botao1);
		this.fundo.add(this.botao3);
		this.fundo.add(this.botao2);
		this.fundo.add(this.saida);
		
		this.getContentPane().add(this.fundo);		
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		MinhaJanela mj = new MinhaJanela();
		
		
	}
	
	private void operar(char op) {
		float v1=0, v2=0, v3=0;
		boolean ok = true;
		
		try {
			v1 = Float.parseFloat(entrada1.getText());
		} catch(NumberFormatException nfe) {
			this.saida.setText("Não é número!");
			ok=false;
		}
		
		try {
			v2 = Float.parseFloat(entrada2.getText());
		} catch(NumberFormatException nfe) {
			this.saida.setText("Não é número!");
			ok=false;
		}
		
		if(ok) {
			if(op=='+') {
				v3 = v1+v2;
			} else if (op=='-'){
				v3 = v1-v2;
			}
			this.saida.setText(v3 + "");
		}
	}
	
	private void limpar() {
		this.entrada1.setText("");
		this.entrada2.setText("");
		this.saida.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.botao1) {
			this.operar('+');
		} else if (e.getSource()==this.botao2){
			this.limpar();
		} else if(e.getSource()==this.botao3){
			this.operar('-');
			
		}
		
		
	}
	
	
}
