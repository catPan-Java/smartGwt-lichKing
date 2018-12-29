package lichKing.client.myException;

import lichKing.client.server.MsgServer;
import lichKing.client.ui.msg.MessageUI;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author catPan
 *
 * @param <T>
 */
public abstract class DefaultCallback<T> implements AsyncCallback<T> {

    @Override
	public void onFailure(Throwable caught) {
        //	Message to display
        String message = caught.getMessage();
        if ((message == null) || (message.length() == 0)) {
            message = caught.toString();
        }
//                "500 The call failed on the server; see server log for details"
        if (message.startsWith("500")) {
        	MessageUI.setMsgError(MsgServer.getMsg("Unhandled_exception"));
//        	SC.say(MsgServer.getMsg("Unhandled_exception"));
        } else {
        	MessageUI.setMsgError(MsgServer.getMsg(message));
//        	SC.say(MsgServer.getMsg(message));
        }
    }

    @Override
	public void onSuccess(T result) {
//        RiaMain.alertMsg(MsgServer.getMsg("save") + MsgServer.getMsg("success"));
        MessageUI.setMsgSuccess(MsgServer.getMsg("save") + MsgServer.getMsg("success"));
    }
}
