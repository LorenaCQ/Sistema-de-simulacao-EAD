package controle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dao.CursoDao;
import modelo.Curso;
import util.Utilidade;

public class CursoControle extends SelectorComposer<Window>{

	@Wire
	Window windowCurso;
	@Wire
	Tab tabCad;
	@Wire
	Tab tabPesq;
	@Wire
	Textbox txtNome;
	@Wire
	Textbox txtCod;
	@Wire
	Button btnIncluir;
	@Wire
	Button btnAlterar;
	@Wire
	Button btnExcluir;
	@Wire
	Button btnLimpar;
	@Wire
	Textbox txtPesquisa;
	@Wire
	Listbox lsbPesquisa;
	@Wire
	Button btnLimparLista;
	@Wire
	Button btnAtualizarLista;
	
	public String OPCAO = "";
	
	/*As linhas 61 a 66 definem o método criaJanela. Note que na linha 61 há a anotation @Listen, indicando que é um
	ouvidor de eventos. Esse ouvidor de evento fica aguardando o evento de criação da tela quando o arquivo CursoVisao.zul
	é ativado. Quando esse evento ocorre @Listen chama o método criaJanela através da expressão
	onCreate=#windowCurso, onde windowCurso é o identificador do componente window do respectivo arquivo zul.
	Quando esse método é chamado, ele chama o método limpaLsbPesquisa (para limpar os dados da tabela de pesquisa)
	e o método limpaDados(para limpar os dados dos componentes da tela construída pelo arquivo CursoVisao.zul).*/
	@Listen("onCreate=#windowCurso")
	public void criaJanela() throws SQLException, InterruptedException, IOException, ClassNotFoundException
	{
		this.limpaLsbPesquisa();
		this.limpaDados();
	}
	
	public void limpaLsbPesquisa()
	{
		this.lsbPesquisa.getItems().clear();
	}
	
	/*Esse método é o responsável por preencher o componente Listbox lsbPesquisa com registros consultados no banco de dados. 
	Cada um desses registros é retornado como um objeto da classe Curso, sendo que os objetos são armazenados em uma Collection ArrayList. 
	*/
	public void preencherLsbPesquisa () throws SQLException, InterruptedException, IOException, ClassNotFoundException
	{
		this.limpaLsbPesquisa();
		CursoDao bdDAO = new CursoDao();
		Curso bd = new Curso();
		ArrayList<Curso> al = new ArrayList<Curso>();
		al = bdDAO.listAll();
		
		for (int i = 0; i < al.size(); i++)
		{
			Listitem li = new Listitem();
			Listcell lc01 = new Listcell();
			lc01.setLabel(al.get(i).getCodigo());
			
			Listcell lc02 = new Listcell();
			lc02.setLabel(al.get(i).getNome());
			
			
			li.appendChild(lc01);
			li.appendChild(lc02);
			
			this.lsbPesquisa.appendChild(li);
		}
	}
	
	/*Esse método permite incluir um novo curso no banco de dados.*/
	@Listen("onClick = #btnIncluir")
	public void onClickbtnIncluir() throws Exception
	{
		OPCAO = "I";
		CursoDao cursoDAO = new CursoDao();
		Curso obj = new Curso();
		
		if (validaDados())
		{
			obj = this.atualizaDados(obj);
			obj = cursoDAO.incluir(obj);
			Utilidade.mensagem(obj.getMsgErro());
		}
		this.limpaDados();
	}
	
	/*Esse método permite alterar um curso no banco de dados.*/
	@Listen("onClick = #btnAlterar")
	public void onClickbtnAlterar() throws Exception
	{
		OPCAO = "A";
		boolean retorno = false;
		CursoDao cursoDAO = new CursoDao();
		Curso curso = new Curso();
		String dataS = "";
		
		if (validaDados())
		{
			curso = this.atualizaDados(curso);
			CursoDao aDao = new CursoDao();
			curso = aDao.alterar(curso);
			Utilidade.mensagem(curso.getMsgErro());
		}
		this.limpaDados();
	}
	
	/* Esse método retorna um boolean, true ou false,
	respectivamente, caso a validação for correta ou não. Para isso, utiliza a variável retorno (boolean), que é inicializada
	com false. Uma variável inteira conta e a variável String msg. Se conta, no final for zero, então as validações estão
	corretas e faz com que a variável retorno receba true. Se conta for maior que zero, será apresentada
	a mensagem de erro que é armazenada em msg. Depois valida se o componente
	txtCod é vazio ou nulo. Se não for, alimenta msg com uma mensagem e incrementa a variável conta. O mesmo
	ocorre com as demais.*/
	public boolean validaDados() throws InterruptedException, WrongValueException, SQLException, IOException
	{
		boolean retorno = false;
		int conta=0;
		String msg = "";
		
		if (this.txtCod.getText().toString().length() == 0 || this.txtCod.getText().toString().equals("") || this.txtCod == null)
		{
			conta++;
			msg+= "Informe o codigo\n";
		}
		
		if (this.txtNome.getText().toString().length() == 0 || this.txtNome.getText().toString().equals("") || this.txtNome == null)
		{
			conta++;
			msg+= "Informe o nome do curso\n";
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
	public Curso atualizaDados(Curso f) throws WrongValueException, SQLException, InterruptedException, IOException
	{
		String dataS = "";
		
	    f.setCodigo(this.txtCod.getValue());
		f.setNome(this.txtNome.getValue());
		this.txtPesquisa.setText("");
		return f;
	}
	
	//limpa a lista que é exibida
	@Listen ("onClick = #btnLimparLista") 
	public void onClickbtnLimparLista() {
		this.limpaLsbPesquisa();
	}
	
	@Listen ("onClick = #btnAtualizarLista")
	public void onClickbtnAtualizarLista() throws SQLException, InterruptedException, IOException, ClassNotFoundException
	{
		this.preencherLsbPesquisa();
	}
	
	/*Esse método, é
	chamado quando ocorre o evento de perda do foco sobre a caixa de texto txtPesquisa (evento onBlur). Quando essa
	ação ocorrer, a variável aux recebe o conteúdo digitado pelo usuário na caixa de texto txtPesquisa através de
	this.txtPesquisa.getValue(). Se o conteúdo de aux for vazio , a variável sql recebe o script SQL
	para pesquisar todos os cursos. Caso contrário, a variável sql recebe o script SQL para obter os registros cujo codigo
	contém apenas o que foi informado pelo usuário e armazenado em aux. O método de
	pesquisa listAllSql(sql) que executa o script SQL enviado e retorna uma coleção de objetos para a variável al. Se essa
	collection for diferente de null um loop é efetuado para preencher a listbox de
	pesquisa.*/
	@Listen ("onBlur = #txtPesquisa")
	public void onBlurtxtPesquisa() throws SQLException, InterruptedException, IOException, WrongValueException, ClassNotFoundException
	{
		this.limpaLsbPesquisa();
		CursoDao objDAO = new CursoDao();
		Curso bd = new Curso();
		ArrayList<Curso> al = new ArrayList<Curso>();
		String aux = "";
		aux = this.txtPesquisa.getValue();
		String sql = "";
		
		if (aux.length() == 0 || aux.isEmpty() || aux.equals("") || aux == null)
		{
			sql = "SELECT * FROM curso order by codigo";
		}
		else
		{
			sql = "SELECT * FROM curso WHERE codigo like '%"+aux+"%' order by codigo";
		}
		al = objDAO.listAllSql(sql);
		if (al != null)
		{
			for (int i = 0; i < al.size(); i++)
			{
				Listitem li = new Listitem();
				Listcell lc01 = new Listcell();
				lc01.setLabel(al.get(i).getCodigo());
				
				Listcell lc02 = new Listcell();
				lc02.setLabel(al.get(i).getNome());
				
				li.appendChild(lc01);
				li.appendChild(lc02);
				this.lsbPesquisa.appendChild(li);
			}
		}
		else
		{
			Utilidade.mensagem("Sem dados para visualizar");
		}
	}
	//obtem somente o curso selecionado
	@Listen ("onSelect = #lsbPesquisa")
	public void obtemCursoSelecionado() throws SQLException, ClassNotFoundException, InterruptedException
	{
		int indice = this.lsbPesquisa.getSelectedIndex();
		int id = 0;
		String x;
		CursoDao objDAO = new CursoDao();
		Curso curso = new Curso();
		if(indice >= 0)
		{
			Listitem li = new Listitem();
			Listcell lc01 = new Listcell();
			lc01 = (Listcell) this.lsbPesquisa.getSelectedItem().getChildren().get(0);
			x = lc01.getLabel().toString();
			curso = objDAO.buscarPorNome(x);
			if (curso != null)
			{
				this.txtCod.setText(curso.getCodigo());
				this.txtNome.setText(curso.getNome());
				this.limpaLsbPesquisa();
				this.txtPesquisa.setText("");
				this.tabCad.setSelected(true);
			}
		}
	}
	//para limpar os dados dos componentes da tela construída pelo arquivo.zul
	public void limpaDados() throws SQLException,InterruptedException, IOException
	{
		OPCAO = "";
		this.txtCod.setText("");
		this.txtNome.setText("");
		this.txtPesquisa.setText("");
		this.limpaLsbPesquisa();
	}
}