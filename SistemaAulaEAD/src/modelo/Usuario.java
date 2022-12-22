package modelo;
//classe criada com os atributos respectivos aos criados no banco de dados
import modelo.ErroMsg;

//essa classe � uma subclasse de ErroMsg
public class Usuario extends ErroMsg {
	
	private String login;
	private String senha;
	
	//o super � utilizado para recuperar os atributos da classe m�e ErroMsg
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
