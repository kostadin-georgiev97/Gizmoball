package model.FileManagement;

import java.io.IOException;
import java.util.Set;

import model.iPlaceable;

public class FileManagerTester {

    public static void main(String[] args) {
        FileLoader files = new FileLoader();
        Set<iPlaceable> placeables = null;
        try {
            placeables = files.loadLayout("example1.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(placeables);
        System.out.println("Saving file");
        FileSaver fileSaver = new FileSaver();
        try {
            fileSaver.saveLayout("saveFile",placeables,null,false,null,10);
        } catch (IOException e) {
            System.out.println("Unable to save file");
        }
        System.out.println(placeables.toString());
    }
}
//blah