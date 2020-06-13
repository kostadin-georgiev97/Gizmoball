package model.FileManagement;

import java.io.IOException;

public class NoSuchGizmoException extends IOException {
    public NoSuchGizmoException(String s){
        super(s);
    }
}
