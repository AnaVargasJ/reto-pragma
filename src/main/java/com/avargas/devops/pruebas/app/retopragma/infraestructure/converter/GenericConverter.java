package com.avargas.devops.pruebas.app.retopragma.infraestructure.converter;

import com.avargas.devops.pruebas.app.retopragma.infraestructure.FieldIgnore;
import com.avargas.devops.pruebas.app.retopragma.infraestructure.FieldMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
public class GenericConverter  {
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    public static <E, D> D mapEntityToDto(E entity, Class<D> dtoClass) {
        try {
            D dto = dtoClass.getDeclaredConstructor().newInstance();
            Map<String, String> fieldMapping = getFieldMappings(dtoClass);

            for (Field entityField : entity.getClass().getDeclaredFields()) {
                if (Modifier.isStatic(entityField.getModifiers()) ||
                        Modifier.isFinal(entityField.getModifiers()) ||
                        Collection.class.isAssignableFrom(entityField.getType()) ||
                        entityField.isAnnotationPresent(FieldIgnore.class)) {
                    continue;
                }

                entityField.setAccessible(true);
                String dtoFieldName = fieldMapping.getOrDefault(entityField.getName(), entityField.getName());

                try {
                    Field dtoField = dtoClass.getDeclaredField(dtoFieldName);
                    if (Modifier.isStatic(dtoField.getModifiers()) || Modifier.isFinal(dtoField.getModifiers())) {
                        continue;
                    }

                    dtoField.setAccessible(true);
                    Object value = entityField.get(entity);

                    if ("fechaNacimiento".equals(entityField.getName()) && value instanceof Date && dtoField.getType().equals(String.class)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("es-CO"));
                        value = sdf.format((Date) value);
                    }

                    dtoField.set(dto, value);

                } catch (NoSuchFieldException ignored) {
                    log.warn("Campo no encontrado en el DTO: " + dtoFieldName);
                }
            }

            return dto;

        } catch (Exception e) {
            log.error("Error detallado en mapEntityToDto", e);
            throw new RuntimeException("Error mapping entity to DTO: " + e.getMessage(), e);
        }
    }




    public static <D, E> E mapDtoToEntity(D dto, Class<E> entityClass) {
        try {
            E entity = entityClass.getDeclaredConstructor().newInstance();
            Map<String, String> fieldMapping = getFieldMappings(dto.getClass());

            for (Field dtoField : dto.getClass().getDeclaredFields()) {

                if (Modifier.isStatic(dtoField.getModifiers()) || Modifier.isFinal(dtoField.getModifiers())) {
                    continue;
                }
                dtoField.setAccessible(true);
                String entityFieldName = fieldMapping.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().equals(dtoField.getName()))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse(dtoField.getName());

                try {
                    Field entityField = entityClass.getDeclaredField(entityFieldName);
                    entityField.setAccessible(true);

                    Object value = dtoField.get(dto);

                    if (value != null &&
                            entityField.getType().equals(Date.class) &&
                            dtoField.getType().equals(String.class)) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                            sdf.setLenient(false);
                            value = sdf.parse((String) value);
                        } catch (ParseException ex) {
                            throw new RuntimeException("Formato de fecha inválido para el campo: " + dtoField.getName());
                        }
                    }
                    entityField.set(entity, value);
                } catch (NoSuchFieldException ignored) {
                    throw new RuntimeException("Campo no mapeado", ignored);
                }
            }
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping DTO to entity", e);
        }
    }

    private static Map<String, String> getFieldMappings(Class<?> clazz) {
        Map<String, String> mapping = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            FieldMapping annotation = field.getAnnotation(FieldMapping.class);
            if (annotation != null) {
                mapping.put(annotation.value(), field.getName());
            }
        }
        return mapping;
    }
}
