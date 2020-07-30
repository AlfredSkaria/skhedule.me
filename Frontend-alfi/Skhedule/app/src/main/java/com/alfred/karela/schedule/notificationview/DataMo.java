package com.alfred.karela.schedule.notificationview;

/**
 * Created by Alfred on 17-03-2017.
 */

public class DataMo {

    String ename;
    String eloctn;
    String edate;


    public DataMo(String ename, String eloctn, String edate) {
        this.ename = ename;
        this.eloctn = eloctn;
        this.edate = edate;

    }

    public String getName() {
        return ename;
    }

    public String getloctn() {
        return eloctn;
    }

    public String getdate(){
        return  edate;
    }

}
