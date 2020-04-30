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
> 이 장에서는 Dependency Injection 즉, 의존성 주입방식에 대해 이해하고, 실습합니다
> 터미널을 통해서 사용자를 생성하고 (중복인 경우 예외를 발생), 패스워드를 변경할 수 있는 서비스를 구현합니다

### 멤버를 관리하는 클래스
> Member, MemberDAO, WrongIdPasswordException

### 멤버 등록 서비스
> RegisterRequest, ResiterService, DuplicateMemberException

### 멤버 패스워드 변경 서비스
> ChangePasswordService, MemberNotFoundException


