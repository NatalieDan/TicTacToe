/**
 * Java 2. Homework 4
 *
 * @author NatalieDan
 * @version 15.10.2021
 */

import java.util.Scanner;
import java.util.Random;

public class TicTacToe {

    final char SIGN_X = 'x';
    final char SIGN_O = 'o';
    final char SIGN_EMPTY = '_';
    char [][] table;

    Random random;
    Scanner scanner;

    public static void main(String[] args){
        new TicTacToe().game();
    }

    TicTacToe(){
        random = new Random();
        scanner = new Scanner(System.in);
        table = new char[5][5];
    }

    void game(){
        int sizeField;
        System.out.println("Enter size of field (max 5)");
        sizeField = scanner.nextInt();
        initTable(sizeField);
        while (true){
            printTable(sizeField);
            turnHuman(sizeField);
            if (isWin(SIGN_X,sizeField)){
                System.out.println();
                System.out.println("You win!");
                printTable(sizeField);
                break;
            }
            if (isTableFull(sizeField)){
                System.out.println();
                System.out.println("Draw");
                printTable(sizeField);
                break;
            }
            turnAi(sizeField);
            if (isWin(SIGN_O,sizeField)){
                System.out.println();
                System.out.println("You lose.");
                printTable(sizeField);
                break;
            }
            if (isTableFull(sizeField)){
                System.out.println();
                System.out.println("Draw");
                printTable(sizeField);
                break;
            }
        }
    }

    void initTable(int sizeField){
        for (int i=0;i<sizeField;i++){
            for (int j=0;j<sizeField;j++){
                table[i][j] = SIGN_EMPTY;
            }
        }
    }

    void printTable(int sizeField){
        for (int i=0;i<sizeField;i++){
            for (int j=0;j<sizeField;j++){
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }

    void turnHuman(int sizeField){
        int x,y;
        do{
            System.out.println("Enter x y");
            x = scanner.nextInt()-1;
            y = scanner.nextInt()-1;
        }while(!isCellValid(x,y,sizeField));
        table[x][y] = SIGN_X;
    }

    void turnAi(int sizeField){
        int x = 0,y = 0;
        int index = checkSituation(sizeField, SIGN_X, SIGN_O);
        if (index%5 == 4){
            do{
                x = random.nextInt(sizeField);
                y = random.nextInt(sizeField);
            }while(!isCellValid(x,y,sizeField));
        }
        else{
            if (index%5 == 0){
                do{
                    x = index/5;
                    y = random.nextInt(sizeField);
                }while(!isCellValid(x,y,sizeField));
            }
            if (index%5 == 1){
                do{
                    y = (index-1)/5;
                    x = random.nextInt(sizeField);
                }while(!isCellValid(x,y,sizeField));
            }
            if (index%5 == 2){
                do{
                    x = random.nextInt(sizeField);
                    y = x;
                }while(!isCellValid(x,y,sizeField));
            }
            if (index%5 == 3){
                do{
                    x = random.nextInt(sizeField);
                    y = sizeField - 1 - x;
                }while(!isCellValid(x,y,sizeField));
            }
        }
        table[x][y] = SIGN_O;
    }

    boolean isWin(char ch, int sizeField){
        int k,l,m,n;
        n=m=0;
        for (int i=0; i<sizeField; i++){
            k=l=0;
            for (int j=0; j<sizeField; j++){
                if (table[i][j] == ch) k++;
                if (table[j][i] == ch) l++;
            }
            if (table[i][i] == ch) m++;
            if (table[i][sizeField-1-i] == ch) n++;
            if (k==sizeField || l==sizeField) return true;
        }
        if (m == sizeField || n == sizeField) return true;
        return false;
    }

    int checkSituation(int sizeField, char chX, char chO){
        int horO,horX,verO,verX,mainDiagO,mainDiagX,diagO,diagX;
        mainDiagO=mainDiagX=diagO=diagX=0;
        for (int i=0; i<sizeField; i++){
            horX=horO=verX=verO=0;
            for (int j=0; j<sizeField; j++){
                if (table[i][j] == chX) horX++;
                if (table[i][j] == chO) horO++;
                if (table[j][i] == chX) verX++;
                if (table[j][i] == chO) verO++;
            }
            if (table[i][i] == chO) mainDiagO++;
            if (table[i][i] == chX) mainDiagX++;
            if (table[i][sizeField-1-i] == chO) diagO++;
            if (table[i][sizeField-1-i] == chX) diagX++;

            if ((horX==sizeField-1 || horO==sizeField-1) && horX+horO != sizeField) return i*5;
            if ((verX==sizeField-1 || verO==sizeField-1) && verX+verO != sizeField) return i*5+1;
        }
        if ((mainDiagO == sizeField-1 || mainDiagX == sizeField-1) && mainDiagX+mainDiagO != sizeField) return 2;
        if ((diagO == sizeField-1 || diagX == sizeField-1) && diagX+diagO != sizeField) return 3;
        return 4;
    }

    boolean isTableFull(int sizeField){
        for (int i=0;i<sizeField;i++){
            for (int j=0;j<sizeField;j++){
                if (table[i][j] == SIGN_EMPTY) return false;
            }
        }
        return true;
    }

    boolean isCellValid(int x, int y, int sizeField){
        if (x<0 || x>sizeField-1 || y<0 || y>sizeField-1){
            return false;
        }
        return table[x][y] == SIGN_EMPTY;
    }
}
