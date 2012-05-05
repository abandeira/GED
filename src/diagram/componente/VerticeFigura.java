package diagram.componente;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Component;

/**
 * Cria um novo v�rtice dentro do contexto do editor de diagramas GEDE no 
 * formato de uma figura de um arquivo.
 *
 * @author Luis Henrique Castilho da Silva
 * @see Vertice
 */
public class VerticeFigura extends Vertice
{
	private ImageIcon		figura;
	
	/**
	 * Cria um novo v�rtice apresentando a figura contida do arquivo.
	 *
	 * @param figura a figura do v�rtice
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
	 * Cria um novo v�rtice apresentando a figura contida do arquivo localizado 
	 * nas coordenadas passada pelo par�metro.
	 *
	 * @param figura a figura do v�rtice
	 * @param x a ccordenada do v�rtice no eixo X
	 * @param y a ccordenada do v�rtice no eixo Y
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
	 * Retorna a figura utilizada pelo v�rtice.
	 *
	 * @return a figura do v�rtice
	 */
	public ImageIcon getImagem()
	{
		return figura;	
	}
	
//Metodos set
	
	/**
	 * Altera a figura do v�rtice a ser apresentada no editor.
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
	
//M�todos sobrescritos

	/**
	 * Desenha a figura do arquivo no contexto gr�fico.
	 *
	 * @param desenho o contexto gr�fico na qual desenhar� o v�rtice
	 * @param componente o componente na qual o contexto gr�fico est� inserido
	 * @see diagram.editor.AreaApplet#paint
	 * @see diagram.editor.AreaAplicativo#paintComponent
	 */
	public final void desenharVertice(Graphics desenho, Component componente)
	{
		if (figura != null)
			figura.paintIcon(componente, desenho, getX(), getY());
	}
	
	/**
	 * Identifica se a coordenada est� contida na �rea do ret�ngulo da figura. 
	 * O m�todo retorna verdadeiro se o ponto estiver dentro dos limites caso 
	 * contr�rio retorna-se falso. 
	 * 
	 * @param x a coordenada do eixo X
	 * @param y a coordenada do eixo Y
	 * @return se a coordenada est� contida no v�rtice
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