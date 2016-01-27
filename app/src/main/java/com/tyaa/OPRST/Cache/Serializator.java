package com.tyaa.OPRST.Cache;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Pavlo on 11/15/2015.
 */
public class Serializator<T> {
    public static final String TAG = "Serializator";
    private File cacheDir;
    protected Context mContext;
    protected String mFilename;

    public String getFilename() {
        return mFilename;
    }

    public void setFilename(String filename) {
        mFilename = filename;
    }

    public Serializator(Context contexr){
        mContext=contexr;
        mFilename =TAG;
        setDir();
    }
    public Serializator(Context contexr,String filename){
        mContext=contexr;
        mFilename=filename;
        setDir();
    }
    private void setDir(){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().
                equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),
                    "com.tyaa.OPRST/.Cache");
        else
            cacheDir=mContext.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }
    protected void clear(){
        try{
        File[] files=cacheDir.listFiles();
        for(File f:files){
            f.delete();}
        } catch(Exception ex){
            Log.e(TAG, "Failed to clear cache", ex);
        }
    }
    public static String md5(String s) { try {

        // Create MD5 Hash
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(s.getBytes());
        byte messageDigest[] = digest.digest();

        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        for (int i=0; i<messageDigest.length; i++)
            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
        return hexString.toString();

    } catch (NoSuchAlgorithmException nsae) {
        Log.e(TAG, "Failed to create hash", nsae);
    }
        return "";

    }
    public void Serialize(T object) {
        Serialize(object,TAG);
    }

    public void Serialize(T object,String filename){
        FileOutputStream fos;
        try {
            if(mContext!=null) {
                //fos = mContext.getApplicationContext().openFileOutput(mFilename, Context.MODE_PRIVATE);
                fos = mContext.openFileOutput(filename, Context.MODE_PRIVATE);

                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(object);
                oos.close();
            }
        } catch (FileNotFoundException fnfe) {
            Log.e(TAG, "Failed to find file on serialize", fnfe);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to get access to file on serialize", ioe);
        }
    }
    public T Desserialize() {
        return Desserialize(TAG);
    }

    public T Desserialize(String filename) {
        FileInputStream fin;
        T afromfile = null;

        try {
            if(mContext!=null) {
                fin = mContext.openFileInput(filename);
                ObjectInputStream ois = new ObjectInputStream(fin);
                afromfile = (T) ois.readObject();
                ois.close();
            }
        } catch (StreamCorruptedException sce) {
            Log.e(TAG, "Failed to read stream", sce);
        } catch (OptionalDataException ode) {
            Log.e(TAG, "Failed to find object in file", ode);
        } catch (FileNotFoundException fnfe) {
            Log.e(TAG, "Failed to find file on deserialize", fnfe);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to get access to file on deserialize", ioe);
        } catch (ClassNotFoundException cnfe) {
            Log.e(TAG, "Failed to get deserialize class", cnfe);
        }

        return afromfile;

    }
}
