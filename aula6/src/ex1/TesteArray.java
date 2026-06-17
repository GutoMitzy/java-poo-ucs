package ex1;

import java.util.Arrays;

public class TesteArray {
	int[] vetor;
	
	public TesteArray(int tamanho) {
		this.vetor = new int[tamanho];
	}
	
	public void recebeArray(int[] vetor) {
		vetor[0] = 1;
	}

	@Override
	public String toString() {
		return "TesteArray [vetor=" + Arrays.toString(vetor) + "]";
	}
	
}
