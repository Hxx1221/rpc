package classLoader;

import sun.misc.Launcher;

import java.io.IOException;
import java.io.InputStream;

public class Mains {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        ClassLoader classLoader = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                System.out.println("findClass");
                return super.findClass(name);
            }

            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {

                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";

                InputStream is = getClass().getResourceAsStream(fileName);

                if (is == null) {
                    return super.loadClass(name);

                }

                try {
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object aClass = classLoader.loadClass("classLoader.Mains").newInstance();
        ClassLoader classLoader1 = aClass.getClass().getClassLoader();
        Mains mains = new Mains();
        Class<? extends ClassLoader> aClass1 = mains.getClass().getClassLoader().getClass();
        System.out.println(aClass1.getName());
        System.out.println(classLoader1.getClass().getName());
        System.out.println(aClass.getClass());
        System.out.println(aClass instanceof classLoader.Mains );




    }
}