package io.xuy.dds.config;

public class DataSourceTypeHolder {
    private static final ThreadLocal<String> dataSourceTypes = new ThreadLocal<String>();

    public static String get(){
        return dataSourceTypes.get();
    }

    public static void set(String dataSourceType){
        dataSourceTypes.set(dataSourceType);
    }

    public static void reset(){
        dataSourceTypes.set(MultipleDateSourceConfig.OFFLINE);
    }

    public static void remove() {
        dataSourceTypes.remove();
    }
}
