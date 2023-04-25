/*=============================================================================
| Assignment: pa02 - Calculating an 8, 16, or 32 bit
| checksum on an ASCII input file
|
| Author: Maria Hernandez
| Language: c, c++, Java, GO, Python
|
| To Compile: javac pa02.java
| gcc -o pa02 pa02.c
| g++ -o pa02 pa02.cpp
| go build pa02.go
| python pa02.py //Caution - expecting input parameters
|
| To Execute: java -> java pa02 inputFile.txt 8
| or c++ -> ./pa02 inputFile.txt 8
| or c -> ./pa02 inputFile.txt 8
| or go -> ./pa02 inputFile.txt 8
| or python-> python pa02.py inputFile.txt 8
| where inputFile.txt is an ASCII input file
| and the number 8 could also be 16 or 32
| which are the valid checksum sizes, all
| other values are rejected with an error message
| and program termination
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Fall 2022
| Instructor: McAlpin
| Due Date: 11/27/2022
|
+=============================================================================*/

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class pa02 {
    public static void main(String[] args) {
        Path file = Paths.get(args[0]); // file input
        String bits = args[1]; // bits, which could be 8, 16, or 32
        int bitSize;
        String inputFile = null; // file input in string
        bitSize = Integer.parseInt(bits);

        try {
            inputFile = new String(Files.readAllBytes(file), StandardCharsets.ISO_8859_1);
            System.out.println(new String(inputFile));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.print("\nValid checksum sizes are 8, 16, or 32\n");
            System.exit(1);
            return;
        }

        byte[] allBytes = getBytes(inputFile, bitSize);
        int checksum = checkSum(allBytes, bitSize);
        System.out.printf("%2d bit checksum is %8x for all %4d chars\n", bitSize, checksum, allBytes.length);
    }

    // method for checksum in 8, 16 and 32 bits, using bitshift
    public static int checkSum(byte[] bytes, int bits) {
        // bitwise and bitshift
        // bitwise & operator
        // bitwise exclusive ^ operator
        // bitwise inclusivle | operator
        int result = 0;

        // if statements for 8, 16 and 32 bits
        // if bits = 8, 
        if(bits == 8) {
            // (condition) ? (return if true) : (return if false);
            // if b == bytes --------- byte b : bytes
            for (byte b: bytes) {
                result += b;
            }
            return result & 0xFF;
        } else if (bits == 16) {

            for (int i = 0; i <= bytes.length - 2; i += 2) {
                result += ((bytes[i] << 8) | (bytes[i + 1] & 0xFF));
            }
    
            return result & 0xFFFF;
        } else if (bits == 32) {
            for (int i = 0; i < bytes.length; i += 4) {
                
                result += ((bytes[i] << 24) | (bytes[i + 1] << 16) | (bytes[i + 2] << 8) | (bytes[i + 3])) & 0xffffffffL;
            }

            return result;
        } else {
            System.err.println("Valid checksum sizes are 8, 16 and 32.");
            return result;
        }
    }
    
    public static byte[] getBytes(String input, int bit) {
        int size = input.length(); // storing size
        int pad = padding(size, bit);
        int size2;

        size2 = size + pad;
        byte[] s = new byte[size2]; // storing result

        for (int i = 0; i < size; i++) {
            s[i] = (byte) input.charAt(i);
        }

        if (pad > 0) {
            for (int j = size; j < size2; j++) {
                s[j] = 88;
            }
        }
        return s;
    }

    public static String output(String output) {
        StringBuffer b = new StringBuffer();

        // for loop for output length
        for (int i = 0; i < output.length(); i++) {
            if (i > 0 && i % 80 == 0) {
                b.append("\n");
            }
            b.append(output.charAt(i));
        }
        return b.toString();
    }

    public static String getString(String inputFile, int bit) {
        int size = inputFile.length();
        int pad = padding(size, bit);
        StringBuffer b = new StringBuffer();

        int size2;
        size2 = size + pad;

        for (int i = 0; i < size; i++) {
            b.append(inputFile.charAt(i));
        }

        // pad with capital x
        if (pad > 0) {
            for (int j = size; j < size2; j++) {
                b.append("X");
            }
        }

        return b.toString();
    }

    public static int padding(int length, int bit) {
        // (condition) ? (return if true) : (return if false);
        int mod = bit == 32 ? 4 : 2;
        int result = 0;
        
        // this is used for modding, mod 4 or mod 2
        // if(mod) {
        //     if(bit == 32) {
        //         return 4;
        //     } else {
        //         return 2;
        //     }
        // }

        // while loop that determines mod for optional padding
        while (length % mod != 0) {
            length = length + 1;
            result++;
        }

        if(bit > 8) {
            return result;
        } else {
            return 0;
        }
    }
}
/*=============================================================================
| I Maria Hernandez (5336239) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+============================================================================*/