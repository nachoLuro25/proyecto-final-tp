package game;

import entities.Player;
import entities.PlayerHuman;
import audio.AudioManager;
import utils.ColorType;
import ui.VentanaPrincipal;
import ui.MenuPausa;
import ui.MenuFinJuego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TronGame extends JPanel implements ActionListener, KeyListener {

    private PlayerHuman player1;
    private PlayerHuman player2;
    private GameRenderer renderer;
    private AudioManager audioManager;

    private Timer timer;
    private boolean running = false;
    private boolean paused = false;
    private String winner = null;

    private JFrame pauseFrame = null;
    private JFrame finJuegoFrame = null;

    public TronGame(ColorType colorType1, ColorType colorType2) {
        player1 = new PlayerHuman(
            GameConfig.PLAYER1_START_X,
            GameConfig.PLAYER1_START_Y,
            GameConfig.PLAYER1_START_DIR,
            colorType1,
            1
        );

        player2 = new PlayerHuman(
            GameConfig.PLAYER2_START_X,
            GameConfig.PLAYER2_START_Y,
            GameConfig.PLAYER2_START_DIR,
            colorType2,
            2
        );

        renderer = new GameRenderer(player1, player2);
        audioManager = AudioManager.getInstance();
        audioManager.inicializarMusicaJuego();

        setPreferredSize(new Dimension(GameConfig.WIDTH, GameConfig.HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        startGame();
    }

    public void startGame() {
        if (timer != null) {
            timer.stop();
        }

        player1.reset(GameConfig.PLAYER1_START_X, GameConfig.PLAYER1_START_Y,
            GameConfig.PLAYER1_START_DIR);
        player2.reset(GameConfig.PLAYER2_START_X, GameConfig.PLAYER2_START_Y,
            GameConfig.PLAYER2_START_DIR);

        winner = null;
        running = true;
        paused = false;

        timer = new Timer(GameConfig.DELAY, this);
        timer.start();
    }

    private void move() {
        if (!running || paused) return;
        int moves1 = player1.getMovesPerFrame();
        for (int i = 0; i < moves1; i++) {
            player1.move(GameConfig.UNIT);

            if (player1.checkCollision(player2, GameConfig.WIDTH, GameConfig.HEIGHT)) {
                endGame("Jugador 2");
                return;
            }
        }

        int moves2 = player2.getMovesPerFrame();
        for (int i = 0; i < moves2; i++) {
            player2.move(GameConfig.UNIT);

            if (player2.checkCollision(player1, GameConfig.WIDTH, GameConfig.HEIGHT)) {
                endGame("Jugador 1");
                return;
            }
        }

        player1.updateAbility(player2);
        player2.updateAbility(player1);
    }

    private void endGame(String winnerName) {
        running = false;
        winner = winnerName;
        mostrarMenuFinJuego();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer.render(g, player1, player2, running, paused, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player1.processInput('U');
                break;
            case KeyEvent.VK_S:
                player1.processInput('D');
                break;
            case KeyEvent.VK_A:
                player1.processInput('L');
                break;
            case KeyEvent.VK_D:
                player1.processInput('R');
                break;
            case KeyEvent.VK_C:
                player1.activateAbility(player2);
                break;
            case KeyEvent.VK_UP:
                player2.processInput('U');
                break;
            case KeyEvent.VK_DOWN:
                player2.processInput('D');
                break;
            case KeyEvent.VK_LEFT:
                player2.processInput('L');
                break;
            case KeyEvent.VK_RIGHT:
                player2.processInput('R');
                break;
            case KeyEvent.VK_M:
                player2.activateAbility(player1);
                break;

            case KeyEvent.VK_R:
                if (!running) {
                    if (finJuegoFrame != null) {
                        finJuegoFrame.dispose();
                        finJuegoFrame = null;
                    }
                    startGame();
                    renderer.loadImages(player1, player2);
                }
                break;

            case KeyEvent.VK_ESCAPE:
                if (running && !paused) {
                    paused = true;
                    mostrarMenuPausa();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    private void mostrarMenuPausa() {
        pauseFrame = new MenuPausa(
            this,
            () -> {
                paused = false;
                pauseFrame = null;
                this.requestFocusInWindow();
            },
            () -> {
                running = false;
                paused = false;
                audioManager.detenerMusicaJuego();
                timer.stop();

                JFrame frameJuego = (JFrame) SwingUtilities.getWindowAncestor(TronGame.this);
                frameJuego.dispose();

                VentanaPrincipal ventana = new VentanaPrincipal();
                ventana.abrirSeleccionJugadores();
            },
            () -> {
                running = false;
                paused = false;
                audioManager.detenerMusicaJuego();
                timer.stop();

                JFrame frameJuego = (JFrame) SwingUtilities.getWindowAncestor(TronGame.this);
                frameJuego.dispose();
                new VentanaPrincipal();
            }
        );
    }

    private void mostrarMenuFinJuego() {
        Color colorGanador = winner.equals("Jugador 1") ? player1.getColor() :
            winner.equals("Jugador 2") ? player2.getColor() : Color.WHITE;

        finJuegoFrame = new MenuFinJuego(
            winner,
            colorGanador,
            () -> {
                finJuegoFrame = null;
                startGame();
                renderer.loadImages(player1, player2);
                this.requestFocusInWindow();
            },
            () -> {
                finJuegoFrame = null;
                audioManager.detenerMusicaJuego();
                timer.stop();

                JFrame frameJuego = (JFrame) SwingUtilities.getWindowAncestor(TronGame.this);
                frameJuego.dispose();

                VentanaPrincipal ventana = new VentanaPrincipal();
                ventana.abrirSeleccionJugadores();
            },
            () -> {
                finJuegoFrame = null;
                audioManager.detenerMusicaJuego();
                timer.stop();

                JFrame frameJuego = (JFrame) SwingUtilities.getWindowAncestor(TronGame.this);
                frameJuego.dispose();
                new VentanaPrincipal();
            }
        );
    }

    public void dispose() {
        audioManager.detenerMusicaJuego();
    }
}
