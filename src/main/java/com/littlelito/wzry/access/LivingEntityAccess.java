package com.littlelito.wzry.access;

import com.littlelito.wzry.data.LivingEntityData;

public interface LivingEntityAccess {
    int getLastUseMingDao();
    void setLastUseMingDao(int tick);

    boolean getCanUseMingDao();
    void setCanUseMingDao(boolean can);

    LivingEntityData getData();
    void setData(LivingEntityData livingEntityData);
}
