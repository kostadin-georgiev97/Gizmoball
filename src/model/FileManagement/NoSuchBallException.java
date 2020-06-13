package model.FileManagement;

import java.io.IOException;

public class NoSuchBallException extends IOException {
    public NoSuchBallException(String s){
        super(s);
    }

}
