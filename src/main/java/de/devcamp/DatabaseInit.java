package de.devcamp;

import de.devcamp.model.entity.Product;
import de.devcamp.model.entity.Role;
import de.devcamp.model.entity.User;
import de.devcamp.repository.ProductRepo;
import de.devcamp.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DatabaseInit implements ApplicationListener<ApplicationReadyEvent> {
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Product product1 = new Product("Product 1", "Description for Product 1");
        product1.setId(new ObjectId("62330f3d824b51421e4c3905"));
        Product product2 = new Product("Product 2", "Description for Product 2");
        product2.setId(new ObjectId("62330da1ec8ccc3dc2846671"));
        productRepo.save(product1);
        productRepo.save(product2);

        User reader = new User(new ObjectId("62330da1ec8ccc3dc2846671"), "user1", passwordEncoder.encode("password"), Set.of(new Role(Role.READER)), true);
        User writer = new User(new ObjectId("6233149261059d54b23d4ff8"), "user2", passwordEncoder.encode("password"), Set.of(new Role(Role.READER), new Role(Role.WRITER)), true);
        User admin = new User(new ObjectId("6233149461059d54b23d4ff9"), "user3", passwordEncoder.encode("password"), Set.of(new Role(Role.READER), new Role(Role.WRITER), new Role(Role.ADMIN)), true);
        User nobody = new User(new ObjectId("6233285e7202476ff9789008"), "user4", passwordEncoder.encode("password"), Set.of(), true);
        User admin2 = new User(new ObjectId("62332d3288311a2515ffae86"), "user5", passwordEncoder.encode("password"), Set.of(new Role(Role.ADMIN)), true);
        userRepo.save(reader);
        userRepo.save(writer);
        userRepo.save(admin);
        userRepo.save(nobody);
        userRepo.save(admin2);
    }
}
