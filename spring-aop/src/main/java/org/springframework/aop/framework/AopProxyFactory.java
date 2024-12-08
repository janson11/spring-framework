/*
 * Copyright 2002-2012 the original author or authors.
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

package org.springframework.aop.framework;

/**
 * Interface to be implemented by factories that are able to create
 * AOP proxies based on {@link AdvisedSupport} configuration objects.
 * 接口将由能够创建的工厂实现基于{@link AdvisedSupport}配置对象的AOP代理
 *
 * <p>Proxies should observe the following contract:
 * 代理应遵守以下协议|契约
 * <ul>
 * <li>They should implement all interfaces that the configuration
 * indicates should be proxied.
 * 1、它们应该实现配置声明需要代理的的所有接口
 * <li>They should implement the {@link Advised} interface.
 * 2、它们应该实现Advised接口。
 * <li>They should implement the equals method to compare proxied
 * interfaces, advice, and target.
 * 3、它们应该实现equals方法来比较代理接口、通知和目标
 * <li>They should be serializable if all advisors and target
 * are serializable.
 * 4、它们应该是可序列化的，如果所有的通知和目标都是可序列化的。
 * <li>They should be thread-safe if advisors and target
 * are thread-safe.
 * 5、如果通知和目标是线程安全的，那么它们应该是线程安全的。
 * </ul>
 *
 * <p>Proxies may or may not allow advice changes to be made.
 * If they do not permit advice changes (for example, because
 * the configuration was frozen) a proxy should throw an
 * {@link AopConfigException} on an attempted advice change.
 * 6、代理可能允许也可能不允许对通知进行更改。如果它们不允许更改建议(例如，因为。
 * * 配置被冻结)代理应该抛出一个{@link AopConfigException}尝试更改通知
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 */
public interface AopProxyFactory {

	/**
	 * Create an {@link AopProxy} for the given AOP configuration.
	 * @param config the AOP configuration in the form of an
	 * AdvisedSupport object
	 * @return the corresponding AOP proxy
	 * @throws AopConfigException if the configuration is invalid
	 */
	AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException;

}
