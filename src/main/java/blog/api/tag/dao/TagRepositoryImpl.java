package blog.api.tag.dao;

import blog.api.tag.model.entity.QPostTagMapping;
import blog.api.tag.model.entity.QTag;
import blog.api.tag.model.entity.Tag;
import blog.api.tag.model.response.FrequentlyUsedTagsResponse;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class TagRepositoryImpl extends QuerydslRepositorySupport implements TagRepositoryCustom {

    QTag tag = QTag.tag;
    QPostTagMapping postTagMapping = QPostTagMapping.postTagMapping;

    public TagRepositoryImpl() {
        super(Tag.class);
    }

    @Override
    public List<FrequentlyUsedTagsResponse> getFrequentlyUsedTagsByLimit(long limit) {
        return from(postTagMapping)
                .select(
                        Projections.bean(FrequentlyUsedTagsResponse.class,
                                tag.name.as("name"),
                                postTagMapping.tag.count().as("count")))
                .join(postTagMapping.tag, tag)
                .groupBy(postTagMapping.tag)
                .orderBy(postTagMapping.tag.count().desc())
                .limit(limit)
                .fetch();
    }
}
