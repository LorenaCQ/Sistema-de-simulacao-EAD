package controle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dao.UsuarioDao;
import modelo.Usuario;
import util.Utilidade;

public class UsuarioControle extends SelectorComposer<Window>{

	@Wire
	Window windowUsuario;
	@Wire
	Textbox txtLogin;
	@Wire
	Textbox txtSenha;
	@Wire
	Button btnEntrar;
	@Wire
	Textbox txtPesquisa;
	@Wire
	Listbox lsbPesquisa;
	
	public String OPCAO = "";
	
	
	/*As linhas 50 a 55 definem o método criaJanela. Note que na linha 55 há a anotation @Listen, indicando que é um
	ouvidor de eventos. Esse ouvidor de evento fica aguardando o evento de criação da tela quando o arquivo login.zul
	é ativado. Quando esse evento ocorre @Listen chama o método criaJanela através da expressão
	onCreate=#windowVisualizacao, onde windowVisualizacao é o identificador do componente window do respectivo arquivo zul.
	Quando esse método é chamado, ele chama o método limpaLsbPesquisa (para limpar os dados da tabela de pesquisa)
	e o método limpaDados(para limpar os dados dos componentes da tela construída pelo arquivo login.zul).*/
	@Listen("onCreate=#windowVisualizacao")
	public void criaJanela2() throws SQLException, InterruptedException, IOException, ClassNotFoundException
	{
		this.limpaLsbPesquisa();
		this.limpaDados();
	}
	
	public void limpaLsbPesquisa()
	{
		//this.lsbPesquisa.getItems().clear();
	}
	
	/*Esse método permite abrir a janela de acordo com o cadastro de quem utiliza, se é administrador ou aluno.*/
	@Listen("onClick = #btnEntrar")
	public void onClickbtnEntrar() throws Exception
	{
		OPCAO = "I";
		UsuarioDao pessoaDAO = new UsuarioDao();
		Usuario obj = new Usuario();
		
		if (validaDados())
		{
			obj = this.atualizaDados(obj);
			//obj = pessoaDAO.fazerLogin(obj);
			
			if(pessoaDAO.fazerLogin(obj) == true) 
			{
				Utilidade.mensagem(obj.getMsgErro());
			}
			
			if(pessoaDAO.fazerLogin(obj) == false && this.txtLogin.getText().toString().equals("administrador")) 
			{
				Executions.sendRedirect("menu.zul");
			}
			
			if(pessoaDAO.fazerLogin(obj) == false && this.txtLogin.getText().toString().equals("aluno")) 
			{
				Executions.sendRedirect("MenuAluno.zul");
			}
		}
		this.limpaDados();
	}
	
	/* Esse método retorna um boolean, true ou false,
	respectivamente, caso a validação for correta ou não. Para isso, utiliza a variável retorno (boolean), que é inicializada
	com false. Uma variável inteira conta e a variável String msg. Se conta, no final for zero, então as validações estão
	corretas e faz com que a variável retorno receba true. Se conta for maior que zero, será apresentada
	a mensagem de erro que é armazenada em msg. Depois valida se o componente
	txtLogin é vazio ou nulo. Se não for, alimenta msg com uma mensagem e incrementa a variável conta. O mesmo
	ocorre com as demais.*/
	public boolean validaDados() throws InterruptedException, WrongValueException, SQLException, IOException
	{
		boolean retorno = false;
		int conta=0;
		String msg = "";
		
		if (this.txtLogin.getText().toString().length() == 0 || this.txtLogin.getText().toString().equals("") || this.txtLogin == null)
		{
			conta++;
			msg+= "Informe o nome de usuario\n";
		}
		
		if (this.txtSenha.getText().toString().length() == 0 || this.txtSenha.getText().toString().equals("") || this.txtSenha == null)
		{
			conta++;
			msg+= "Informe a senha\n";
		}
		
		if (conta > 0)
		{
			Utilidade.mensagem(msg);
			retorno = false;
		}
		
		if (conta == 0)
		{
			retorno = true;
		}
		return retorno;
	}
	
	//realiza a atualização dos dados digitados
	public Usuario atualizaDados(Usuario f) throws WrongValueException, SQLException, InterruptedException, IOException
	{
		String dataS = "";
		f.setLogin(this.txtLogin.getValue());
		f.setSenha(this.txtSenha.getValue());
		
		return f;
	}
	
	//(para limpar os dados dos componentes da tela construída pelo arquivo.zul
	public void limpaDados() throws SQLException,InterruptedException, IOException
	{
		OPCAO = "";
		
		this.txtSenha.setText("");
		this.txtLogin.setText("");
		this.txtPesquisa.setText("");
		this.limpaLsbPesquisa();
	}
}