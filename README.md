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





