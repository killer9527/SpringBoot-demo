package com.wfw.study.ioc.xml;

/**
 * Created by killer9527 on 2018/3/26.
 */
public class AInterfaceImpl implements AInterface {
    private BInterface bInterface;

    public BInterface getbInterface() {
        return bInterface;
    }

    public void setbInterface(BInterface bInterface) {
        this.bInterface = bInterface;
    }

    @Override
    public void print() {
        System.out.println("this is an implementation of AInterface, it's dependency bInterface say: "
        + bInterface.say());
    }
}
