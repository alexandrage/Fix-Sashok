package net;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;
import net.lang.Hex;


public final class y {

    private static AtomicLong a = new AtomicLong(Long.MIN_VALUE);

    private static String b = null;

    private static long c = 0x8000000000000000L;

    static {

        try {
            Class.forName("java.net.InterfaceAddress");
            b = y.class.newInstance().toString();
        }
        catch (ExceptionInInitializerError err) {

        }
        catch (ClassNotFoundException ex) {

        }
        catch (LinkageError err) {

        }
        catch (IllegalAccessException ex) {
 
        }
        catch (InstantiationException ex) {

        }
        catch (SecurityException ex) {

        }

        if (b == null) {

            Process p = null;
            BufferedReader in = null;

            try {
                String osname = System.getProperty("os.name", ""), osver = System.getProperty("os.version", "");

                if (osname.startsWith("Windows")) {
                    p = Runtime.getRuntime().exec(
                    new String[] { "ipconfig", "/all" }, null);
                }

                else if (osname.startsWith("Solaris")
                        || osname.startsWith("SunOS")) {
                    if (osver.startsWith("5.11")) {
                        p = Runtime.getRuntime().exec(
                        new String[] { "dladm", "show-phys", "-m" }, null);
                    }
                    else {
                        String hostName = getFirstLineOfCommand("uname", "-n");
                        if (hostName != null) {
                            p = Runtime.getRuntime().exec(
                                    new String[] { "/usr/sbin/arp", hostName },
                                    null);
                        }
                    }
                }
                else if (new File("/usr/sbin/lanscan").exists()) {
                    p = Runtime.getRuntime().exec(
                            new String[] { "/usr/sbin/lanscan" }, null);
                }
                else if (new File("/sbin/ifconfig").exists()) {
                    p = Runtime.getRuntime().exec(
                            new String[] { "/sbin/ifconfig", "-a" }, null);
                }

                if (p != null) {
                    in = new BufferedReader(new InputStreamReader(
                            p.getInputStream()), 128);
                    String l = null;
                    while ((l = in.readLine()) != null) {
                        b = x.parse(l);
                        if (b != null
                                && Hex.parseShort(b) != 0xff) {
                            break;
                        }
                    }
                }

            }
            catch (SecurityException ex) {

            }
            catch (IOException ex) {

            }
            finally {
                if (p != null) {
                    close(in, p.getErrorStream(), p.getOutputStream());
                    p.destroy();
                }
            }

        }

        if (b != null) {
            c |= Hex.parseLong(b);
        }
        else {
            try {
                byte[] local = InetAddress.getLocalHost().getAddress();
                c |= (local[0] << 24) & 0xFF000000L;
                c |= (local[1] << 16) & 0xFF0000;
                c |= (local[2] << 8) & 0xFF00;
                c |= local[3] & 0xFF;
            }
            catch (UnknownHostException ex) {
                c |= (long) (Math.random() * 0x7FFFFFFF);
            }
        }

        c |= (long) (Math.random() * 0x3FFF) << 48;

    }

    public static long getClockSeqAndNode() {
        return c;
    }

    public static long newTime() {
        return createTime(System.currentTimeMillis());
    }

    public static long createTime(long currentTimeMillis) {

        long time;


        long timeMillis = (currentTimeMillis * 10000) + 0x01B21DD213814000L;

        while (true) {
            long current = a.get();
            if (timeMillis > current) {
                if (a.compareAndSet(current, timeMillis)) {
                    break;
                }
            } else {
                if (a.compareAndSet(current, current + 1)) {
                    timeMillis = current + 1;
                    break;
                }
            }
        }


        time = timeMillis << 32;

        time |= (timeMillis & 0xFFFF00000000L) >> 16;

        time |= 0x1000 | ((timeMillis >> 48) & 0x0FFF); // version 1

        return time;

    }

    public static String e() {
        return b;
    }

    static String getFirstLineOfCommand(String... commands) throws IOException {

        Process p = null;
        BufferedReader reader = null;

        try {
            p = Runtime.getRuntime().exec(commands);
            reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()), 128);

            return reader.readLine();
        }
        finally {
            if (p != null) {
                close(reader, p.getErrorStream(), p.getOutputStream());
                p.destroy();
            }
        }

    }

        @Override
        public String toString() {
            String out = null;
            try {
                Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
                if (ifs != null) {
                    while (ifs.hasMoreElements()) {
                        NetworkInterface iface = ifs.nextElement();
                        byte[] hardware = iface.getHardwareAddress();
                        if (hardware != null && hardware.length == 6
                                && hardware[1] != (byte) 0xff) {
                            out = Hex.append(new StringBuilder(36), hardware).toString();
                            break;
                        }
                    }
                }
            }
            catch (SocketException ex) {

            }
            return out;
        }

    //}
    private static void close(BufferedReader in, InputStream errorStream, OutputStream outputStream) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
