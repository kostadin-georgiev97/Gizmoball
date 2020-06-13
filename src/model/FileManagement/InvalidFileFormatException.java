package model.FileManagement;

import java.io.IOException;

public class InvalidFileFormatException extends IOException {
    public InvalidFileFormatException(String s){
        super(s);
    }
}
