package com.thermostate;


import java.util.Map;

class DBAsserter {

    DbUtils dbUtils = new DbUtils();

    public void update(String sql) {
        dbUtils.executeUpdate(sql);
    }

}
