package com.heihei.management.system.entity;

public class ImportResult {
    int addCount;
    int updateCount;

    public ImportResult() {
    }

    public ImportResult(int addCount, int updateCount) {
        this.addCount = addCount;
        this.updateCount = updateCount;
    }

    public int getAddCount() {
        return addCount;
    }

    public void setAddCount(int addCount) {
        this.addCount = addCount;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }
}
