package diagram.componente;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.RoundRectangle2D;
import java.awt.Component;

/**
 * Cria um novo vértice dentro do contexto do editor de diagramas GEDE no 
 * formato de um retângulo com as bordas arredondadas.
 *
 * @author Luis Henrique Castilho da Silva
 * @see Vertice
 */
public class VerticeRetanguloBordaArredondado extends Vertice
{
	/**
	 * A largura padrão da borda do vértice.
	 */
	public static final int		LARGURABORDAPADRAO = 1;
	
	/**
	 * A largura máxima permitida para a borda do vértice.
	 */
	public static final int		LARGURAMAXIMABORDA = 10;
	
	/**
	 * A largura padrão do arco vértice.
	 */
	public static final int		LARGURAARCO = 30;
	
	/**
	 * A altura padrão do arco do vértice.
	 */
	public static final int		ALTURAARCO = 30;
									
	/**
	 * A cor da borda padrão para o vértice.
	 */						
	public static final Color	CORBORDAPADRAO = Color.black;
	
	/**
	 * A cor de fundo padrão para o vértice.
	 */
	public static final Color	CORFUNDOPADRAO = Color.white;
		
	private int 	larguraBorda;
	
	private Color	corBorda,
					corFundo;
	
	/**
	 * Cria um novo vértice na forma de um retângulo com bordas arredondadas.
	 *
	 * @see diagram.Grafo#getVertice
	 */		
	public VerticeRetanguloBordaArredondado()
	{
		super();
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo vértice na forma de um retângulo com bordas arredondadas
	 * localizado nas coordenadas passada pelo parâmetro.
	 *
	 * @param x a ccordenada do vértice no eixo X
	 * @param y a ccordenada do vértice no eixo Y
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeRetanguloBordaArredondado(int x, int y)
	{
		super(x, y);
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo vértice na forma de um retângulo com bordas arredondadas
	 * localizado nas coordenadas passada pelo parâmetro, com largura e altura
	 * definida na chamada deste construtor e um código inicial.
	 * 
	 * @param codigo o inteiro que define o código para o vértice
	 * @param x a ccordenada do vértice no eixo X
	 * @param y a ccordenada do vértice no eixo Y
	 * @param largura o comprimento da largura do vértice
	 * @param altura o comprimento da altura do vértice
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeRetanguloBordaArredondado(int codigo, int x, int y, int largura, int altura)
	{
		super(codigo, x, y, largura, altura);
		setLarguraBorda(LARGURABORDAPADRAO);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	/**
	 * Cria um novo vértice na forma de um retângulo com bordas arredondadas
	 * localizado nas coordenadas passada pelo parâmetro além de outros atributos
	 * definidos neste construtor.
	 * 
	 * @param codigo o inteiro que define o código para o vértice
	 * @param x a ccordenada do vértice no eixo X
	 * @param y a ccordenada do vértice no eixo Y
	 * @param largura o comprimento da largura do vértice
	 * @param altura o comprimento da altura do vértice
	 * @param larguraBorda a largura, em pixels, da borda do vértice
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeRetanguloBordaArredondado(int codigo, int x, int y, int largura, int altura, int larguraBorda)
	{
		super(codigo, x, y, largura, altura);
		setLarguraBorda(larguraBorda);
		setCorBorda(CORBORDAPADRAO);
		setCorFundo(CORFUNDOPADRAO);
	}
	
	//	Métodos GET...
	
	/**
	 * Retorna a largura da borda atual do vértice.
	 *
	 * @return a largura da borda
	 */
	public int getLarguraBorda()
	{
		return larguraBorda;
	}
	
	/**
	 * Retorna a cor atual utilizada para a borda.
	 *
	 * @return o cor da borda
	 */
	public Color getCorBorda()
	{
		return corBorda;
	}
	
	/**
	 * Retorna a cor atual de fundo utilizada no vértice.
	 *
	 * @return a cor de fundo
	 */
	public Color getCorFundo()
	{
		return corFundo;	
	}
	
	
//	Métodos SET...
	
	/**
	 * Altera a largura de borda do vértice.
	 *
	 * @param larguraBorda a nova largura
	 */
	public void setLarguraBorda(int larguraBorda)
	{
		if ((larguraBorda > 0) && (larguraBorda <= LARGURAMAXIMABORDA))
			this.larguraBorda = larguraBorda;
		else
			this.larguraBorda = LARGURABORDAPADRAO;	
	}
	
	/**
	 * Altera a cor atual da borda.
	 *
	 * @param corBorda a nova cor
	 */
	public void setCorBorda(Color corBorda)
	{
		this.corBorda = corBorda;	
	}
	
	/**
	 * Altera a cor atual utilizada no fundo do vértice.
	 *
	 * @param corFundo a nova cor
	 */
	public void setCorFundo(Color corFundo)
	{
		this.corFundo = corFundo;	
	}
	
	/**
	 * Desenha um retângulo com bordas arredondadas no contexto gráfico passado
	 * pelo parâmetro.
	 *
	 * @param desenho o contexto gráfico na qual desenhará o vértice
	 * @param componente o componente na qual o contexto gráfico está inserido
	 * @see diagram.editor.AreaApplet#paint
	 * @see diagram.editor.AreaAplicativo#paintComponent
	 */
	public final void desenharVertice(Graphics desenho, Component componente)
	{
		int i;
		
		//Desenhar Borda...
		desenho.setColor(corBorda);
		desenho.fillRoundRect(getX(), getY(), getLargura(), getAltura(), LARGURAARCO, ALTURAARCO);
		
		//Desenha o fundo do vértice...
		desenho.setColor(corFundo);
		desenho.fillRoundRect(getX() + larguraBorda, getY() + larguraBorda, getLargura() - 2 * larguraBorda, getAltura() - 2 * larguraBorda, LARGURAARCO, ALTURAARCO);		
	}
	
	/**
	 * Identifica se a coordenada está contida na área do retângulo arredondado.
	 * O método retorna verdadeiro se o ponto estiver dentro dos limites caso 
	 * contrário retorna-se falso.
	 * 
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo Y
	 * @return se a coordenada está contida no vértice
	 * @see java.awt.geom.RoundRectangle2D.Float
	 */
	public final boolean coordenadaPertenceVertice(int x, int y)
	{	
		RoundRectangle2D.Float area = new RoundRectangle2D.Float(getX(), getY(), getLargura(), getAltura(), LARGURAARCO, ALTURAARCO);
		
		return area.contains(x, y);
	}
}