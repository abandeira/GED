package diagram.componente;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Component;

/**
 * Cria um novo vértice dentro do contexto do editor de diagramas GEDE no 
 * formato de uma figura de um arquivo.
 *
 * @author Luis Henrique Castilho da Silva
 * @see Vertice
 */
public class VerticeFigura extends Vertice
{
	private ImageIcon		figura;
	
	/**
	 * Cria um novo vértice apresentando a figura contida do arquivo.
	 *
	 * @param figura a figura do vértice
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeFigura(ImageIcon figura)
	{
		super();
		setLargura(figura.getIconWidth());
		setAltura(figura.getIconHeight());
		setImagem(figura);
	}
	
	/**
	 * Cria um novo vértice apresentando a figura contida do arquivo localizado 
	 * nas coordenadas passada pelo parâmetro.
	 *
	 * @param figura a figura do vértice
	 * @param x a ccordenada do vértice no eixo X
	 * @param y a ccordenada do vértice no eixo Y
	 * @see diagram.Grafo#getVertice
	 */
	public VerticeFigura(ImageIcon figura, int x, int y)
	{
		super(x, y);
		setLargura(figura.getIconWidth());
		setAltura(figura.getIconHeight());
		setImagem(figura);
	}
	
//Metodos get
	
	/**
	 * Retorna a figura utilizada pelo vértice.
	 *
	 * @return a figura do vértice
	 */
	public ImageIcon getImagem()
	{
		return figura;	
	}
	
//Metodos set
	
	/**
	 * Altera a figura do vértice a ser apresentada no editor.
	 *
	 * @param figura a nova figura
	 */
	public boolean setImagem(ImageIcon figura)
	{
		this.figura = figura;
		setLargura(figura.getIconWidth());
		setAltura(figura.getIconHeight());
		
		if (getLargura() != figura.getIconWidth())
		{
			this.figura = null;
			setLargura(Vertice.LARGURAMINIMA);
			setAltura(Vertice.ALTURAMINIMA);
			return false;
		}
		else
		{
			if (getAltura() != figura.getIconHeight())
			{
				this.figura = null;
				setLargura(Vertice.LARGURAMINIMA);
				setAltura(Vertice.ALTURAMINIMA);
				return false;
			}
		}		
		
		return true;
	}
	
//Métodos sobrescritos

	/**
	 * Desenha a figura do arquivo no contexto gráfico.
	 *
	 * @param desenho o contexto gráfico na qual desenhará o vértice
	 * @param componente o componente na qual o contexto gráfico está inserido
	 * @see diagram.editor.AreaApplet#paint
	 * @see diagram.editor.AreaAplicativo#paintComponent
	 */
	public final void desenharVertice(Graphics desenho, Component componente)
	{
		if (figura != null)
			figura.paintIcon(componente, desenho, getX(), getY());
	}
	
	/**
	 * Identifica se a coordenada está contida na área do retângulo da figura. 
	 * O método retorna verdadeiro se o ponto estiver dentro dos limites caso 
	 * contrário retorna-se falso. 
	 * 
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo Y
	 * @return se a coordenada está contida no vértice
	 */
	public final boolean coordenadaPertenceVertice(int x, int y)
	{
		if (figura == null)
			return false;
			
		if ((x >= getX()) && (x <= getLargura() + getX()))
			if ((y >= getY()) && (y <= getAltura() + getY()))
				return true;
			
		return false;
	}
}