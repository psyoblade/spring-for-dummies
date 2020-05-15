package spring.ch08.services;

import org.springframework.stereotype.Service;
import spring.ch08.interfaces.IChangeProfile;
import spring.ch08.repositories.ChangeProfileRepository;

@Service
public class ChangeProfileService implements IChangeProfile {

    ChangeProfileRepository changeProfileRepository;

    public ChangeProfileService(ChangeProfileRepository changeProfileRepository) {
        this.changeProfileRepository = changeProfileRepository;
    }

    @Override
    public void changeUsernameAndAge(String email, String username, int age) {
        changeProfileRepository.changeUsernameAndAge(email, username, age);
    }

    @Override
    public int getAge(String email) {
        return changeProfileRepository.getAge(email);
    }

    @Override
    public String getName(String email) {
        return changeProfileRepository.getName(email);
    }
}
