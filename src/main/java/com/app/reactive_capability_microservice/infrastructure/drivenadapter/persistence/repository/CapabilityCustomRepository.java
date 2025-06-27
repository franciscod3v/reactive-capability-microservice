package com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.repository;

import com.app.reactive_capability_microservice.infrastructure.drivenadapter.persistence.entity.CapabilityEntity;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class CapabilityCustomRepository {

    private final DatabaseClient databaseClient;

    public CapabilityCustomRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Flux<CapabilityEntity> findPaginatedAndSorted(String sortBy, Boolean asc, int page, int size) {
        int offset = page * size;

        String sortField = validateSortField(sortBy);
        String orderClause = "ORDER BY " + sortField + (Boolean.TRUE.equals(asc) ? " ASC" : " DESC");

        String sql = """
        SELECT id, name, description
        FROM capabilities
        %s
        LIMIT :limit OFFSET :offset
    """.formatted(orderClause);

        return databaseClient.sql(sql)
                .bind("limit", size)
                .bind("offset", offset)
                .map((row, metadata) -> CapabilityEntity.builder()
                        .id(row.get("id", Long.class))
                        .name(row.get("name", String.class))
                        .description(row.get("description", String.class))
                        .build())
                .all();
    }

    private String validateSortField(String sortBy) {
        return switch (sortBy.toLowerCase()) {
            case "name" -> "name";
            case "description" -> "description";
            default -> "name";
        };
    }

}
