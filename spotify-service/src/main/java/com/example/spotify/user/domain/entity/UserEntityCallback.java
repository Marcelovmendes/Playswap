package com.example.spotify.user.domain.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;
import reactor.util.annotation.NonNull;

import java.util.UUID;

@Component
public class UserEntityCallback implements BeforeConvertCallback<User> {

    @Override
    @NonNull
    public User onBeforeConvert(@NonNull User user) {
        System.out.println("Callback chamado!");
/***
        if (user.getId() == null) {
            UUID newId = UUID.randomUUID();
            System.out.println("Generated new UUID: " + newId);
            return user.copyWithId(newId);
        }

        return user;
    }
 ***/

        return user;
    }
}
