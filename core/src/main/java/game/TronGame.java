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

/**
 * Clase principal del juego TRON
 * Gestiona la lógica del juego y coordina todos los componentes
 *
 * REFACTORIZADA:
 * - Usa GameRenderer para dibujar
 * - Usa AudioManager para sonido
 * - Usa PlayerHuman en vez de manejar jugadores directamente
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class TronGame extends JPanel implements ActionListener, KeyListener {

    // Componentes del juego
    private PlayerHuman player1;
    private PlayerHuman player2;
    private GameRenderer renderer;
    private AudioManager audioManager;

    // Control del juego
    private Timer timer;
    private boolean running = false;
    private boolean paused = false;
    private String winner = null;

    // Ventanas de menú
    private JFrame pauseFrame = null;
    private JFrame finJuegoFrame = null;

    /**
     * Constructor del juego
     * @param colorType1 Color del jugador 1
     * @param colorType2 Color del jugador 2
     */
    public TronGame(ColorType colorType1, ColorType colorType2) {
        // Crear jugadores
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

        // Inicializar componentes
        renderer = new GameRenderer(player1, player2);
        audioManager = AudioManager.getInstance();
        audioManager.inicializarMusicaJuego();

        // Configurar panel
        setPreferredSize(new Dimension(GameConfig.WIDTH, GameConfig.HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // Iniciar juego
        startGame();
    }

    /**
     * Inicia o reinicia el juego
     */
    public void startGame() {
        // Detener timer anterior si existe
        if (timer != null) {
            timer.stop();
        }

        // Resetear jugadores
        player1.reset(GameConfig.PLAYER1_START_X, GameConfig.PLAYER1_START_Y,
            GameConfig.PLAYER1_START_DIR);
        player2.reset(GameConfig.PLAYER2_START_X, GameConfig.PLAYER2_START_Y,
            GameConfig.PLAYER2_START_DIR);

        // Resetear estado del juego
        winner = null;
        running = true;
        paused = false;

        // Iniciar timer del juego
        timer = new Timer(GameConfig.DELAY, this);
        timer.start();
    }

    /**
     * Mueve ambos jugadores
     */
    private void move() {
        if (!running || paused) return;

        // Mover jugador 1 (puede moverse 2 veces si tiene speed boost)
        int moves1 = player1.getMovesPerFrame();
        for (int i = 0; i < moves1; i++) {
            player1.move(GameConfig.UNIT);

            if (player1.checkCollision(player2, GameConfig.WIDTH, GameConfig.HEIGHT)) {
                endGame("Jugador 2");
                return;
            }
        }

        // Mover jugador 2
        int moves2 = player2.getMovesPerFrame();
        for (int i = 0; i < moves2; i++) {
            player2.move(GameConfig.UNIT);

            if (player2.checkCollision(player1, GameConfig.WIDTH, GameConfig.HEIGHT)) {
                endGame("Jugador 1");
                return;
            }
        }

        // Actualizar habilidades
        player1.updateAbility(player2);
        player2.updateAbility(player1);
    }

    /**
     * Termina el juego
     * @param winnerName Nombre del ganador
     */
    private void endGame(String winnerName) {
        running = false;
        winner = winnerName;
        mostrarMenuFinJuego();
    }

    /**
     * Dibuja el juego
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer.render(g, player1, player2, running, paused, this);
    }

    /**
     * Llamado por el timer en cada frame
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
        }
        repaint();
    }

    /**
     * Maneja las teclas presionadas
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Controles Jugador 1 (WASD)
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

            // Controles Jugador 2 (Flechas)
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

            // Reiniciar
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

            // Pausa (solo si está corriendo y NO está pausado)
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

    /**
     * Muestra el menú de pausa
     */
// En TronGame.java, reemplazar el método mostrarMenuPausa():

    /**
     * Muestra el menú de pausa
     */
    private void mostrarMenuPausa() {
        pauseFrame = new MenuPausa(
            this,
            // onResume (cuando se cierra el menú con ESC o X)
            () -> {
                paused = false;
                pauseFrame = null;
                this.requestFocusInWindow();
            },
            // onVolverSeleccion
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
            // onVolverMenu
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

// Y reemplazar el método mostrarMenuFinJuego():

    /**
     * Muestra el menú de fin de juego
     */
    private void mostrarMenuFinJuego() {
        Color colorGanador = winner.equals("Jugador 1") ? player1.getColor() :
            winner.equals("Jugador 2") ? player2.getColor() : Color.WHITE;

        finJuegoFrame = new MenuFinJuego(
            winner,
            colorGanador,
            // onReiniciar (tecla R)
            () -> {
                finJuegoFrame = null;
                startGame();
                renderer.loadImages(player1, player2);
                this.requestFocusInWindow();
            },
            // onVolverSeleccion
            () -> {
                finJuegoFrame = null;
                audioManager.detenerMusicaJuego();
                timer.stop();

                JFrame frameJuego = (JFrame) SwingUtilities.getWindowAncestor(TronGame.this);
                frameJuego.dispose();

                VentanaPrincipal ventana = new VentanaPrincipal();
                ventana.abrirSeleccionJugadores();
            },
            // onVolverMenu
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

// Y eliminar el método cerrarMenuPausa() (ya no es necesario)

    /**
     * Limpia recursos al cerrar
     */
    public void dispose() {
        audioManager.detenerMusicaJuego();
    }
}
