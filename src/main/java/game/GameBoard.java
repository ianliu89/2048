package game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static game.GameConfig.HEIGHT;
import static game.GameConfig.WIDTH;


public class GameBoard implements KeyListener {

    public static GameBoard gameBoard;
    private JPanel panel;
    private NumberButton[] numberButtons;
    private JButton gameOverButton = new JButton();
    private JFrame jframe = new JFrame();
    private Random random = new Random();

    private GameBoard() {

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(187, 173, 160));
        panel.addKeyListener(this);
        panel.setFocusable(true);
        panel.add(gameOverButton);
        initializeButtons();
        jframe.setTitle("2048");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.add(panel);
        jframe.setSize(WIDTH + 20, HEIGHT + 40);
        jframe.setResizable(false);
        jframe.setFocusable(true);
        jframe.addKeyListener(this);
        jframe.setVisible(true);
        refreshJframe();
    }

    private void initializeButtons() {
        numberButtons = new NumberButton[16];
        for(int i = 0; i<numberButtons.length; i++) {
            numberButtons[i] = new NumberButton(LocationConverter.numberToLocation(i + 1), 0 , new Color(205, 192, 181), new Color(205, 192, 181));
            panel.add(numberButtons[i]);
        }
        for(int i = 0; i < 2; i++) {
            setupRandomNewButton();
        }
    }

    private void setupRandomNewButton() {
        List<Integer> emptyButtons = getEmptyButtons();
        int number = random.nextInt(emptyButtons.size());
        int value = Math.random() < 0.9 ? 2 : 4;
        updateNumberButton(value, emptyButtons.get(number));
    }

    private void refreshJframe() {
        SwingUtilities.updateComponentTreeUI(jframe);
    }

    private List<Integer> getEmptyButtons() {
        List<Integer> emptyButtons = new ArrayList<>();
        for(int i = 0; i<numberButtons.length; i ++) {
            if(numberButtons[i].getText().equals("")) {
                emptyButtons.add(i);
            }
        }
        
        return emptyButtons;
    }

    public static void main(String[] args)
    {
       gameBoard = new GameBoard();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getExtendedKeyCode();
        boolean movable = true;
        switch(key) {
            case KeyEvent.VK_DOWN:
                movable = downPush();
                break;
            case KeyEvent.VK_LEFT:
                movable = leftPush();
                break;
            case KeyEvent.VK_RIGHT:
                movable = rightPush();
                break;
            case KeyEvent.VK_UP:
                movable = upPush();
                break;
            default:
                return;
        }

        if(movable) {
            setupRandomNewButton();
        }

        if(!checkTotalMovable() && getEmptyButtons().size() == 0){
            gameOver(false);
        }
        refreshJframe();
    }

    protected void gameOver(boolean isWin) {
        jframe.removeKeyListener(this);
        if(isWin) {
            gameOverButton.setText("You win 2048!");
            gameOverButton.setForeground(Color.BLUE);
        }
        else {
            gameOverButton.setText("Game Over!");
            gameOverButton.setForeground(Color.RED);
        }

        gameOverButton.setFont(new Font("TimesRoman", Font.BOLD, 40));
        gameOverButton.setOpaque(false);
        gameOverButton.setContentAreaFilled(false);
        gameOverButton.setBorderPainted(false);
        gameOverButton.setBounds(60, 100, 400, 100);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }

    private boolean leftPush() {
        int movableCount = 0;
        for(int i = 0; i<numberButtons.length; i=i+4 ){
            Integer[] inputArray = new Integer[4];
            for(int j = 0; j< 4 ; j++) {
                int value  =  numberButtons[i + j].getText().equals("") ? 0 : Integer.parseInt(numberButtons[i + j].getText());
                inputArray[j] = value;
            }
            Integer[] results = createMoveArray(inputArray);
            if(!Arrays.equals(inputArray, results)) {
                movableCount++;
            }
            for(int k = 0; k<4; k++) {
                updateNumberButton(results[k], i + k);
            }
        }

        return movableCount > 0;
    }

    private boolean rightPush() {
        int movableCount = 0;
        for(int i = 0; i<numberButtons.length; i=i+4 ){
            Integer[] inputArray = new Integer[4];
            for(int j = 0; j< 4 ; j++) {
                int value  =  numberButtons[i + (3-j)].getText().equals("") ? 0 : Integer.parseInt(numberButtons[i + (3-j)].getText());
                inputArray[j] = value;
            }
            Integer[] results = createMoveArray(inputArray);
            if(!Arrays.equals(inputArray, results)) {
                movableCount++;
            }
            for(int k = 0; k<4; k++) {
                updateNumberButton(results[k], i + (3-k));
            }
        }

        return movableCount > 0;
    }

    private boolean upPush() {
        int movableCount = 0;
        for(int i = 0; i<4; i++ ){
            Integer[] inputArray = new Integer[4];
            int count = 0;
            for(int j = 0; j< numberButtons.length ; j=j+4) {
                int value  =  numberButtons[i + j].getText().equals("") ? 0 : Integer.parseInt(numberButtons[i + j].getText());
                inputArray[count] = value;
                count++;
            }
            Integer[] results = createMoveArray(inputArray);
            if(!Arrays.equals(inputArray, results)) {
                movableCount++;
            }
            count = 0;
            for(int k = 0; k < numberButtons.length; k=k+4) {
                updateNumberButton(results[count], i + k);
                count++;
            }
        }

        return movableCount > 0;
    }

    private boolean downPush() {
        int movableCount = 0;
        for(int i = 0; i<4; i++ ){
            Integer[] inputArray = new Integer[4];
            int count = 3;
            for(int j = 0; j< numberButtons.length ; j=j+4) {
                int value  =  numberButtons[i + j].getText().equals("") ? 0 : Integer.parseInt(numberButtons[i + j].getText());
                inputArray[count] = value;
                count--;
            }
            Integer[] results = createMoveArray(inputArray);
            if(!Arrays.equals(inputArray, results)) {
                movableCount++;
            }
            count = 3;
            for(int k = 0; k < numberButtons.length; k=k+4) {
                updateNumberButton(results[count], i + k);
                count--;
            }
        }

        return movableCount > 0;
    }

    private static Integer[] createMoveArray(Integer[] array) {
        Integer[] newArray = Arrays.stream(array).filter(value -> value !=0).toArray(Integer[]::new);
        int left = 0;
        while( left < newArray.length) {
            int right = left + 1;
            if(right < newArray.length && newArray[left].equals(newArray[right])) {
                newArray[left] = 0;
                newArray[right] = newArray[right]*2;
                left = right + 1;
            }
            else {
                left++;
            }
        }
        List<Integer> processList = Arrays.stream(newArray).filter(value -> value !=0).collect(Collectors.toList());
        int listSize = processList.size();
        for(int i = 0; i < 4 - listSize; i++){
            processList.add(0);
        }

        Integer[] results = new Integer[processList.size()];
        return processList.toArray(results);
    }

    private boolean checkTotalMovable() {
        for(int i = 0; i < numberButtons.length; i=i+4) {
            for(int j = 0; j < 4; j++) {
                if( i + 4 >= numberButtons.length) {
                    if(j%4 != 3) {
                        if(numberButtons[i+j].getText().equals(numberButtons[i+j+1].getText())){
                            return true;
                        }
                    }
                }
                else {
                    if(numberButtons[i+j].getText().equals(numberButtons[i+j+4].getText())){
                        return true;
                    }
                    if(j%4 != 3) {
                        if(numberButtons[i+j].getText().equals(numberButtons[i+j+1].getText())){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void updateNumberButton(int value, int number) {
        switch(value) {
            case 0:
                numberButtons[number].setText("");
                numberButtons[number].setBackground(new Color(205, 192, 181));
                numberButtons[number].setForeground(new Color(205, 192, 181));
                break;
            case 2:
                numberButtons[number].setText(Integer.toString(value));
                numberButtons[number].setBackground(new Color(238, 228, 218));
                numberButtons[number].setForeground(new Color(118, 110, 100));
                break;
            case 4:
                numberButtons[number].setText(Integer.toString(value));
                numberButtons[number].setBackground(new Color(238, 225, 201));
                numberButtons[number].setForeground(new Color(118, 110, 100));
                break;
            case 8:
                numberButtons[number].setText(Integer.toString(value));
                numberButtons[number].setBackground(new Color(243, 178, 121));
                numberButtons[number].setForeground(new Color(249, 246, 242));
                break;
            case 16:
                numberButtons[number].setText(Integer.toString(value));
                numberButtons[number].setBackground(new Color(246, 150, 99));
                numberButtons[number].setForeground(new Color(249, 246, 242));
                break;
            case 32:
                numberButtons[number].setText(Integer.toString(value));
                numberButtons[number].setBackground(new Color(247, 124, 95));
                numberButtons[number].setForeground(new Color(249, 246, 242));
                break;
            case 64:
                numberButtons[number].setText(Integer.toString(value));
                numberButtons[number].setBackground(new Color(247, 100, 61));
                numberButtons[number].setForeground(new Color(249, 246, 242));
                break;
            case 128:
                numberButtons[number].setText(Integer.toString(value));
                numberButtons[number].setBackground(new Color(117, 167, 241));
                numberButtons[number].setForeground(new Color(249, 246, 242));
                break;
            case 256:
                numberButtons[number].setText(Integer.toString(value));
                numberButtons[number].setBackground(new Color(69, 133, 242));
                numberButtons[number].setForeground(new Color(249, 246, 242));
                break;
            case 512:
                numberButtons[number].setText(Integer.toString(value));
                numberButtons[number].setBackground(new Color(237, 201, 79));
                numberButtons[number].setForeground(new Color(249, 246, 242));
                break;
            case 1024:
                numberButtons[number].setText(Integer.toString(value));
                numberButtons[number].setBackground(new Color(237, 197, 63));
                numberButtons[number].setForeground(new Color(249, 246, 242));
                break;
            case 2048:
                numberButtons[number].setText(Integer.toString(value));
                numberButtons[number].setBackground(new Color(212, 165, 51));
                numberButtons[number].setForeground(new Color(249, 246, 242));
                gameOver(true);
                break;
            default:
                break;
        }
    }
}
