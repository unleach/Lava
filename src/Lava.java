import LavaFiles.USDF_Handler;

public class Lava {
    public static void main(String[] args) {
        // testing and understanding the fetch code
        USDF_Handler handler = new USDF_Handler("C:\\Users\\mohamed\\OneDrive\\Bureau\\Lava version 1.0.2\\testing\\Test.txt");
        for (String str : handler.getFileContent()) {
            System.out.println(str);
        }
        handler.test();


    }
}
