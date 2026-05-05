package aula;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Damas extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int SIZE = 8;
    private final int TILE_SIZE = 70;
    private Square[][] board;
    private JPanel boardPanel;
    private int playerTurn = 1; // 1: Vermelho, 2: Preto
    private Square selectedSquare = null;

    public Damas() {
        setTitle("Jogo de Damas - Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        board = new Square[SIZE][SIZE];
        boardPanel = new JPanel(new GridLayout(SIZE, SIZE));
        
        initializeBoard();
        
        add(boardPanel, BorderLayout.CENTER);
        setSize(SIZE * TILE_SIZE, SIZE * TILE_SIZE);
        setLocationRelativeTo(null); // Centraliza
        setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Square square = new Square(row, col);
                
                // Define cores do tabuleiro
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.GRAY);
                    // Adiciona peças nas casas escuras
                    if (row < 3) {
                        square.setPiece(2); // Preto
                    } else if (row > 4) {
                        square.setPiece(1); // Vermelho
                    }
                }
                
                square.addMouseListener(new SquareClickListener());
                board[row][col] = square;
                boardPanel.add(square);
            }
        }
    }

    // Classe interna para representar cada casa
    private class Square extends JPanel {
        int row, col, piece = 0; // 0: Vazio, 1: Vermelho, 2: Preto

        public Square(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void setPiece(int piece) {
            this.piece = piece;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (piece != 0) {
                g.setColor(piece == 1 ? Color.RED : Color.BLACK);
                g.fillOval(10, 10, TILE_SIZE - 20, TILE_SIZE - 20);
            }
            if (this == selectedSquare) {
                setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
            } else {
                setBorder(null);
            }
        }
    }

    // Lógica de clique
    private class SquareClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Square clickedSquare = (Square) e.getSource();
            
            if (selectedSquare == null) {
                // Selecionar peça
                if (clickedSquare.piece == playerTurn) {
                    selectedSquare = clickedSquare;
                }
            } else {
                // Tentar mover
                if (clickedSquare.piece == 0 && isValidMove(selectedSquare, clickedSquare)) {
                    clickedSquare.setPiece(selectedSquare.piece);
                    selectedSquare.setPiece(0);
                    
                    // Lógica de captura (simples)
                    if (Math.abs(clickedSquare.row - selectedSquare.row) == 2) {
                        int rMid = (clickedSquare.row + selectedSquare.row) / 2;
                        int cMid = (clickedSquare.col + selectedSquare.col) / 2;
                        board[rMid][cMid].setPiece(0);
                    }
                    
                    // Alternar jogador
                    playerTurn = (playerTurn == 1) ? 2 : 1;
                    selectedSquare = null;
                } else {
                    // Deselecionar ou mudar seleção
                    selectedSquare = (clickedSquare.piece == playerTurn) ? clickedSquare : null;
                }
            }
            boardPanel.repaint();
        }
    }

    // Validação básica de movimento
    private boolean isValidMove(Square from, Square to) {
        int rowDiff = to.row - from.row;
        int colDiff = Math.abs(to.col - from.col);
        
        // Direção correta
        if (from.piece == 1 && rowDiff >= 0) return false; // Vermelho sobe
        if (from.piece == 2 && rowDiff <= 0) return false; // Preto desce
        
        // Movimento normal ou captura
        return Math.abs(rowDiff) == 1 && colDiff == 1 || 
               Math.abs(rowDiff) == 2 && colDiff == 2;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Damas::new);
    }
}


public class damas {

}
