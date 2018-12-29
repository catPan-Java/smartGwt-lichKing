package lichKing.client.myException;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 *
 * @author catPan
 */
public  class ClientException extends RuntimeException implements IsSerializable  {
    private static final long serialVersionUID = -4179743509516475528L;
    private String message;
    
    public ClientException() {
    }

    public ClientException(String msg) {
    	super(msg);
    	message=msg;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
}
