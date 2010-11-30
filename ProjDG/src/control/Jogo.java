/* Projeto Academico de Construcao de um jogo em Java. O jogo escolhido foi o DAMA
 *
 * Faculdade Jorge Amado
 * Curso de Desenvolvimento de Software
 * Autores: Davi Sande, Rodrigo Valentim, Ueber Lima
 *
 */
package control;

import exception.ListaVaziaException;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import networkgame.clienteserver.Cliente;
import networkgame.clienteserver.ServicoCliente;
import networkgame.servidor.Servico;
import service.Chat;
import service.Jogador;
import service.Peca;
import service.Placar;
import service.Tabuleiro;
import utils.ImagemLoad;

public class Jogo extends JFrame implements ServicoCliente {

    private Jogador[] jogadores;
    private Tabuleiro tabuleiro;
    private Placar placar;
    private Chat chat;
    private static final int valAjusteClick = 10;
    private boolean novaJogada;
    private int jogadorDaVez;
    private int oldX;
    private int oldY;
    private Cliente cliente;
    private int idPlayer;
    private static BufferedImage imagem;
    private final String fundo = "imagem/backgroundFloor.png";
    private boolean reAnaliseMovimento = false;

    public Jogo(final Jogador jogador1, final Jogador jogador2) {
        /*
         * Colocando titulo na tela principal
         */
        setUndecorated(true);
        imagem = new ImagemLoad().imageLoader(fundo);
        final JDesktopPane guiDamas = new JDesktopPane() {

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagem != null) {
                    g.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(), this);
                } else {
                    g.drawString("Image not found", 50, 50);
                }
            }
        };
        getContentPane().add(guiDamas);
        setResizable(false); //impossibilitando o resize
        /*
         * Escutador do click do botao close para fechar o jogo.
         */
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        /*
         * Inicializando Variaveis
         */
        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);
        JMenu menuConexao = new JMenu("Conexão");
        JMenuItem mItemServidor = new JMenuItem("Iniciar Servidor");
        mItemServidor.setMnemonic('S');
        mItemServidor.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        new Servico(false).iniciaServidor();
                        setIdPlayer(0);
                        iniciaCliente(guiDamas, jogador1, jogador2, "localhost");
                    }
                });
        menuConexao.add(mItemServidor);
        JMenuItem mItemCliente = new JMenuItem("Iniciar Cliente");
        mItemCliente.setMnemonic('C');
        menuConexao.addSeparator();
        mItemCliente.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        String ip = JOptionPane.showInputDialog(null,
                                "Informe o IP/HOST do Servidor",
                                "Endereco do Servidor",
                                JOptionPane.QUESTION_MESSAGE);
                        setIdPlayer(1);
                        iniciaCliente(guiDamas, jogador1, jogador2, ip);
                    }
                });
        menuConexao.add(mItemCliente);
        JMenuItem mItemMsg = new JMenuItem("Enviar Mensagem");
        mItemMsg.setMnemonic('M');
        menuConexao.addSeparator();
        mItemMsg.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                    }
                });
        menuConexao.add(mItemCliente);
        menu.add(menuConexao);
        JMenu menuJogo = new JMenu("Jogo");
        menuJogo.setMnemonic('J');

        JMenuItem mItemSair = new JMenuItem("Sair");
        mItemSair.setMnemonic('S');
        mItemSair.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        System.exit(0);
                    }
                });
        menuJogo.add(mItemSair);
        menuJogo.setMnemonic('C');
        menu.add(menuJogo);
        this.setOldX(-1);
        this.setOldY(-1);
        setJogadorDaVez(0); //Jogador da Vez - Inicia com o jogador 0
        jogadores = new Jogador[2];
        setJogadores(jogador1.getId(), jogador1);
        setJogadores(jogador2.getId(), jogador2);
    }

    public void iniciaCliente(JDesktopPane guiDamas, Jogador jogador1, Jogador jogador2, String ip) {
        if (!ip.equals("")) {
            try {
                cliente = new Cliente(this);
                cliente.iniciaCliente(ip, 1235);
                placar = new Placar(getJogadores()[jogador1.getId()], getJogadores()[jogador2.getId()]);
                tabuleiro = new Tabuleiro();
                setChat(new Chat());
                distribuirPedras();
                posicionarPedras(getJogadores()[jogador1.getId()]);
                posicionarPedras(getJogadores()[jogador2.getId()]);
//                getChat().mostra(guiDamas);
                getPlacar().mostra(guiDamas); //Exibe o placar na tela principal
                getTabuleiro().mostra(guiDamas); //Exibe o tabuleiro na tela principal
                /*
                 * Inicializando Listener para ouvir o click do mouse
                 */
                getTabuleiro().addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {
                        capturaClicks(mouseEvent);
                    }
                });
            } catch (UnknownHostException ex) {
                JOptionPane.showInputDialog(null,
                        "Impossível iniciar jogo, IP (" + ip + ") incorreto",
                        "Erro!",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showInputDialog(null,
                        "Impossível iniciar jogo, IP (" + ip + ") incorreto",
                        "Erro!",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showInputDialog(null,
                    "Impossível iniciar jogo, IP/HOST em branco",
                    "Erro!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void capturaMensagem(String mensagem) {
        System.out.println("Mensagem " + mensagem);

        String[] dados = mensagem.split(",");

//        System.out.println("Tamanho do Dados " + dados.length);
//        if (dados[0].compareTo("MOVE") == 0) {
//            move(Integer.parseInt(dados[1]), Integer.parseInt(dados[2]), Integer.parseInt(dados[3]), Integer.parseInt(dados[4]));
//        }
//
//        if (dados[0].compareTo("OLD") == 0) {
//            setOldX(Integer.parseInt(dados[1]));
//            setOldY(Integer.parseInt(dados[2]));
//        }
//
//        if (dados[0].compareTo("MUDAJOGADOR") == 0) {
//            mudarJogadorVez();
//        }
//
//        if (dados[0].compareTo("ANALISTATIPOMOVIMENTO") == 0) {
//            analistaTipoMovimento(Integer.parseInt(dados[1]), Integer.parseInt(dados[2]), reAnaliseMovimento);
//        }
//        System.out.println(dados[0]);
//        if (dados[0].compareTo("HIDECASASELECIONADA") == 0) {
//            hideCasaSelecionada();
//        }
//
//        if (dados[0].compareTo("HIDEMOVIMENTOSPOSSIVEIS") == 0) {
//            hideMovimentosPossiveis();
//        }
//
//        if (dados[0].compareTo("SETCASASELECIONADA") == 0) {
//            getTabuleiro().getCasas()[Integer.parseInt(dados[1])][Integer.parseInt(dados[2])].setCasaSelecionada(true, Color.black);
//        }
//        if (dados[0].compareTo("NOVAJOGADA") == 0 && dados[0].compareTo("TRUE") == 0) {
//            novaJogada = true;
//        } else {
//            if (dados[0].compareTo("NOVAJOGADA") == 0 && dados[0].compareTo("false") == 0) {
//                novaJogada = false;
//            }
//        }
//
//        if (dados[0].compareTo("REANALISEMOVIMENTO") == 0 && dados[1].compareTo("TRUE") == 0) {
//            reAnaliseMovimento = true;
//        } else {
//            if (dados[0].compareTo("REANALISEMOVIMENTO") == 0 && dados[1].compareTo("FALSE") == 0) {
//                reAnaliseMovimento = false;
//            }
//        }
//
//        if (dados[0].compareTo("RETIRAPECA") == 0) {
//            try {
//                retiraPeca(Integer.parseInt(dados[1]), Integer.parseInt(dados[2]));
//            } catch (IOException ex) {
//                Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

        movimentoServidor(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]));

//        if (dados.length == 1) {
//            System.out.println("jogador da vez - Atualizando " + getJogadorDaVez());
//            setJogadorDaVez(Integer.parseInt(dados[0]));
//            System.out.println("jogador da vez - Atualizado " + getJogadorDaVez());
//        }
    }

    private void executaJogada(int getX, int getY) throws IOException {
//        System.out.println("Id do player " + getIdPlayer() + " - " + getJogadorDaVez());
        /*
         * Evento que dispara quando o botao do mouse e solto.
         *
         * super.getWidth serve para pegar a largura do componente
         *
         * super.getHeight serve para pegar a altura do componente
         *
         * A divisao por 8 e necessaria por que temos 8 casas
         *
         * Y esta recebendo testaMovimentos e X esta recebendo Y por que o mouseEvent interte isso, pra nos ajudar, claro.
         * Atraves destes testes, leitura de documentacoes
         * http://download.oracle.com/javase/1.4.2/docs/api/java/awt/event/MouseEvent.html
         * indentificamos que o MouseEvent retorna X como sendo linha e o Y como sendo coluna.
         * Invertendo as posicoes, tudo se resolveu.
         *
         *
         * O valor de ajuste igual a 20, quando subtraido da linha, temos uma maior precisao
         * no calculo nos possibilitando clicar bem proximo da borda da casa sem que ele pegue
         * a casa errada. Sem esse valor de ajuste, ao nos aproximarmos das bordas, ele pegava a casa vizinha.
         *
         */
        int y = (getX) / (getTabuleiro().getWidth() / 8);
        int x = (getY - valAjusteClick) / (getTabuleiro().getHeight() / 8);

        /*
         * Variavel abaixo usada para configurar a reanalise dos movimentos
         */
//        cliente.enviaMensagem("REANALISEMOVIMENTO" + "," + "FALSE");
        reAnaliseMovimento = false;
        /*
         * Metodo set da rotina que configura novaJogada
         */
//        cliente.enviaMensagem("NOVAJOGADA" + "," + "FALSE");
        setNovaJogada(false);
        /*
         * Rotina abaixo remover que foram "comidas" pelo adversario
         */
//        System.out.println("x" + x + "y" + y);
//        if (getTabuleiro().getCasas()[x][y].isMovimentoPossivel()) {
//            System.out.println("Casa Movimento possivel");
//        } else {
//            System.out.println("Casa Movimento Não possivel");
//        }
        if (getTabuleiro().getCasas()[x][y].isMovimentoPossivel()) {
            /*
             * Calculo usado para saber se o movimento realizado foi de mais de uma casa
             * Caso posivito, entra na rotina de analise de possiveis pecas comidas
             */
            if (Math.abs(getOldY() - y) >= 2) {
                int l = getOldX(), c = getOldY();
                if (x > l && y > c) {
                    while (getTabuleiro().getCasas()[l++][c++].isCasaPossivel()
                            && l < x
                            && c < y) {
                        if (getTabuleiro().getCasas()[l][c].getPedra() != null) {
//                            cliente.enviaMensagem("RETIRAPECA" + "," + l + "," + c);
                            retiraPeca(l, c);
                        }
                    }
                }
                l = getOldX();
                c = getOldY();
                if (x > l && y < c) {
                    while (getTabuleiro().getCasas()[l++][c--].isCasaPossivel()
                            && l < x
                            && c > y) {
                        if (getTabuleiro().getCasas()[l][c].getPedra() != null) {
//                            cliente.enviaMensagem("RETIRAPECA" + "," + l + "," + c);
                            retiraPeca(l, c);
                        }
                    }
                }
                l = getOldX();
                c = getOldY();
                if (x < l && y > c) {
                    while (getTabuleiro().getCasas()[l--][c++].isCasaPossivel()
                            && l > x
                            && c < y) {
                        if (getTabuleiro().getCasas()[l][c].getPedra() != null) {
//                            cliente.enviaMensagem("RETIRAPECA" + "," + l + "," + c);
                            retiraPeca(l, c);
                        }
                    }
                }
                l = getOldX();
                c = getOldY();
                if (x < l && y < c) {
                    while (getTabuleiro().getCasas()[l--][c--].isCasaPossivel()
                            && l > x
                            && c > y) {
                        if (getTabuleiro().getCasas()[l][c].getPedra() != null) {
//                            cliente.enviaMensagem("RETIRAPECA" + "," + l + "," + c);
                            retiraPeca(l, c);
                        }
                    }
                }
                /*
                 * Rotina de movimentacao da peca, da origem pro destino
                 */
//                cliente.enviaMensagem("MOVE" + getOldX() + "," + getOldY() + "," + x + "," + y);
                move(getOldX(), getOldY(), x, y);
//                cliente.enviaMensagem("OLD" + "," + x + "," + y);
                setOldX(x);
                setOldY(y);
//                cliente.enviaMensagem("REANALISEMOVIMENTO" + "," + "TRUE");
                reAnaliseMovimento = true;
                /*
                 * Re analisa os movimentos para saber se e possivel comer mais pedras antes de parar
                 */
//                cliente.enviaMensagem("ANALISTATIPOMOVIMENTO" + "," + x + "," + y);
                analistaTipoMovimento(x, y, reAnaliseMovimento);
            } else {
                /*
                 * Simples rotina de movimento, sem acao de comer
                 */
//                cliente.enviaMensagem("MOVE" + "," + getOldX() + "," + getOldY() + "," + x + "," + y);
                move(getOldX(), getOldY(), x, y);
            }
            /*
             * Controla vez do jgoador
             */
            if (!isNovaJogada()) {
//                cliente.enviaMensagem("MUDAJOGADOR");
                mudarJogadorVez();
//                cliente.enviaMensagem("REANALISEMOVIMENTO" + "," + "FALSE");
                reAnaliseMovimento = false;
            }
        }
        /*
         * Limpa casas selecionads
         */
//        cliente.enviaMensagem("HIDECASASELECIONADA");
        hideCasaSelecionada();
        /*
         * limpa Movimentos selecionados
         */
//        cliente.enviaMensagem("HIDEMOVIMENTOSPOSSIVEIS");
        hideMovimentosPossiveis();
//
        if (getTabuleiro().getCasas()[x][y].isCasaPossivel()
                && getTabuleiro().getCasas()[x][y].getPedra() != null
                && getTabuleiro().getCasas()[x][y].getPedra().getIdOwner() == getIdPlayer()
                && getTabuleiro().getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()) {
            /*
             * seta como selecionada a casa, pintando sua borda
             */
//            cliente.enviaMensagem("SETCASASELECIONADA" + "," + x + "," + y);
            getTabuleiro().getCasas()[x][y].setCasaSelecionada(true, Color.black);
//            cliente.enviaMensagem("OLD" + "," + x + "," + y);
            setOldX(x);
            setOldY(y);
//            cliente.enviaMensagem("ANALISTATIPOMOVIMENTO" + "," + getOldX() + "," + getOldY());
            analistaTipoMovimento(getOldX(), getOldY(), reAnaliseMovimento);
        }
    }

    private void movimentoServidor(int getX, int getY) {
        try {
            executaJogada(getX, getY);
        } catch (IOException ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void capturaClicks(MouseEvent e) {
        try {
            executaJogada(e.getX(), e.getY());
            cliente.enviaMensagem(String.valueOf(e.getX()) + "," + String.valueOf(e.getY()));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao enviar mensagem ao servidor");
        }
    }
    /*
     * Metodo Distribuit Pedras e responsalvel por colocar na mao dos jogores
     * suas respectivas pedras
     */

    public void distribuirPedras() {
        for (int i = 1; i <= 12; i++) {
            getJogadores()[0].addPedra(new Peca(getJogadores()[0].getId(), i, Color.white));
        }
        for (int i = 13; i <= 24; i++) {
            getJogadores()[1].addPedra(new Peca(getJogadores()[1].getId(), i, Color.black));
        }
    }

    /*
     * Classe posicionar Pedras eh responavel por posiconar as pedras em seus lugares
     * usando como base uma matriz logica feita para isso.
     */
    public void posicionarPedras(Jogador jogador) {
        /*
         * jogador 1 inicia na parte superior do tabuleiro com as pedras pretas
         * jogador 2 inicia na parte inferior do tabuleiro com as pedras brancas
         */

        /*
         * distribuindo as 12 pecas
         */
        for (int pecas = 0; pecas < 12; pecas++) {
            /*
             * cada jogador tem 24 casas (12 utilizadas e 12 inutilizadas)
             */
            for (int qtdCasas = 0; qtdCasas < 24; qtdCasas++) {
                /*
                 * O calculo usado abaixo, eh a forma mais precisa de identificar a posicao testaMovimentos e y da matriz logica.
                 *
                 * Se tenho posicao 0 + qtdCasas que vai de 0 ate ate 23, entao, fazendo o calculo usando DIV e MOD, obtenho exatamente
                 * a posicao X e Y
                 * [0,0][0,1][0,2][0,3][0,4][0,5][0,6][0,7]
                 * [1,0][1,1][1,2][1,3][1,4][1,5][1,6][1,7]
                 * [2,0][2,1][2,2][2,3][2,4][2,5][2,6][2,7]
                 * [3,0][3,1][3,2][3,3][3,4][3,5][3,6][3,7]
                 * [4,0][4,1][4,2][4,3][4,4][4,5][4,6][4,7]
                 * [5,0][5,1][5,2][5,3][5,4][5,5][5,6][5,7]
                 * [6,0][6,1][6,2][6,3][6,4][6,5][6,6][6,7]
                 * [7,0][7,1][7,2][7,3][7,4][7,5][7,6][7,7]
                 *
                 * Com base na informacao booleana da casa, vejo se ela eh possivel de movimento ou nao, ou seja, casa clara ou escura
                 * alem disso vejo se a casa atual ja esta ocupada para por uma outra pedra no local.
                 *
                 */
                if ((getTabuleiro().getCasas()[((getJogadores()[jogador.getId()].getPosicaoInicial() + qtdCasas) / 8)][((getJogadores()[jogador.getId()].getPosicaoInicial() + qtdCasas) % 8)].isCasaPossivel())
                        && (getTabuleiro().getCasas()[((getJogadores()[jogador.getId()].getPosicaoInicial() + qtdCasas) / 8)][((getJogadores()[jogador.getId()].getPosicaoInicial() + qtdCasas) % 8)].getPedra() == null)) {
                    try {
                        /*
                         * setando as pecas que estao na mao do jogador na casa informada como possivel
                         */
                        getTabuleiro().getCasas()[((getJogadores()[jogador.getId()].getPosicaoInicial() + qtdCasas) / 8)][((getJogadores()[jogador.getId()].getPosicaoInicial() + qtdCasas) % 8)].setPedra(getJogadores()[jogador.getId()].getPedras().buscar(pecas));
                    } catch (ListaVaziaException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage());
                    } /*
                     * BREAK
                     * O Break foi usado para quando a condicao for atendida,
                     * sair do loop e ir para proxima casa, pegando o proximo ID da peca
                     */
                    break;
                }
            }
        }
    }

    private void setDama(int linha, int coluna) {
        getTabuleiro().promovePedra(getJogadorDaVez(), linha, coluna);
    }

    /*
     * Metodo MOVE remove a pedra da casa de origem e coloca o mesmo na casa de destino.
     * Caso a casa de destino seja a primeira linha superior (posicao 0 do array) ou inferior (posicao 7 do array)
     * O metodo promovePedra eh chamado.
     */
    private void move(int linhaCasaOrigem, int colunaCasaOrigem, int linhaCasaDestino, int colunaCasaDestino) {
        getTabuleiro().getCasas()[linhaCasaDestino][colunaCasaDestino].setPedra(getTabuleiro().getCasas()[linhaCasaOrigem][colunaCasaOrigem].getPedra());
        getTabuleiro().getCasas()[linhaCasaOrigem][colunaCasaOrigem].retiraPedra();
        if (((getJogadorDaVez() == 0) && (linhaCasaDestino == 7)) || ((getJogadorDaVez() == 1) && (linhaCasaDestino == 0))) {
            setDama(linhaCasaDestino, colunaCasaDestino);
        }
    }

    private void hideCasaSelecionada() {
        /*
         * Metodo desativaSelecao - Metodo utilizado para desabilitar a
         * selecao apos a jogada ou caso selecionado outra pedra
         */
        for (int linha = 0; linha
                < 8; linha++) {
            for (int coluna = 0; coluna
                    < 8; coluna++) {
                getTabuleiro().getCasas()[linha][coluna].setCasaSelecionada(false, getTabuleiro().getCorCasaEscura());
                setOldX(-1);
                setOldY(-1);
            }
        }
    }

    /*
     * remove e pedra
     */
    private void retiraPeca(int x, int y) throws IOException {
        getJogadores()[getJogadorDaVez()].setPontos(getJogadores()[getJogadorDaVez()].getPontos() + 1);
        getTabuleiro().getCasas()[x][y].retiraPedra();
        getPlacar().atualizarPlacar(getJogadorDaVez());
        getPlacar().verificaVencedor(this);
    }

    /*
     * Metodo criado para desmarcar as casas possiveis selecionadas.
     */
    private void hideMovimentosPossiveis() {
//        System.out.println("Limpando movimentos possiveis " + getJogadorDaVez() + " " + getIdPlayer());
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                getTabuleiro().getCasas()[coluna][linha].setMovimentoPossivel(false, getTabuleiro().getCorCasaEscura());
            }
        }
    }

    /*
     * Metodo criado para testar todos os movimentos possiveis em todas as direcoes, usada pela pedra configurada como Dama.
     */
    private void testaMovimentosDama(int x, int y, boolean reanalise) {
        int linha = x;
        int coluna = y;
        boolean continua = true;
        while (getTabuleiro().getCasas()[linha++][coluna++].isCasaPossivel() && linha <= 7 && coluna <= 7 && linha >= 0 && coluna >= 0 && continua) {
            continua = estudaJogadas(linha, coluna, reanalise);
        }
        linha = x;
        coluna = y;
        continua = true;
        while (getTabuleiro().getCasas()[linha--][coluna++].isCasaPossivel() && linha <= 7 && coluna <= 7 && linha >= 0 && coluna >= 0 && continua) {
            continua = estudaJogadas(linha, coluna, reanalise);
        }
        linha = x;
        coluna = y;
        continua = true;
        while (getTabuleiro().getCasas()[linha--][coluna--].isCasaPossivel() && linha <= 7 && coluna <= 7 && linha >= 0 && coluna >= 0 && continua) {
            continua = estudaJogadas(linha, coluna, reanalise);
        }
        linha = x;
        coluna = y;
        continua = true;
        while (getTabuleiro().getCasas()[linha++][coluna--].isCasaPossivel() && linha <= 7 && coluna <= 7 && linha >= 0 && coluna >= 0 && continua) {
            continua = estudaJogadas(linha, coluna, reanalise);
        }
    }

    /*
     * Metodo analisaTipoMovimento
     * Usado para ver qual tipo de movimento sera realizado, por Peca comum ou por Dama
     */
    private void analistaTipoMovimento(int x, int y, boolean reanalise) {
        if (getTabuleiro().getCasas()[x][y].isCasaPossivel()
                && getTabuleiro().getCasas()[x][y].getPedra() != null
                && getTabuleiro().getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()
                && getJogadorDaVez() == 0
                && getTabuleiro().getCasas()[x][y].getPedra().identificaPedra().equals("peca")) {
            if (x < 7) {
                if (y < 7) {
                    if (getTabuleiro().getCasas()[x + 1][y + 1].isCasaPossivel()
                            && getTabuleiro().getCasas()[x + 1][y + 1].getPedra() != null
                            && getTabuleiro().getCasas()[x + 1][y + 1].getPedra().getIdOwner() != getJogadorDaVez()
                            && y < 6 && x < 6) {
                        if (getTabuleiro().getCasas()[x + 2][y + 2].isCasaPossivel() && getTabuleiro().getCasas()[x + 2][y + 2].getPedra() == null) {
                            getTabuleiro().getCasas()[x + 2][y + 2].setMovimentoPossivel(true, Color.red);
                            setNovaJogada(true);
                        }
                    }
                    if (getTabuleiro().getCasas()[x + 1][y + 1].isCasaPossivel() && getTabuleiro().getCasas()[x + 1][y + 1].getPedra() == null && !reanalise) {
                        getTabuleiro().getCasas()[x + 1][y + 1].setMovimentoPossivel(true, Color.black);
                    }
                }
                if (y > 0) {
                    if (getTabuleiro().getCasas()[x + 1][y - 1].isCasaPossivel()
                            && getTabuleiro().getCasas()[x + 1][y - 1].getPedra() != null
                            && getTabuleiro().getCasas()[x + 1][y - 1].getPedra().getIdOwner() != getJogadorDaVez()
                            && x < 6 && y > 1) {
                        if (getTabuleiro().getCasas()[x + 2][y - 2].isCasaPossivel() && getTabuleiro().getCasas()[x + 2][y - 2].getPedra() == null) {
                            getTabuleiro().getCasas()[x + 2][y - 2].setMovimentoPossivel(true, Color.red);
                            setNovaJogada(true);
                        }
                    }
                    if (getTabuleiro().getCasas()[x + 1][y - 1].isCasaPossivel() && getTabuleiro().getCasas()[x + 1][y - 1].getPedra() == null && !reanalise) {
                        getTabuleiro().getCasas()[x + 1][y - 1].setMovimentoPossivel(true, Color.black);
                    }
                }
            }
        } else {
            if (getTabuleiro().getCasas()[x][y].isCasaPossivel()
                    && getTabuleiro().getCasas()[x][y].getPedra() != null
                    && getTabuleiro().getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()
                    && getTabuleiro().getCasas()[x][y].getPedra().identificaPedra().equals("dama")
                    && getJogadorDaVez() == 0) {
                testaMovimentosDama(x, y, reanalise);
            }
        }

        if (getTabuleiro().getCasas()[x][y].isCasaPossivel()
                && getTabuleiro().getCasas()[x][y].getPedra() != null
                && getTabuleiro().getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()
                && getJogadorDaVez() == 1
                && getTabuleiro().getCasas()[x][y].getPedra().identificaPedra().equals("peca")) {
            if (x > 0) {
                if (y < 7) {
                    if (getTabuleiro().getCasas()[x - 1][y + 1].isCasaPossivel() && getTabuleiro().getCasas()[x - 1][y + 1].getPedra() != null && getTabuleiro().getCasas()[x - 1][y + 1].getPedra().getIdOwner() != getJogadorDaVez() && y < 6 && x > 1) {
                        if (getTabuleiro().getCasas()[x - 2][y + 2].isCasaPossivel() && getTabuleiro().getCasas()[x - 2][y + 2].getPedra() == null) {
                            getTabuleiro().getCasas()[x - 2][y + 2].setMovimentoPossivel(true, Color.red);
                            setNovaJogada(true);
                        }
                    }
                    if (getTabuleiro().getCasas()[x - 1][y + 1].isCasaPossivel() && getTabuleiro().getCasas()[x - 1][y + 1].getPedra() == null && !reanalise) {
                        getTabuleiro().getCasas()[x - 1][y + 1].setMovimentoPossivel(true, Color.black);
                    }
                }
                if (y > 0) {
                    if (getTabuleiro().getCasas()[x - 1][y - 1].isCasaPossivel() && getTabuleiro().getCasas()[x - 1][y - 1].getPedra() != null && getTabuleiro().getCasas()[x - 1][y - 1].getPedra().getIdOwner() != getJogadorDaVez() && x > 1 && y > 1) {
                        if (getTabuleiro().getCasas()[x - 2][y - 2].isCasaPossivel() && getTabuleiro().getCasas()[x - 2][y - 2].getPedra() == null) {
                            getTabuleiro().getCasas()[x - 2][y - 2].setMovimentoPossivel(true, Color.red);
                            setNovaJogada(true);
                        }
                    }
                    if (getTabuleiro().getCasas()[x - 1][y - 1].getPedra() == null && !reanalise) {
                        getTabuleiro().getCasas()[x - 1][y - 1].setMovimentoPossivel(true, Color.black);
                    }
                }
            }
        } else {
            if (getTabuleiro().getCasas()[x][y].isCasaPossivel()
                    && getTabuleiro().getCasas()[x][y].getPedra() != null
                    && getTabuleiro().getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()
                    && getTabuleiro().getCasas()[x][y].getPedra().identificaPedra().equals("dama")
                    && getJogadorDaVez() == 1) {
                testaMovimentosDama(x, y, reanalise);
            }
        }
    }

    /*
     * Metodo Estuda Rotina que valida todas as movimentacoes possiveis de uma pedra
     */
    private boolean estudaJogadas(int linha, int coluna, boolean reanalise) {
        /*
         * Primeira validacao eh responsavel por parar as analises na direcao que foi encontrado peca igual a do jogador atual
         */
        if (getTabuleiro().getCasas()[linha][coluna].isCasaPossivel() && getTabuleiro().getCasas()[linha][coluna].getPedra() != null && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() == getJogadorDaVez()) {
            return false;
        } /*
         * Validacao faz a analise para so continuar analise se localizar 2 pedras seguidas
         */
        if (linha < 7
                && coluna < 7
                && linha > getOldX() && coluna > getOldY()
                && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                && getTabuleiro().getCasas()[linha + 1][coluna + 1].getPedra() != null
                && getTabuleiro().getCasas()[linha + 1][coluna + 1].isCasaPossivel()) {
            return false;
        } else {
            if (linha < 7
                    && coluna < 7
                    && linha > getOldX() && coluna > getOldY()
                    && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                    && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                    && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                    && getTabuleiro().getCasas()[linha + 1][coluna + 1].getPedra() == null
                    && getTabuleiro().getCasas()[linha + 1][coluna + 1].isCasaPossivel()) {
                getTabuleiro().getCasas()[linha + 1][coluna + 1].setMovimentoPossivel(true, Color.red);
                setNovaJogada(
                        true);
                return true;
            }
        }
        /*
         * Validacao faz a analise para so continuar analise se localizar 2 pedras seguidas
         */
        if (linha < 7
                && coluna > 0
                && linha > getOldX() && coluna < getOldY()
                && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                && getTabuleiro().getCasas()[linha + 1][coluna - 1].getPedra() != null
                && getTabuleiro().getCasas()[linha + 1][coluna - 1].isCasaPossivel()) {
            return false;
        } else {
            if (linha < 7
                    && coluna > 0
                    && linha > getOldX() && coluna < getOldY()
                    && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                    && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                    && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                    && getTabuleiro().getCasas()[linha + 1][coluna - 1].getPedra() == null
                    && getTabuleiro().getCasas()[linha + 1][coluna - 1].isCasaPossivel()) {
                getTabuleiro().getCasas()[linha + 1][coluna - 1].setMovimentoPossivel(true, Color.red);
                setNovaJogada(true);
                return true;
            }
        }
        /*
         * Validacao faz a analise para so continuar analise se localizar 2 pedras seguidas
         */
        if (linha > 0
                && coluna > 0
                && linha < getOldX() && coluna < getOldY()
                && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                && getTabuleiro().getCasas()[linha - 1][coluna - 1].getPedra() != null
                && getTabuleiro().getCasas()[linha - 1][coluna - 1].isCasaPossivel()) {
            return false;
        } else {
            if (linha > 0
                    && coluna > 0
                    && linha < getOldX() && coluna < getOldY()
                    && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                    && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                    && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                    && getTabuleiro().getCasas()[linha - 1][coluna - 1].getPedra() == null
                    && getTabuleiro().getCasas()[linha - 1][coluna - 1].isCasaPossivel()) {
                getTabuleiro().getCasas()[linha - 1][coluna - 1].setMovimentoPossivel(true, Color.red);
                setNovaJogada(true);
                return true;
            }
        }
        /*
         * Validacao faz a analise para so continuar analise se localizar 2 pedras seguidas
         */
        if (linha > 0
                && coluna < 7
                && linha < getOldX() && coluna > getOldY()
                && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                && getTabuleiro().getCasas()[linha - 1][coluna + 1].getPedra() != null
                && getTabuleiro().getCasas()[linha - 1][coluna + 1].isCasaPossivel()) {
            return false;
        } else {
            if (linha > 0
                    && coluna < 7
                    && linha < getOldX() && coluna > getOldY()
                    && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                    && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                    && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                    && getTabuleiro().getCasas()[linha - 1][coluna + 1].getPedra() == null
                    && getTabuleiro().getCasas()[linha - 1][coluna + 1].isCasaPossivel()) {
                getTabuleiro().getCasas()[linha - 1][coluna + 1].setMovimentoPossivel(true, Color.red);
                setNovaJogada(true);
                return true;
            }
        }
        /*
         * Caso nao tenha localizado duas perdras consecutivas
         * em alguma das direcoes, seta casa como possivel de movimento.
         */
        if (getTabuleiro().getCasas()[linha][coluna].getPedra() == null && !reanalise) {
            getTabuleiro().getCasas()[linha][coluna].setMovimentoPossivel(true, Color.cyan);
            return true;
        }
        return false;
    }

    /**
     * @return the tabuleiro
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    /**
     * @param tabuleiro the tabuleiro to set
     */
    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    /**
     * @return the jogadores
     */
    public Jogador[] getJogadores() {
        return jogadores;
    }

    /**
     * @param j the jogadores to set
     */
    public void setJogadores(int idx, Jogador j) {
        jogadores[idx] = j;
    }

    /**
     * @return the placar
     */
    public Placar getPlacar() {
        return placar;
    }

    /**
     * @param placar the placar to set
     */
    public void setPlacar(Placar placar) {
        this.placar = placar;
    }

    /**
     * @return the novaJogada
     */
    public boolean isNovaJogada() {
        return novaJogada;
    }

    /**
     * @param novaJogada the novaJogada to set
     */
    public void setNovaJogada(boolean novaJogada) {
        this.novaJogada = novaJogada;
    }

    /**
     * @return the jogadorDaVez
     */
    public int getJogadorDaVez() {
        return jogadorDaVez;
    }

    /**
     * @param jogadorDaVez the jogadorDaVez to set
     */
    private void setJogadorDaVez(int jogadorDaVez) {
        this.jogadorDaVez = jogadorDaVez;
    }

    /**
     * @param oldX the oldX to set
     */
    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    /**
     * @return the oldX
     */
    public int getOldX() {
        return oldX;
    }

    /**
     * @return the ySelecionado
     */
    public int getOldY() {
        return oldY;
    }

    /**
     * @param oldY the oldY to set
     */
    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    private void mudarJogadorVez() {
        if (getJogadorDaVez() == 1) {
            setJogadorDaVez(0);
        } else {
            setJogadorDaVez(1);
        }
//        try {
//            cliente.enviaMensagem(String.valueOf(getJogadorDaVez()));
//        } catch (IOException ex) {
//            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * @return the chat
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * @param chat the chat to set
     */
    public void setChat(Chat chat) {
        this.chat = chat;
    }

    /**
     * @return the idPlayer
     */
    public int getIdPlayer() {
        return idPlayer;
    }

    /**
     * @param idPlayer the idPlayer to set
     */
    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
}
