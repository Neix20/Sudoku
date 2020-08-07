package sample;

public class solveSudoku {
    public int data[][] = new int[9][9];

    public solveSudoku(int[][] data){
        this.data=data;
    }

    public boolean isPresentInCol(int col,int num){
        for(int i=0;i<9;i++){
            if(data[i][col]==num)
                return true;
        }
        return false;
    }

    public boolean isPresentInRow(int row,int num){
        for(int i=0;i<9;i++){
            if(data[row][i]==num)
                return true;
        }
        return false;
    }

    public boolean isPresentInGrid(int startRow,int startCol,int num){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(data[startRow+i][startCol+j]==num)
                    return true;
            }
        }
        return false;
    }

    public boolean isValid(int row,int col,int num){
        return !isPresentInRow(row,num)&&!isPresentInCol(col,num)&&!isPresentInGrid(row-row%3,col-col%3,num);
    }

    public boolean solveSudoku(int[][] board,int n){
        int row=-1,col=-1;
        boolean isEmpty=true;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(data[i][j]==0){
                    row=i;
                    col=j;
                    isEmpty=false;
                    break;
                }
            }
            if(!isEmpty){
                break;
            }
        }

        if(isEmpty){
            return true;
        }

        for (int i = 1; i <= 9; i++) {
            if (isValid(row, col, i)) {
                board[row][col] = i;
                if (solveSudoku(board, n)) {
                    return true;
                }
                else {
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }
}
