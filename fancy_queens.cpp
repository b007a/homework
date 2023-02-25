#include <iostream>
using namespace std;

//same ok function as 1d w.o goto
bool ok(int a[], int c){
    for(int i=0; i<c; i++){
        if(a[c]==a[i] || (c-i)==abs(a[c]-a[i])) return false;
    }
    return true;
}


typedef string box[5][7];
    //black queen
    //i did it like this so it was easier to see 
box bq = {{"\u2588","\u2588","\u2588", "\u2588", "\u2588", "\u2588", "\u2588"},
{"\u2588"," ", "\u2588", " ", "\u2588", " ", "\u2588" },
{"\u2588", " ", " ", " ", " ", " ", "\u2588"},
{"\u2588", " ", " ", " ", " ", " ", "\u2588"},
{"\u2588","\u2588","\u2588", "\u2588", "\u2588", "\u2588", "\u2588"} };

    // white queen
box wq = {{" ", " ", " ", " ", " "," ", " "},
{" ","\u2588"," ", "\u2588", " ", "\u2588", " "},
{" ","\u2588","\u2588","\u2588", "\u2588", "\u2588"," "},
{" ","\u2588","\u2588","\u2588", "\u2588", "\u2588"," "},
{" ", " ", " ", " ", " "," ", " "}};


void print_box(string b[5][7]){
    for(int i=0; i<5; i++){
        for(int j=0; j<7; j++){
            cout << b[i][j];
        }
        cout << endl;
    }
}

void print(int q[]){
    // static int bc we went over in class today and makes the code look nicer 
    static int count=0;
    int x;
    // 1d w.o goto
    cout << "Solution #" << ++count <<endl;
    for(int i=0; i<8; i++){
        cout << q[i];
    }
    cout << endl << endl;


    box bb, wb;
    //pg 48 filling in the boxes
    for(int i=0; i<5; i++){
        for(int j=0; j<7; j++){
            bb[i][j] = " ";
            wb[i][j] = "\u2588";
        }
    }
    box *board[8][8];

    for(int i=0; i<8; i++){
        for(int j=0; j<8; j++){
            //x is what i used in my 1d w.o goto
            x=j;

            if((i+j)%2==0){
                // you want to use the q array to place the queens in the right places
                board[i][j]=&bb;
                if(i==q[x] && j==x) board[i][j]= &wq;
                //use ampersand because board is a pointer array
            }
            else{
                // black queen has a white background so you print it where you would print a white box
                board[i][j]=&wb;
                if(i==q[x] && j==x) board[i][j]= &bq;
            }
        }
    }

//top pg 48
    cout << " ";
    for(int i=0; i<7*8; i++){
        cout << "_";
    }
    cout << endl;


// the board pg 48
    for(int i=0; i<8; i++){
        for(int k=0; k<5; k++){
            cout << " " << "\u258c";
            for(int j=0; j<8; j++){
                for(int l=0; l<7; l++){
                    cout << (*board[i][j])[k][l];
                }
                
            }
            cout << "\u258c" << endl;
        }
    }


//bottom pg 48

    cout << " ";
    for(int i=0; i<7*8; i++){
        cout << "\u2584";
    }
    cout << endl << endl << endl;   
    

}

int main(){
    //code from 1d w.o goto

    int q[8]={0,};
    int c=0;
    q[0]=0;

    while(c>=0){
        c++;
        if(c==8){
            print(q);
            c--;
        }
        else q[c]=-1;
        while(c>=0){
            q[c]++;
            if(q[c]==8){
                c--;
            }
            else if(ok(q, c) == true) break;
        }
        
    }

    return 0;
    




    
    

}
