package diagram.componente;

/**
 * Est� � a super classe dos componentes existentes no editor de diagramas. 
 * O Componente est� acima da hierarquia de classes e portanto � a classe abstrata 
 * b�sica utilizada no contexto do pacote diagram para permitir a cria��o de 
 * novos modelos de componentes. Tais componentes do editor s�o o v�rtice 
 * e a aresta.
 *
 * A classe R�tulo n�o � um componente dentro da modelagem do editor e sim, 
 * um objeto que faz parte de um componente, ou seja, uma agrega��o. Ent�o 
 * uma classe supertipo de r�tulo n�o existe no pacote.
 *
 * Na cria��o de um novo modelo de componente, a classe <B>Componente</B> dever� 
 * necessariamente ser herdada. Ela tamb�m possui um atributo c�digo dispon�vel 
 * para ser utilizado junto ao banco de dados. Um r�tulo tamb�m est� associado
 * a est� classe e consequentemente todos suas subclasses.
 *
 * @author Luis Henrique Castilho da Silva
 * @see Aresta
 * @see Vertice
 */
public abstract class Componente extends Object
{
	/**
	 * O valor padr�o do c�digo inicial de um componente.
	 */
	public static int 		CODIGOINICIAL = 0;
	
	private int				codigo;
	
	private boolean			componenteSelecionado,
							componenteVisitado;
	
	private Rotulo			rotuloComponente;
	
        private String descricao;
	/**
	 * Inst�ncia uma novo componente.
	 *
	 * Como a classe Componente � abstrata, as aplica��es n�o podem chamar
	 * est� classe diretamente.
	 */	
	public Componente()
	{
		setCodigo(CODIGOINICIAL);
		setVisitado(false);
		setSelecionado(false);
		rotuloComponente = new Rotulo();
	}

	/**
	 * Inst�ncia uma novo componente com um c�digo especificado pelo par�metro.
	 *
	 * Como a classe Componente � abstrata, as aplica��es n�o podem chamar
	 * est� classe diretamente.
	 *
	 * @param codigo o c�digo do componente
	 */	
	public Componente(int codigo)
	{
		setCodigo(codigo);
		setSelecionado(false);
		setVisitado(false);
	}
	
	//Metodos Get
	
	/**
	 * Retorna o valor do c�digo atual do componente.
	 *
	 * @return o valor do c�digo
	 */
	public int getCodigo()
	{
		return codigo;	
	}
	
	/**
	 * Retorna se o componente do editor est� selecionado. Caso o retorno seja 
	 * verdadeiro o componente est� seleiconado e falso se n�o estiver selecionado.
	 *
	 * @return se o componente est� ou n�o selecionado
	 */
	public boolean getSelecionado()
	{
		return componenteSelecionado;	
	}

	/**
	 * Retorna se o componente j� foi marcado como avaliado pelos algoritmos de 
	 * grafos. Algoritmos, como o menor caminho e o percurso em largura e profundidade
	 * utilizam este atributo para facilitar na implemental��o do c�digo.
	 *
	 * @return se o componente j� foi visitado
	 */
	public boolean getVisitado()
	{
		return componenteVisitado;	
	}
	
	/**
	 * Retorna o r�tulo associado ao componente.
	 *
	 * @return o objeto r�tulo
	 */
	public Rotulo getRotulo()
	{
		return rotuloComponente;
	}
	
	//Metodos Set
	
	/**
	 * Altera o c�digo atual do componente.
	 *
	 * @param codigo o valor no novo c�digo
	 */
	public void setCodigo(int codigo)
	{
		if (codigo > 0)
			this.codigo = codigo;
		else
			this.codigo = CODIGOINICIAL;
	}
	
	/**
	 * Altera se o componente est� selecionado ou n�o. Um boleano verdadeiro
	 * dever� ser passado caso o componente esteja selecionado e falso caso
	 * contr�rio.
	 *
	 * @param componenteSelecionado se o componente est� ou n�o selecionado
	 * @see Componente#selecionarComponente
	 * @see Componente#desmarcarComponente
	 */
	public void setSelecionado(boolean componenteSelecionado)
	{
		this.componenteSelecionado = componenteSelecionado;	
	}
	
	/**
	 * Seleciona o componente
	 *
	 * @see Componente#setSelecionado
	 * @see Componente#desmarcarComponente
	 */
	public void selecionarComponente()
	{
		componenteSelecionado = true;
	}
	
	/**
	 * Desmarca o componente.
	 *
	 * @see Componente#setSelecionado
	 * @see Componente#selecionarComponente
	 */
	public void desmarcarComponente()
	{
		componenteSelecionado = false;
                
	}
	
	/**
	 * Marca o componente como visitado. Algoritmos, como o menor caminho e o 
	 * percurso em largura e profundidade utilizam este atributo para facilitar
	 * na implemental��o do c�digo.
	 *
	 * @param componenteVisitado se o componente foi visitado ou n�o
	 */
	public void setVisitado(boolean componenteVisitado)
	{
		this.componenteVisitado = componenteVisitado;	
	}
	
	/**
	 * Altera o r�tulo do componente.
	 *
	 * @param rotuloComponente o novo r�tulo
	 */
	public void setRotulo(Rotulo rotuloComponente)
	{
		if (rotuloComponente != null)
			this.rotuloComponente = rotuloComponente;
	}

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}