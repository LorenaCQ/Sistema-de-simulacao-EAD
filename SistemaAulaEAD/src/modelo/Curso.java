package modelo;
//classe criada com os atributos respectivos aos criados no banco de dados
//essa classe � uma subclasse de ErroMsg
public class Curso extends ErroMsg {
	
	private String codigo;
	private String nome;
	
	//o super � utilizado para recuperar os atributos da classe m�e ErroMsg
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
