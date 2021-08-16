package com.littlelito.wzry.access;

public interface LivingEntityAccess {
    int getLastUseMingDao();
    void setLastUseMingDao(int tick);

    boolean getCanUseMingDao();
    void setCanUseMingDao(boolean can);
}
