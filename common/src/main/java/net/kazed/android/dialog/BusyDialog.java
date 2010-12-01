package net.kazed.android.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Convenience class to display a ProgressDialog for long actions.
 * @author Koert Zeilstra
 */
public class BusyDialog {
    
    private Context androidContext;
    private BackgroundAction backgroundAction;
    private DoneAction doneAction;
    
    private ProgressDialog progressDialog;
    
    private Exception exception;
    
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Boolean done = msg.getData().getBoolean("done");
            if (Boolean.TRUE.equals(done)) {
                progressDialog.dismiss();
                doneAction.execute(exception);
            }
        }
    };

    /**
     * Constructor.
     * @param androidContext Android context.
     * @param backgroundAction Action to run in background.
     * @param doneAction Action to run when background action is done.
     */
    public BusyDialog(Context androidContext, BackgroundAction backgroundAction, DoneAction doneAction) {
        super();
        this.androidContext = androidContext;
        this.backgroundAction = backgroundAction;
        this.doneAction = doneAction;
    }
    
    public void execute(int title, int message) {
        Resources resources = androidContext.getResources();
        progressDialog = ProgressDialog.show(androidContext, resources.getString(title),
                        resources.getString(message), true);
        Thread progressThread = new Thread() {
            public void run() {
                try {
                    backgroundAction.execute();
                    exception = null;
                } catch (Exception e) {
                    exception = e;
                }

                Message msg = handler.obtainMessage();
                Bundle b = new Bundle();
                b.putBoolean("done", Boolean.TRUE);
                msg.setData(b);
                handler.sendMessage(msg);
            }
        };
        progressThread.start();
    }

    public interface BackgroundAction {
        public void execute();
    }
    
    public interface DoneAction {
        public void execute(Exception exception);
    }
}
