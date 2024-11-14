package OS_Codes_Java;

import java.io.*;

public class File_Reader {
    public static void main(String[] args) {
        String inputFile = "input.txt";
        String outputFile = "/tmp/output.txt";

        try (
                FileReader fileReader = new FileReader(inputFile);
                FileReader fileReader1 = new FileReader(inputFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
                FileWriter fileWriter = new FileWriter(outputFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            int character = 0;
            while ((character = bufferedReader.read()) != -1) {
                System.out.println((char)(character));
                StringBuilder sb =new StringBuilder();
                sb.append((char)(character));
                bufferedWriter.write(sb.toString());
                fileWriter.write(sb.toString());
                sb.delete(0,sb.length());
            }
            String line = null;
            while ((line = bufferedReader1.readLine())!=null)
            {
                System.out.println(line);
                bufferedWriter.write(line);
            }
            System.out.println("File copied successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}