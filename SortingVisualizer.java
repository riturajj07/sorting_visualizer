import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class SortingVisualizer extends JPanel {
    private static final int MAX_BAR_HEIGHT = 400; // Maximum bar height in pixels
    private int[] array;
    private boolean isSorting = false;
    private int currentPos1 = -1, currentPos2 = -1; // Positions of the elements being swapped
    private JTextField arrayElementsField;
    private JComboBox<String> algorithmComboBox;
    private JButton startButton;
    private JButton resetButton;
    private JButton updateArrayButton;

    public SortingVisualizer(int arraySize) {
        array = new int[arraySize];
        generateRandomArray();
        setPreferredSize(new Dimension(arraySize * 30, 400)); // Adjust size based on array size
        setBackground(Color.WHITE);
    }

    // Generate a random array
    private void generateRandomArray() {
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(MAX_BAR_HEIGHT);
        }
    }

    // Paint the array as vertical bars
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < array.length; i++) {
            if (i == currentPos1 || i == currentPos2) {
                g.setColor(Color.RED); // Highlight the bars being swapped
            } else {
                g.setColor(Color.BLUE);
            }
            g.fillRect(i * 30, getHeight() - array[i], 30, array[i]);
        }
    }

    // Bubble Sort visualization
    public void bubbleSort() {
        new Thread(() -> {
            for (int i = 0; i < array.length - 1; i++) {
                for (int j = 0; j < array.length - i - 1; j++) {
                    if (array[j] > array[j + 1]) {
                        // Swap elements
                        int temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                        currentPos1 = j;
                        currentPos2 = j + 1;
                        repaint();
                        try {
                            Thread.sleep(50); // Delay for visual effect
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            isSorting = false;
            repaint();
        }).start();
    }

    // Selection Sort visualization
    public void selectionSort() {
        new Thread(() -> {
            for (int i = 0; i < array.length - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < array.length; j++) {
                    if (array[j] < array[minIdx]) {
                        minIdx = j;
                    }
                }
                // Swap elements
                int temp = array[minIdx];
                array[minIdx] = array[i];
                array[i] = temp;
                currentPos1 = i;
                currentPos2 = minIdx;
                repaint();
                try {
                    Thread.sleep(50); // Delay for visual effect
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isSorting = false;
            repaint();
        }).start();
    }

    // Insertion Sort visualization
    public void insertionSort() {
        new Thread(() -> {
            for (int i = 1; i < array.length; i++) {
                int key = array[i];
                int j = i - 1;
                while (j >= 0 && array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                    currentPos1 = j + 1;
                    currentPos2 = i;
                    repaint();
                    try {
                        Thread.sleep(50); // Delay for visual effect
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                array[j + 1] = key;
            }
            isSorting = false;
            repaint();
        }).start();
    }

    // Quick Sort visualization
    public void quickSort() {
        new Thread(() -> {
            quickSortHelper(0, array.length - 1);
            isSorting = false;
            repaint();
        }).start();
    }

    private void quickSortHelper(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSortHelper(low, pi - 1);
            quickSortHelper(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                currentPos1 = i;
                currentPos2 = j;
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        currentPos1 = i + 1;
        currentPos2 = high;
        repaint();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i + 1;
    }

    // Merge Sort visualization
    public void mergeSort() {
        new Thread(() -> {
            mergeSortHelper(0, array.length - 1);
            isSorting = false;
            repaint();
        }).start();
    }

    private void mergeSortHelper(int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSortHelper(left, middle);
            mergeSortHelper(middle + 1, right);
            merge(left, middle, right);
        }
    }

    private void merge(int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;
        int[] L = new int[n1];
        int[] R = new int[n2];
        System.arraycopy(array, left, L, 0, n1);
        System.arraycopy(array, middle + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            currentPos1 = i;
            currentPos2 = j;
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            k++;
        }

        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to reset the array
    public void resetArray() {
        generateRandomArray();
        repaint();
    }

    // Method to update array elements from user input
    public void updateArrayFromInput(String input) {
        String[] inputElements = input.split(",");
        array = new int[inputElements.length];
        for (int i = 0; i < inputElements.length; i++) {
            array[i] = Integer.parseInt(inputElements[i].trim());
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sorting Visualizer");
        SortingVisualizer visualizer = new SortingVisualizer(20);

        // UI Components
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // Array input text field
        JTextField arrayInputField = new JTextField(20);
        controlPanel.add(new JLabel("Enter Array Elements (comma-separated):"));
        controlPanel.add(arrayInputField);

        // Algorithm selection dropdown
        String[] algorithms = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort", "Merge Sort"};
        JComboBox<String> algorithmComboBox = new JComboBox<>(algorithms);
        controlPanel.add(algorithmComboBox);

        // Start Button
        JButton startButton = new JButton("Start Sorting");
        controlPanel.add(startButton);

        // Reset Button
        JButton resetButton = new JButton("Reset Array");
        controlPanel.add(resetButton);

        // Update Button to take user input for array
        JButton updateArrayButton = new JButton("Update Array");
        controlPanel.add(updateArrayButton);

        // Add components to the frame
        frame.add(visualizer, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        // Button actions
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (visualizer.isSorting) {
                    return; // Prevent multiple sorts at once
                }

                visualizer.isSorting = true;
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
                switch (selectedAlgorithm) {
                    case "Bubble Sort":
                        visualizer.bubbleSort();
                        break;
                    case "Selection Sort":
                        visualizer.selectionSort();
                        break;
                    case "Insertion Sort":
                        visualizer.insertionSort();
                        break;
                    case "Quick Sort":
                        visualizer.quickSort();
                        break;
                    case "Merge Sort":
                        visualizer.mergeSort();
                        break;
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizer.resetArray();
            }
        });

        updateArrayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = arrayInputField.getText();
                visualizer.updateArrayFromInput(input);
            }
        });

        // Frame setup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
