package geek.week1;

import java.lang.reflect.Method;
import java.util.Base64;

public class HelloXClassLoader extends ClassLoader{
    public static void main(String[] args) {
        try {
            ClassLoader loader = new HelloXClassLoader();
            Class<?> c = loader.loadClass("Hello");
            Method m = c.getMethod("hello");
            m.invoke(c.getDeclaredConstructor().newInstance());
        } catch (Throwable e) {
            System.out.println(e);
        }
    }
    @Override
    protected Class<?> findClass(String name) {
//        Hello.xlass经过base64后的编码
        String base64Str = "NQFFQf///8v/4/X/+f/x9v/w/+/3/+71/+3/7Pj/6/j/6v7/+cOWkZaLwf7//NfWqf7/+7yQm5r+//CzlpGasYqSnZqNq56dk5r+//qXmpOTkP7/9ayQio2cmrmWk5r+//W3mpOTkNGVnome8//4//f4/+nz/+j/5/7/7Leak5OQ09+ck56MjLOQnpuajd74/+bz/+X/5P7/+reak5OQ/v/vlZ6JntCTnpGY0LCdlZqci/7/75WeiZ7Qk56RmNCshoyLmpL+//yQiov+/+qzlZ6JntCWkNCvjZaRi6yLjZqeksT+/+yVnome0JaQ0K+NlpGLrIuNmp6S/v/4j42WkYuTkf7/6tezlZ6JntCTnpGY0KyLjZaRmMTWqf/e//r/+f///////f/+//j/9//+//b////i//7//v////rVSP/+Tv////7/9f////n//v////7//v/0//f//v/2////2v/9//7////2Tf/97fxJ//tO/////v/1////9f/9////+//3//r//v/z/////f/y";
        byte[] classBytes = Base64.getDecoder().decode(base64Str);
        for (int i = 0; i < classBytes.length; i++) {
            classBytes[i] = (byte) (255 - classBytes[i]);
        }
        Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
        return cl;
    }
}
