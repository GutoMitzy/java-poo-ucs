package aula5.interfaces;

public interface Professor extends Colaborador{
	public static final float GRADUACAO=1;
	public static final float MESTRADO=1.2f;
	public static final float DOUTORADO=1.5f;
	
	float obtemTitulacao();
}
