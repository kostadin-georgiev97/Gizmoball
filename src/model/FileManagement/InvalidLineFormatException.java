package model.FileManagement;

import java.io.IOException;

public class InvalidLineFormatException extends IOException {
    public InvalidLineFormatException(String s){
        super(s);
    }
}
