package com.example.ChatApp.Infrastructure.Persistence.Conversation;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.ChatApp.Application.General.DTO.CursorBaseCodec;
import com.example.ChatApp.Domain.Conversation.ReadModel.MessageSearchItem;
import com.example.ChatApp.Domain.Conversation.ReadModel.MessageSearchResponse;
import com.example.ChatApp.Domain.Conversation.ReadModel.MessageSummary;
import com.example.ChatApp.Domain.Conversation.Repository.MessageQueryRepository;
import com.example.ChatApp.Domain.Conversation.ValueObject.ConversationId;
import com.example.ChatApp.Domain.User.ReadModel.UserSummary;
import com.example.ChatApp.Domain.User.Repository.QueryUserRepository;
import com.example.ChatApp.Domain.ValueObject.CursorBase;
import com.example.ChatApp.Infrastructure.Elasticsearch.MessageDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageQueryRepositoryImpl
        implements MessageQueryRepository {

    private final CursorBaseCodec cursorCodec;
    private final MessageJpaRepository jpaRepository;
    private final ElasticsearchOperations operations;
    private final QueryUserRepository queryUserRepository;

    @Override
    public List<MessageSummary> findByConversation(
            ConversationId conversationId,
            CursorBase cursor,
            int limit
    ) {

        String cursorLastId =
                cursor != null ? cursor.id() : null;

        Pageable pageable = PageRequest.of(0, limit);

        return jpaRepository.findByConversationCursor(
                        conversationId.value(),
                        cursorLastId,
                        pageable
                )
                .stream()
                .map(this::toSummary)
                .toList();
    }

    private MessageSummary toSummary(MessageRow row) {
        return new MessageSummary(
                row.getId(),
                row.getSenderId(),
                row.getContent(),
                row.getSentAt()
        );
    }

    public MessageSearchResponse searchMessages(
            String conversationId,
            String keyword,
            String lastId,
            int size
    ) {
        // 1. Build bool query
        Query query = Query.of(q -> q
                .bool(b -> {
                    b.must(m -> m.term(t -> t
                            .field("conversationId")
                            .value(conversationId)
                    ));

                    if (keyword != null && !keyword.isBlank()) {
                        b.must(m -> m.match(mt -> mt
                                .field("message")
                                .query(keyword)
                        ));
                    }
                    return b;
                })
        );

        // 2. Build native query với cursor pagination
        NativeQueryBuilder nativeQueryBuilder = NativeQuery.builder()
                .withQuery(query)
                .withSort(s -> s.field(f -> f
                        .field("id")
                        .order(SortOrder.Asc) // cursor paginate tốt hơn với ASC
                ))
                .withMaxResults(size);

        if (lastId != null && !lastId.isBlank()) {
            nativeQueryBuilder.withSearchAfter(List.of(lastId));
        }

        NativeQuery nativeQuery = nativeQueryBuilder.build();

        IndexCoordinates index = IndexCoordinates.of("indexes_messages");

        // 3. Thực hiện search
        SearchHits<MessageDocument> hits = operations.search(nativeQuery, MessageDocument.class, index);

        if (hits.getSearchHits().isEmpty()) {
            return MessageSearchResponse.empty();
        }

        // 4. Map hits → DTO
        List<MessageSearchItem> items = toItems(hits);

        // 5. Lấy cursor mới
        SearchHit<MessageDocument> lastHit = hits.getSearchHits().get(hits.getSearchHits().size() - 1);
        List<Object> nextCursor = lastHit.getSortValues();

        return new MessageSearchResponse(
                items,
                cursorCodec.encode(new CursorBase(
                        (String) nextCursor.get(0)
                ))
        );
    }

    private List<MessageSearchItem> toItems(SearchHits<MessageDocument> hits) {
        Set<String> senderIds = hits.getSearchHits().stream()
                .map(h -> h.getContent().getSenderId())
                .collect(Collectors.toSet());

        Map<String, UserSummary> userMap = queryUserRepository.getSummaries(senderIds);

        return hits.getSearchHits().stream()
                .map(hit -> {
                    MessageDocument doc = hit.getContent();
                    UserSummary user = userMap.get(doc.getSenderId());

                    return new MessageSearchItem(
                            doc.getId(),
                            doc.getConversationId(),
                            doc.getSenderId(),
                            user != null ? user.fullName() : null,
                            user != null ? user.avatar() : null,
                            doc.getMessage(),
                            Instant.ofEpochMilli(doc.getSentAt())
                    );
                }).toList();
    }
}
