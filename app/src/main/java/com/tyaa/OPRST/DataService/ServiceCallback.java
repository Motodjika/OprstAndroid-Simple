package com.tyaa.OPRST.DataService;

/**
 * Created by Pavlo on 12/13/2015.
 */

import java.util.ArrayList;

public interface ServiceCallback {
    void onDataObtained(MsgHolder holder,String jsonString);
}
