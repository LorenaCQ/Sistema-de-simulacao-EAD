package modelo;

//classe criada com os atributos respectivos aos criados no banco de dados

public class Aluno extends ErroMsg
{
	private int matricula;
	private String nome_aluno;
	private String celular;
	
	//o super é utilizado para recuperar os atributos da classe mãe ErroMsg
	public Aluno() {
		super();
	}
	
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public String getNome_aluno() {
		return nome_aluno;
	}
	public void setNome_aluno(String nome_aluno) {
		this.nome_aluno = nome_aluno;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	
}