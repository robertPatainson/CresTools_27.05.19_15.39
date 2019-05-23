package com.lafayeboulhalfa.app.crestools;

/**
 * Created by HP on 19.03.2019.
 */

public interface SocketHandler {

    public void didReceiveData(String data, Device device);

    public void didDisconnect(Exception error, Device device);

    public void didConnect();

    public void errorHandler();

    public void loadHandler();

    public void exceptionBrokenPipe(String deviceAddress);

}