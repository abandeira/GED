package diagram.editor;

import Teste.GUI;
import java.applet.Applet;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Scrollbar;
import java.awt.Container;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;

import javax.swing.JApplet;
import javax.swing.JScrollBar;

import diagram.Grafo;

import diagram.menu.MenuMouse;
import diagram.menu.JMenuMouse;

import diagram.componente.Aresta;
import diagram.componente.Vertice;

/**
 * Est� � a classe principal do editor de applet. Uma inst�ncia simples desta 
 * classe cria-se um novo editor de grafo preparado para editar e apresentar os diagramas.
 * Este novo editor utiliza todos os objetos do pacote Swing ou do pacote awt para 
 * compatibilidade com browser mais antigos. � importante resaltar que se utilizar 
 * o construtor do pacote awt, todos os componentes que utilizam o pacote swing ser�o 
 * nulos e n�o ser�o inicializados e vice e versa. Portanto uma chamada a algum m�todo 
 * que utiliza o pacote swing o editor n�o funcionar�.
 *
 * A classe chama automaticamente a classe <B>AreaAplicativo</B> quando utiliza o pacote
 * Swing e a classe <B>AreaApplet</B> quando utiliza o pacote awt criando uma �rea padr�o
 * para editar o diagrama.
 *
 * @author Luis Henrique Castilho da Silva
 * @see diagram.editor.EditorAplicativoGrafo
 */
public class EditorAppletGrafo implements AdjustmentListener
{
        private GUI frame;
	/**
	 * O valor de edi��o do grafo.
	 */
	public static final int		EDITAR = 1;
	
	/**
	 * O valor de n�o edi��o do grafo. Este grafo ser� somente apresentado.
	 */	
	public static final int		NAO_EDITAR = 0;
	
	private static int			DISTANCIA_EIXO_X = 130,
								DISTANCIA_EIXO_Y = 130;
						
	//Tratam o layout do Aplicativo
	private GridBagLayout		layoutJanela;
	private GridBagConstraints	componenteLayout;
	
	//Barras de rolagem Vertical e Horizontal...
	private Scrollbar			barraVertical,
								barraHorizontal;
	
	private JScrollBar			jBarraVertical,
								jBarraHorizontal;
								
	//Painel onde ser� apresentado o grafo...	
	private AreaApplet			areaGrafo;
	
	private AreaAplicativo		jAreaGrafo;
	
	//Variaveis reponsaveis pelo tamanho e localizacao do editor...
	private int					tipoApplet,
								editar,
								posicaoAtualBarraHorizontal,
								posicaoAtualBarraVertical;
	
	private Applet				editorApplet;
	
	private JApplet				jEditorApplet;
	
	private Container			jAreaLayout;
	
	private Grafo				grafo;
	
	/**
	 * Cria um editor applet padr�o utilizando as classes do pacote awt e sem 
	 * nenhum grafo apresentado. Este editor poder� ser editado pelo usu�rio final.
	 *
	 * @param editorApplet a Applet onde o editor ser� criado
	 * @throws NullPointerException em caso da Applet ser nula
	 */	
	public EditorAppletGrafo(Applet editorApplet) throws NullPointerException
	{
		//Seta o editor para editar o grafo por padr�o...
		editar = EDITAR;
		
		//Inicializa a Applet
		if (editorApplet == null)
			new NullPointerException("\n***Excecao***\ndiagram.NullPointerException: Nao existe uma Applet para criar o editor de Grafo");
		this.editorApplet = editorApplet;
		
		//Define o tipo para Applet do pacote awt
		tipoApplet = 1;
		
		//O grafo inicial n�o existe...
		grafo = null;
		
		layoutJanela = new GridBagLayout();
		componenteLayout = new GridBagConstraints();
		
		//Seta o layout de inser��o dos objetos...
		editorApplet.setLayout(layoutJanela);
		
		//Instancia��o da barra de rolagem Vertical
		barraVertical = new Scrollbar();
		barraVertical.setEnabled(false);
		barraVertical.setOrientation(Scrollbar.VERTICAL);
		barraVertical.addAdjustmentListener(this);
		
		//Instancia��o da barra de rolagem Horizontal
		barraHorizontal = new Scrollbar();
		barraHorizontal.setEnabled(false);
		barraHorizontal.setOrientation(Scrollbar.HORIZONTAL);
		barraHorizontal.addAdjustmentListener(this);
		
		areaGrafo = new AreaApplet(this);
		
		//Inser��o dos objetos no EditorGrafo...
		//Configura��o dos parametros da �rea de plotagem...
		componenteLayout.fill = GridBagConstraints.BOTH;
		componenteLayout.gridx = 0;
		componenteLayout.gridy = 0;
		componenteLayout.gridheight = 1;
		componenteLayout.gridwidth = 1;
		componenteLayout.weightx = 2.0;
		componenteLayout.weighty = 2.0;
		layoutJanela.setConstraints(areaGrafo, componenteLayout);
		editorApplet.add(areaGrafo);
		
		//Configura��o dos parametros para a BarraVertical...
		componenteLayout.fill = GridBagConstraints.VERTICAL;
		componenteLayout.gridx = 1;
		componenteLayout.gridy = 0;
		componenteLayout.gridheight = 1;
		componenteLayout.gridwidth = 1;
		componenteLayout.weightx = 0;
		componenteLayout.weighty = 0;
		layoutJanela.setConstraints(barraVertical, componenteLayout);
		editorApplet.add(barraVertical);
		
		//Configura��o dos parametros para a BarraHorizontal...
		componenteLayout.fill = GridBagConstraints.HORIZONTAL;
		componenteLayout.gridx = 0;
		componenteLayout.gridy = 1;
		componenteLayout.gridheight = 1;
		componenteLayout.gridwidth = 1;
		componenteLayout.weightx = 0;
		componenteLayout.weighty = 0;
		layoutJanela.setConstraints(barraHorizontal, componenteLayout);
		editorApplet.add(barraHorizontal);
		
		editorApplet.setVisible(true);
	}
	
	/**
	 * Cria um editor applet padr�o utilizando as classes do pacote awt e com 
	 * um grafo apresentado. Este editor poder� ser editado pelo usu�rio final.
	 *
	 * @param editorApplet a Applet onde o editor ser� criado
	 * @param grafo o grafo a ser apresentado
	 */
	public EditorAppletGrafo(Applet editorApplet, Grafo grafo)
	{
		this(editorApplet);
		setGrafo(grafo);
		
		setarBarraHorizontal();
		setarBarraVertical();
	}
	
//Construtor do Applet SWING

	/**
	 * Cria um editor applet padr�o utilizando as classes do pacote swing e sem 
	 * nenhum grafo apresentado. Este editor poder� ser editado pelo usu�rio final.
	 *
	 * @param jEditorApplet a JApplet onde o editor ser� criado
	 * @throws NullPointerException em caso da JApplet ser nula
	 */
	public EditorAppletGrafo(JApplet jEditorApplet, GUI frame) throws NullPointerException
	{
		//Seta o editor para editar o grafo por padr�o...
		editar = EDITAR;
		
                this.frame = frame;
                
		//Inicializa a Applet
		if (jEditorApplet == null)
			new NullPointerException("\n***Excecao***\ndiagram.NullPointerException: Nao existe uma JApplet para criar o editor de Grafo");
		this.jEditorApplet = jEditorApplet;
		
		//Define o tipo para JApplet do pacote swing
		tipoApplet = 2;
		
		//O grafo inicial n�o existe...
		grafo = null;
		
		jAreaLayout = jEditorApplet.getContentPane();
		
		layoutJanela = new GridBagLayout();
		componenteLayout = new GridBagConstraints();
		
		//Seta o layout de inser��o dos objetos...
		jAreaLayout.setLayout(layoutJanela);
		
		//Instancia��o da barra de rolagem Vertical
		jBarraVertical = new JScrollBar();
		jBarraVertical.setEnabled(false);
		jBarraVertical.setOrientation(JScrollBar.VERTICAL);
		jBarraVertical.addAdjustmentListener(this);
		
		//Instancia��o da barra de rolagem Horizontal
		jBarraHorizontal = new JScrollBar();
		jBarraHorizontal.setEnabled(false);
		jBarraHorizontal.setOrientation(JScrollBar.HORIZONTAL);
		jBarraHorizontal.addAdjustmentListener(this);
		
		jAreaGrafo = new AreaAplicativo(this, frame);
		
		//Inser��o dos objetos no EditorGrafo...
		//Configura��o dos parametros da �rea de plotagem...
		componenteLayout.fill = GridBagConstraints.BOTH;
		componenteLayout.gridx = 0;
		componenteLayout.gridy = 0;
		componenteLayout.gridheight = 1;
		componenteLayout.gridwidth = 1;
		componenteLayout.weightx = 2.0;
		componenteLayout.weighty = 2.0;
		layoutJanela.setConstraints(jAreaGrafo, componenteLayout);
		jAreaLayout.add(jAreaGrafo);
		
		//Configura��o dos parametros para a BarraVertical...
		componenteLayout.fill = GridBagConstraints.VERTICAL;
		componenteLayout.gridx = 1;
		componenteLayout.gridy = 0;
		componenteLayout.gridheight = 1;
		componenteLayout.gridwidth = 1;
		componenteLayout.weightx = 0;
		componenteLayout.weighty = 0;
		layoutJanela.setConstraints(jBarraVertical, componenteLayout);
		jAreaLayout.add(jBarraVertical);
		
		//Configura��o dos parametros para a BarraHorizontal...
		componenteLayout.fill = GridBagConstraints.HORIZONTAL;
		componenteLayout.gridx = 0;
		componenteLayout.gridy = 1;
		componenteLayout.gridheight = 1;
		componenteLayout.gridwidth = 1;
		componenteLayout.weightx = 0;
		componenteLayout.weighty = 0;
		layoutJanela.setConstraints(jBarraHorizontal, componenteLayout);
		jAreaLayout.add(jBarraHorizontal);
		
		jEditorApplet.setVisible(true);
	}
	
	/**
	 * Cria um editor applet padr�o utilizando as classes do pacote swing e com 
	 * um grafo apresentado. Este editor poder� ser editado pelo usu�rio final.
	 *
	 * @param jEditorApplet a JApplet onde o editor ser� criado
	 * @param grafo o grafo a ser apresentado
	 * @throws NullPointerException em caso da JApplet ser nula
	 */
	public EditorAppletGrafo(JApplet jEditorApplet, Grafo grafo, GUI frame)
	{
		this(jEditorApplet);
		setGrafo(grafo);
		this.frame = frame;
		setarBarraHorizontal();
		setarBarraVertical();
	}
	
//M�todos Get...

	/**
	 * Retorna o grafo atual do editor.
	 *
	 * @return o grafo apresentado
	 */
	public Grafo getGrafo()
	{
		return grafo;
	}
	
	/**
	 * Retorna a Applet atual onde o editor est� inserido.
	 *
	 * @return o objeto Applet
	 */
	public Applet getEditorApplet()
	{
		return editorApplet;	
	}
	
	/**
	 * Retorna a JApplet atual onde o editor est� inserido.
	 *
	 * @return o objeto JApplet
	 */
	public JApplet getJEditorApplet()
	{
		return jEditorApplet;
	}
	
	/**
	 * Retorna o menu do pacote awt utilizado quando ocorrer um evento sobre 
	 * uma aresta.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es da aresta
	 */
	public MenuMouse getMenuMouseAresta()
	{
		return areaGrafo.getMenuMouseAresta();
	}
	
	/**
	 * Retorna o menu do pacote awt utilizado quando ocorrer um evento sobre 
	 * um r�tulo.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es do r�tulo
	 */
	public MenuMouse getMenuMouseRotulo()
	{
		return areaGrafo.getMenuMouseRotulo();
	}
	
	/**
	 * Retorna o menu do pacote awt utilizado quando ocorrer um evento sobre 
	 * um v�rtice.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es do v�rtice
	 */
	public MenuMouse getMenuMouseVertice()
	{
		return areaGrafo.getMenuMouseVertice();
	}
	
	/**
	 * Retorna o menu do pacote awt utilizado quando ocorrer um evento sobre nenhum 
	 * dos componentes. Este menu possui op��es genericas do grafo.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es gerais
	 */
	public MenuMouse getMenuMouse()
	{
		return areaGrafo.getMenuMouse();
	}
	
	/**
	 * Retorna o menu do pacote swing utilizado quando ocorrer um evento sobre 
	 * uma aresta.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es da aresta
	 */
	public JMenuMouse getJMenuMouseAresta()
	{
		return jAreaGrafo.getJMenuMouseAresta();
	}
	
	/**
	 * Retorna o menu do pacote swing utilizado quando ocorrer um evento sobre 
	 * um r�tulo.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es do r�tulo
	 */
	public JMenuMouse getJMenuMouseRotulo()
	{
		return jAreaGrafo.getJMenuMouseRotulo();
	}
	
	/**
	 * Retorna o menu do pacote swing utilizado quando ocorrer um evento sobre 
	 * um v�rtice.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es do v�rtice
	 */
	public JMenuMouse getJMenuMouseVertice()
	{
		return jAreaGrafo.getJMenuMouseVertice();
	}
	
	/**
	 * Retorna o menu do pacote swing utilizado quando ocorrer um evento sobre nenhum 
	 * dos componentes. Este menu possui op��es genericas do grafo.
	 *
	 * @return o menu que cont�m as op��es para altera��o das informa��es gerais
	 */
	public JMenuMouse getJMenuMouse()
	{
		return jAreaGrafo.getJMenuMouse();
	}
	
	/**
	 * Retorna o valor que define se o grafo pode ser editado pelo usu�rio final.
	 * 
	 *
	 * @return o valor que definindo se o grafo pode ser editado ou somente apresentado
	 * @see EditorAppletGrafo#EDITAR
	 * @see EditorAppletGrafo#NAO_EDITAR
	 */
	public int getEditar()
	{
		return editar;
	}
	
//M�todos Set..

	/**
	 * Altera o grafo apresnetado no editor.
	 *
	 * @param grafo o novo grafo a ser apresentado
	 */
	public void setGrafo(Grafo grafo)
	{
		this.grafo = grafo;
		
		if (tipoApplet == 1)
			areaGrafo.setGrafo(grafo);
		else
			jAreaGrafo.setGrafo(grafo);
		
		setarBarraHorizontal();
		setarBarraVertical();
		repintarArea();
	}
	
	/**
	 * Altera o menu do pacote awt utilizado pelo GEDE quando houver um evento 
	 * sobre uma aresta.
	 *
	 * @param menuMouse o menu de aresta do editor
	 */
	public void setMenuMouseAresta(MenuMouse menuMouse)
	{
		areaGrafo.setMenuMouseAresta(menuMouse);
	}
	
	/**
	 * Altera o menu do pacote awt utilizado pelo GEDE quando houver um evento 
	 * sobre um r�tulo.
	 *
	 * @param menuMouse o menu do r�tulo do editor
	 */
	public void setMenuMouseRotulo(MenuMouse menuMouse)
	{
		areaGrafo.setMenuMouseRotulo(menuMouse);
	}
	
	/**
	 * Altera o menu do pacote awt utilizado pelo GEDE quando houver um evento 
	 * sobre um v�rtice.
	 *
	 * @param menuMouse o menu de v�rtice do editor
	 */
	public void setMenuMouseVertice(MenuMouse menuMouse)
	{
		areaGrafo.setMenuMouseVertice(menuMouse);
	}
	
	/**
	 * Altera o menu do pacote awt utilizado pelo GEDE quando houver um evento 
	 * sobre nenhum dos componentes contidos na �rea de apresenta��o.
	 *
	 * @param menuMouse o menu do editor
	 */
	public void setMenuMouse(MenuMouse menuMouse)
	{
		areaGrafo.setMenuMouse(menuMouse);
	}
	
	/**
	 * Altera o menu do pacote swing utilizado pelo GEDE quando houver um evento 
	 * sobre uma aresta.
	 *
	 * @param menuMouse o menu de aresta do editor
	 */
	public void setMenuMouseAresta(JMenuMouse menuMouse)
	{
		jAreaGrafo.setJMenuMouseAresta(menuMouse);
	}
	
	/**
	 * Altera o menu do pacote swing utilizado pelo GEDE quando houver um evento 
	 * sobre um r�tulo.
	 *
	 * @param menuMouse o menu do r�tulo do editor
	 */
	public void setMenuMouseRotulo(JMenuMouse menuMouse)
	{
		jAreaGrafo.setJMenuMouseRotulo(menuMouse);
	}
	
	/**
	 * Altera o menu do pacote swing utilizado pelo GEDE quando houver um evento 
	 * sobre um v�rtice.
	 *
	 * @param menuMouse o menu de v�rtice do editor
	 */
	public void setMenuMouseVertice(JMenuMouse menuMouse)
	{
		jAreaGrafo.setJMenuMouseVertice(menuMouse);
	}
	
	/**
	 * Altera o menu do pacote swing utilizado pelo GEDE quando houver um evento 
	 * sobre nenhum dos componentes contidos na �rea de apresenta��o.
	 *
	 * @param menuMouse o menu do editor
	 */
	public void setMenuMouse(JMenuMouse menuMouse)
	{
		jAreaGrafo.setJMenuMouse(menuMouse);
	}
	
	
	/**
	 * Altera a informa��o do editor para permitir ou n�o edi��o do grafo.
	 *
	 * @param editar o valor indicado se o grafo pode ser ou n�o editado
	 * @see EditorAppletGrafo#EDITAR
	 * @see EditorAppletGrafo#NAO_EDITAR
	 */
	public void setEditar(int editar)
	{
		if ((editar == NAO_EDITAR) || (editar == EDITAR))
			this.editar = editar;
		else
			this.editar = EDITAR;
	}
	
	/**
	 * Chama o m�todo <B>repaint</B> da classe AreaAplicativo  ou da classe AreaApplet
	 * para atualizar a apresenta��o do grafo na tela.
	 */
	public void repintarArea()
	{
		if (tipoApplet == 1)
			areaGrafo.repaint();
		else
			jAreaGrafo.repaint();
	}
	
	/**
	 * Atualiza os valores das barras de rolagem horizontal e vertical. Este m�todo 
	 * � chamado automaticamente pela classe AreaAplicativo quando o usu�rio final 
	 * arrasta um componente dentro do editor.
	 */
	public void setBarrasRolagem()
	{
		setarBarraHorizontal();
		setarBarraVertical();
	}
	
// M�todos de tratamento de eventos

	/**
	 * M�todo chamado pela classe AreaAplicativo ou AreaApplet sempre que o usu�rio 
	 * final pressionar a tecla CTRL e movimentar o mouse sobre um componente 
	 * contido dentrio do editor. Ele � muito util quando deseja-se chamar uma 
	 * nova URL na Web ou executar um determinada tarefa.
	 *
	 * Para o pacote GEDE, nenhuma implementa��o deste m�todo � feita e portanto 
	 * as novas aplica��es dever�o sobrescrever este m�todo para executar suas
	 * tarefas.
	 *
	 * @param e o evento de mouse gerado sobre o componente
	 * @param objetoClicado o componente do editor que sofreu a chamada do evento
	 */
	public void movimentoMouse(MouseEvent e, Object objetoClicado){}
	
	/**
	 * M�todo chamado pela classe AreaAplicativo  ou AreaApplet sempre que usu�rio
	 * final executar um clique duplo sobre algum componente do editor.
	 *
	 * Para o pacote GEDE, nenhuma implementa��o deste m�todo � feita e portanto 
	 * as novas aplica��es dever�o sobrescrever este m�todo para executar suas
	 * tarefas.
	 *
	 * @param e o evento de mouse gerado sobre o componente
	 * @param objetoClicado o componente do editor que sofreu a chamada do evento
	 */
	public void cliqueDuploMouse(MouseEvent e, Object objetoClicado){}
	
	/**
	 * Este m�todo � chamado sempre que o usu�rio final movimetar as barras de 
	 * rolagem do editor. Ele atualiza a AreaAplicativo  ou a AreaApplet onde o 
	 * grafo est� sendo apresentado.
	 *
	 * @param e o evento de barra gerado pelo usu�rio
	 */
	public void adjustmentValueChanged(AdjustmentEvent e)
	{
		if (tipoApplet == 1)
		{
			if (e.getSource() == barraHorizontal)
			{
				//Move o painel na Horizontal
				areaGrafo.setBounds(barraHorizontal.getValue() * -1,
									areaGrafo.getY(),
									editorApplet.getWidth()-barraVertical.getWidth() + barraHorizontal.getValue(),
									editorApplet.getHeight()-barraHorizontal.getHeight() + barraVertical.getValue());
				
				posicaoAtualBarraHorizontal = barraHorizontal.getValue();
			}
			
			if (e.getSource() == barraVertical)
			{
				//Move o painel na Vertical...
				areaGrafo.setBounds(areaGrafo.getX(),
									barraVertical.getValue() * -1,
									editorApplet.getWidth()-barraVertical.getWidth() + barraHorizontal.getValue(),
									editorApplet.getHeight()-barraHorizontal.getHeight() + barraVertical.getValue());
	
				posicaoAtualBarraVertical = barraVertical.getValue();
			}
		}
		else
		{
			if (e.getSource() == jBarraHorizontal)
			{
				//Move o painel na Horizontal
				jAreaGrafo.setBounds(jBarraHorizontal.getValue() * -1,
									jAreaGrafo.getY(),
									jEditorApplet.getWidth() - jBarraVertical.getWidth() + jBarraHorizontal.getValue(),
									jEditorApplet.getHeight() - jBarraHorizontal.getHeight() + jBarraVertical.getValue());
				
				posicaoAtualBarraHorizontal = jBarraHorizontal.getValue();
			}
			
			if (e.getSource() == jBarraVertical)
			{
				//Move o painel na Vertical...
				jAreaGrafo.setBounds(jAreaGrafo.getX(),
									jBarraVertical.getValue() * -1,
									jEditorApplet.getWidth() - jBarraVertical.getWidth() + jBarraHorizontal.getValue(),
									jEditorApplet.getHeight() - jBarraHorizontal.getHeight() + jBarraVertical.getValue());
	
				posicaoAtualBarraVertical = jBarraVertical.getValue();
			}
		}
	}
	
//	M�todos privados
	private void setarBarraHorizontal()
	{
		int maiorPosicaoX;
		
		maiorPosicaoX = verificarMaiorPosicaoX() + DISTANCIA_EIXO_X;
		
		if (tipoApplet == 1)
		{
			if (maiorPosicaoX > editorApplet.getWidth())
			{
				barraHorizontal.setEnabled(true);
				barraHorizontal.setMinimum(0);
				barraHorizontal.setMaximum(maiorPosicaoX - editorApplet.getWidth());
				barraHorizontal.setValue(posicaoAtualBarraHorizontal);
				barraHorizontal.setUnitIncrement(20);
			}
			else
			{
				areaGrafo.setBounds(0, 0, editorApplet.getWidth() - barraVertical.getWidth(), editorApplet.getHeight() - barraHorizontal.getHeight());
				barraHorizontal.setEnabled(false);
				posicaoAtualBarraHorizontal = 0;
			}
		}
		else
		{
			if (maiorPosicaoX > jEditorApplet.getWidth())
			{
				jBarraHorizontal.setEnabled(true);
				jBarraHorizontal.setMinimum(0);
				jBarraHorizontal.setMaximum(maiorPosicaoX - jEditorApplet.getWidth());
				jBarraHorizontal.setValue(posicaoAtualBarraHorizontal);
				jBarraHorizontal.setUnitIncrement(20);
			}
			else
			{
				jAreaGrafo.setBounds(0, 0, jEditorApplet.getWidth() - jBarraVertical.getWidth(), jEditorApplet.getHeight() - jBarraHorizontal.getHeight());
				jBarraHorizontal.setEnabled(false);
				posicaoAtualBarraHorizontal = 0;
			}
		}
	}
	
	private void setarBarraVertical()
	{
		int maiorPosicaoY;
		
		maiorPosicaoY = verificarMaiorPosicaoY() + DISTANCIA_EIXO_Y;
		
		if (tipoApplet == 1)
		{
			if (maiorPosicaoY > editorApplet.getHeight())
			{
				barraVertical.setEnabled(true);
				barraVertical.setMinimum(0);
				barraVertical.setMaximum(maiorPosicaoY - editorApplet.getHeight());
				barraVertical.setValue(posicaoAtualBarraVertical);
				barraVertical.setUnitIncrement(20);
			}
			else
			{
				areaGrafo.setBounds(0, 0, editorApplet.getWidth() - barraVertical.getWidth(), editorApplet.getHeight() - barraHorizontal.getHeight());
				barraVertical.setEnabled(false);
				posicaoAtualBarraVertical = 0;
			}
		}
		else
		{
			if (maiorPosicaoY > jEditorApplet.getHeight())
			{
				jBarraVertical.setEnabled(true);
				jBarraVertical.setMinimum(0);
				jBarraVertical.setMaximum(maiorPosicaoY - jEditorApplet.getHeight());
				jBarraVertical.setValue(posicaoAtualBarraVertical);
				jBarraVertical.setUnitIncrement(20);
			}
			else
			{
				jAreaGrafo.setBounds(0, 0, jEditorApplet.getWidth() - jBarraVertical.getWidth(), jEditorApplet.getHeight() - jBarraHorizontal.getHeight());
				jBarraVertical.setEnabled(false);
				posicaoAtualBarraVertical = 0;
			}
		}
	}
	
	private int verificarMaiorPosicaoX()
	{
		int 	i, j, eixoX, quebras[];
		Vertice verticeTemp;
		Aresta	arestas[];
		
		eixoX = 0;
		
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			verticeTemp = grafo.getVertice(i);
			
			if (verticeTemp.getX() > eixoX)
				eixoX = verticeTemp.getX();
		}
		
		arestas = grafo.getTodasArestas();
		if (arestas != null)
		{
			for (i = 0; i < arestas.length; i++)
			{
				quebras = arestas[i].getTodasQuebrasX();
				
				if (quebras != null)
				{
					for (j = 0; j < quebras.length; j++)
					{
						if (quebras[j] > eixoX)
							eixoX = quebras[j];
					}
				}
			}
		}
		
		return eixoX;
	}
	
	private int verificarMaiorPosicaoY()
	{
		int 	i, j, eixoY, quebras[];
		Vertice verticeTemp;
		Aresta	arestas[];
		
		eixoY = 0;
		
		for (i = 0; i < grafo.getNumeroTotalVertices(); i++)
		{
			verticeTemp = grafo.getVertice(i);
			
			if (verticeTemp.getY() > eixoY)
				eixoY = verticeTemp.getY();
		}
		
		arestas = grafo.getTodasArestas();
		if (arestas != null)
		{
			for (i = 0; i < arestas.length; i++)
			{
				quebras = arestas[i].getTodasQuebrasY();
				
				if (quebras != null)
				{
					for (j = 0; j < quebras.length; j++)
					{
						if (quebras[j] > eixoY)
							eixoY = quebras[j];
					}
				}
			}
		}
		
		return eixoY;
	}
}