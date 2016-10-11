package net.launcher.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ProcessUtils {
    
    private Process process = null;
    
    public ProcessUtils(Process process) {
        this.process = process;
    }
    
    public void print() {
        Thread errorThread = new Thread() { public void run() { print(true); } };
        errorThread.start();   
        print(false);
    }
    
    private void print(boolean isErrorStream) {
        try {
            InputStream inputStream;
            if (isErrorStream) inputStream = process.getErrorStream();
            else inputStream = process.getInputStream();
            
            InputStreamReader reader;
            if(BaseUtils.getPlatform()==2) {
            	reader = new InputStreamReader(inputStream, "cp1251");
            } else {
            	reader = new InputStreamReader(inputStream, "utf-8");
            }
            BufferedReader buf = new BufferedReader(reader);
            String line = null;
            
            while (isRunning()) {
                try {
                    while ((line = buf.readLine()) != null) {
                        if (isErrorStream)
                            BaseUtils.sendErrp(line);
                        else
                            BaseUtils.sendp(line);
                    }
                } catch (IOException ex)
                {} finally {
                    try
                    {
                        buf.close();
                    } catch (IOException ex)
                    {}
                }
            }
        } catch (UnsupportedEncodingException e) {
            BaseUtils.sendErr("Не удалось установить кодировку при выводе сообщений об отладке");
            e.printStackTrace();
        }
    }
    
    public boolean isRunning() {
        try {
            process.exitValue();
        } catch (IllegalThreadStateException ex) {
            return true;
        }
        
        System.exit(0);
        return false;
    }
}
