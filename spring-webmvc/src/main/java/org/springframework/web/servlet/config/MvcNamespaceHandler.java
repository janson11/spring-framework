/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.servlet.config;

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * {@link NamespaceHandler} for Spring MVC configuration namespace.
 * NamespaceHandler支持Spring MVC配置命名空间。
 *
 * @author Keith Donald
 * @author Jeremy Grelle
 * @author Sebastien Deleuze
 * @since 3.0
 */
public class MvcNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		// 注解驱动配置解析器
		registerBeanDefinitionParser("annotation-driven", new AnnotationDrivenBeanDefinitionParser());
		// 默认Servlet处理器解析器
		registerBeanDefinitionParser("default-servlet-handler", new DefaultServletHandlerBeanDefinitionParser());
		// 拦截器解析器
		registerBeanDefinitionParser("interceptors", new InterceptorsBeanDefinitionParser());
		// 资源解析器
		registerBeanDefinitionParser("resources", new ResourcesBeanDefinitionParser());
		// 视图解析器
		registerBeanDefinitionParser("view-controller", new ViewControllerBeanDefinitionParser());
		// 重定向视图控制器解析器
		registerBeanDefinitionParser("redirect-view-controller", new ViewControllerBeanDefinitionParser());
		// 状态视图控制器解析器
		registerBeanDefinitionParser("status-controller", new ViewControllerBeanDefinitionParser());
		// 视图解析器解析器
		registerBeanDefinitionParser("view-resolvers", new ViewResolversBeanDefinitionParser());
		// tiles视图解析器解析器
		registerBeanDefinitionParser("tiles-configurer", new TilesConfigurerBeanDefinitionParser());
		// freemarker视图解析器解析器
		registerBeanDefinitionParser("freemarker-configurer", new FreeMarkerConfigurerBeanDefinitionParser());
		// groovy视图解析器解析器
		registerBeanDefinitionParser("groovy-configurer", new GroovyMarkupConfigurerBeanDefinitionParser());
		// script模板视图解析器解析器
		registerBeanDefinitionParser("script-template-configurer", new ScriptTemplateConfigurerBeanDefinitionParser());
		// 跨域资源处理器解析器
		registerBeanDefinitionParser("cors", new CorsBeanDefinitionParser());
	}

}
