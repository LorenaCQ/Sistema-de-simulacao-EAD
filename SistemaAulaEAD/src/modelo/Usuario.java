package modelo;
//classe criada com os atributos respectivos aos criados no banco de dados
import modelo.ErroMsg;

//essa classe é uma subclasse de ErroMsg
public class Usuario extends ErroMsg {
	
	private String login;
	private String senha;
	
	//o super é utilizado para recuperar os atributos da classe mãe ErroMsg
	public Usuario()
	{
		super();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
