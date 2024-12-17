package org.pag.api.factories;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.pag.api.dto.TaskStateDto;
import org.pag.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Component
public class TaskStateDtoFactory {

    TaskDtoFactory taskDtoFactory;

    public TaskStateDto makeTaskStateDto(TaskStateEntity entity) {

        return TaskStateDto.builder()

                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .leftTaskStateId(entity.getLeftTaskState().map(TaskStateEntity::getId).orElse(null))
                .rightTaskStateId(entity.getRightTaskState().map(TaskStateEntity::getId).orElse(null))
                .tasks(
                        entity
                                .getTasks()
                                .stream()
                                .map(taskDtoFactory::makeTaskDto)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
