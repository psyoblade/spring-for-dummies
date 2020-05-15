package spring.ch08.interfaces;

public interface IChangeProfile {

    public void changeUsernameAndAge(String email, String username, int age) throws Exception;
    public int getAge(String email);
    public String getName(String email);

}
