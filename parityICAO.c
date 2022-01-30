// Online C compiler to run C program online
#include <stdio.h>
unsigned char a[] = { 0xC0,0x76,0x7F,0x6D,0x32,0x1C,0xDF,0x76
};

unsigned char ParityNumberCount(unsigned char data)
{
    unsigned char realData = data;
    unsigned char parity = 0;
    int oneBitsCount = 0, i = 0;;

    while (data) {
        parity ^= (data & 1);
        oneBitsCount += data & 1;
        //  printf("oneBitsCount:%d   (data & 1):%d\n", oneBitsCount, (data & 1));
        data >>= 1;
    }

    if (oneBitsCount % 2 == 0) {
        if ((realData % 2) == 0) {
            //  printf("(realData % 2)  == 0\n");
            realData += 1;
            //  printf("new realData:%02X\n", realData);
        }
        else {
            //printf("(realData % 2)  != 0\n");
            realData -= 1;
            // printf("new realData:%02X\n", realData);
        }

    }
   // printf("%02X", realData);
    
    FILE* f = fopen("parityResult.txt", "a");

    if (f == NULL)
    {
        printf("Error opening file!\n");
        exit(1);
    }

   

    
    fprintf(f, "%02x", realData);

    

    fclose(f);


}

int main() {
    int c,i=0,j=0;
  
    char hexstring[50] = { 0 },ch;
    //to clean file
    FILE* f = fopen("parityResult.txt", "w");
    fclose(f);
    //to clean file

    FILE* file;
    file = fopen("test.txt", "r");
    if (file) {
        while ((ch = getc(file)) != EOF){
           // putchar(ch);//print file data
            hexstring[i] = ch;
        i++;
        }
        fclose(file);
    }
   // printf("\n\n\n\n\n\n");
    //read array
    /*
    printf("\n\n\n\n\n\n");
    for (j = 0; j < i; j++) {
        printf("array[j]:%c\n", array[j]);
           
    }
    */

  

    //hex string to byte array
   // const char hexstring[] = "DEadbeef10203040b00b1e50", 
    const char* pos = hexstring;
    unsigned char val[100];

    /* WARNING: no sanitization or error-checking whatsoever */
    for (size_t count = 0; count < (i + 1)/2; count++) {
        sscanf_s(pos, "%2hhx", &val[count]);
        pos += 2;
    }
    /*
  
    for (size_t count = 0; count < (i + 1) / 2 ; count++)
        printf("%02x", val[count]);
    printf("\n");
    //hex string to byte array
    */

    

    for (size_t count = 0; count < (i + 1) / 2; count++) {
        // printf("new Operations for :%02X\n", a[i]);
        ParityNumberCount(val[count]);
    }

    


   






   
    return 0;
}