package modelo;
//classe criada com os atributos respectivos aos criados no banco de dados
//essa classe é uma subclasse de ErroMsg
public class Curso extends ErroMsg {
	
	private String codigo;
	private String nome;
	
	//o super é utilizado para recuperar os atributos da classe mãe ErroMsg
	public Curso() {
		super();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
