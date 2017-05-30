package com.doapps.petservices;

import android.app.Application;

import com.doapps.petservices.Network.CustomService;
import com.doapps.petservices.Network.RequestManager;

/**
 * Created by NriKe on 30/05/2017.
 */

public class PetServicesApplication extends Application {
    private CustomService services;
    public static PetServicesApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        services = new RequestManager().getWebServices();
    }

    public static PetServicesApplication getInstance(){
        return instance;
    }

    public CustomService getServices(){
        return services;
    }
}
