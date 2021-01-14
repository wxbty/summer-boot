package ink.zfei.boot.mvc;

import ink.zfei.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

import javax.servlet.*;
import java.io.IOException;

public class DispatcherServlet implements Servlet {

   private AnnotationConfigServletWebServerApplicationContext appletContext;

    public DispatcherServlet(AnnotationConfigServletWebServerApplicationContext appletContext) {
        this.appletContext = appletContext;
    }

    public void init(ServletConfig servletConfig) throws ServletException {
            //初始化mapping
            //先找到容器里所有带@Controller 的bean
//          List<Object> list = appletContext.getBean()

            //解析controller,把path，和controller实例放到map中
        }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

//        if (isGet(servletRequest))
//        {
//            doGet();
//        }
        System.out.println(1111111);
        //1、 解析path
//        doc.write("<body>xxxx</body>")
        //2、在mapping里找到handler

        //3、通过发射调用handler的目标方法

    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
