# 스프링5 프로그래밍 입문
> [스프링5 프로그래밍 입문](http://www.yes24.com/Product/Goods/62268795) 내용을 읽고 실습하기 위한 레포지토리입니다


## Chapter 02. 스프링 시작하기
> 스프링은 Annotation, Xml 및 Groovy 등을 통해 객체 관리를 할 수 있는데 여기서는 AnnotationConfigApplication을 다룹니다
> 일반적인 방식은 아니지만 Spring 내부의 구조를 이해하기 위한 예제를 다음과 같이 작성합니다

### Configuration, Bean 어노테이션을 통해 Singleton 객체를 사용하는 방법
```java

@Configuration
class AppConfig {
    @Bean
    public MyObject newObject() {
        MyObject o = new MyObject();
        o.setName("new object");
        return o;
    }
}

class MyObject {
    private String name;
    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }
}

@Test
public void 같은객체() {
    AnnotationConfigApplicationContext appConfig = new AnnotationConfigApplicationContext(AppConfig.class);
    MyObject o1 = appConfig.getBean(MyObject.class);
    MyObject o2 = appConfig.getBean(MyObject.class);
    assertEquals(o1, o2);
}
```


## Chapter 03. 스프링 DI
> 이 장에서는 Dependency Injection 즉, 의존성 주입방식에 대해 이해하고, Assembler 를 통해 관리되는 어플리케이션을 실습합니다
> 이와 같은 구조는 실제 스프링 프레임워크에서 동작하는 것과 아주 유사하기 때문에 스프링의 이해에 도움이 됩니다
> 터미널을 통해서 사용자를 생성하고 (중복인 경우 예외를 발생), 패스워드를 변경할 수 있는 서비스를 구현합니다

### 1. 멤버관리 서비스 구현 예제

* 멤버를 관리하는 클래스
  * Member, MemberDAO, WrongIdPasswordException
* 멤버 등록 서비스
  * RegisterRequest, ResiterService, DuplicateMemberException
* 멤버 패스워드 변경 서비스
  * ChangePasswordService, MemberNotFoundException
* Aggregator 에서 관련작업을 대행
  * Assembler, MainForAssembler

### 2. 스프링의 DI 
> 스프링이 의존성을 어떻게 주입할 지에 대한 설정정보를 @Configuration 어노테이션을 통해서 수행합니다
> 어노테이션이 매겨진 내부에는 해당 객체를 생성하는 new 가 있어서 왜 singleton 으로 동작하는지 의아해 했는데 내부 호출 시에 new 를 통해 수행하지 않고 메소드나 별도의 name 을 통해 applicationContext 내부에 있는 캐시된 객체를 가져올 수 있기 때문이다. 즉, 설정해 두지 않았더라도 기본적으로 메소드 이름이 해당 Bean 객체의 이름이 되기 때문입니다.
> 특정 데이터 유형에 매칭되는 메소드가 하나 밖에 없다면 해당 메소드도 당연히 찾을 수 있어 @Autowired 가 왜 동작하는 지 알 수 있습니다
> 즉, 스프링은 설정된 클래스의 구현을 그대로 매번 호출하지 않으며, 캐싱된 객체를 사용하도록 프레임워크에서 돕고 있습니다

* 멤버 목록/정보를 출력하는 클래스를 구현하되 ApplicationContext 통해 getBean 을 이용해 구현해보자
  * MemberListService, MemberInfoService
* 예제로 잘 못 구현한 클래스가 MainForAutowired 라는 어플리케이셔인데 이는 ApplicationContex 생성시에 매개변수로 Configuration 을 넣어주지 못 했기 때문에 Autowired 되지 않는다
  * SpringBoot 에서는 ComponentScan 이라는 기법을 통해서 자동으로 찾아주기 때문에 동작하는 것이다
  * 제대로 동작하게 하기 위해서는 SpringBoot 의 ComponentScan 즉, SpringBootApplication 으로 구현하여 자동으로 찾게 만들고 ApplicationRunner 를 이용해서 구현하면 된다


## Chapter 04. 의존성 자동 주입
> 기존에 생성자를 통해 의존성을 주입 받았으나 @Autowired 를 통해 자동 주입받을 수 있게 되었습니다. 마찬가지로 setter 방식의 주입도 간결하게 변경되었습니다
> 동일한 유형의 빈이 2개가 존재한다면 상속의 경우도 하나로 받을 수 있기 때문에 반드시 빈의 이름을 명시해 주거나 Autowired 의 경우 @Qualifier 로 구분되어야만 합니다

### 1. @Autowired 를 통해 자동 주입되기 위한 조건
* @Autowired 가 붙은 변수 혹은 메소드에 해당하는 해당 Class 유형의 @Bean 이 존재해야만 한다
  * 해당 Bean 객체는 SpringContext 에서 찾을 수 있어야 하는데 AnnotationConfigSpringContext 의 경우 해당 @Configuration 설정이 된 Class 를 매개변수로 넘겨야만 한다
  * 이런 방식으로 Configuration 즉 설정된 Bean 들에 대해서만 스프링이 객체를 관리해 주게 되고 @Configuration 내부의 @Bean 메소드 혹은 객체를 통해 찾을 수 있습니다
  * 동일한 유형의 Bean 이 여러개 존재하는 경우 Autowire 될 수 없는데 이 때에는 ctx.getBean("name", class) 와 같이 이름을 명시해 주거나, @Bean 과 @Autowired 양쪽 모두 @Qualifier가 명시되어야 한다


## Chatper 05. 컴포넌트 스캔
> 컴포넌트 스캔이란? 기존의 Configuration 및 Import 를 통한 방식은 매번 등록해 주거나 관리해 주어야 하는 부담이 있으므로 Scan 하고 싶은 범위와 제외하고 싶은 조건을 통해 모든 Component 를 찾아주는 기능입니다
> 벌써 논의하기에는 이르지만 빈의 기본 설정은 singleton 이기 때문에 결국 빈으로 등록되는 클래스 혹은 객체는 상태가 없을 가능성이 높고 함수 덩어리라고 보아도 좋을 것 같다
> 즉, 자주 변하지 않으며 처음에 생성해 두면 좋을 것 같은 객체를 Bean 혹은 Component 로 두고, 상태를 가지는 경우는 별도로 구현하거나 Prototype 유형으로 관리되면 좋을 것 같다

### ComopnentScan 을 사용해보자
> 단순한 Caculator 서비스를 하나 만들어 main 함수에서 1 ~ 10 까지 더한 합을 출력하는 Spring Application 을 만들어봅니다

* 1. 각 컴포넌트에 @Component 달고, @Configuration 에서 @ComponentScan 달고, 해당 Configuration 물고 ApplicationContext 생성하면 @Autowired 먹을까?
  * 해당 컨텍스트를 통해 getBean 한 경우는 동작하지만, 그냥 @Autowired 한 경우는 NullPointerException 이 떨어지는데 왜 그럴까..?
* 2. 이미 ComponentScan 된 Bean 에 대해서 다시 Bean 선언하면 문제 없는지?
  * 같은 이름으로 생성한 경우 즉, Calculator -> calculator 로 메소드를 Bean 등록 시에는 완전히 동일하게 동작
  * 다른 이름으로 생성한 경우는 { expected single matching bean but found 2: calculator,getCalculator } 오류를 뱉어내고 같은 Bean 이 2개 있다는 오류가 나옴
* 3. ComponentScan 된 Bean 에 대해서 다른 이름으로 같은 반환값을 Bean 선언하면 어떻게 되는지?
  * @Bean calculator() 가 있는 상태에서 @Bean getCalculator() 선언은 2번과 같은 오류가 발생합니다
  * getBean 호출 시에 명시적으로 이름을 getBean("calculator", Calculator.class) 로 지정해야만 합니다


## Chapter 06. 빈 라이프사이클과 범위
> 스프링 내에서 빈 객체가 생성 및 소멸되기 전후에 메소드를 호출할 수 있고 이는 InitializingBean 및 DisposableBean 인터페이스를 구현함으로써 destroy() afterPropertiesSet() 메소드를 Override 할 수 있다
> 하지만 외부 라이브러리의 경우 직접 implementation 이 불가능하기 때문에 @Bean(initMethod="externalMethod", destroyMethod="destroyExternalBean") 을 통해 구현이 가능합니다


## Chapter 07. AOP 프로그래밍
> AOP 의 구현은 크게 아래의 3가지로 구분되는데 스프링의 경우는 3번째에 해당하는 proxy 를 통한 공통 기능을 구현할 수가 있도록 설계되어 있습니다. 즉, 런타임에 특정 함수 호출 시에 원래 메소드 대신 proxyMethod# 가 호출되고 해당 메소드 내에서 Before, After, Around 등의 Advice 를 통해 공통 메소드가 호출되며 joinPoint 를 통해 원래 메소드가 proceed 되고 Object 를 반환하게 됩니다
* 컴파일 시점에 공통 기능을 삽입하는 방법
* 클래스 로딩 시점에 바이트 코드에 공통 기능을 삽입하는 방법
* 런타임에 프록시 객체를 통해 공통기능을 삽입하는 방법


### 주의사항
* @Pointcut("execution(public * spring.ch07..\*(..))"과 같이 내가 포함된 package 내에 다시 포함하는 경우에 아래와 같은 오류가 발생할 수 있으므로 반드시 Aspect 클래스가 포함된 패키지를 제외한 나머지 패키지까지 내려가도록 설정해야만 합니다
```java
Caused by: java.lang.IllegalArgumentException: Pointcut is not well-formed: expecting '(' at character position 0
```
* @Bean 객체 반환시의 유형이 interface 인 경우 Aspect 의 경우 Proxy 객체를 바라보기 때문에 아래와 같이 오류가 발생할 수 있습니다 @EnableAspjectJAutoProxy(proxyTaretClass=true) 설정이 필요합니다
```java
Exception in thread "main" org.springframework.beans.factory.BeanNotOfRequiredTypeException: Bean named 'calculator' is expected to be of type 'spring.ch07.entities.RecursionCalculator' but was actually of type 'com.sun.proxy.$Proxy18'
```

## Chapter 08. DB 연동
> 데이터베이스 연동을 위해 DataSource 및 JdbcTemplate 을 이용하여 데이터베이스 명령을 수행합니다. 메이블 레포지로티의 [de.flapdoodle.embedded](https://mvnrepository.com/artifact/de.flapdoodle.embed)에 가보면 mongodb 및 각종 NoSQL 은 지원하지만 MySQL은 지원하지 않았고 하지만 운이 좋게도 flapdoodle의 embedded process 를 활용한 [wix/wix-embedded-mysql](https://mvnrepository.com/artifact/com.wix/wix-embedded-mysql) 이 있어 테스트할 수 있었습니다 [Unit Test using embedded MySQL in Java](https://medium.com/@takezoe/unit-test-using-embedded-mysql-in-java-24cfdf574a16) 를 참고하여 예제코드를 만들었습니다

* Bean 객체에서 사용하는 풀링 객체를 항상 풀로 되돌리기 위해서는 close 메소드를 호출해야 하는데, 외부 라이브러리의 경우에는 init, destroy 구현이 불가능하므로 destroyMethod 키워드를 이용합니다.
```java
@Bean(destroyMethod = "close")
public DataSource dataSource() {
    ...
    datasource.setTestWhileIdle(true);  // 커넥션 풀에 유휴 커넥션이 있는지 여부를 체크함 (노는 커넥션을 제거하기 위함)
    datasource.setMinEvictableIdleTimeMillis(1000 * 60 * 3);  // 최소 유휴 시간을 3분 : idle 옵션을 true 로 정하고 이 값을 정하면 3분이 지난 커넥션은 커넥션 풀에서 제거하고 idle 로 빠지게 된다
    datasource.setTimeBetweenEvictionRunsMillis(1000 * 10);  // 커넥션 풀의 커넥션 객체의 유휴 여부 체크를 10초에 한 번씩 한다
    ...
}
```
