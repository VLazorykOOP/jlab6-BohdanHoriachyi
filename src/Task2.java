import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

// Власне виключення
class CustomException extends ArithmeticException {
    public CustomException(String message) {
        super(message);
    }
}

// Основний клас програми
public class Task2 {
    private JFrame frame;
    private JPanel panel;
    private JButton loadButton;
    private JTable tableA, tableB;
    private JTextField[] resultFields;

    public Task2() {
        frame = new JFrame("Matrix Comparison");
        panel = new JPanel();
        loadButton = new JButton("Load Matrices");
        
        panel.setLayout(new BorderLayout());
        panel.add(loadButton, BorderLayout.SOUTH);
        frame.add(panel);
        
        loadButton.addActionListener(new LoadMatricesAction());
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private class LoadMatricesAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                readMatricesFromFile("matrices.txt");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input format: " + ex.getMessage());
            } catch (CustomException ex) {
                JOptionPane.showMessageDialog(frame, "Custom exception: " + ex.getMessage());
            }
        }
    }

    private void readMatricesFromFile(String filename) throws IOException, CustomException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        
        int n = Integer.parseInt(reader.readLine());
        if (n <= 0) {
            throw new CustomException("Matrix size must be greater than 0.");
        }

        int[][] A = new int[n][n];
        int[][] B = new int[n][n];
        DefaultTableModel modelA = new DefaultTableModel(n, n);
        DefaultTableModel modelB = new DefaultTableModel(n, n);
        
        // Читання матриці A
        for (int i = 0; i < n; i++) {
            String[] values = reader.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                A[i][j] = Integer.parseInt(values[j]);
                modelA.setValueAt(A[i][j], i, j);
            }
        }
        
        // Читання матриці B
        for (int i = 0; i < n; i++) {
            String[] values = reader.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                B[i][j] = Integer.parseInt(values[j]);
                modelB.setValueAt(B[i][j], i, j);
            }
        }
        
        // Налаштування таблиць
        tableA = new JTable(modelA);
        tableB = new JTable(modelB);
        
        panel.add(new JScrollPane(tableA), BorderLayout.WEST);
        panel.add(new JScrollPane(tableB), BorderLayout.EAST);
        panel.revalidate();
        panel.repaint();
        
        reader.close();
        
        // Обробка матриць та оновлення результатів
        processMatrices(A, B);
    }

    private void processMatrices(int[][] A, int[][] B) {
        int n = A.length;
        resultFields = new JTextField[n];
        
        JPanel resultPanel = new JPanel(new GridLayout(n, 1));
        
        for (int i = 0; i < n; i++) {
            boolean allGreater = true;
            for (int j = 0; j < n; j++) {
                if (A[i][j] <= B[i][j]) {
                    allGreater = false;
                    break;
                }
            }
            resultFields[i] = new JTextField(allGreater ? "1" : "0");
            resultFields[i].setEditable(false);
            resultPanel.add(resultFields[i]);
        }
        
        panel.add(resultPanel, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Task2::new);
    }
}
