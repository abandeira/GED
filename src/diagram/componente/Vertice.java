package diagram.componente;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.accessibility.AccessibleContext;

/**
 * O Vertice � uma classe abstrata utilizada pelo GEDE para permitir a cria��o 
 * de novos modelos de v�rtices para as aplica��es.
 *
 * Na cria��o de um novo modelo de v�rtice, est� classe dever� necessariamente 
 * ser herdada e os m�todos abstratos desenharVertice e coordenadaPertenceVertice
 * devem ser sobrescritos. Ela tamb�m possui, al�m dos requisitos b�sicos do v�rtice,
 * um atributo c�digo dispon�vel para ser utilizado junto ao banco de dados para
 * facilitar no armazenamento de suas informa��es.
 *
 * Para o funcionamento correto do editor, o m�todo desenharVertice dever� implementar
 * todo o c�digo necess�rio para desenhar um v�rtice e o m�todo coordenadaPertenceVertice
 * dever� implementar a �rea ocupada pelo v�rtice. Est� �rea, por exemplo, s�o todos 
 * os pontos contidos em um ret�ngulo. Entretanto, nas classes do pacote java.awt.geom
 * j� existe implementa��es que calculam se uma coordenada est� contida dentro de uma 
 * �rea ou n�o.
 *
 * @author Luis Henrique Castilho da Silva
 * @see java.awt.geom.Rectangle2D
 * @see java.awt.geom.Ellipse2D
 * @see java.awt.geom.RoundRectangle2D
 * @see ArestaSimples
 */
public abstract class Vertice extends Componente 
{
	/**
	 * A coordenada inicial no eixo X.
	 */
	public static int 		COORDXINICIAL = 1;
	
	/**
	 * A coordenada inicial no eixo Y.
	 */
	public static int		COORDYINICIAL = 1;
	
	/**
	 * A largura padr�o do v�rtice.
	 */
	public static int		LARGURAPADRAO = 60;
	
	/**
	 * A largura m�nima do v�rtice.
	 */
	public static int		LARGURAMINIMA = 5;
	
	/**
	 * A largura m�xima do v�rtice.
	 */
	public static int		LARGURAMAXIMA = 300;
	
	/**
	 * A altura padr�o do v�rtice.
	 */
	public static int		ALTURAPADRAO = 40;
	
	/**
	 * A altura m�nima do v�rtice.
	 */
	public static int		ALTURAMINIMA = 5;
	
	/**
	 * A largura m�xima do v�rtice.
	 */
	public static int		ALTURAMAXIMA = 300;
	
	private static Color	COR_FUNDO_SELECAO = Color.red,
							COR_BORDA_SELECAO = Color.black;
	
	private	int				x,
							y,
							largura,
							altura;
        
        //Lista de arestas de um vertice
        private ArrayList<Aresta> arestas = new ArrayList<Aresta>();
        
        //Lista de vertices associados a um vertice
        private ArrayList<Vertice> vertices = new ArrayList<Vertice>();        
	
        /**
	 * Inst�ncia um novo v�rtice.
	 *
	 * Como a classe Vertice � abstrata, as aplica��es n�o podem chamar
	 * est� classe diretamente.
	 * 
	 * @see diagram.Grafo#getVertice
	 */					
	public Vertice()
	{
		super();
		setX(COORDXINICIAL);
		setY(COORDYINICIAL);
		setLargura(LARGURAPADRAO);
		setAltura(ALTURAPADRAO);
                
                //adiciona o vertice na sua lista de vertices para que este não possa se relacionar com ele mesmo
                this.addVertices(this);
	}
	
	/**
	 * Inst�ncia um novo v�rtice alterando suas coordenadas iniciais.
	 *
	 * Como a classe V�rtice � abstrata, as aplica��es n�o podem chamar
	 * est� classe diretamente.
	 * 
	 * @param x a coordenada do v�rtice no eixo X
	 * @param y a coordenada do v�rtice no eixo Y
	 * @see diagram.Grafo#getVertice
	 */
	public Vertice(int x, int y)
	{
		this();
		setX(x);
		setY(y);
	}
	
	/**
	 * Inst�ncia um novo v�rtice alterando suas coordenadas, largura e altura 
	 * inicial.
	 *
	 * Como a classe V�rtice � abstrata, as aplica��es n�o podem chamar
	 * est� classe diretamente.
	 * 
	 * @param x a coordenada do v�rtice no eixo X
	 * @param y a coordenada do v�rtice no eixo Y
	 * @param largura o comprimento da largura do v�rtice
	 * @param altura o comprimento da altura do v�rtice
	 * @see diagram.Grafo#getVertice
	 */
	public Vertice(int x, int y, int largura, int altura)
	{
		this();
		setX(x);
		setY(y);
		setLargura(largura);
		setAltura(altura);
	}
	
	/**
	 * Inst�ncia um novo v�rtice alterando suas coordenadas, largura, altura 
	 * inicial. Al�m disso um interio que representa o c�digo do v�rtice tamb�m
	 * � alterado.
	 *
	 * Como a classe V�rtice � abstrata, as aplica��es n�o podem chamar
	 * est� classe diretamente.
	 * 
	 * @param codigo o inteiro que define o c�digo para o v�rtice
	 * @param x a coordenada do v�rtice no eixo X
	 * @param y a coordenada do v�rtice no eixo Y
	 * @param largura o comprimento da largura do v�rtice
	 * @param altura o comprimento da altura do v�rtice
	 * @see diagram.Grafo#getVertice
	 */
	public Vertice(int codigo, int x, int y, int largura, int altura)
	{
		this();
		setCodigo(codigo);
		setX(x);
		setY(y);
		setLargura(largura);
		setAltura(altura);
	}

//M�todos Get...
	
	/**
	 * Retorna a coordenada do v�rtice no eixo horizontal ou eixo X.
	 * 
	 * @param a coordenada no eixo X
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Retorna a coordenada do v�rtice no eixo vertical ou eixo Y..
	 * 
	 * @param a coordenada no eixo Y
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Retorna a largura do v�rtice.
	 * 
	 * @param o comprimento da largura
	 */
	public int getLargura()
	{
		return largura;
	}
	
	/**
	 * Retorna a altura do v�rtice.
	 * 
	 * @param o comprimento da altura
	 */
	public int getAltura()
	{
		return altura;
	}
	
//M�todos Set...

	/**
	 * Altera a coordenada do v�rtice no eixo X.
	 *
	 * @param x o inteiro que define a coordenada
	 */
	public void setX(int x)
	{
		if (x > 0)
			this.x = x;
		else
			this.x = COORDXINICIAL;
	}
	
	/**
	 * Altera a coordenada do v�rtice no eixo Y.
	 *
	 * @param y o inteiro que define a coordenada
	 */
	public void setY(int y)
	{
		if (y > 0)
			this.y = y;
		else
			this.y = COORDYINICIAL;	
	}
	
	/**
	 * Altera a largura do v�rtice.
	 *
	 * @param x o inteiro que define a largura
	 */
	public void setLargura(int largura)
	{
		if ((largura >= LARGURAMINIMA) && (largura <= LARGURAMAXIMA))
			this.largura = largura;
		else
		{
			if (largura < LARGURAMINIMA)
				this.largura = LARGURAMINIMA;
			else
				this.largura = LARGURAMAXIMA;
		}
	}
	
	/**
	 * Altera a altura do v�rtice.
	 *
	 * @param x o inteiro que define a altura
	 */
	public void setAltura(int altura)
	{
		if ((altura >= ALTURAMINIMA) && (altura <= ALTURAMAXIMA))
			this.altura = altura;
		else
		{
			if (altura < ALTURAMINIMA)
				this.altura = ALTURAMINIMA;
			else
				this.altura = ALTURAMAXIMA;
		}
	}
	
//Metodo para selecionar o v�rtice

 	/**
 	 * M�todo utilizado pela classe AreaAplicativo e AreaApplet do pacote GEDE 
 	 * para desenhar os quadrados de sele��o do v�rtice. Este m�todo � invocado
 	 * pela pelas classe sempre que o v�rtice estiver selecionado.
 	 *
 	 * @param desenho o contexto gr�fico na qual o quadrados ser�o desenhados
 	 * @param ladoQuadradoSelecao o comprimento do lado do quadrado de sele��o
 	 */
	public void selecionarVertice(Graphics desenho, int ladoQuadradoSelecao)
	{
		if (getSelecionado())
		{
			desenho.setColor(COR_FUNDO_SELECAO);
			//Canto Superior Esquerdo
			desenho.fillRect(x - ladoQuadradoSelecao, y - ladoQuadradoSelecao, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Canto Superior Direito
			desenho.fillRect(x + largura, y - ladoQuadradoSelecao, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Canto Inferior Esquerdo
			desenho.fillRect(x - ladoQuadradoSelecao, y + altura, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Canto Inferior Direito
			desenho.fillRect(x + largura, y + altura, ladoQuadradoSelecao, ladoQuadradoSelecao);
			
			//Desenha a borda da sele��o do v�rtice
			desenho.setColor(COR_BORDA_SELECAO);
			//Canto Superior Esquerdo
			desenho.drawRect(x - ladoQuadradoSelecao, y - ladoQuadradoSelecao, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Canto Superior Direito
			desenho.drawRect(x + largura, y - ladoQuadradoSelecao, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Canto Inferior Esquerdo
			desenho.drawRect(x - ladoQuadradoSelecao, y + altura, ladoQuadradoSelecao, ladoQuadradoSelecao);
			//Canto Inferior Direito
			desenho.drawRect(x + largura, y + altura, ladoQuadradoSelecao, ladoQuadradoSelecao);
		}
	}
	
	//Metodos abstratos
	
	/**
	 * Desenha o v�rtice no contexto gr�fico passado pelo par�metro. No editor
	 * este m�todo e chamado sempre que for necess�rio desenhar o v�rtice.
	 *
	 * @param desenho o contexto gr�fico na qual desenhar� o v�rtice
	 * @param componente o componente na qual o contexto gr�fico est� inserido
	 * @see diagram.editor.AreaApplet#paint
	 * @see diagram.editor.AreaAplicativo#paintComponent
	 */
	public abstract void desenharVertice(Graphics desenho, Component componente);
	
	/**
	 * Identifica se a coordenada est� contida na �rea do v�rtice. Ele retorna 
	 * verdadeiro se o ponto estiver dentro dos limites do v�rtice caso contr�rio
	 * retorna-se falso. 
	 * 
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo Y
	 * @return se a coordenada est� contida no v�rtice
	 */
	public abstract boolean coordenadaPertenceVertice(int x, int y);
        
    @Override
    public String toString(){  
        return this.getRotulo().getTexto();      
    }

    /**
     * @return the arestas
     */
    public ArrayList<Aresta> getArestas() {
        return arestas;
    }

    /**
     * @param arestas the arestas to set
     */
    public void addArestas(Aresta aresta) {
        this.arestas.add(aresta);
    }

    /**
     * @return the vertices
     */
    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    /**
     * @param vertices the vertices to set
     */
    public void addVertices(Vertice vertice) {
        this.vertices.add(vertice);
    }

}