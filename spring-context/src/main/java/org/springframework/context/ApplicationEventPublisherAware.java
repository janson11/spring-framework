/*
 * Copyright 2002-2011 the original author or authors.
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

package org.springframework.context;

import org.springframework.beans.factory.Aware;

/**
 * Interface to be implemented by any object that wishes to be notified
 * of the ApplicationEventPublisher (typically the ApplicationContext)
 * that it runs in.
 * 任何对象都可以实现此接口，以便在运行时接收到ApplicationEventPublisher的通知（通常是ApplicationContext）。
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 1.1.1
 * @see ApplicationContextAware
 */
public interface ApplicationEventPublisherAware extends Aware {

	/**
	 * Set the ApplicationEventPublisher that this object runs in.
	 * 设置此对象运行的ApplicationEventPublisher。
	 * <p>Invoked after population of normal bean properties but before an init
	 * callback like InitializingBean's afterPropertiesSet or a custom init-method.
	 * 在正常bean属性填充之后但在初始化回调（如InitializingBean的afterPropertiesSet或自定义init-method）之前调用。
	 * Invoked before ApplicationContextAware's setApplicationContext.
	 * ApplicationContextAware的setApplicationContext之前调用。
	 * @param applicationEventPublisher event publisher to be used by this object 事件发布者，用于此对象
	 */
	void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher);

}
