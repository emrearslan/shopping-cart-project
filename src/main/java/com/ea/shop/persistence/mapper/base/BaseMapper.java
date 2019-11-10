package com.ea.shop.persistence.mapper.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public abstract class BaseMapper<Entity, Dto> {

    public abstract Entity toEntity(final Dto dto);

    public abstract Dto toDto(final Entity entity);

    public List<Dto> toDtoList(Iterable<Entity> entityList) {
        List<Dto> dtoList = new ArrayList<>();
        for (Entity entity : entityList) {
            dtoList.add(toDto(entity));
        }

        return dtoList;
    }

    public Set<Dto> toDtoSet(Iterable<Entity> entityList) {
        return new HashSet<Dto>(toDtoList(entityList));
    }

    public List<Entity> toEntityList(Iterable<Dto> dtoList) {
        List<Entity> entityList = new ArrayList<>();
        for (Dto dto : dtoList) {
            entityList.add(toEntity(dto));
        }

        return entityList;
    }

    public Set<Entity> toEntitySet(Iterable<Dto> dtoList) {
        return new HashSet<Entity>(toEntityList(dtoList));
    }


    public Function<Entity, Dto> toDtoPage() {
        return new Function<Entity, Dto>() {

            @Override
            public Dto apply(Entity entity) {
                return toDto(entity);
            }
        };
    }

}
