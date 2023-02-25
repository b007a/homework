#include <iostream>
using namespace std;

int main() {
    int b[8][8] ={0};
    int r;
    int c=0;
    int count=0;

    b[0][0] = 1;
nc:
    c++;
    if(c == 8) goto print;
    r=-1;
nr:
    r++;
    if(r == 8) goto backtrack;
    // row test
    for(int i=0; i<c; i++)
        if(b[r][i] == 1) goto nr;
    // up diagonal test
    for (int i=1; (r-i) >=0 && (c-i)>=0; i++)
        if(b[r-i][c-i] == 1) goto nr;
    //down diagonal test
    for(int i=1; (r+i)<8 && (c-i)>=0; i++)
        if(b[r+i][c-i] == 1) goto nr;   
    b[r][c]=1;
    goto nc;



backtrack:
    c--;
    if(c == -1) return 0;
    r=0;
    while (b[r][c] != 1) r++;
    b[r][c]=0;
    goto nr;

print:
    cout << "Solution #" << ++count << endl;
    for(int x=0; x<8; x++){
        for(int y=0; y<8; y++){
            cout << b[x][y] << " ";
        }
        cout << endl;
    }
    cout << endl;
    goto backtrack;
}
