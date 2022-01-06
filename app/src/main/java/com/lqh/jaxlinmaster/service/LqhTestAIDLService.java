package com.lqh.jaxlinmaster.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.lqh.jaxlinmaster.IMyAidlInterface;

/**
 * Created by Linqh on 2021/12/2.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
public class LqhTestAIDLService  extends Service {
    public LqhTestAIDLService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
    class MyBinder extends IMyAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int getFoodPrice(String foodName) throws RemoteException {
            int price=-1;
            if("11".equals(foodName)){
                price= 88;
            }else if("22".equals(foodName)){
                price=24;
            }
            return price;
        }
    }
}
