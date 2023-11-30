import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class Trans {
    public static void main(String[] args) throws IOException {

        if (args.length != 5) {
            System.exit(1);
        }

        int keylength = Integer.parseInt(args[0]);
        String key = args[1];
        String inputfile = args[2] + ".txt";
        String outputfile = args[3] + ".txt";
        String operation = args[4];

        validateKey(key,keylength);
        String line = readfunction(inputfile, keylength);
    
        if (operation.equals("enc")) {
            encryption(line, keylength,key,outputfile);
        } else if (operation.equals("dec")) {
            decryption(line, keylength, key, outputfile);
        } else {
            System.out.println("Error: Invalid operation.");
            System.exit(1);
        }
    }

    private static void validateKey(String key, int keylength) {
        if (key.length() != keylength) {
            System.out.println("Error: Key length does not match the specified keylength.");
            System.exit(1);
        }

        Set<Character> expectedDigits = new HashSet<>();
        for (char digit = '1'; digit <= '9'; digit++) {
            expectedDigits.add(digit);
        }

        for (int i = 0; i < key.length(); i++) {
            char digit = key.charAt(i);
            if (!expectedDigits.contains(digit)) {
                System.out.println("Error: The key must include all digits from 1 to keylength exactly once.");
                System.exit(1);
            }
            expectedDigits.remove(digit);
        }
    }

    //file writing
    static void writefunction(String inputdata, String outputfile) throws IOException {
        try {
            FileWriter fw = new FileWriter(outputfile);
            for (int i = 0; i < inputdata.length(); i++)
                fw.write(inputdata.charAt(i));
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    //file reading
    static String readfunction(String inputfile, int keylength) throws IOException {
        try {
            String pattern = "^[a-z0-9]+$";
            FileReader reader = new FileReader(inputfile);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            line = bufferedReader.readLine();
            reader.close();
            int len = line.length();
            int num;
            if (len % keylength != 0) {
                num = keylength - (len % keylength);
                for (int i = 0; i < num; i++) {
                    line = line + "z";
                }
            }
            if(line.matches(pattern) == false)
            {
                System.exit(1);
            }
            return line;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    //encryption function
    private static void encryption(String line, int keylength, String key, String outputfile) throws IOException {
        int rows = line.length() / keylength;
        char[][] arr = new char[rows][keylength];

        int a = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < keylength; j++) {
                arr[i][j] = line.charAt(a);
                a++;
            }
        }
        int[] num = new int[keylength];
        for (int i = 0; i < keylength; i++){
            num[i] = key.charAt(i) - '0';
        }
        String cipher = "";
        for (int i = 0, k = 0; i < keylength; i++, k++) {
            int d;
            d = num[i]-1;
            for (int j = 0; j < rows; j++) {
                cipher = cipher + arr[j][d];

            }
        }
        writefunction(cipher, outputfile);
    }

    //decryption
    private static void decryption(String line, int keylength, String key, String outputfile) throws IOException {
        int rows = line.length() / keylength;
        char[][] arr = new char[rows][keylength];

        String plainText = "";

        int[] num = new int[keylength];
        for (int i = 0; i < keylength; i++){
            num[i] = key.charAt(i) - '0';
        }

        int index = 0;
        for(int j=0; j<keylength;j++){
            for (int i=0; i<rows;i++){
                arr[i][num[j]-1] = line.charAt(index);
                index += 1;
            }
        }
        for (int i=0; i<rows;i++){
            for(int j=0; j<keylength;j++){
                plainText += arr[i][j];
            }
        }
        writefunction(plainText, outputfile);
    }

}


