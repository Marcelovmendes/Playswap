package com.example.spotify.user.domain.entity;

import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;
import reactor.util.annotation.NonNull;

@Component
public class UserEntityCallback implements BeforeConvertCallback<UserEntity> {

    @Override
    @NonNull
    public UserEntity onBeforeConvert(@NonNull UserEntity user) {
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
