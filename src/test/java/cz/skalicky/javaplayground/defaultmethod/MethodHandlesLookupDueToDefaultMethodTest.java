package cz.skalicky.javaplayground.defaultmethod;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.testng.annotations.Test;

public class MethodHandlesLookupDueToDefaultMethodTest {

    public static interface MyInterface {

        String getMessage();

        default String dump() {
            return "dump=" + getMessage();
        }
    }

    private static final class MyInterfaceInvocationHandler implements InvocationHandler {

        private final Class<?> myinterfaceClass;
        private final MethodHandles.Lookup myInterfaceInstance;

        public MyInterfaceInvocationHandler(final Class<?> interfaceClass) {

            myinterfaceClass = interfaceClass;

            try {
                final Constructor<Lookup> classConstructor = MethodHandles.Lookup.class
                        .getDeclaredConstructor(Class.class, int.class);
                classConstructor.setAccessible(true);
                myInterfaceInstance = classConstructor.newInstance(interfaceClass,
                        MethodHandles.Lookup.PRIVATE);
            } catch (NoSuchMethodException | SecurityException | InstantiationException
                    | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isDefault()) {

                // @formatter:off
                return "interception1 " + myInterfaceInstance.unreflectSpecial(method, myinterfaceClass)
                        .bindTo(proxy).invokeWithArguments(args);
                // @formatter:on
            } else {
                return "interception2 MESSAGE";
            }
        }
    }

    private MyInterface createJavaProxy() {

        Class<?> interfaceClass = MyInterface.class;

        return (MyInterface) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[] { interfaceClass }, new MyInterfaceInvocationHandler(interfaceClass));
    }

    @Test
    public void testOrdinalInterfaceMethod() {

        final MyInterface proxyObject = createJavaProxy();

        assertThat(proxyObject.getMessage(), is("interception2 MESSAGE"));
    }

    @Test
    public void testDefaultMethod() {

        final MyInterface proxyObject = createJavaProxy();

        assertThat(proxyObject.dump(), is("interception1 dump=interception2 MESSAGE"));
    }

}
