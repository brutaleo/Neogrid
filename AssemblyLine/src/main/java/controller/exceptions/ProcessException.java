package controller.exceptions;

import java.io.Serial;

public class ProcessException extends Exception{
  
    @Serial
    private static final long serialVersionUID = 1L;

    public ProcessException(String msg) {
        super(msg);
    }

}
