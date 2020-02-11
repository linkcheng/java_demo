package cn.xyf.algorithm;

import cn.xyf.algorithm.reflect.Person;
import cn.xyf.algorithm.reflect.Student;
import cn.xyf.algorithm.reflect.User;

import java.lang.reflect.*;
import java.util.Arrays;

public class ReflectTest {
    public static void main(String[] args) throws Exception {
        Student student = new Student();

        Class stuClass = student.getClass();
        System.out.println(Arrays.asList(stuClass.getFields()));
        System.out.println(Arrays.asList(stuClass.getDeclaredFields()));
        System.out.println("===================");

        Field score = stuClass.getField("score");
        System.out.println(score.getName());
        System.out.println(score.getType());
        System.out.println(score.getModifiers());
        System.out.println(score.get(student));
        System.out.println("===================");

        Field grade = stuClass.getDeclaredField("grade");
        System.out.println(grade.getName());
        System.out.println(grade.getType());
        System.out.println(grade.getModifiers());
        // raise error
        // Exception in thread "main" java.lang.IllegalAccessException: Class cn.xyf.algorithm.ReflectTest can not access a member of class cn.xyf.algorithm.Student with modifiers "private"
        // System.out.println(grade.get(student));
        grade.setAccessible(true);
        System.out.println(grade.get(student));
        grade.set(student, 12);
        System.out.println(grade.get(student));
        System.out.println("===================");


        System.out.println(Arrays.asList(stuClass.getMethods()));
        System.out.println(Arrays.asList(stuClass.getDeclaredMethods()));
        Method getGrade = stuClass.getMethod("getGrade");
        System.out.println(getGrade);
        Method setGrade = stuClass.getMethod("setGrade", int.class);
        System.out.println(setGrade);
        // Exception in thread "main" java.lang.NoSuchMethodException: cn.xyf.algorithm.Student.getScore()
        //	at java.lang.Class.getMethod(Class.java:1786)
        //	at cn.xyf.algorithm.ReflectTest.main(ReflectTest.java:43)
        Method getScore = stuClass.getDeclaredMethod("getScore");
        System.out.println(getScore);
        System.out.println("===================");

        System.out.println(getGrade.getName());
        System.out.println(getGrade.getReturnType());
        System.out.println(Arrays.asList(setGrade.getParameterTypes()));
        System.out.println(setGrade.getModifiers());
        System.out.println("===================");

        String s = "Hello world";
        // 获取String substring(int)方法，参数为int:
        Method sub = String.class.getMethod("substring", int.class);
        // 在s对象上调用该方法并获取结果:
        String r = (String) sub.invoke(s, 6);
        System.out.println(r);
        System.out.println("===================");

        Method parseInt = Integer.class.getMethod("parseInt", String.class);
        // 调用该静态方法并获取结果:
        System.out.println(parseInt.invoke(null, "123"));
        System.out.println("===================");

        Method hello = Person.class.getMethod("hello");
        // 使用反射调用方法时，仍然遵循多态原则，相当于
        // Person p = new Student();
        // p.hello();
        hello.invoke(new Student());
        System.out.println("===================");

        // 获取构造方法
        Constructor constructor = stuClass.getConstructor(int.class, int.class);
        Student student1 = (Student) constructor.newInstance(10, 10);
        System.out.println(student1.toString());
        System.out.println("===================");

        // 继承
        System.out.println(Integer.class.getSuperclass());
        // 实现的接口
        System.out.println(Arrays.asList(Integer.class.getInterfaces()));
        System.out.println("===================");

        // 动态代理
        ProxyInvocationHandler handler = new ProxyInvocationHandler();
        handler.setTarget(new Person());

        User user = (User) handler.getProxy();
        user.hello();
        System.out.println("===================");
    }
}


class ProxyInvocationHandler implements InvocationHandler {
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.print("[debug] ");
        return method.invoke(target, args);
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }
}
