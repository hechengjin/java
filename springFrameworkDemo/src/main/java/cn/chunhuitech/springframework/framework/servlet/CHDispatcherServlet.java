package cn.chunhuitech.springframework.framework.servlet;

import cn.chunhuitech.springframework.framework.annotation.CHAutowried;
import cn.chunhuitech.springframework.framework.annotation.CHController;
import cn.chunhuitech.springframework.framework.annotation.CHRequestMapping;
import cn.chunhuitech.springframework.framework.annotation.CHService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class CHDispatcherServlet extends HttpServlet{

    //配置信息都存入了Properties中
    private Properties p = new Properties();

    private List<String> classNames = new ArrayList<String>();

    private Map<String, Object> ioc = new HashMap<String, Object>();

    private Map<String, Method> handlerMapping = new HashMap<String, Method>();

///////////////////////////////////////
    public static String name = "Hello";   //静态变量，可能发生线程安全问题
    int i;  //实例变量，可能发生线程安全问题
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    //只要在Servlet里面的任何方法里面都不使用实例变量，那么该Servlet就是线程安全的。
    //从Java 内存模型也可以知道，方法中的临时变量是在栈上分配空间，而且每个线程都有自己私有的栈空间，所以它们不会影响线程的安全。
///////////////////////////////////////
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("--------CHServlet初始化---------");
        //1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        //2.根据配置文件扫描所有的相关的类
        String packageName = p.getProperty("scanPackage");
        doScanner(packageName);

        //3.初始化所有的相关类的实例，并且将其放入到IOC容器之中，也就是Map中
        doInstance();

        //4.实现自动依赖注入
        doAutowried();

        //5.初始化HandlerMapping
        initHandlerMapping();

        super.init(config);
    }

    private void initHandlerMapping() {
        if (ioc.isEmpty()) {return;}
        for(Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            //不是所有的牛奶都叫金典
            if (!clazz.isAnnotationPresent(CHController.class)) {continue;}

            String baseUrl = "";
            if (clazz.isAnnotationPresent(CHRequestMapping.class)) {
                CHRequestMapping requestMapping = clazz.getAnnotation(CHRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                //不是所有的牛奶都叫金典
                if (!method.isAnnotationPresent(CHRequestMapping.class)) { continue; }
                CHRequestMapping requestMapping = method.getAnnotation(CHRequestMapping.class);
                String url = (baseUrl + requestMapping.value().replaceAll("/+", "/"));
                handlerMapping.put(url, method);
                System.out.println("Mapping : " + url + "," + method);
            }
        }

    }

    private void doAutowried() {
        if (ioc.isEmpty()) {return;}
        for (Map.Entry<String, Object> entry: ioc.entrySet()) {
            //首先第一步要获取到所有的字段Field
            //不管是private还是protected还是default都要强制注入
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field: fields) {
                //不是所有的牛奶都叫金典
                if (!field.isAnnotationPresent(CHAutowried.class)) { continue; }
                CHAutowried autowried = field.getAnnotation(CHAutowried.class);
                String beanName = autowried.value().trim();
                if ("".equals(beanName)) {
                    beanName = field.getType().getName();
                }
                //要想访问到私有的，或者受保护的，我们强制授权访问
                field.setAccessible(true);

                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doInstance() {
        if (classNames.isEmpty()) {return;}
        //如果不为空，利用反射机制将刚刚扫描进来的所有的className初始化
        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                //接下来进入Bean实例化阶段，初始化IOC容器
                //不是所有的牛奶都叫金典

                //IOC容器规则
                //1.key默认用类名 首字母小写




                if (clazz.isAnnotationPresent(CHController.class)) {
                    String beanName = lowerFirestCase(clazz.getSimpleName());
                    ioc.put(beanName, clazz.newInstance());

                } else if(clazz.isAnnotationPresent(CHService.class)) {
                    //2.如果用户自定义名字，那么要优先选择用户自定义的名字
                    CHService service = clazz.getAnnotation(CHService.class);
                    String beanName = service.value();
                    if ("".equals(beanName.trim())) {
                        beanName = lowerFirestCase(clazz.getSimpleName());
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName, instance);

                    //3.如果是接口的话，我们可以巧妙用接口类型作为key
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for(Class<?> i : interfaces) {
                        ioc.put(i.getName(), instance);
                    }

                } else {
                    continue;
                }


            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String lowerFirestCase(String str) {
        char [] chars = str.toCharArray();
        chars[0] +=32;
        return String.valueOf(chars);
    }

    private void doScanner(String packageName) {
        //进行递归扫描
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.","/"));
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if (file.isDirectory()) {
                doScanner(packageName + "." + file.getName());
            } else {
                classNames.add(packageName + "." + file.getName().replace(".class", ""));
            }
        }
    }

    private void doLoadConfig(String location) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(location);
        try {
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        if (!handlerMapping.containsKey(url)){
            resp.getWriter().write("404 not found!");
        }
        Method m = handlerMapping.get(url);
        System.out.println("===========" + m);
//        super.doGet(req, resp);
    }

    //6.等待请求，进入运行阶段  运行阶段执行的方法
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    //上面的方法是在service里根据语法方法分别调用的
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.printf("%s：%s[%s]\n", Thread.currentThread().getName(), i, format.format(new Date()));
        i++;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s：%s[%s]\n", Thread.currentThread().getName(), i, format.format(new Date()));
//        resp.getWriter().println("<html><body><h1>" + i + "</h1></body></html>");
        super.service(req, resp);
    }
}
