package diagram.editor;

import java.awt.Panel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Cursor;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import diagram.Grafo;

import diagram.componente.Aresta;
import diagram.componente.Rotulo;
import diagram.componente.Vertice;

import diagram.menu.MenuMouse;
import diagram.menu.MenuMouseArea;
import diagram.menu.MenuMouseAresta;
import diagram.menu.MenuMouseVertice;
import diagram.menu.MenuMouseRotulo;

/**
 * Est� classe � respons�vel pelo desenho do grafo ou diagrama dentro do editor.
 * Nela existem todos os m�todos implementados para suporte aos eventos de
 * mouse e de teclado do GEDE.
 *
 * Est� classe � inst�nciada sempre que houver a necessidade de cria uma �rea 
 * para apresenta��o de grafos no Web browser utilizando as classes do pacote awt.
 *
 * @author Luis Henrique Castilho da Silva
 * @see EditorAppletGrafo
 */
public class AreaApplet extends Panel implements MouseMotionListener, MouseListener, KeyListener
{
	/**
	 * A cor de fundo padr�o utilizada pelo editor GEDE,
	 */
	public static final Color	CORFUNDOPADRAO = Color.white;

	private final int			LADO_QUADRADO_SELECAO = 5;
	
	private Cursor				cursorMao, cursorPadrao;
	
	private Grafo				grafo;
	
	private int					distBordaX,
								distBordaY,
								cliqueAresta,
								cliquePontoAresta,
								cliqueRotuloAresta,
								cliqueRotuloVertice,
								cliqueVertice,
								cliqueSelecaoVertice,
								coordX,
								coordY,
								localQuadradoSelecao,
								larguraVertice,
								alturaVertice,
								mouseMoveX,
								mouseMoveY,
								maximoEsquerdaX1,
								maximoDireitaX1,
								maximoSuperiorY,
								maximoInferiorY;
	
	private	boolean				houverArraste,
								teclaShiftPressionada,
								teclaCtrlPressionada,
								teclaCtrlZPressionada;
	
	private EditorAppletGrafo	editorGrafo;
	
	private static MenuMouse	menuMouseAresta, 
								menuMouseRotulo, 
								menuMouseVertice,
								menuMouse;
	
	/**
	 * Cria uma nova �rea de apresenta��o do grafo ou diagrama utilizada pela
	 * <B>Applet</B> inst�nciada. Para criar est� nova �rea, deve-se passar 
	 * uma applet de edi��o do pacote GEDE.
	 *
	 * @param editorGrafo a applet que conter� a �rea de apresenta��o
	 * @see EditorAppletGrafo#EditorAppletGrafo
	 */
	public AreaApplet(EditorAppletGrafo editorGrafo) 
	{
		super();
		
		this.editorGrafo = editorGrafo;
		
		distBordaX = 0;
		distBordaY = 0;
		
		coordX = 0;
		coordY = 0;
		localQuadradoSelecao = 0;
		larguraVertice = 0;
		alturaVertice = 0;
		
		mouseMoveX = 0;
		mouseMoveY = 0;
		maximoEsquerdaX1 = 0;
		maximoDireitaX1 = 0;
		maximoSuperiorY = 0;
		maximoInferiorY = 0;
		
		cliqueAresta = -1;
		cliquePontoAresta = -1;
		cliqueRotuloAresta = -1;
		cliqueRotuloVertice = -1;
		cliqueVertice = -1;
		cliqueSelecaoVertice = -1;
		
		houverArraste = false;
		
		setBackground(CORFUNDOPADRAO);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		MenuMouseAresta menuAresta = new MenuMouseAresta(this);
		editorGrafo.getEditorApplet().add(menuAresta);
		menuMouseAresta = menuAresta;
		
		MenuMouseRotulo menuRotulo = new MenuMouseRotulo(this);
		editorGrafo.getEditorApplet().add(menuRotulo);
		menuMouseRotulo = menuRotulo;
		
		MenuMouseVertice menuVertice = new MenuMouseVertice(this);
		editorGrafo.getEditorApplet().add(menuVertice);
		menuMouseVertice = menuVertice;
			
		MenuMouseArea menuArea = new MenuMouseArea(this);
		editorGrafo.getEditorApplet().add(menuArea);
		menuMouse = menuArea;
				
		cursorMao = new Cursor(Cursor.HAND_CURSOR);
		cursorPadrao = new Cursor(Cursor.DEFAULT_CURSOR);
		
		teclaCtrlPressionada = false;
		teclaCtrlZPressionada = false;
		teclaShiftPressionada = false;
	}
	
	/**
	 * Este m�todo � respons�vel pelo desenho de todo o grafo existente no editor.
	 * 
	 * @param desenhar o contexto gr�fico que desenhar� o grafo na �rea da applet
	 * @see EditorAppletGrafo#repintarArea
	 */
	public void paint(Graphics desenhar)
	{
		int			i;
		Aresta 		arestas[];
		Vertice		vertices[];
		Rotulo		rotulo;
		
		//Chama o construtor para limpar a tela...
		super.paint(desenhar);
		
		if (grafo != null)
		{
			//Desenha as arestas
			arestas = grafo.getTodasArestas();
			if (arestas != null)
			{
				for (i = 0; i < arestas.length; i++)
				{
					desenhar.setColor(CORFUNDOPADRAO);
					arestas[i].desenharAresta(desenhar);
				}
			}
			
			//Desenha os vertices
			vertices = grafo.getTodosVertices();
			if (vertices != null)
			{
				for (i = 0;i < vertices.length; i++)
				{
					desenhar.setColor(CORFUNDOPADRAO);
					vertices[i].desenharVertice(desenhar, this);
				}
			}
			
			//Desenha os rotulos e sua selecao. Rotulos relacionados com as arestas
			if (arestas != null)
			{
				for (i = 0; i < arestas.length; i++)
				{
					desenhar.setColor(CORFUNDOPADRAO);
					rotulo = arestas[i].getRotulo();
					rotulo.desenharRotuloAresta(desenhar, arestas[i], LADO_QUADRADO_SELECAO);
				}	
			}
			
			//Desenha os rotulos e sua sele��o. Rotulos relacionados com os vertices
			if (vertices != null)
			{
				for (i = 0;i < vertices.length; i++)
				{
					desenhar.setColor(CORFUNDOPADRAO);
					rotulo = vertices[i].getRotulo();
					rotulo.desenharRotuloVertice(desenhar, vertices[i], LADO_QUADRADO_SELECAO);
				}
			}
			
			//Desenho a sele��o das arestas
			if (arestas != null)
			{
				for (i = 0;i < arestas.length; i++)
					arestas[i].selecionarAresta(desenhar, LADO_QUADRADO_SELECAO);
			}
			
			//Desenho a sele��o dos v�rtices
			if (vertices != null)
			{
				for (i = 0;i < vertices.length; i++)
					vertices[i].selecionarVertice(desenhar, LADO_QUADRADO_SELECAO);
			}
		}
	}
	
	//Metodos Get...
	
	/**
	 * Retorna o editor de applet atual da �rea de desenho do grafo.
	 *
	 * @return o editor da applet do GEDE
	 */
	public EditorAppletGrafo getEditorAppletGrafo()
	{
		return editorGrafo;
	}
	
	/**
	 * Retorna o grafo atual desenhado na �rea do editor applet.
	 * 
	 * @return o grafo atual desenhado
	 */
	public Grafo getGrafo()
	{
		return grafo;
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre 
	 * uma aresta.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es da aresta
	 * @see MenuMouseAresta#MenuMouseAresta
	 */
	public MenuMouse getMenuMouseAresta()
	{
		return menuMouseAresta;
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre 
	 * um r�tulo.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es do r�tulo
	 * @see diagram.menu.MenuMouseRotulo#MenuMouseRotulo
	 */
	public MenuMouse getMenuMouseRotulo()
	{
		return menuMouseRotulo;
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre 
	 * um v�rtice.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es do v�rtice
	 * @see MenuMouseVertice#MenuMouseVertice
	 */
	public MenuMouse getMenuMouseVertice()
	{
		return menuMouseVertice;
	}
	
	/**
	 * Retorna o menu utilizado pelo pacote GEDE quando ocorrer um evento sobre nenhum dos 
	 * componentes.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es gerais
	 * @see MenuMouseArea#MenuMouseArea
	 */
	public MenuMouse getMenuMouse()
	{
		return menuMouse;
	}
	
	//M�todos Set...
	/**
	 * Altera o grafo a ser desenhado pela classe. Por padr�o n�o existe nenhum grafo
	 * no in�cio.
	 *
	 * @param grafo o novo grafo a ser desenhado pela �rea
	 */
	public void setGrafo(Grafo grafo)
	{
		this.grafo = grafo;
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre uma aresta.
	 *
	 * @param menuMouse o menu de aresta do editor
	 * @see MenuMouseAresta#MenuMouseAresta
	 */
	public void setMenuMouseAresta(MenuMouse menuMouse)
	{
		this.menuMouseAresta = menuMouseAresta;
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre um r�tulo.
	 *
	 * @param menuMouse o menu do r�tulo do editor
	 * @see MenuMouseRotulo#MenuMouseRotulo
	 */
	public void setMenuMouseRotulo(MenuMouse menuMouse)
	{
		this.menuMouseRotulo = menuMouseRotulo;
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre um v�rtice.
	 *
	 * @param menuMouse o menu de v�rtice do editor
	 * @see MenuMouseVertice#MenuMouseVertice
	 */
	public void setMenuMouseVertice(MenuMouse menuMouse)
	{
		this.menuMouseVertice = menuMouseVertice;
	}
	
	/**
	 * Altera o menu utilizado pelo GEDE quando houver um evento sobre nenhum dos 
	 * componentes contidos na �rea de apresenta��o.
	 *
	 * @param menuMouse o menu do editor
	 * @see MenuMouseArea#MenuMouseArea
	 */
	public void setMenuMouse(MenuMouse menuMouse)
	{
		this.menuMouse = menuMouse;
	}
	
	//M�todos do KeyListener
	
	/**
	 * M�todo invocado sempre que uma tecla for pressionada utilizando o editor
	 * de digramas em primeiro plano.
	 * 
	 * @param e o evento do teclado
	 */
	public void keyPressed(KeyEvent e)
	{
		int		i;
		Vertice	vertice;
		Aresta	arestas[];
		boolean	selecionado;
		
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			if (teclaCtrlPressionada)
			{
				//Ctrl + Z
				teclaCtrlZPressionada = true;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_DELETE)
		{
			//Captura o tecla delete...
			selecionado = false;
			for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
			{
				if (grafo.getVertice(i).getSelecionado())
				{
					selecionado = true;
					break;
				}
				
				if (grafo.getVertice(i).getRotulo().getRotuloSelecionado())
				{
					selecionado = true;
					break;
				}
			}
			
			arestas = grafo.getTodasArestas();
			for (i = 0; i < grafo.getNumeroTotalArestas(); i++)
			{
				if (arestas[i].getSelecionado())
				{
					selecionado = true;
					break;
				}
				
				if (arestas[i].getRotulo().getRotuloSelecionado())
				{
					selecionado = true;
					break;
				}
			}
			
			if (!selecionado) //Para caso n�o exista componente selecionado
				return;
				
			CaixaDialogo caixa = new CaixaDialogo(this, true);
			caixa.showCaixaDialogo();
		}
		else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			//Captura a tecla Shift
			teclaShiftPressionada = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			//Captura a tecla Control
			teclaCtrlPressionada = true;
		}
	}
	
	/**
	 * M�todo invocado sempre que uma tecla for liberada utilizando o editor
	 * de digramas em primeiro plano.
	 * 
	 * @param e o evento do teclado
	 */
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			teclaCtrlZPressionada = false;		
		}
		else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			//Libera a tecla Shift
			teclaShiftPressionada = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			//Libera a tecla CTRL
			teclaCtrlPressionada = false;
			teclaCtrlZPressionada = false;
		}
	}
	
	/**
	 * M�todo invocado sempre que uma tecla for digitada utilizando o editor
	 * de digramas em primeiro plano.
	 * 
	 * @param e o evento do teclado
	 */
	public void keyTyped(KeyEvent e){}
	
	//M�todos do MouseMotionListener
	
	/**
	 * M�todo invocado sempre que houver um arraste do mouse sobre a �rea de 
	 * apresenta��o do editor. 
	 *
	 * Um observa��o importante dentro do contexto do editor de diagramas � que 
	 * os m�todos de arraste das classes MenuMouseArea, MenuMouseAresta, 
	 * MenuMouseRotulo e MenuMouseVertice sempre ser�o invocados antes de executar
	 * suas opera��es implementadas.
	 *
	 * @param e o evento do mouse
	 * @see MenuMouseAresta#mouseDragged
	 * @see MenuMouseArea#mouseDragged
	 * @see MenuMouseRotulo#mouseDragged
	 * @see MenuMouseVertice#mouseDragged
	 */
	public void mouseDragged(MouseEvent e)
	{
		Aresta		arestas[];
		Rotulo		rotulo;
		Vertice 	vertice;
		int 		auxX, auxY, moveEixoX, moveEixoY;
		
		//Identifica se pode ser editado...
		if (editorGrafo.getEditar() == EditorAppletGrafo.NAO_EDITAR)
			return;
				
		//Chama o mouseDragged para componente clicado
		menuMouseAresta.mouseDragged(e);
		menuMouseRotulo.mouseDragged(e);
		menuMouseVertice.mouseDragged(e);
		menuMouse.mouseDragged(e);
		
		if (cliqueRotuloVertice != -1)
		{
			//Clique ocorreu no rotulo do vertice
			rotulo = grafo.getVertice(cliqueRotuloVertice).getRotulo();
			
			auxY = e.getY() - rotulo.getCoordenadaY();
			if (auxY >= 0)
				rotulo.setDistanciaEixoCentral(rotulo.getDistanciaEixoCentral() + 1);
			else
				rotulo.setDistanciaEixoCentral(rotulo.getDistanciaEixoCentral() - 1);
		}	
		else if (cliqueVertice != -1)
		{
			//Clique ocorreu no v�rtice
			vertice = grafo.getVertice(cliqueVertice);	
			auxX = e.getX() - distBordaX;
			auxY = e.getY() - distBordaY;
			
			vertice.setX(auxX);
			vertice.setY(auxY);
		}
		else if(cliqueSelecaoVertice != -1)
		{
			//Clique no quadrado e sele��o do v�rtice
			/*Variavel
			 * localQuadradoSelecao = 1 --> Canto superior esquerdo
			 * localQuadradoSelecao = 2 --> Canto superior direito
			 * localQuadradoSelecao = 3 --> Canto inferior esquerdo
			 * localQuadradoSelecao = 4 --> Canto inferior direito
			*/						
			vertice = grafo.getVertice(cliqueSelecaoVertice);
			
			auxX = e.getX() - coordX;
			auxY = e.getY() - coordY;
			
			moveEixoX = e.getX() - mouseMoveX;
			moveEixoY = e.getY() - mouseMoveY;
			
			if (localQuadradoSelecao == 4)
			{
				//Quadrado de sele��o inferior direito do v�rtice
				vertice.setLargura(larguraVertice + auxX);
				vertice.setAltura(alturaVertice + auxY);
			}
			else if (localQuadradoSelecao == 3)
			{
				//Quadrado de sele��o inferior esquerdo
				if (moveEixoX > 0)
				{
					//Movimento para a direita
					if (vertice.getLargura() != Vertice.LARGURAMINIMA)
					{
						if (e.getX() >= maximoEsquerdaX1)
						{
							vertice.setLargura(larguraVertice - auxX);
							vertice.setX(e.getX());
							maximoDireitaX1 = e.getX();
						}
					}
				}
				else
				{
					//Movimento para a esquerda
					if (vertice.getLargura() != Vertice.LARGURAMAXIMA)
					{
						if (e.getX() <= maximoDireitaX1)
						{
							vertice.setLargura(larguraVertice - auxX);
							vertice.setX(e.getX());
							maximoEsquerdaX1 = e.getX();	
						}
					}
				}
				
				vertice.setAltura(alturaVertice + auxY);
			}
			else if (localQuadradoSelecao == 1)
			{
				//Quadrado de sele��o superior esquerdo
				if (moveEixoX > 0)
				{
					//Movimento para a direita
					if (vertice.getLargura() != Vertice.LARGURAMINIMA)
					{
						if (e.getX() >= maximoEsquerdaX1)
						{
							vertice.setLargura(larguraVertice - auxX);
							vertice.setX(e.getX());
							maximoDireitaX1 = e.getX();
						}
					}
				}
				else
				{
					//Movimento para a esquerda
					if (vertice.getLargura() != Vertice.LARGURAMAXIMA)
					{
						if (e.getX() <= maximoDireitaX1)
						{
							vertice.setLargura(larguraVertice - auxX);
							vertice.setX(e.getX());
							maximoEsquerdaX1 = e.getX();	
						}
					}
				}
				
				if (moveEixoY > 0)
				{
					//Movimento para baixo
					if (vertice.getAltura() != Vertice.ALTURAMINIMA)
					{
						if (e.getY() >= maximoSuperiorY)
						{
							vertice.setAltura(alturaVertice - auxY);
							vertice.setY(e.getY());
							maximoInferiorY = e.getY();
						}
					}
				}
				else
				{
					//Movimento para cima
					if (vertice.getAltura() != Vertice.ALTURAMAXIMA)
					{
						if (e.getY() <= maximoInferiorY)
						{
							vertice.setAltura(alturaVertice - auxY);
							vertice.setY(e.getY());
							maximoSuperiorY = e.getY();
						}
					}
				}
			}
			else if (localQuadradoSelecao == 2)
			{
				//Quadrado de sele��o superior direito
				vertice.setLargura(larguraVertice + auxX);
				
				if (moveEixoY > 0)
				{
					//Movimento para baixo
					if (vertice.getAltura() != Vertice.ALTURAMINIMA)
					{
						if (e.getY() >= maximoSuperiorY)
						{
							vertice.setAltura(alturaVertice - auxY);
							vertice.setY(e.getY());
							maximoInferiorY = e.getY();
						}
					}
				}
				else
				{
					//Movimento para cima
					if (vertice.getAltura() != Vertice.ALTURAMAXIMA)
					{
						if (e.getY() <= maximoInferiorY)
						{
							vertice.setAltura(alturaVertice - auxY);
							vertice.setY(e.getY());
							maximoSuperiorY = e.getY();
						}
					}
				}
			}
			
			mouseMoveX = e.getX();
			mouseMoveY = e.getY();
		}
		else if (cliquePontoAresta != -1)
		{
			//Clique ocorreu na sele��o da aresta
			arestas = grafo.getTodasArestas();
			arestas[cliqueAresta].setQuebraX(cliquePontoAresta, e.getX());
			arestas[cliqueAresta].setQuebraY(cliquePontoAresta, e.getY());
		}
		
		houverArraste = true;
		repaint();	
		editorGrafo.setBarrasRolagem();
	}
	
	/**
	 * M�todo invocado sempre que houver um movimento do mouse sobre a �rea de 
	 * apresenta��o do editor. 
	 *
	 * Um observa��o importante dentro do contexto do editor de diagramas � que 
	 * os m�todos de movimenta��o das classes MenuMouseArea, MenuMouseAresta,
	 * MenuMouseRotulo e MenuMouseVertice sempre ser�o invocados antes de 
	 * executar suas opera��es implementadas.
	 *
	 * @param e o evento do mouse
	 * @see MenuMouseAresta#mouseMoved
	 * @see MenuMouseArea#mouseMoved
	 * @see MenuMouseRotulo#mouseMoved
	 * @see MenuMouseVertice#mouseMoved
	 */
	public void mouseMoved(MouseEvent e)
	{
		int 		i, j;
		Vertice		verticeTemp;
		Aresta		arestasTemp[];
		Rotulo		rotuloTemp;
		
		//Identifica se pode ser editado...
		if (editorGrafo.getEditar() == EditorAppletGrafo.NAO_EDITAR)
			return;
				
		//Chama o mouseMoved para componente clicado
		menuMouseAresta.mouseMoved(e);
		menuMouseRotulo.mouseMoved(e);
		menuMouseVertice.mouseMoved(e);
		menuMouse.mouseMoved(e);
		
		if (getTeclaCtrlPressionada() && !getTeclaCtrlZPressionada())
		{
			//Somente a tecla Ctrl pressionada
			//Identifica se o clique ocooreu no rotulo do vertice
			for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
			{
				verticeTemp = grafo.getVertice(i);
				rotuloTemp = verticeTemp.getRotulo();
				
				if (rotuloTemp.coordenadaPertenceRotulo(e.getX(), e.getY()))
				{
					//Gera um evento chamando um metodo da classe EditorAplicativoGrafo
					desmarcarTodosComponentes();
					repaint();
					setCursor(cursorMao);
					editorGrafo.movimentoMouse(e, rotuloTemp);
					return;
				}
			}
			
			//Identifica se o clique ocooreu no rotulo da aresta
			arestasTemp = grafo.getTodasArestas();
			if (arestasTemp != null)
			{
				for (i = 0; i < arestasTemp.length; i++)
				{
					rotuloTemp = arestasTemp[i].getRotulo();
					
					if (rotuloTemp.coordenadaPertenceRotulo(e.getX(), e.getY()))
					{
						desmarcarTodosComponentes();
						repaint();
						setCursor(cursorMao);
						editorGrafo.movimentoMouse(e, rotuloTemp);
						return;
					}
				}
			}
			
			//Identifica se o clique ocooreu no vertice
			for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
			{
				//Verificar se a coordenada do clique do mouse pertence a area do vertice
				verticeTemp = grafo.getVertice(i);
				if (verticeTemp.coordenadaPertenceVertice(e.getX(), e.getY()))
				{
					desmarcarTodosComponentes();
					repaint();
					setCursor(cursorMao);
					editorGrafo.movimentoMouse(e, verticeTemp);
					return;
				}
			}
			
			//Identifica se o clique ocooreu na aresta
			if (arestasTemp != null)
			{
				for (i = 0; i < arestasTemp.length; i++)
				{
					//Verificar se a coordenada do clique do mouse pertence a area da aresta
					if (arestasTemp[i].coordenadaPertenceAresta(e.getX(), e.getY()))
					{
						desmarcarTodosComponentes();
						repaint();
						setCursor(cursorMao);
						editorGrafo.movimentoMouse(e, arestasTemp[i]);
						return;
					}
				}
			}
		}
		
		setCursor(cursorPadrao);
	}
	
	//Metodos do MouseListener
	
	/**
	 * M�todo invocado sempre que houver um clique do mouse sobre a �rea de 
	 * apresenta��o do editor. 
	 *
	 * Um observa��o importante dentro do contexto do editor de diagramas � que 
	 * os m�todos de clique com o bot�o direito do mouse das classes MenuMouseAresta,
	 * MenuMouseArea, MenuMouseRotulo e MenuMouseVertice sempre ser�o 
	 * invocados antes de executar suas opera��es implementadas.
	 *
	 * @param e o evento do mouse
	 * @see MenuMouseAresta#mouseClicked
	 * @see MenuMouseArea#mouseClicked
	 * @see MenuMouseRotulo#mouseClicked
	 * @see MenuMouseVertice#mouseClicked
	 */
	public void mouseClicked(MouseEvent e)
	{
		int 		i;
		Aresta		arestas[];
		Rotulo		rotulo;
		Vertice		vertice;
		Object		objetoClicado;
		
		objetoClicado = null;
		
		if (!e.isMetaDown())
		{
			//Identifica se pode ser editado...
			if (editorGrafo.getEditar() == EditorAppletGrafo.NAO_EDITAR)
				return;
						
			//Clique com o bot�o esquerdo do Mouse
			if (e.getClickCount() == 2)
			{
				//Clique duplo com o bot�o esquerdo
				desmarcarTodosComponentes();
				
				if (cliqueRotuloAresta != -1)
				{
					arestas = grafo.getTodasArestas();
					if (arestas != null)
						objetoClicado = arestas[cliqueRotuloAresta].getRotulo();
				}
				else if (cliqueRotuloVertice != -1)
					objetoClicado = grafo.getVertice(cliqueRotuloVertice).getRotulo();
				else if (cliqueVertice != -1)
					objetoClicado = grafo.getVertice(cliqueVertice);
				else if (cliqueAresta != -1)
				{
					arestas = grafo.getTodasArestas();
					if (arestas != null)
						objetoClicado = arestas[cliqueAresta];
				}
				
				editorGrafo.cliqueDuploMouse(e, objetoClicado);
			}
			else
			{
				//Clique simples com o bot�o esquerdo
				if (!getTeclaShiftPressionada())
					desmarcarTodosComponentes();
								
				if (cliqueRotuloVertice != -1)
				{
					//Clique sobre o rotulo do vertice
					rotulo = grafo.getVertice(cliqueRotuloVertice).getRotulo();
					rotulo.setRotuloSelecionado(true);
				}
				else if (cliqueRotuloAresta != -1)
				{
					//Clique sobre o rotulo da aresta
					arestas = grafo.getTodasArestas();
					if (arestas != null)
					{
						rotulo = arestas[cliqueRotuloAresta].getRotulo();
						rotulo.setRotuloSelecionado(true);
					}
				}
				else if (cliqueVertice != -1)
				{
					//Clique sobre o v�rtice
					vertice = grafo.getVertice(cliqueVertice);
					grafo.SelecionarComponente(vertice);
				}
				else if (cliqueAresta != -1)
				{
					//Clique sobre a aresta
					arestas = grafo.getTodasArestas();
					if (arestas != null)
						grafo.SelecionarComponente(arestas[cliqueAresta]);
				}
				
				repaint();
			}
		}
		else
		{
			//Clique com o bot�o direito do Mouse
			//Chama os metodos do mouse com o clique do bot�o direito dos componentes
			menuMouseAresta.mouseClicked(e);
			menuMouseRotulo.mouseClicked(e);
			menuMouseVertice.mouseClicked(e);
			menuMouse.mouseClicked(e);
			
			if (cliqueRotuloAresta != -1)
			{
				arestas = grafo.getTodasArestas();
				if (arestas != null)
				{
					menuMouseRotulo.menu(arestas[cliqueRotuloAresta].getRotulo(), e.getX(), e.getY());
				}
			}
			else if (cliqueRotuloVertice != -1)
			{
				menuMouseRotulo.menu(grafo.getVertice(cliqueRotuloVertice).getRotulo(), e.getX(), e.getY());
			}
			else if (cliqueVertice != -1)
			{
				menuMouseVertice.menu(grafo.getVertice(cliqueVertice), e.getX(), e.getY());
			}
			else if (cliqueAresta != -1)
			{
				arestas = grafo.getTodasArestas();
				if (arestas != null)
				{
					menuMouseAresta.menu(arestas[cliqueAresta], e.getX(), e.getY());
				}
			}
			else
			{
				menuMouse.menu(null, e.getX(), e.getY());
			}
		}
		
		distBordaX = 0;
		distBordaY = 0;
		
		coordX = 0;
		coordY = 0;
		localQuadradoSelecao = 0;
		larguraVertice = 0;
		alturaVertice = 0;
		mouseMoveX = 0;
		mouseMoveY = 0;
		maximoEsquerdaX1 = 0;
		maximoDireitaX1 = 0;
		maximoSuperiorY = 0;
		maximoInferiorY = 0;
		
		cliqueAresta = -1;
		cliqueVertice = -1;
		cliqueSelecaoVertice = -1;
		
		cliqueRotuloAresta = -1;
		cliqueRotuloVertice = -1;
		cliquePontoAresta = -1;
	}
	
	/**
	 * M�todo invocado sempre que houver uma entrada do mouse na a �rea de 
	 * apresenta��o do editor. 
	 *
	 * Um observa��o importante dentro do contexto do editor de diagramas � que 
	 * os m�todos de entrada do mouse das classes MenuMouseArea, MenuMouseAresta,
	 * MenuMouseRotulo eJMenuMouseVertice sempre ser�o invocados antes de 
	 * executar suas opera��es implementadas.
	 *
	 * @param e o evento do mouse
	 * @see MenuMouseAresta#mouseEntered
	 * @see MenuMouseArea#mouseEntered
	 * @see MenuMouseRotulo#mouseEntered
	 * @see MenuMouseVertice#mouseEntered
	 */
	public void mouseEntered(MouseEvent e)
	{
		//Chama os metodos do mouse com o clique do bot�o direito dos componentes
		menuMouseAresta.mouseEntered(e);
		menuMouseRotulo.mouseEntered(e);
		menuMouseVertice.mouseEntered(e);
		menuMouse.mouseEntered(e);
	}
	
	/**
	 * M�todo invocado sempre que houver uma sa�da do mouse na a �rea de 
	 * apresenta��o do editor. 
	 *
	 * Um observa��o importante dentro do contexto do editor de diagramas � que 
	 * os m�todos de sa�da do mouse das classes MenuMouseArea, MenuMouseAresta,
	 * MenuMouseRotulo e MenuMouseVertice sempre ser�o invocados antes de 
	 * executar suas opera��es implementadas.
	 *
	 * @param e o evento do mouse
	 * @see MenuMouseAresta#mouseExited
	 * @see MenuMouseArea#mouseExited
	 * @see MenuMouseRotulo#mouseExited
	 * @see MenuMouseVertice#mouseExited
	 */
	public void mouseExited(MouseEvent e)
	{
		//Chama os metodos do mouse com o clique do bot�o direito dos componentes
		menuMouseAresta.mouseExited(e);
		menuMouseRotulo.mouseExited(e);
		menuMouseVertice.mouseExited(e);
		menuMouse.mouseExited(e);
	}
	
	/**
	 * M�todo invocado sempre que algum bot�o do mouse for pressionado na �rea de 
	 * apresenta��o do editor. 
	 *
	 * Um observa��o importante dentro do contexto do editor de diagramas � que 
	 * os m�todos de bot�o do mouse pressionado das classes MenuMouseArea, 
	 * MenuMouseAresta, MenuMouseRotulo e MenuMouseVertice sempre ser�o 
	 * invocados antes de executar suas opera��es implementadas.
	 *
	 * @param e o evento do mouse
	 * @see MenuMouseAresta#mousePressed
	 * @see MenuMouseArea#mousePressed
	 * @see MenuMouseRotulo#mousePressed
	 * @see MenuMouseVertice#mousePressed
	 */
	public void mousePressed(MouseEvent e)
	{
		int 		i, j;
		Vertice		verticeTemp;
		Aresta		arestasTemp[];
		Rotulo		rotuloTemp;
		
		if (editorGrafo.getEditar() == EditorAppletGrafo.NAO_EDITAR)
			return;
				
		//Chama os metodos do mouse com o clique do bot�o direito dos componentes
		menuMouseAresta.mousePressed(e);
		menuMouseRotulo.mousePressed(e);
		menuMouseVertice.mousePressed(e);
		menuMouse.mousePressed(e);
		
		//Identifica se o clique ocooreu no rotulo do vertice
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			verticeTemp = grafo.getVertice(i);
			rotuloTemp = verticeTemp.getRotulo();
			
			if (rotuloTemp.coordenadaPertenceRotulo(e.getX(), e.getY()))
			{
				cliqueRotuloVertice = i;
				return;
			}
			else
				cliqueRotuloVertice = -1;
		}
		
		//Identifica se o clique ocooreu no rotulo da aresta
		arestasTemp = grafo.getTodasArestas();
		if (arestasTemp != null)
		{
			for (i = 0; i < arestasTemp.length; i++)
			{
				rotuloTemp = arestasTemp[i].getRotulo();
				
				if (rotuloTemp.coordenadaPertenceRotulo(e.getX(), e.getY()))
				{
					cliqueRotuloAresta = i;
					return;
				}
				else
					cliqueRotuloAresta = -1;
			}
		}
		
		//Identifica se o clique ocooreu no vertice
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			//Verificar se a coordenada do clique do mouse pertence a area do vertice
			verticeTemp = grafo.getVertice(i);
			if (verticeTemp.coordenadaPertenceVertice(e.getX(), e.getY()))
			{
				cliqueVertice = i;//Determina a localiza��o do vertice na lista
				distBordaX = e.getX() - verticeTemp.getX();
				distBordaY = e.getY() - verticeTemp.getY();
				return;
			}
			else
				cliqueVertice = -1; //Nenhum vertice clicado;
			
		}//Fim do for (contador de vertices)
		
		//Identifica se o clique ocorreu no quadrado de sele��o do vertice
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			verticeTemp = grafo.getVertice(i);
			if (verticeTemp.getSelecionado())
			{
				//V�rtice est� selecionado
				localQuadradoSelecao = verificarCliqueQuadradoSelecao(verticeTemp, e.getX(), e.getY());
				if (localQuadradoSelecao != 0)
				{
					cliqueSelecaoVertice = i;
					larguraVertice = verticeTemp.getLargura();
					alturaVertice = verticeTemp.getAltura();
					mouseMoveX = e.getX();
					mouseMoveY = e.getY();
					coordX = e.getX();
					coordY = e.getY();
					maximoDireitaX1 = e.getX();
					maximoEsquerdaX1 = e.getX();
					maximoSuperiorY = e.getY();
					maximoInferiorY = e.getY();
					return;
				}
				else
					cliqueSelecaoVertice = -1;//Nenhum quadrado de vertice clicado;
			}
		}
		
		//Identifica se o clique ocooreu na aresta
		if (arestasTemp != null)
		{
			for (i = 0; i < arestasTemp.length; i++)
			{
				//Verifica se a coordenada pertence ao ponto intermediario da aresta
				cliquePontoAresta = arestasTemp[i].cliqueSobreQuebraAresta(e.getX(), e.getY(), LADO_QUADRADO_SELECAO);
				if (cliquePontoAresta != -1)
				{
					//Houve o clique no ponto de sele��o da aresta
					cliqueAresta = i;
					break;
				}
				
				///Verificar se a coordenada do clique do mouse pertence a area da aresta
				if (arestasTemp[i].coordenadaPertenceAresta(e.getX(), e.getY()))
				{
					cliqueAresta = i;
					break;
				}
				else
					cliqueAresta = -1;
				
			}//Fim do for (contador de arestas)
		
		}//Fim do if (aresta nula)
	
	}//Fim do metodo mousePressed
	
	/**
	 * M�todo invocado sempre que algum bot�o do mouse for liberado na �rea de 
	 * apresenta��o do editor. 
	 *
	 * Um observa��o importante dentro do contexto do editor de diagramas � que 
	 * os m�todos de bot�o do mouse liberado das classes MenuMouseArea, 
	 * MenuMouseAresta, MenuMouseRotulo e MenuMouseVertice sempre ser�o 
	 * invocados antes de executar suas opera��es implementadas.
	 *
	 * @param e o evento do mouse
	 * @see MenuMouseAresta#mouseReleased
	 * @see MenuMouseArea#mouseReleased
	 * @see MenuMouseRotulo#mouseReleased
	 * @see MenuMouseVertice#mouseReleased
	 */
	public void mouseReleased(MouseEvent e)
	{
		//Identifica se pode ser editado...
		if (editorGrafo.getEditar() == EditorAppletGrafo.NAO_EDITAR)
			return;
				
		//Chama os metodos do mouse com o clique do bot�o direito dos componentes
		menuMouseAresta.mouseReleased(e);
		menuMouseRotulo.mouseReleased(e);
		menuMouseVertice.mouseReleased(e);
		menuMouse.mouseReleased(e);
		
		//Seta todos os valores como falso, ou seja, nenhum componente clicado
		if (houverArraste)
		{
			//Houve um arraste nos componentes do editor
			desmarcarTodosComponentes();
			marcarComponenteSelecinado();
			repaint();
			
			//Setar as barras de rolagem para as novas coordenadas...
			editorGrafo.setBarrasRolagem();
			
			distBordaX = 0;
			distBordaY = 0;
			
			coordX = 0;
			coordY = 0;
			localQuadradoSelecao = 0;
			larguraVertice = 0;
			alturaVertice = 0;
			mouseMoveX = 0;
			mouseMoveY = 0;
			maximoEsquerdaX1 = 0;
			maximoDireitaX1 = 0;
			maximoSuperiorY = 0;
			maximoInferiorY = 0;
		
			cliqueAresta = -1;
			cliqueVertice = -1;
			cliqueSelecaoVertice = -1;
			
			cliqueRotuloAresta = -1;
			cliqueRotuloVertice = -1;
			cliquePontoAresta = -1;
		}
		
		houverArraste = false;	
	}

//Metodos privados da classe AreaApplet
	private boolean getTeclaCtrlPressionada()
	{
		return teclaCtrlPressionada;
	}
	
	private boolean getTeclaCtrlZPressionada()
	{
		return teclaCtrlZPressionada;
	}
	
	private boolean getTeclaShiftPressionada()
	{
		return teclaShiftPressionada;
	}
	
	private void desmarcarTodosComponentes()
	{
		int 	i;
		Vertice vertice;
		Rotulo	rotulo;
		Aresta	arestas[];
		
		//desmarcar todos os v�rtices e seus rotulos respectivos
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			vertice = grafo.getVertice(i);
			vertice.desmarcarComponente();
			
			rotulo = vertice.getRotulo();
			rotulo.setRotuloSelecionado(false);
		}
		
		//desmarcar todas as arestas e seus rotulos respectivos
		arestas = grafo.getTodasArestas();
		if (arestas != null)
		{
			for (i = 0; i < arestas.length; i++)
			{
				arestas[i].desmarcarComponente();
				
				rotulo = arestas[i].getRotulo();
				rotulo.setRotuloSelecionado(false);
			}	
		}
	}
	
	private void marcarComponenteSelecinado()
	{
		int 	i;
		Aresta	arestas[];
		Rotulo 	rotulo;
		Vertice vertice;
		
		if (cliqueRotuloAresta != -1)
		{
			arestas = grafo.getTodasArestas();
			if (arestas != null)
			{
				rotulo = arestas[cliqueRotuloAresta].getRotulo();
				rotulo.setRotuloSelecionado(true);
			}
		}
		else if (cliqueRotuloVertice != -1)
		{
			rotulo = grafo.getVertice(cliqueRotuloVertice).getRotulo();
			rotulo.setRotuloSelecionado(true);
		}
		else if (cliqueVertice != -1)
		{
			grafo.SelecionarComponente(grafo.getVertice(cliqueVertice));
		}
		else if (cliqueAresta != -1)
		{
			arestas = grafo.getTodasArestas();
			if (arestas != null)
				grafo.SelecionarComponente(arestas[cliqueAresta]);
		}
	}
	
	private int verificarCliqueQuadradoSelecao(Vertice vertice, int cliqueX, int cliqueY)
	{
		//Verifica o quadrado superior esquerdo do vertice
		if ((cliqueX >= vertice.getX() - LADO_QUADRADO_SELECAO) && (cliqueX <= vertice.getX()))
			if ((cliqueY >= vertice.getY() - LADO_QUADRADO_SELECAO) && (cliqueY <= vertice.getY()))
				return 1;
				
		//Verifica o quadrado superior direito do vertice
		if ((cliqueX >= vertice.getX() + vertice.getLargura()) && (cliqueX <= vertice.getX() + vertice.getLargura() + LADO_QUADRADO_SELECAO))
			if ((cliqueY >= vertice.getY() - LADO_QUADRADO_SELECAO) && (cliqueY <= vertice.getY()))
				return 2;
				
		//Verifica o quadrado inferior esquerdo do vertice
		if ((cliqueX >= vertice.getX() - LADO_QUADRADO_SELECAO) && (cliqueX <= vertice.getX()))
			if ((cliqueY >= vertice.getY() + vertice.getAltura()) && (cliqueY <= vertice.getY() + vertice.getAltura() + LADO_QUADRADO_SELECAO))
				return 3;
				
		//Verifica o quadrado inferior direito do vertice
		if ((cliqueX >= vertice.getX() + vertice.getLargura()) && (cliqueX <= vertice.getX() + vertice.getLargura() + LADO_QUADRADO_SELECAO))
			if ((cliqueY >= vertice.getY() + vertice.getAltura()) && (cliqueY <= vertice.getY() + vertice.getAltura() + LADO_QUADRADO_SELECAO))
				return 4;
				
		return 0;	
	}
}