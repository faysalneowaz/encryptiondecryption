package com.example.encdecjava;

import android.annotation.SuppressLint;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    private static  final String CHANNEL = "com.example.encdecjava";
    //private static  final String CHANNELdec = "dec";
    MethodChannel methodChannel;
    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),CHANNEL)
                .setMethodCallHandler((call,result)->{
            if (call.method.equals("encrypt")){
                Toast.makeText(this, "sad", Toast.LENGTH_SHORT).show();
                Map<String,String> arg = (Map<String, String>) call.arguments;
                String msg = arg.get("text");
                String key = arg.get("key");

                try {
                    String enctext = Encrypt(msg,key);
                    if (enctext.equals("error")){
                        result.success(enctext);
                    }else {
                        result.success(enctext);
                        Toast.makeText(this, enctext, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            else if (call.method.equals("decrypt")){
                Toast.makeText(this, "decrypttext", Toast.LENGTH_SHORT).show();
                Map<String,String> arg = (Map<String, String>) call.arguments;
                String msg1 = arg.get("text");
                String key1 = arg.get("key");
                try {
                    String decrypttext = Decrypt(msg1,key1);
                    result.success(decrypttext);
                    Toast.makeText(this, decrypttext, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            else{
                result.notImplemented();
            }


        });


    }
    public static String Decrypt(String text, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        //BASE64Decoder decoder = new BASE64Decoder();
        //byte[] results = cipher.doFinal(decoder.decodeBuffer(text));
        byte[] results = cipher.doFinal(Base64.decode(text.getBytes(),Base64.DEFAULT));
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            results = cipher.doFinal(Base64.decode(text));
//        }
        return new String(results, "UTF-8");
//        return results;
    }

    public static String Encrypt(String text, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
        //BASE64Encoder encoder = new BASE64Encoder();
        //String base64 = Base64.encodeToString(results, Base64.);
        byte[] encodedBytes = Base64.encode(results,Base64.DEFAULT);
        return new String(encodedBytes);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            return String.valueOf(Base64.getEncoder().encode(results));
//        }else{
//            return "error";
//        }

    }
}
