package com.itechvision.ecrobo.pickman.AsyncTask;

import java.io.File;

public class ParamsGetter {
    private String key,values;
    private File file=null;
    private StringBuffer values1 = new StringBuffer();

    public ParamsGetter(){}

    public ParamsGetter(String key, String values){
        setKey(key);
        setValues(values);
    }
    public ParamsGetter(String key, File file){
        setKey(key);
        setFile(file);
    }

    public ParamsGetter(String key, StringBuffer values1 ){
        setKey(key);
        setValues1(values1);
    }



    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public StringBuffer getValues1() {
        return values1;
    }

    public void setValues1(StringBuffer values1 ) {
        this.values1 = values1;
    }

}