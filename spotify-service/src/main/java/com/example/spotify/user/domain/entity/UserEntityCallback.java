package com.example.spotify.user.domain.entity;

import org.springframework.data.relational.core.conversion.MutableAggregateChange;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;
import org.springframework.stereotype.Component;
import reactor.util.annotation.NonNull;

import java.util.UUID;

@Component
public class UserEntityCallback implements BeforeSaveCallback<User> {


    @Override
    @NonNull
    public User onBeforeSave(User aggregate, MutableAggregateChange<User> aggregateChange) {

        System.out.println("Callback chamado!");
        return (aggregate.getId() == null)
                ? aggregate.copyWithId(UUID.randomUUID())
                : aggregate;
    }
}
