package org.example;


import org.example.entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
@Component
public class Main {

    private final String url = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders headers = new HttpHeaders();


    public Main() {
        String sessionId = getAllUsers();
        headers.set("cookie", sessionId);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.createUser();
        main.updateUser();
        main.deleteUser(3L);
    }

    public String getAllUsers() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return String.join(";", Objects.requireNonNull(forEntity.getHeaders().get("set-cookie")));
    }

    public void createUser() {
        User user = new User("James","Brown", (byte) 41);
        user.setId(3L);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        String request = restTemplate.postForEntity(url, entity, String.class).getBody();
        System.out.println(request);
    }

    public void updateUser() {
        User user = new User("Thomas","Shelby", (byte) 27);
        user.setId(3L);
        HttpEntity<User> entity = new HttpEntity<>(user,headers);
        String request = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class).getBody();
        System.out.println(request);
    }

    public void deleteUser(@PathVariable Long id) {
        HttpEntity<User> entity = new HttpEntity<>(headers);
        String request = restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, entity, String.class).getBody();
        System.out.println(request);
    }
}