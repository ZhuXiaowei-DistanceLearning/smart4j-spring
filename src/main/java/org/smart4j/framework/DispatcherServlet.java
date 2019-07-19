package org.smart4j.framework;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.UploadHelper;
import org.smart4j.framework.utils.ArrayUtil;
import org.smart4j.framework.utils.CodecUtil;
import org.smart4j.framework.utils.JsonUtil;
import org.smart4j.framework.utils.ReflectionUtil;
import org.smart4j.framework.utils.RequestHelper;
import org.smart4j.framework.utils.StreamUtil;

/**
 * 请求转发器
 * 
 * @author zxw
 *
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		// 初始化相关Helple类
		HelperLoader.init();
		// 获取ServletContext对象(用于注册servlet)
		ServletContext servletContext = servletConfig.getServletContext();
		// 注册处理JSP的Servlet
		ServletRegistration jspServletRegistration = servletContext.getServletRegistration("jsp");
		jspServletRegistration.addMapping(ConfigHelper.getAppJspPath() + "*");
		// 注册处理静态资源的默认Servlet
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
		UploadHelper.init(servletContext);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取请求方法与请求路径
		String requestMethod = req.getMethod().toLowerCase();
		String requestPath = req.getPathInfo();
		// 如果是如下请求,则跳过
		if (requestPath.equals("/favicon.ico")) {
			return;
		}
		// 获取Action处理器
		Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
		// 判断是否能获取对应的handler,如果有就表示后台有此方法
		if (handler != null) { // 执行代理方法，通过类对象执行代理方法
			// 获取Controller类
			Class<?> controllerClass = handler.getControllerClass();
			// 通过类获取实例,实例和类存放在Bean的Map中
			Object controllerBean = BeanHelper.getBean(controllerClass);
			Param param;
			if (UploadHelper.isMultpart(req)) {
				param = UploadHelper.createParam(req);
			} else {
				param = RequestHelper.createParam(req);
			}
			// 创建请求参数对象,通过名称-值 存储前台传入的参数
//			Map<String, Object> paramMap = new HashMap<String, Object>();
			// 已过时
//			getAllArgument(req, paramMap);
			Object result;
			// 得到方法
			Method actionMethod = handler.getActionMethod();
			if (param.isEmpty()) {
				result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
			} else {
				// 得到方法的类型
				// 三个参数 对象,方法,参数,如果对象是代理对象,则执行切面代理
				result = ReflectionUtil.invokeMethod(controllerBean, actionMethod,param);
			}
			// 方法执行完后,得到返回值的类型 处理Acition方法返回值
			if (result instanceof View) {
				// 返回JSP页面
				handleViewResult((View) result, req, resp);
			} else if (result instanceof Data) {
				// 返回json数据
				handleDataResult((Data) result, resp);
			}
		}
	}

	private void handleDataResult(Data data, HttpServletResponse resp) throws ServletException, IOException {
		Object model = data.getModel();
		if (model != null) { // 设置返回相应类型 resp.setContentType("application/json"); // 设置编码
			resp.setCharacterEncoding("utf-8");
			PrintWriter writer = resp.getWriter(); // 转换成Json返回
			String json = JsonUtil.toJson(model);
			writer.write(json);
			writer.flush();
			writer.close();
		}
	}

	private void handleViewResult(View view, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = view.getPath();
		if (path.startsWith("/")) {
			resp.sendRedirect(req.getContextPath() + path);
		} else {
			Map<String, Object> model = view.getModel();
			for (Map.Entry<String, Object> entry : model.entrySet()) {
				req.setAttribute(entry.getKey(), entry.getValue());
			}
			req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
		}
	}

	// 获取前台传入的所有参数集合
	@Deprecated
	private void getAllArgument(HttpServletRequest req, Map<String, Object> paramMap) throws Exception {
		Enumeration<String> parameterNames = req.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = req.getParameter(paramName);
			paramMap.put(paramName, paramValue);
		}
		// URLDecoder 和 URLEncoder 用于完成普通字符串 和 application/x-www-form-urlencoded MIME
		// 字符串之间的相互转换。
		// 首先获取通过BufferReader获取流，在同过Decode进行解码，等到get方法中?xxx的中文参数
		String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
		if (StringUtils.isNotEmpty(body)) {
			// 将参数进行分割
			String[] params = StringUtils.split(body, "&");
			// 判断是否为空
			if (ArrayUtil.isNotEmpty(params)) {
				// 获取所有参数
				for (String param : params) {
					// 通过=号划分所有参数
					String[] array = StringUtils.split(param, "=");
					if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
						String paramName = array[0];
						String paramValue = array[1];
						paramMap.put(paramName, paramValue);
					}
				}
			}
		}
	}

}
