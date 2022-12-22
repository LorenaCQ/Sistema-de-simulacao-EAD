package modelo;
import modelo.ErroMsg;
//classe criada com os atributos respectivos aos criados no banco de dados
//essa classe é uma subclasse de ErroMsg

public class Matricula extends ErroMsg {
	
	private String codigo_curso;
	private String matricula_aluno;
	
	//o super é utilizado para recuperar os atributos da classe mãe ErroMsg
	public Matricula() {
		super();
	}

	public String getCodigo_curso() {
		return codigo_curso;
	}

	public void setCodigo_curso(String codigo_curso) {
		this.codigo_curso = codigo_curso;
	}

	public String getMatricula_aluno() {
		return matricula_aluno;
	}

	public void setMatricula_aluno(String matricula_aluno) {
		this.matricula_aluno = matricula_aluno;
	}
	
}
