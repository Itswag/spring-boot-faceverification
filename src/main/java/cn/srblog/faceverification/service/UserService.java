package cn.srblog.faceverification.service;

import cn.srblog.faceverification.entity.User;
import cn.srblog.faceverification.repository.UserRepository;
import cn.srblog.faceverification.util.FaceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User login(String name,String password){
        return userRepository.findByUsernameAndPassword(name,password);
    }
    public User faceLogin(String BASE64){
        String token = FaceUtil.search(BASE64);
        if (StringUtils.isEmpty(token)){
            return null;
        }
        return userRepository.findByToken(token);
    }

    public boolean addFace(Integer userId,String BASE64){
        String token;
        User user = userRepository.findById(userId).get();
        if (StringUtils.isEmpty(user.getToken())){
            token = (UUID.randomUUID().toString()+userId).replace("-","_");
        }else{
            token = user.getToken();
        }
        if (FaceUtil.addUser(token, BASE64)){
            user.setToken(token);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public User register(User user){
       return userRepository.save(user);
    }

    public int isRegFace(Integer userId){
        int count = 0;
        User user = userRepository.findById(userId).get();
        String token = user.getToken();
        if (StringUtils.isEmpty(token)){
            return count;
        }
       return FaceUtil.faceGetlist(token);
    }

}
