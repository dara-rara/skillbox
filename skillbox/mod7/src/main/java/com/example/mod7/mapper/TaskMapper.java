package com.example.mod7.mapper;

import com.example.mod7.adapter.web.dto.request.TaskRequest;
import com.example.mod7.adapter.web.dto.response.TaskResponse;
import com.example.mod7.domain.Task;
import com.example.mod7.domain.Status;
import com.example.mod7.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TaskMapper {

    TaskResponse toResponse(Task model);

    default Mono<TaskResponse> toMonoResponse(Mono<Task> model) {
        return model.map(this::toResponse);
    }

    default Task toTask(TaskRequest request, Instant createdAt) {
        var task = new Task();
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        if (request.getStatus().equals("TODO"))
            task.setStatus(Status.TODO);
        else if(request.getStatus().equals("DONE"))
            task.setStatus(Status.DONE);
        else task.setStatus(Status.IN_PROGRESS);
        task.setAuthorId(request.getAuthorId());
        task.setAssigneeId(request.getAssigneeId());
        task.setObserverIds(request.getObserverIds());
        return task;
    };

    default Task toTask(Task task, List<User> users) {
        final Map<String, User> usersMap = users.stream()
                .collect(toMap(User::getId, identity()));
        final Set<String> observerIds = task.getObserverIds();
        task.setAuthor(usersMap.get(task.getAuthorId()));
        task.setAssignee(usersMap.get(task.getAssigneeId()));
        task.setObservers(usersMap.keySet().stream()
                .filter(observerIds::contains)
                .map(usersMap::get).collect(toSet()));
        return task;
    }

    default Task toUpdatedTask(Task task, TaskRequest request) {
        task.setUpdatedAt(Instant.now());
        if (nonNull(request.getName())) {
            task.setName(request.getName());
        }
        if (nonNull(request.getDescription())) {
            task.setDescription(request.getDescription());
        }
        if (nonNull(request.getAuthorId())) {
            task.setAuthorId(request.getAuthorId());
        }
        if (nonNull(request.getAssigneeId())) {
            task.setAssigneeId(request.getAssigneeId());
        }
        if (nonNull(request.getStatus())) {
            task.setStatus(Status.valueOf(request.getStatus()));
        }
        if(!CollectionUtils.isEmpty(request.getObserverIds())) {
            task.setObserverIds(request.getObserverIds());
        }
        return task;
    }

}

