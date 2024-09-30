/*
 * Copyright 2002-2020 the original author or authors.
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

package org.springframework.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.core.annotation.AliasFor;

/**
 * Indicates that a method produces a bean to be managed by the Spring container.
 * 声明一个方法来产生一个bean，该bean将由Spring容器管理。
 *
 * <h3>Overview</h3>
 * 综述
 * <p>The names and semantics of the attributes to this annotation are intentionally
 * similar to those of the {@code <bean/>} element in the Spring XML schema. For
 * example:
 * 此注释的属性的名称和语义是有意的
 * *类似于Spring XML模式中的{@code <bean/>}元素的名称和语义。例如：
 *
 * <pre class="code">
 *     &#064;Bean
 *     public MyBean myBean() {
 *         // instantiate and configure MyBean obj
 *         // 实例化并配置MyBean对象
 *         return obj;
 *     }
 * </pre>
 *
 * <h3>Bean Names</h3>
 *  Bean名称集合
 *
 * <p>While a {@link #name} attribute is available, the default strategy for
 * determining the name of a bean is to use the name of the {@code @Bean} method.
 * This is convenient and intuitive, but if explicit naming is desired, the
 * {@code name} attribute (or its alias {@code value}) may be used. Also note
 * that {@code name} accepts an array of Strings, allowing for multiple names
 * (i.e. a primary bean name plus one or more aliases) for a single bean.
 * 当存在{@link #name}属性时，确定bean名称的默认策略是使用{@code @Bean}方法的名称。
 * 这很方便和直观，但是如果需要显式命名，则可以使用{@code name}属性（或其别名{@code value}）。
 * 请注意，{@code name}接受一个String数组，允许为单个bean提供多个名称（即主bean名称和一个或多个别名）。
 * 例如：
 *
 * <pre class="code">
 *     &#064;Bean({"b1", "b2"}) // bean available as 'b1' and 'b2', but not 'myBean'
 *     // 该bean可作为'b1'和'b2'，但不能作为'myBean'
 *     public MyBean myBean() {
 *         // instantiate and configure MyBean obj
 *         return obj;
 *     }
 * </pre>
 *
 * <h3>Profile, Scope, Lazy, DependsOn, Primary, Order</h3>
 *  配置文件、作用域、延迟、依赖、主、顺序
 *
 * <p>Note that the {@code @Bean} annotation does not provide attributes for profile,
 * scope, lazy, depends-on or primary. Rather, it should be used in conjunction with
 * {@link Scope @Scope}, {@link Lazy @Lazy}, {@link DependsOn @DependsOn} and
 * {@link Primary @Primary} annotations to declare those semantics. For example:
 *  注意，{@code @Bean}注释不提供配置文件、作用域、延迟、依赖或主属性。相反，应该与{@link Scope @Scope}、
 *   {@link Lazy @Lazy}、{@link DependsOn @DependsOn}和{@link Primary @Primary}注释一起使用，
 *    以声明这些语义。例如：
 *
 * <pre class="code">
 *     &#064;Bean
 *     &#064;Profile("production")
 *     &#064;Scope("prototype")
 *     public MyBean myBean() {
 *         // instantiate and configure MyBean obj
 *         return obj;
 *     }
 * </pre>
 *
 * The semantics of the above-mentioned annotations match their use at the component
 * class level: {@code @Profile} allows for selective inclusion of certain beans.
 * {@code @Scope} changes the bean's scope from singleton to the specified scope.
 * {@code @Lazy} only has an actual effect in case of the default singleton scope.
 * {@code @DependsOn} enforces the creation of specific other beans before this
 * bean will be created, in addition to any dependencies that the bean expressed
 * through direct references, which is typically helpful for singleton startup.
 * {@code @Primary} is a mechanism to resolve ambiguity at the injection point level
 * if a single target component needs to be injected but several beans match by type.
 * 这个语义与组件类级别的{@code @Profile}、{@code @Scope}、{@code @Lazy}、{@code @DependsOn}和{@code @Primary}注释的使用相匹配。
 * {@code @Profile}允许选择性地包含某些bean。{@code @Scope}将bean的作用域从单例更改为指定的作用域。
 * {@code @Lazy}仅在默认的单例作用域中才有实际效果。{@code @DependsOn}强制创建特定其他bean，
 * 这些bean在创建此bean之前将被创建，除了通过直接引用表达的bean的依赖，这通常有助于单例启动。
 * {@code @Primary}是一种机制，用于在注入点级别解决不明确性，如果需要注入单个目标组件，但多个bean匹配类型。
 * 例如：
 *
 * <p>Additionally, {@code @Bean} methods may also declare qualifier annotations
 * and {@link org.springframework.core.annotation.Order @Order} values, to be
 * taken into account during injection point resolution just like corresponding
 * annotations on the corresponding component classes but potentially being very
 * individual per bean definition (in case of multiple definitions with the same
 * bean class). Qualifiers narrow the set of candidates after the initial type match;
 * order values determine the order of resolved elements in case of collection
 * injection points (with several target beans matching by type and qualifier).
 * 另外，{@code @Bean}方法可以声明限定符注释和{@link org.springframework.core.annotation.Order @Order}值，
 * 这些注释将在注入点解析时考虑，就像相应的组件类上的注释一样，但可能非常具体地针对每个bean定义。
 * 限定符缩小了候选集，在初始类型匹配之后进行。顺序值确定了在集合注入点（有多个目标bean匹配类型和限定符）中解析元素的顺序。
 *
 *
 * <p><b>NOTE:</b> {@code @Order} values may influence priorities at injection points,
 * but please be aware that they do not influence singleton startup order which is an
 * orthogonal concern determined by dependency relationships and {@code @DependsOn}
 * declarations as mentioned above. Also, {@link javax.annotation.Priority} is not
 * available at this level since it cannot be declared on methods; its semantics can
 * be modeled through {@code @Order} values in combination with {@code @Primary} on
 * a single bean per type.
 * 注意，{@code @Order}值可能会影响注入点的优先级，但请注意，它们不影响单例启动顺序，这是一个与依赖关系和{@code @DependsOn}
 * 声明无关的关注点。此外，在此级别上不提供{@link javax.annotation.Priority}，因为它不能声明在方法上。
 * 其语义可以通过{@code @Order}值和{@code @Primary}注释的组合来建模。
 *
 *
 * <h3>{@code @Bean} Methods in {@code @Configuration} Classes</h3>
 * {@code @Bean} 方法在 {@code @Configuration} 类中
 *
 * <p>Typically, {@code @Bean} methods are declared within {@code @Configuration}
 * classes. In this case, bean methods may reference other {@code @Bean} methods in the
 * same class by calling them <i>directly</i>. This ensures that references between beans
 * are strongly typed and navigable. Such so-called <em>'inter-bean references'</em> are
 * guaranteed to respect scoping and AOP semantics, just like {@code getBean()} lookups
 * would. These are the semantics known from the original 'Spring JavaConfig' project
 * which require CGLIB subclassing of each such configuration class at runtime. As a
 * consequence, {@code @Configuration} classes and their factory methods must not be
 * marked as final or private in this mode. For example:
 * 经典的，{@code @Bean}方法通常在{@code @Configuration}类中声明。在这种情况下，bean方法可以直接调用同一类中的其他{@code @Bean}方法。
 * 这可以确保bean之间的引用是强类型的和可导航的。所谓的“bean间引用”是受到scoping和AOP语义的保证，就像{@code getBean()}查找一样。
 * 这些语义来自原始的“Spring JavaConfig”项目，要求运行时对每个这样的配置类进行CGLIB子类化。因此，{@code @Configuration}类及其工厂方法不得在这种模式下被标记为final或私有。
 * 例如：
 *
 * <pre class="code">
 * &#064;Configuration
 * public class AppConfig {
 *
 *     &#064;Bean
 *     public FooService fooService() {
 *         return new FooService(fooRepository());
 *     }
 *
 *     &#064;Bean
 *     public FooRepository fooRepository() {
 *         return new JdbcFooRepository(dataSource());
 *     }
 *
 *     // ...
 * }</pre>
 *
 * <h3>{@code @Bean} <em>Lite</em> Mode</h3>
 * {@code @Bean} <em>Lite</em> 模式
 *
 * <p>{@code @Bean} methods may also be declared within classes that are <em>not</em>
 * annotated with {@code @Configuration}. For example, bean methods may be declared
 * in a {@code @Component} class or even in a <em>plain old class</em>. In such cases,
 * a {@code @Bean} method will get processed in a so-called <em>'lite'</em> mode.
 * Bean方法可以在不带{@code @Configuration}注解的类中声明。例如，bean方法可以声明在{@code @Component}类或甚至是<em>普通老类</em>中。
 * 在这种情况下，将以一种称为“lite”模式处理{@code @Bean}方法。
 *
 * <p>Bean methods in <em>lite</em> mode will be treated as plain <em>factory
 * methods</em> by the container (similar to {@code factory-method} declarations
 * in XML), with scoping and lifecycle callbacks properly applied. The containing
 * class remains unmodified in this case, and there are no unusual constraints for
 * the containing class or the factory methods.
 * Bean方法在lite模式下将被容器视为普通的工厂方法（类似于XML中的{@code factory-method}声明）。
 * 作用域和生命周期回调适当地应用。在这种情况下，包含类保持不变，并且包含类或工厂方法的任何不寻常约束都不适用。
 *
 * <p>In contrast to the semantics for bean methods in {@code @Configuration} classes,
 * <em>'inter-bean references'</em> are not supported in <em>lite</em> mode. Instead,
 * when one {@code @Bean}-method invokes another {@code @Bean}-method in <em>lite</em>
 * mode, the invocation is a standard Java method invocation; Spring does not intercept
 * the invocation via a CGLIB proxy. This is analogous to inter-{@code @Transactional}
 * method calls where in proxy mode, Spring does not intercept the invocation &mdash;
 * Spring does so only in AspectJ mode.
 * 在与{@code @Configuration}类中的bean方法语义相比，lite模式不支持“bean间引用”。相反，当一个{@code @Bean}-方法调用另一个{@code @Bean}-方法
 * 通过CGLIB代理调用。这类似于inter-{@code @Transactional}方法在代理模式下调用，Spring不会拦截调用
 * lite模式下，调用是标准的Java方法调用；Spring不会通过CGLIB代理拦截调用。这类似于事务方法调用，在代理模式下，
 * Spring只在AspectJ模式下这样做。
 *
 * <p>For example:
 *
 * <pre class="code">
 * &#064;Component
 * public class Calculator {
 *     public int sum(int a, int b) {
 *         return a+b;
 *     }
 *
 *     &#064;Bean
 *     public MyBean myBean() {
 *         return new MyBean();
 *     }
 * }</pre>
 *
 * <h3>Bootstrapping</h3>
 * 引导过程
 *
 * <p>See the @{@link Configuration} javadoc for further details including how to bootstrap
 * the container using {@link AnnotationConfigApplicationContext} and friends.
 * 看一下@{@link Configuration} javadoc以获取进一步详细信息，包括如何使用{@link AnnotationConfigApplicationContext}等启动容器。
 *
 * <h3>{@code BeanFactoryPostProcessor}-returning {@code @Bean} methods</h3>
 * {@code BeanFactoryPostProcessor}-returning {@code @Bean} 方法
 *
 * <p>Special consideration must be taken for {@code @Bean} methods that return Spring
 * {@link org.springframework.beans.factory.config.BeanFactoryPostProcessor BeanFactoryPostProcessor}
 * ({@code BFPP}) types. Because {@code BFPP} objects must be instantiated very early in the
 * container lifecycle, they can interfere with processing of annotations such as {@code @Autowired},
 * {@code @Value}, and {@code @PostConstruct} within {@code @Configuration} classes. To avoid these
 * lifecycle issues, mark {@code BFPP}-returning {@code @Bean} methods as {@code static}. For example:
 * 特别注意，应对返回Spring {@link org.springframework.beans.factory.config.BeanFactoryPostProcessor BeanFactoryPostProcessor}
 * （BFPP）类型的{@code @Bean}方法。因为BFPP对象必须在容器生命周期的最早期实例化，它们可能干扰{@code @Configuration}类中类似于{@code @Autowired}、
 * {@code @Value}和{@code @PostConstruct}的注解的处理。为了避免这些生命周期问题，请将返回BFPP的{@code @Bean}方法标记为{@code static}。
 * 例如：
 *
 * <pre class="code">
 *     &#064;Bean
 *     public static PropertySourcesPlaceholderConfigurer pspc() {
 *         // instantiate, configure and return pspc ...
 *     }
 * </pre>
 *
 * By marking this method as {@code static}, it can be invoked without causing instantiation of its
 * declaring {@code @Configuration} class, thus avoiding the above-mentioned lifecycle conflicts.
 * Note however that {@code static} {@code @Bean} methods will not be enhanced for scoping and AOP
 * semantics as mentioned above. This works out in {@code BFPP} cases, as they are not typically
 * referenced by other {@code @Bean} methods. As a reminder, an INFO-level log message will be
 * issued for any non-static {@code @Bean} methods having a return type assignable to
 * {@code BeanFactoryPostProcessor}.
 * 通过将此方法标记为{@code static}，可以调用它而不会导致其声明的{@code @Configuration}类实例化，从而避免了上述提到的生命周期冲突。
 * 请注意，{@code static} {@code @Bean}方法不会受到上述所述的作用域和AOP语义的增强。在{@code BFPP}情况下，这很重要，因为它们通常不会被其他{@code @Bean}方法引用。
 * 作为提醒，对于任何非{@code static} {@code @Bean}方法，其返回类型可分配给{@code BeanFactoryPostProcessor}的日志消息将会被发出。
 *
 *
 * @author Rod Johnson
 * @author Costin Leau
 * @author Chris Beams
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 3.0
 * @see Configuration
 * @see Scope
 * @see DependsOn
 * @see Lazy
 * @see Primary
 * @see org.springframework.stereotype.Component
 * @see org.springframework.beans.factory.annotation.Autowired
 * @see org.springframework.beans.factory.annotation.Value
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {

	/**
	 * Alias for {@link #name}.
	 * <p>Intended to be used when no other attributes are needed, for example:
	 * {@code @Bean("customBeanName")}.
	 * @since 4.3.3
	 * @see #name
	 */
	@AliasFor("name")
	String[] value() default {};

	/**
	 * The name of this bean, or if several names, a primary bean name plus aliases.
	 * bean名称或多个名称的主bean名称加别名。
	 * <p>If left unspecified, the name of the bean is the name of the annotated method.
	 * If specified, the method name is ignored.
	 * 如果未指定，bean名称为注解方法的名称。如果指定，则忽略方法名称。
	 * <p>The bean name and aliases may also be configured via the {@link #value}
	 * attribute if no other attributes are declared.
	 * 这个bean的名称和别名也可以通过{@link #value}属性进行配置，如果没有其他属性声明。
	 * @see #value
	 */
	@AliasFor("value")
	String[] name() default {};

	/**
	 * Are dependencies to be injected via convention-based autowiring by name or type?
	 * <p>Note that this autowire mode is just about externally driven autowiring based
	 * on bean property setter methods by convention, analogous to XML bean definitions.
	 * <p>The default mode does allow for annotation-driven autowiring. "no" refers to
	 * externally driven autowiring only, not affecting any autowiring demands that the
	 * bean class itself expresses through annotations.
	 * @see Autowire#BY_NAME
	 * @see Autowire#BY_TYPE
	 * @deprecated as of 5.1, since {@code @Bean} factory method argument resolution and
	 * {@code @Autowired} processing supersede name/type-based bean property injection
	 */
	@Deprecated
	Autowire autowire() default Autowire.NO;

	/**
	 * Is this bean a candidate for getting autowired into some other bean?
	 * <p>Default is {@code true}; set this to {@code false} for internal delegates
	 * that are not meant to get in the way of beans of the same type in other places.
	 * @since 5.1
	 */
	boolean autowireCandidate() default true;

	/**
	 * The optional name of a method to call on the bean instance during initialization.
	 * Not commonly used, given that the method may be called programmatically directly
	 * within the body of a Bean-annotated method.
	 * <p>The default value is {@code ""}, indicating no init method to be called.
	 * @see org.springframework.beans.factory.InitializingBean
	 * @see org.springframework.context.ConfigurableApplicationContext#refresh()
	 */
	String initMethod() default "";

	/**
	 * The optional name of a method to call on the bean instance upon closing the
	 * application context, for example a {@code close()} method on a JDBC
	 * {@code DataSource} implementation, or a Hibernate {@code SessionFactory} object.
	 * The method must have no arguments but may throw any exception.
	 * <p>As a convenience to the user, the container will attempt to infer a destroy
	 * method against an object returned from the {@code @Bean} method. For example, given
	 * an {@code @Bean} method returning an Apache Commons DBCP {@code BasicDataSource},
	 * the container will notice the {@code close()} method available on that object and
	 * automatically register it as the {@code destroyMethod}. This 'destroy method
	 * inference' is currently limited to detecting only public, no-arg methods named
	 * 'close' or 'shutdown'. The method may be declared at any level of the inheritance
	 * hierarchy and will be detected regardless of the return type of the {@code @Bean}
	 * method (i.e., detection occurs reflectively against the bean instance itself at
	 * creation time).
	 * <p>To disable destroy method inference for a particular {@code @Bean}, specify an
	 * empty string as the value, e.g. {@code @Bean(destroyMethod="")}. Note that the
	 * {@link org.springframework.beans.factory.DisposableBean} callback interface will
	 * nevertheless get detected and the corresponding destroy method invoked: In other
	 * words, {@code destroyMethod=""} only affects custom close/shutdown methods and
	 * {@link java.io.Closeable}/{@link java.lang.AutoCloseable} declared close methods.
	 * <p>Note: Only invoked on beans whose lifecycle is under the full control of the
	 * factory, which is always the case for singletons but not guaranteed for any
	 * other scope.
	 * @see org.springframework.beans.factory.DisposableBean
	 * @see org.springframework.context.ConfigurableApplicationContext#close()
	 */
	String destroyMethod() default AbstractBeanDefinition.INFER_METHOD;

}
